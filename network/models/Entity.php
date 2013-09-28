<?php

abstract class Entity {
  protected $id;
  protected $db;
  private $res;

  abstract function entity_type();
  protected function table() {
    return $this->entity_type() . 's';
  }

  function __construct($id) {
    $this->id = addslashes($id);
    $this->db = new DB();
  
    if (!$this->res())
      throw new Exception('This entity does not exist.');
  }

  protected function res() {
    if (!$this->res)
      $this->res = $this->db->query("SELECT * FROM {$this->table()} WHERE id = {$this->id}")->fetch_object();
    return $this->res;
  }

  protected function invalidate() {
    $this->res = null;
  }

  function id() {
    return $this->id;
  }

  static function compare($e1, $e2) {
    return get_class($e1) == get_class($e2) && $e1->id() == $e2->id();    
  }
}  

?>
