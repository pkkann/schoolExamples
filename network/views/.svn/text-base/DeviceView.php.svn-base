<?php

class DeviceView extends View {
  public static function show($device) {
    echo "<h2>{$device}</h2>\n";
    echo "<p>" . Router::link_to($device->devicetype());
    echo " with serial# {$device->serial()} at ";
    echo "{$device->mgmtip()} (uptime {$device->uptime()}) ";
    echo "running firmware {$device->firmware()} ";

    echo "(validate " . Router::link_to($device, 'validate');
    echo "/generate " . Router::link_to($device, 'generate') . ")";

    echo " located at " . Router::link_to($device, 'location', 
					  $device->location() ? 
					  $device->location() : 'unknown');

    echo ".</p>";
    
    echo '<table border="1">';
    echo '<tr><th>Port</th><th>tagged VLANs</th><th>untagged VLANs</th></tr>';

    foreach ($device->ports() as $port) {
      echo "<tr>";
      echo "<td>" . Router::link_to($port);
      if ($port->neighbour()) 
	echo " (&harr;" . Router::link_to($port->neighbour()) . ")";
      else if ($port->endpoint()) 
	echo " (&rarr;" . Router::link_to($port->endpoint()) . ")";
      if ($port->disabled()) 
        echo "<b>&darr;</b>";
      echo "</td><td>";
      echo implode(', ', array_map(array('Router', 'link_to'), 
				   $port->tagged_vlans()));
      echo "</td><td>";
      if ($port->untagged_vlan())
	echo Router::link_to($port->untagged_vlan());
      echo "</td></tr>";
    }
    echo '</table>';

    echo '<h3>IPs on VLANs</h3>';
    echo '<table border="1">';
    echo '<tr><th>VLAN</th><th>IP</th></tr>';
    foreach ($device->ips() as $ip => $vlan)
      echo "<tr><td>$vlan</td><td>$ip</td></tr>";
    echo '</table>';

    echo '<table border="1">';

    echo '<h3>Routes</h3>';
    echo '<ul>';
    foreach($device->routes() as $route) {
      echo "<li>{$route['ip']} / {$route['mask']} &rarr; {$route['nexthop']} ({$route['description']})</li>";
    }
    echo '</ul>';
  }

  public static function overview() {
    echo "<h2>Device Overview</h2>";
    
    printf("<table border='1'><tr>" . str_repeat("<th>%s</th>", 8) . "</tr>", 
	   "Device", "Location", "IP", "Model", "Boot", "Firmware", "Serial", 
	   "Uptime");
    $types = DeviceType::types();
    foreach($types as $type) {
      foreach($type->devices() as $device) {
        printf ("<tr>" . str_repeat("<td>%s</td>", 8) . "</tr>",
		Router::link_to($device), 
		$device->location(), 
		$device->mgmtip(), 
		Router::link_to($type),
		$device->bootrom(), 
		$device->firmware(), 
		$device->serial(), 
		$device->uptime()); 
      }
    }
    print "</table>";
  }

  public static function generate($device) {
    echo "<h2>$device</h2> " . 
      DeviceController::upload_button($device);
    
    echo "<pre>\n";
    foreach ($device->generate_config() as $o)
      echo "$o\n";

    echo "\n</pre>";

  }

  public static function validate($device) {
    echo "<h2>Status for ";
    echo Router::link_to($device);
    echo "</h2>";
    
    //TODO: Move DeviceValidator-stuff into Device
    $valid = $device->validate(Configuration::$verbose_validation);
    $neighbours = $device->check_neighbours();   
    
    if (empty($valid) && empty($neighbours)) {
      echo '<h4>The device is properly configured!</h4>';
    } else {
      echo '<h4>Non-compliant!</h4>';
      echo '<table border="1">';
      echo '<tr><th>Test</th><th>Status</th></tr>';

      foreach ($valid as $test) {
        echo "<tr><td>{$test['description']}</td>" .
	  "<td>{$test['result']}</td></tr>";
      }
      echo '</table>';

      echo '<h4>Mismatch with neighbours</h4>';
      echo '<ul>';
      foreach ($neighbours as $neighbour) {
        echo '<li>' . Router::link_to($neighbour) . " &harr; " . 
	  Router::link_to($neighbour->neighbour()) . '</li>';
      }
      echo '</ul>';
    }
  }

  public static function search($dev) {
    return Device::find_by_name($dev);
  }

  public static function location($device) {
    echo "<h2>Device $device</h2>";
    echo DeviceController::change_location_form($device);
  }
}
