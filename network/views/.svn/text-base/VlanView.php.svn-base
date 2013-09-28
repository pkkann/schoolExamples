<?php

class VlanView extends View {
  public static function show($vlan) {
    echo "<h2>VLAN_{$vlan}</h2><p>"; 
    echo "IP-range: {$vlan->iprange()}<br/>";
    echo "Description: {$vlan->description()}<br/>";
    echo "</p>";
    echo VlanController::link_to_remove_vlan($vlan);
    echo "<ul>";
    
    foreach ($vlan->tagged_ports() as $p) {
      echo "<li>Port " . 
	Router::link_to($p) . 
	" on " . 
	Router::link_to($p->device()) .
	" (tagged)</li>";
    }
    
    foreach ($vlan->untagged_ports() as $p) {
      echo "<li>Port " . 
	Router::link_to($p) . 
	" on " . 
	Router::link_to($p->device()) .
	" (untagged)</li>";
    }
    echo "</ul>";
  }

  public static function search($ip) {
    return Vlan::find_by_ip($ip);
  }

  public static function overview() {
    $vlans = Vlan::vlans();

    echo "<h2>VLANs</h2>";
    echo "<table border='1'>";
    echo "<tr><th>VLAN</th><th>IP range</th><th>Description</th></tr>";
    foreach($vlans as $vlan) {
      echo "<tr><td>" . Router::link_to($vlan) . "</td><td>{$vlan->iprange()}" .
        "</td><td>{$vlan->description()}</td></tr>";
    }
    echo "</table>";
  }

  public static function add() {
    echo "<h2>Add VLAN</h2>";
    echo VlanController::add_vlan_form();
  }
}
