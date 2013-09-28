<?php

class PortView extends View {
  private static function port_desc($port) {
    return "Port $port on " . Router::link_to($port->device());
  }
 
  public static function show($port) {
    echo "<h2>" . self::port_desc($port) . "</h2>";
    	
    $state = !$port->disabled() ? 'enable' : 'disable';
    echo "<p>Port is {$state}d. "; 
    $toggle = $port->disabled() ? 'enable' : 'disable';
    echo Router::link_to($port, $toggle, $toggle);
    
    if ($port->neighbour()) {
      $ne = $port->neighbour();
      echo "<form method='POST' action='{$_SERVER['PHP_SELF']}'>";
      echo "<p>Port connected to " . Router::link_to($ne->device()) . 
	" port " . Router::link_to($ne);
      echo " (neighbour is " . ($port->check_neighbour() ? 'correctly' : 'incorrectly') . " configured)";
      echo " <input type='submit' value='disconnect'>"
        . "<input type='hidden' name='entity' value='port'>"
        . "<input type='hidden' name='action' value='unlink'>"
        . "<input type='hidden' name='port' value='{$port->id()}'>";
      echo ".</form>";
    } else if ($port->endpoint()) {
      $ep = $port->endpoint();
      echo "<p>Port connected to endpoint " . Router::link_to($ep) . ".</p>";
    } else {
      echo PortController::add_neighbour_form($port);
      echo PortController::add_endpoint_form($port);
    }
    
    echo "<h3>VLANs</h3>\n<ul>";
    foreach ($port->tagged_vlans() as $v) {
      echo "<li>" . Router::link_to($v) . " (tagged) "
        . PortController::link_to_remove_vlan($port, $v) 
        . "</li>\n";
    }

    $v = $port->untagged_vlan();
    if ($v) {
      echo "<li>" . Router::link_to($v) . " (untagged) ";
      if ($port->disabled()) 
	echo "<i>port is disabled</i>";
      else
        echo PortController::link_to_remove_vlan($port, $v); 
      echo "</li>\n";
    }
    echo "</ul>";

    if (!$port->disabled()) {
      echo "<h3>Add VLAN to port</h3>";
      echo PortController::add_vlan_form($port);
    }

    echo "<p>" .  Router::link_to($port, 'history', 'history') . "</p>";
  }

  private static function show_history($hist) {
    echo "<table border='1'>\n";
    echo "\t<tr><th>Endpoint</th><th>Port</th><th>When</th><th>Who</th><th>Why</th>" .
      "<th>Moved to</th></tr>\n";
    foreach ($hist as $h) {
      $p = new Port($h['port']);
      echo "\t<tr><td>" . $p->endpoint() . "</td><td>" . Router::link_to($p) . "</td>" .
	"<td>{$h['changed']}</td><td>{$h['who']}</td>" .
	"<td>{$h['why']}</td><td>";
      echo $h['forced_vlan'] ? 
	Router::link_to(new Vlan($h['forced_vlan'])) : "default";
      echo "</td>\n";
    }
    echo "</table>\n";
  }

  static function history($port) {
    echo "<h2>" . self::port_desc($port) . "</h2>";

    echo "<h3>Port history</h3>";
    $hist = $port->history();
    if (!empty($hist)) {
      self::show_history($hist);
    } else { 
      echo "No history for port. It's always been enabled.";
    }
  }

  public static function enable($port) {
    echo "<h2>" . self::port_desc($port) . "</h2>";
    echo "<h3>Enable port</h3>"; 
    $reason = "";
    echo PortController::toggle_port('enable', $port, $reason);
  }

  public static function disable($port) {
    echo "<h2>" . self::port_desc($port) . "</h2>";
    echo "<h3>Disable port</h3>"; 
    $reason = isset($_GET['reason']) ? $_GET['reason'] : '';
    echo PortController::toggle_port('disable', $port, $reason);
  }

  public static function closed_overview() {
    echo "<h2>Closed ports</h2>";

    $ports = Port::find_closed_ports();
    echo "<ul>";
    foreach ($ports as $port) {
      $disabled = $port->disabled();
      echo "<li>" . $port->device()->name() . "/" . Router::link_to($port) . " (" . $port->endpoint() . "): " 
	. $disabled['why'] . "</li>";  
    }
    echo "</ul>";
  }

  public static function closed_history() {
    echo "<h2>Previously closed ports</h2>";

    self::show_history(Port::closed_history());
  }

  public static function overview() {
    $ports = Port::ports();
    echo "<h2>Ports</h2>";

    echo "<ul>";

    foreach($ports as $port) {
      echo "<li>" . Router::link_to($port);
      if ($port->neighbour()) 
	echo " (&harr;" . Router::link_to($port->neighbour()) . ")";
      else if ($port->endpoint()) 
	echo " (&rarr;" . Router::link_to($port->endpoint()) . ")";
      if ($port->disabled()) 
        echo "<b>&darr;</b>";
      echo " @ " . Router::link_to($port->device());

      echo "</li>\n";
    }
    echo "</ul>";
  }
}
