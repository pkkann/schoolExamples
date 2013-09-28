<?php

class Dummy extends Device {
  function uptime(){ return; }
  function firmware(){ return; }
  function bootrom(){ return; }
  function get_vlans(){ return; }
  function get_untagged_vlans(){ return; }
  function get_tagged_vlans(){ return; }
  function get_ports_name(){ return; }
  function get_serial(){ return; }
  function get_name(){ return; }

  function generate_config() {
    throw new Exception('Generate not supported by Dummy.');
  }
}

?>