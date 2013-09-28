<?php

class View {
  public static function menu() {
    echo "<div id='menu'>";
   
    echo Router::link_to('Device', 'overview', 'Devices') . ' ';
    echo Router::link_to('Port', 'closed_overview', 'Closed ports') . ' ';
    echo Router::link_to('Port', 'closed_history', 'Previously closed');
    
    foreach (array('device' => array('Switch',8), 
		   'endpoint' => array('Endpoint', 8), 
		   'vlan' => array('IP', 14))
	     as $k => $v) {
      echo "<form class='menu' method='get' " .
	"action='{$_SERVER['SCRIPT_NAME']}'>\n";
      echo "{$v[0]} ";
      echo "<input class='search_i' type='text' name='search' " .
	"size='{$v[1]}'/>\n";
      echo "<input type='hidden' name='entity' value='$k'/>\n";
      echo "<input class='search_s' type='submit' value='Search'/>\n";
      echo "</form>\n";
    }

    echo Router::link_to('Vlan', 'add', 'Add Vlan');
    echo ' ';
    echo Router::link_to('Port/current', 'show', 'Where am I?');

    echo "</div>";
  }
}