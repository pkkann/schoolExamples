<?php

class EndpointController extends Controller {
  static function update($endpoint) {
    try {
      $endpoint->update($_POST['description']);
    } catch (Exception $e) {
      return parent::error($endpoint, 
			   "Cannot update endpoint: " . $e->getMessage());
    }
    return parent::status($endpoint, "Endpoint updated.");
  }

  static function delete($endpoint) {
    try {
      $port = $endpoint->port();
      $endpoint->delete();
    } catch (Exception $e) {
      return parent::error($endpoint, 
			   "Cannot delete endpoint: " . $e->getMessage());
    }
    return parent::status($port, "Endpoint deleted.");
  }

  static public function update_endpoint_form($port, $endpoint) {
    return Router::form_to($endpoint, 'update', 
	     array("<input type='text' name='description' value='" .
		   $endpoint->description() ."'>"));
  }

  static public function delete_endpoint($endpoint) {
    return Router::form_to($endpoint, 'delete');
  }
}
