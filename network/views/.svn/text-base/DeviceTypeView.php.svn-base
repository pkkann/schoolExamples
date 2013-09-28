<?php

class DeviceTypeView extends View {
  public static function show($type) {
    echo "<h2>{$type->name()} ({$type->model()})</h2>";
    
    echo '<ul>';
    foreach ($type->devices() as $device) {
      echo '<li>' . Router::link_to($device) . '</li>';
    }
  }

  public static function overview() {
    $types = DeviceType::types();
    echo "<h2>Device types</h2>";
    echo "<ul>";
    foreach($types as $type)
      echo "<li>" . Router::link_to($type) . "</li>";
    echo "</ul>";

  }
}
