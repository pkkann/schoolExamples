<?php

class Endpoint extends Entity {
  function entity_type() { return 'endpoint'; }

  static function find_by_id($id) {
    return new Endpoint($id);
  }

  static function endpoints() {
    $db = new DB();
    $res = $db->query("SELECT id FROM endpoints");
    $endpoints = array();
    if ($res) {
      while($obj = $res->fetch_object()) {
        $endpoints[] = new Endpoint($obj->id);
      }
    }
    return $endpoints;
  }

  static function find_by_description($desc) {
    $desc = addslashes($desc);
    $db = new DB();
    $res = $db->query("SELECT id FROM endpoints WHERE description = '$desc'");
    if ($res) {
      return new Endpoint($res->fetch_object()->id);
    } else {
      throw new Exception('The Endpoint does not exist.');
    }
  }

  static function create($port, $description = '') {
    $db = new DB();
    if ($db->query("SELECT * FROM endpoints WHERE port = {$port->id()}")) {
      throw new Exception("This port already has an endpoint.");
    }
    $res = $db->query("INSERT INTO endpoints(port, description) VALUES({$port->id()}, '$description')"); 
    return new Endpoint($db->query("SELECT id FROM endpoints WHERE port = {$port->id()}")->fetch_object()->id);
  }

  function delete() {
    return $this->db->query("DELETE FROM endpoints WHERE id = {$this->id()}");
    $this->invalidate();
  }

  function update($description) {
    $description = addslashes($description);
    $status = $this->db->query("UPDATE endpoints SET description = '$description' WHERE id = {$this->id()}");
    $this->invalidate();
    return $status;
  }

  function port() {
    if ($this->res()->port) {
      return new Port($this->res()->port);
    }
  }

  function description() {
    $desc = trim($this->res()->description);
    return $desc ? $desc : '_'; //todo: Replace with something better
  }

  function __tostring() {
    return $this->description();
  }
}

?>
