<?php

class DeviceType extends Entity {
  function entity_type() { return 'devicetype'; }

  static function find_by_id($id) {
    return new DeviceType($id);
  }

  function name() {
    return $this->res()->name;
  }

  function model() {
    return $this->res()->model;
  }

  function __tostring() {
    return $this->name();
  }

  function devices() {
    $res = $this->db->query("SELECT id FROM devices WHERE type = '{$this->id}';");
    $devices = array();
    while($obj = $res->fetch_object()) {
      $devices[] = Device::find_by_id($obj->id);
    }
    return $devices;
  }

  static function types() {
    $db = new DB();
    $res = $db->query("SELECT id FROM devicetypes");
    $types = array();
    while($obj = $res->fetch_object()) {
      $types[] = new DeviceType($obj->id);
    }
    return $types;
  }
}
?>
