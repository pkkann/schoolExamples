<?php

class PortController extends Controller {
  static function link_to_remove_vlan($port, $vlan) {
    return Router::form_to($port, 'remove_vlan', 
	     array("<input type='hidden' name='vlan' value='{$vlan->id()}'>"));
  }

  static function add_vlan_form($port) {
    return Router::form_to($port, 'add_vlan',
			   array("VLAN <input type='input' name='vlan'>",
				 "<select name='tag'> ",
				 "<option value='1' selected>tagged</option>",
				 "<option value='0'>untagged</option>",
				 "</select>"));
  }

  static public function add_endpoint_form($port) {
    return Router::form_to($port, 'add_endpoint');
  }

  static function add_neighbour_form($port) {
    return Router::form_to($port, 'link',
	     array("Connect to port <input type='text' name='neighbour'/>"));

  }

  static function toggle_port($toggle, $port, $reason = '') {
    return Router::form_to($port, $toggle, 
			   array("Reason: <input type='text' name='reason' value='$reason'/>"));
  }


  static function add_vlan($port) {
    $vlan = new Vlan($_POST['vlan']);
    try {
      $port->add_vlan($vlan, $_POST['tag']);
    } catch (Exception $e) {
      return parent::error($port, "Adding VLAN failed: " . $e->getMessage());
    }
    return parent::status($port, "VLAN {$vlan->id()} added to port.");
  }

  static function remove_vlan($port) {
    $vlan = new Vlan($_POST['vlan']);
    try {
      $port->remove_vlan($vlan);
    } catch (Exception $e) {
      return parent::error($port, "Could not remove VLAN {$vlan->id()}: " . 
			   $e->getMessage());
    }
    return parent::status($port, "VLAN removed from port.");
  }

  static function add_endpoint($port) {
    try {
      $endpoint = Endpoint::create($port);
    } catch (Exception $e) {
      return parent::error($port, 
			   "Cannot add endpoint: " . $e->getMessage());
    }
    return parent::status($endpoint, 'Endpoint added.');
  }

  static function link($port) {
    $neighbour = new Port($_POST['neighbour']);
    try {
      $port->link_to_neighbour($neighbour);
    } catch (Exception $e) {
      return parrent::error($port, "Could not connect {$port->id()} to " .
			    "{$neighbour->id()}: " . $e->getMessage());
    }
    return parent::status($port, "Neighbour connected.");
  }

  static function unlink($port) {
    try {
      $port->disconnect_neighbour();
    } catch (Exception $e) {
      return parent::error($port, "Could not disconnect neighbour from " .
			   "{$port->id()}: " . $e->getMessage());
    }
    return parent::status($port, "Neighbour disconnected.");
  }

  static function disable($port) {
    try {
      $port->disable($_POST['reason']);
    } catch (Exception $e) {
      return parent::error($port, "Could not disable port: " .
			   $e->getMessage());
    }
    return parent::status($port, "Port disabled.");
  }

  static function enable($port) {
    try {
      $port->enable($_POST['reason']);
    } catch (Exception $e) {
      return parent::error($port ,"Could not enable port: " . 
			   $e->getMessage()) ;
    }
    return parent::status($port, "Port enabled.");
  }

  static function _current() {
    $ip = $_SERVER['REMOTE_ADDR'];
    $vlan = Vlan::find_by_ip($ip);
    $ports = $vlan->untagged_ports();
    if (count($ports) == 1) 
      return $ports[0];
    throw new Exception("Unknown port.");
  }
}
