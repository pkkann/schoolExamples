<?php

class EndpointView extends View {
  public static function show($endpoint) {
    $port = $endpoint->port();
    echo "<h2>" . Router::link_to($port->device()) . "  port " . 
      Router::link_to($port) . "</h2>";
    echo "<p>Connected to: {$endpoint->description()}</p>";
    echo EndpointController::update_endpoint_form($port, $endpoint);
    echo EndpointController::delete_endpoint($endpoint);
  }

  public static function search($end) {
    return Endpoint::find_by_description($end);
  }

  public static function overview() {
    $endpoints = Endpoint::endpoints();

    echo "<h2>Endpoints</h2>";
    echo "<ul>";
    foreach($endpoints as $endpoint) {
      echo "<li>";
      echo Router::link_to($endpoint);
      echo " (" . Router::link_to($endpoint->port());
      echo " @ " . Router::link_to($endpoint->port()->device()) . ")"; 
    }
    echo "</ul>";
  }
}