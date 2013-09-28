<?php

class VlanController extends Controller {
  static function add_vlan_form() {
    return "<form method='POST' action='{$_SERVER['PHP_SELF']}'>"
      . "<input type='hidden' name='action' value='create'>"
      . "<input type='hidden' name='entity' value='vlan'>"
      . "VLAN: <input type='text' name='vlan_id'><br/>"
      . "Description: <input type='text' name='description'><br/>"
      . "IP-range: <input type='text' name='range'><br/>"
      . "<input type='submit' value='Create'>"
      . "</form>";
  }

  static function link_to_remove_vlan($vlan) {
    return Router::form_to($vlan, 'delete');
  }

  static function create() {
    $vlan_id = $_POST['vlan_id'];
    if (Vlan::create($_POST['vlan_id'], $_POST['range'], 
		     $_POST['description'])) {
      return parent::status(new VLAN($vlan_id), 'VLAN created succesfully.');
    } else {
      return array('message' => 'Error creating VLAN.', 'entity' => 'default',
		   'request' => 'show');
    }
  }

  static function delete($vlan) {
    if ($vlan->delete()) {
      return array('message' => "VLAN succesfully deleted.", 
		   'entity' => 'default', 'request' => 'show');
    } else {
      return parent::error($vlan, 
			   "Could not delete VLAN (is there ports in it?)");
    }
  }
}
