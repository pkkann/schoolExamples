<?php

abstract class Controller  {
  protected $model;

  public function __construct($model) {
    $this->model = $model;
  }

  protected static function self_uri() {
    return $_SERVER['SCRIPT_NAME'] . '/';
  }

  protected static function error($object, $msg) {
    $a = self::status($object, $msg);
    $a['error'] = true;
    return $a;
  }

  protected static function status($object, $msg) {
    return array('entity' => $object->entity_type(),
		 'id' => $object->id(),
		 'request' => 'show',
		 'message' => $msg);
  }
}