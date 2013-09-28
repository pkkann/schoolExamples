<?php

class Router {
  /*
   * The routing is done rather simplistic. There are the following rules:
   * 1) POST requests are always for controllers.
   * 2) GET requests are always for views.
   */

  public static function default_route() {
    return array('entity' => 'index', 'request' => 'show');
  }

  private static function decode_entity($entity) {
    $first = substr($entity, 0, 1);
    $rest = substr($entity, 1);
    return strtoupper($first) . strtolower($rest);
  }

  private static function handle_post_request() {
    $entity = self::decode_entity($_POST['entity']);
    $first = substr($entity, 0, 1);
    $rest = substr($entity, 1);
    $entity = strtoupper($first) . strtolower($rest);
    $value = isset($_POST['id']) ? 
      call_user_func(array($entity, 'find_by_id'), $_POST['id']) : null;
    $callback = array("{$entity}Controller", $_POST['action']);
    if (is_callable($callback))
      return call_user_func($callback, $value);
    else
      return array('entity' => 'index', 'request' => 'show',
		   'error' => true, 'message' => 'Controller does not exist.');
  }

  /* 
   * TODO: We (temporarily) factor search requests our separately, even though 
   * they're conceptually very similar to ordinary requests except that they 
   * come in through a different vector and has no 
   */
  static function handle_search_request() {
    $entity = self::decode_entity($_GET['entity']);
    return array('entity' => $entity,
		 'request' => 'search',
		 'needle' => $_GET['search']);
  }

  private static function handle_get_request() {
    $name = $_SERVER['SCRIPT_NAME'];
    $uri = $_SERVER['REQUEST_URI'];
    DB::log("$name <-> $uri");
    $offset = strrpos($_SERVER['REQUEST_URI'], $name) + strlen($name);
    $uri = substr($uri, $offset);
  
    if (preg_match('%^/?$%', $uri)) {
      return self::default_route();
    } else if (preg_match('%^/+(\w+)/+(\d+)/+(\w+)/*\??.*$%', $uri, $matches)) {
      return array('entity' => $matches[1], 'id' => $matches[2], 
		   'request' => $matches[3]);
    } else if (preg_match('%^/+(\w+)/+(\w+)/+(\w+)/*\??.*$%', $uri, $matches)) {
      return array('entity' => $matches[1], 'noun' => $matches[2], 
		   'request' => $matches[3]);
    } else if (preg_match('%^/+(\w+)/+(\w+)/*\??.*$%', $uri, $matches)) {
      return array('entity' => $matches[1], 'request' => $matches[2]);
    } else if (preg_match('%^/+(\w+)/+search/+(\w+)/*\??.*$%', $uri, $matches)) {
      return array('entity' => $matches[1], 'request' => 'search',
		   'needle' => $matches[2]);
    } else {
      return array('entity' => 'index', 'request' => 'show', 'error' => true,
		   'message' => "Unknown route.");
    }
  }

  private static function decode_route() {
    if ($_SERVER['REQUEST_METHOD'] == 'POST')
      return self::handle_post_request();
    else if ($_SERVER['REQUEST_METHOD'] == 'GET' && isset($_GET['search']))
      return self::handle_search_request();
    else
      return self::handle_get_request();
  }

  private static function class_name($name) {
    if (!in_array(strtolower($name), Configuration::$visible_models))
      throw new Exception("Unknown class '$name'.");

    // (minor) hack
    if (strtolower($name) == 'devicetype')
      return 'DeviceType';

    $first = substr($name, 0, 1);
    $rest = substr($name, 1);
    return strtoupper($first) . strtolower($rest);
  }

  private static function crumble($object) {
    echo "\n\n<div id='crumble'>";
    echo "<a href='" . $_SERVER['SCRIPT_NAME'] . "'>Home</a> &gt;";
    if (is_object($object))  {
      echo Router::link_to($object->entity_type(), 'overview', 
			   $object->entity_type()) .
	" &gt;";
      echo Router::link_to($object);
    } else {
      echo Router::link_to($object, 'overview', "{$object}s"); 
    }
    echo "</div>\n";
  }

  static function run() {
    self::route(self::decode_route());
  }

  static function route($routing) {
    HTML::header();

    if (isset($routing['message'])) 
      HTML::show_message($routing['message'], isset($routing['error']));

    if ($routing['entity'] == 'index') {
      echo Configuration::$default_text;
      return;
    }

    try {
      $class = self::class_name($routing['entity']);

      if (isset($routing['id']))
	$value = call_user_func(array($class, 'find_by_id'), $routing['id']);
      else if (isset($routing['noun'])) {
        $callback = array("{$class}Controller", "_{$routing['noun']}");
	if (!is_callable($callback))
	  throw new Exception("Route does not exist.");
	$value = call_user_func($callback);
      }
      else if ($routing['request'] == 'search') {
	//TODO: hackish
	$callback = array("{$class}View", $routing['request']);
	if (!is_callable($callback))
	  throw new Exception("Route does not exist.");
	$value = call_user_func($callback, $routing['needle']);
	$routing['request'] = 'show';
      } else
	$value = null;

      $callback = array("{$class}View", $routing['request']);

      if (!is_callable($callback))
	throw new Exception("Route does not exist.");

      self::crumble($value ? $value : $class);
      call_user_func($callback, $value);

    } catch (Exception $e) {
      $r = self::default_route();
      $r['error'] = true;
      $r['message'] = $e->getMessage();
      self::route($r);
    }
  }

  static function form_to($object, $action, $fields = array()) {
    if (is_object($object))
      $entity = strtolower($object->entity_type());
    else 
      $entity = strtolower($object);

    $form = "<form method='POST' action='{$_SERVER['SCRIPT_NAME']}'>\n"
      . "<input type='hidden' name='entity' value='$entity'>\n";
    if (is_object($object))
      $form .= "<input type='hidden' name='id' value='{$object->id()}'>\n";
    $form .= "<input type='hidden' name='action' value='$action'>\n";
   
    foreach($fields as $field) 
      $form .= $field . "\n";

    $submit = str_replace('_', ' ', $action);
    $form .= "<input type='submit' value='$submit'>\n</form>\n";
    return $form;   
  }

  static function link_to($object, $mode = 'show', $title = null) {
    $base = $_SERVER['SCRIPT_NAME'];
    if (is_object($object)) {
      $entity = $object->entity_type();
      $id = $object->id();
    } else {
      $entity = strtolower($object);
      $id = '';
    }    

    if (!$title)
      $title = $object;

    //TODO: title/description for link
    $uri = "<a href='$base/$entity/$id/$mode'>$title</a>";
    // fixes occasional PHP misbehaviour on //
    return str_replace('//', '/', $uri); 
  }
}

?>
