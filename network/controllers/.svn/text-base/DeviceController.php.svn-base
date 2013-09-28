<?php

class DeviceController extends Controller {
  static public function upload_button($device) {
    return Router::form_to($device, 'upload');
  }

  static function upload($device) {
    try {
      $cfg = $device->generate_config();
      $cfgfile = fopen("configs/$device", 'w');
      
      foreach ($cfg as $cfgline)
	fwrite($cfgfile, $cfgline . "\n");
      
      fclose($cfgfile);
      
      $device->update_config("configs/$device");
    } catch (Exception $e) {
      return parent::error($device, 
			   "Cannot upload config: " . $e->getMessage());
    }
    return parent::status($device, "Config uploaded.");
  }

  static public function change_location_form($device) {
    return Router::form_to($device, 'set_location', 
			   array("<input type='text' name='location' value='" .
				 $device->location() ."'>"),
			   'change location');
  }

  static public function set_location($device) {
    try {
      $device->set_location($_POST['location']);
    } catch (Exception $e) {
      return parent::error($endpoint, 
			   "Cannot update location: " . $e->getMessage());
    }
    return parent::status($device, "Location updated.");
  }

}
?>
