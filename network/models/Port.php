<?php

class Port extends Entity {
  function entity_type() { return 'port'; }

  static function find_by_id($id) {
    return new Port($id);
  }

  static function find_closed_ports() {
    $db = new DB();
$res = $db->query(<<<EOT
SELECT	port
FROM 	ports_status P
WHERE	forced_vlan IS NOT NULL
  AND   NOT EXISTS (SELECT * FROM ports_status S WHERE P.port = S.PORT AND S.changed > P.changed)
EOT
);

  if (!$res)
    return array();

  $ports = array();
  while($obj = $res->fetch_object())
    $ports[] = new Port($obj->port);
  return $ports;
  }

  function neighbour() {
    if ($this->res()->link) {
      return new Port($this->res()->link);
    }
  }

  function disconnect_neighbour() {
    $n = $this->neighbour();
    if (!$n)
      throw new Exception('Port has no neighbour');
    $this->db->query("UPDATE ports as P, ports as N SET P.link = null, N.link = null WHERE P.id = {$this->id} AND N.id = {$n->id()}");
    $this->invalidate();
  }

  function link_to_neighbour($port) {
    if ($port == $this)
      throw new Exception("You cannot link a port to itself.");
    if ($this->neighbour())
      throw new Exception("This port is already connected to {$this->neighbour()}");
    if ($port->neighbour()) 
      throw new Exception("The other port is already connected to {$port->neighbour()}");

    $this->db->query("UPDATE ports as P, ports as N SET P.link = {$port->id()}, N.link = {$this->id} WHERE P.id = {$this->id} AND N.id = {$port->id()}");
    $this->invalidate();
  }

  function check_neighbour() {
    #Logic: A switch with a neighbour should exchange vlans (i.e., they should
    #be tagged) if they are tagged at either. If either have tagged vlans, 
    #neither should have untagged vlans (needs db/config clean-up; todo).
    $neighbour = $this->neighbour();
    if ($neighbour) {
      if ($this->untagged_vlan() != $neighbour->untagged_vlan())
	return false;
      $my_vlans = $this->tagged_vlans();
#      $my_vlans[] = $this->untagged_vlan();
      $his_vlans = $neighbour->tagged_vlans();
#      $his_vlans[] = $neighbour->untagged_vlan();
      if (count($my_vlans) != count($his_vlans))
        return false;
      return count(array_udiff($my_vlans, $his_vlans, "Entity::compare")) == 0; 
    } else {
      #If port has no neighbours, it shouldn't have tagged ports either.
#      return count($this->tagged_vlans()) == 0;#nb: doesn't work for spare uplink ports
       return true;
    }

  }
    
  function device() {
    return Device::find_by_id($this->res()->device);
  }

  function name() {
    return $this->res()->name;
  }

  function __tostring() {
    return $this->name();
  }

  function endpoint() {
    $q = $this->db->query("SELECT id FROM endpoints WHERE port = '{$this->id}'");
    if ($q) {
      return new Endpoint($q->fetch_object()->id);
    }
  }

  private function get_vlans($tag) {
    $res = $this->db->query("SELECT vlan FROM vlans_ports WHERE port = '{$this->id}' AND tagged = '$tag'");
    $vlans = array();
    if ($res) {
      while($obj = $res->fetch_object()) {
        $vlans[] = new Vlan($obj->vlan);
      }
    }
    return $vlans;
  }

  function untagged_vlan() {
    $disabled = $this->disabled();
    if ($disabled)
      return new Vlan($disabled['vlan']);

    $vlans = $this->get_vlans(0);
    if ($vlans)
      return $vlans[0];
  }

  function tagged_vlans() {
    if ($this->disabled())
      return array();

    return $this->get_vlans(1);
  }

  function remove_vlan($vlan) {
    if ($this->disabled())
      throw new Exception("Port is disabled.");

#    exec("./bin/change_untagged_vlan.pl {$this->device()->mgmtip()} {$this->name()} {$this->untagged_vlan()} 1");
    $this->db->query("DELETE FROM vlans_ports WHERE port = '{$this->id()}' AND vlan = '{$vlan->id()}'");
  }

  function add_vlan($vlan, $tagged) {
    if ($this->disabled())
      throw new Exception("Port is disabled.");

    if ($this->db->query("SELECT * FROM vlans_ports WHERE vlan = '{$vlan->id()}' AND port = '{$this->id()}")) {
      throw new Exception('Port is already a member of this Vlan');
    }

    if ($tagged == 0 && 
        $this->db->query("SELECT * FROM vlans_ports WHERE port = '{$this->id()}' AND tagged = '0'")) {
      throw new Exception('Port is already untagged in another Vlan');
    }
    
    if (!$this->db->query("INSERT INTO vlans_ports(vlan,port,tagged) VALUES({$vlan->id()}, {$this->id()}, $tagged)")) {
      throw new Exception('Could not update database');
    }

#    if ($tagged == 0)
#      exec("./bin/change_untagged_vlan.pl {$this->device()->mgmtip()} {$this->name()} {$this->untagged_vlan()}");
  }

  function disabled() {
    $res = $this->db->query("SELECT * FROM ports_status WHERE port = '{$this->id()}' ORDER BY changed DESC LIMIT 1");

    if (!$res) #default is enabled
	return null;

    $obj = $res->fetch_object();
    if ($obj->forced_vlan)
      return array('vlan' => $obj->forced_vlan, 'when' => $obj->changed, 'who' => $obj->who, 'why' => $obj->why);
  }
 
  private function move_port($vlan, $reason) {
    $reason = $this->db->escape_string($reason);
    $user = DB::user();
    $this->db->query("INSERT INTO ports_status(port, changed, who, why, forced_vlan) VALUES('{$this->id()}', NOW(), '$user', '$reason', $vlan);");
  }

  private function set_untagged_vlan() {
    DB::log("Setting untagged vlan on {$this->device()->name()} port {$this->name()} to {$this->untagged_vlan()}");
    exec("./bin/change_untagged_vlan.pl {$this->device()->mgmtip()} {$this->name()} {$this->untagged_vlan()}");
  }

  function enable($reason) {
    if (!$this->disabled())
      throw new Exception("Port is already enabled.");
    if (trim(!$reason)) 
      throw new Exception("You must give a reason to enable the port.");

    $this->move_port('NULL', $reason);
 
    $this->set_untagged_vlan();
  }

  function disable($reason) {
    if ($this->disabled())
      throw new Exception("Port is already disabled.");
    if (count($this->tagged_vlans()) > 0)
      throw new Exception("Cannot disable uplink ports (ports with tagged Vlans).");
    if (trim(!$reason)) 
      throw new Exception("You must give a reason to disable the port.");

    $this->move_port(Configuration::$switch['default_disabled_vlan'], 
                     trim($reason));

    $this->set_untagged_vlan();
  }

  function history() {
    $hist = array();
    if ($res = $this->db->query("SELECT * FROM ports_status WHERE port = {$this->id()} ORDER BY changed DESC"))

    while($obj = $res->fetch_array())
	$hist[] = $obj;
    return $hist;
  }

  static function closed_history() {
    $db = new DB();
    $hist = array();
    if ($res = $db->query("SELECT * FROM ports_status ORDER BY changed DESC"))
      while($obj = $res->fetch_array())
	$hist[] = $obj;
    return $hist;
  }

  static function ports() {
    $db = new DB();
    $res = $db->query("SELECT id FROM ports");
    $ports = array();
    if ($res) {
      while($obj = $res->fetch_object()) {
        $ports[] = new Port($obj->id);
      }
    }
    return $ports;
  }
}

?>
