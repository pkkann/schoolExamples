<?php

class Vlan extends Entity {
  static function find_by_ip($ip) {
    if (preg_match('/10\.(\d+)\.(\d+)\.(\d+)/', $ip, $match)) {
      return self::find_by_range("10.{$match[1]}.{$match[2]}.0/");
    }
  }

  static function find_by_id($id) {
    return new Vlan($id);
  }

  static function find_by_range($range) {
    $range = addslashes($range);
    $db = new DB();
    $res = $db->query("SELECT id FROM vlans WHERE iprange LIKE '$range%'");
    if ($res) {
      return new Vlan($res->fetch_object()->id);
    } else {
      throw new Exception('The vlan does not exist.');
    }
  }

  static function vlans() {
    $db = new DB();
    $res = $db->query("SELECT id FROM vlans");
    $vlans = array();
    if ($res) {
      while($obj = $res->fetch_object()) {
        $vlans[] = new Vlan($obj->id);
      }
    }
    return $vlans;
  }

  static function create($vlan, $range, $description) {
    $db = new DB();
    $vlan = addslashes($vlan);
    $range = addslashes($range);
    $description = addslashes($description);
    return $db->query("INSERT INTO vlans(id, range, description) VALUES('$vlan', '$range', '$description');");
      
  }

  function delete() {
    if (!$this->tagged_ports() && !$this->untagged_ports()) {
      $db = new DB();
      return $db->query("DELETE FROM vlans WHERE id = '{$this->id()}';");
    }
    $this->invalidate();
  }

  function entity_type() { return 'vlan'; }

  function name() {
    return $this->id();
  }

  function __tostring() {
    return $this->name();
  }

  function iprange() {
    return $this->res()->iprange;
  }

  function description() {
    return $this->res()->description;
  }

  private function get_tags($tag) {
    $res = $this->db->query("SELECT port FROM vlans_ports WHERE vlan = '{$this->id}' AND tagged = '$tag'");
    $ports = array();
    if ($res) {
      while($obj = $res->fetch_object()) {
	$ports[] = new Port($obj->port);
      }
    }
    return $ports;
  }

  function tagged_ports() {
    return $this->get_tags('1');
  }

  function untagged_ports() {
    return $this->get_tags('0');
  }
}
?>
