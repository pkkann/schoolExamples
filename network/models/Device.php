<?php

abstract class Device extends Entity {
  function entity_type() { return 'device'; }

  static function find_by_id($id) {
    if (!is_numeric($id))
      throw new Exception('Non-numeric id.');

    $db = new DB();
    $res = $db->query("SELECT T.class FROM devicetypes T, devices D WHERE D.id = '$id' AND D.type = T.id");
    if (!$res) 
      throw new Exception("Unknown device type.");

    $class = $res->fetch_object()->class;
    return new $class($id);
  }

  static function find_by_name($name) {
    $name = addslashes($name);
    $db = new DB();
    $res = $db->query("SELECT id FROM devices WHERE name = '$name'");
    if ($res) {
      return Device::find_by_id($res->fetch_object()->id);
    } else {
      throw new Exception('The device does not exist.');
    }
  }
  
  function name() {
    return $this->res()->name;
  }

  function __tostring() {
    return $this->name();
  }

  function devicetype() {
    return new DeviceType($this->res()->type);
  }

  function serial() {
    return $this->res()->serial;
  }

  function mgmtip() {
    return $this->ip(new Vlan(Configuration::$switch['mgmt_vlan']));
  }

  function location() {
    return $this->res()->location;
  }

  function ip($vlan) {
    $res = $this->db->query("SELECT ip FROM vlans_devices WHERE device = {$this->id()} and vlan = {$vlan->id()}");

    if ($res)
      return $res->fetch_object()->ip;
  }

  function dhcp($vlan) {
    $res = $this->db->query("SELECT dhcp FROM vlans_devices WHERE device = {$this->id()} and vlan = {$vlan->id()}");

    if ($res)
      return $res->fetch_object()->dhcp;
  }
 
  function routes() {
    $res = $this->db->query("SELECT R.* FROM routes R, routes_devices RD WHERE RD.device = {$this->id()} AND RD.route = R.id");

    $routes = array();
    if ($res)
      while($obj = $res->fetch_object())
	$routes[] = array('ip' => $obj->ip, 
			  'mask' => $obj->mask,
			  'nexthop' => $obj->nexthop,
			  'description' => $obj->description);
    
    return $routes;
  }

  function modules() {
    $res = $this->db->query("SELECT module, number FROM modules WHERE device = {$this->id()}");

    $modules = array();
    if ($res)
      while($obj = $res->fetch_object())
	$modules[$obj->number] = $obj->module;

    ksort($modules);
    return $modules;
  }


  function ips() {
    $res = $this->db->query("SELECT vlan, ip FROM vlans_devices WHERE device = {$this->id()}");

    $a = array();
    if ($res)
      while($obj = $res->fetch_object())
	$a[$obj->ip] = new Vlan($obj->vlan);
    
    return $a;
  }

  function add_ip($vlan, $ip) {
    //TODO
  }

  function set_location($location) {
    if ($location)
      $location = '"' . $this->db->escape_string(trim($location)) . '"';
    else
      $location = 'NULL';

    $status = $this->db->query("UPDATE devices SET location = $location WHERE id = {$this->id()}");
    $this->invalidate();
    return $status;
    
  }

  function ports() {
    $res = $this->db->query("SELECT id FROM ports WHERE device = '{$this->id}';");
    $ports = array();
    while($obj = $res->fetch_object()) {
      $ports[] = new Port($obj->id);
    }
    return $ports;
  }

  function update_config($config_file) {
    exec("./bin/writeconf.pl {$this->name()}");
  }

  function check_neighbours() {
    $failed = array();
    foreach ($this->ports() as $port) 
      if (!$port->check_neighbour())
        $failed[] = $port;
    return $failed;    
  }

  abstract function uptime();
  abstract function firmware();
  abstract function bootrom();
  abstract function get_vlans();
  abstract function get_untagged_vlans();
  abstract function get_tagged_vlans();
  abstract function get_ports_name();
  abstract function get_serial();
  abstract function get_name();
  abstract function generate_config();

  protected static function valid($text, $v1, $v2, $verbose = false) {
    $verbose = Configuration::$verbose_validation; //hackish
    echo $verbose;
    if ($v1 != $v2) 
      return array('description' => $text, 
                   'success' => false,
                   'result' => "FAILED ('$v1' <> '$v2')");
    else if ($verbose)
      return array('description' => $text,
                   'success' => true,
                   'result' => "OK ('$v1')");
    return null;
  }

  //TODO: Refactor
  function validate($verbose = false) {
    $t = array();
    
    $t[] = self::valid("Device Name", 
		       "{$this->name()}." . Configuration::$switch['domain'], 
		       $this->get_name());
    
    $t[] = self::valid("Serial number", 
		       trim($this->serial()), $this->get_serial());

#Ports (check both inclusions; we check that they have identical 
#cardinality, then one inclusion follows from the other)
    $t[] = self::valid("Number of ports", 
		       count($this->ports()), 
		       count($this->get_ports_name()));
    
    $snmp_untagged = $this->get_untagged_vlans();
    $snmp_tagged = $this->get_tagged_vlans();
    $ports = $this->ports();
    foreach ($this->get_ports_name() as $port => $name) {
      $t[] = self::valid("Port name (port $port / $name)", 
			 $ports[$port - 1]->name(), $name); 
      
      $tagged_vlans = $ports[$port - 1]->tagged_vlans();
      $t[] = self::valid("Number of tagged VLANs on port $name ($port)", 
			 count($tagged_vlans), count($snmp_tagged[$port]));
      
      foreach($snmp_tagged[$port] as $vlan) {
	$t[] = self::valid("Tagged VLAN $vlan on port $name ($port)", 
			   in_array($vlan, $tagged_vlans), true);	
      }
      
      // The next two tests are intertwined in an ugly and hackish way
      $untagged_vlan = $ports[$port - 1]->untagged_vlan();
      $t[] = self::valid("Untagged VLAN status on port $name ($port)",
			 $untagged_vlan != null,
			 array_key_exists($port, $snmp_untagged));
      
      if (array_key_exists($port, $snmp_untagged))
	$t[] = self::valid("Untagged VLAN on port $name ($port)",
			   $untagged_vlan,
			   $snmp_untagged[$port]);
    }
    
    foreach ($t as $k=>$v)
      if(is_null($v))
	unset($t[$k]);
    return $t;
  }
}
?>