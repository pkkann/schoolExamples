<?php

class Procurve extends Device {
  private function get_vlan_names() {
    //TODO: refactor data structure so that there's only a single map that maps
    //to vlan name, tagged ports and untagged ports.
    $vlans = array();
    $vlan_names = array();
    foreach($this->ports() as $port) {
      foreach($port->tagged_vlans() as $vlan) {
        if (!array_key_exists($vlan->name(), $vlans)) 
          $vlans[$vlan->name()] = array();
        if (!array_key_exists('tagged', $vlans[$vlan->name()]))
          $vlans[$vlan->name()]['tagged'] = array();
         
        $vlans[$vlan->name()]['tagged'][] = $port;
        if ($vlan->description())
          $vlan_names[$vlan->name()] = $vlan->description();
        else
          $vlan_names[$vlan->name()] = "VLAN_{$vlan->name()}";
      }

      $vlan = $port->untagged_vlan();
      if ($vlan) {
        if (!array_key_exists($vlan->name(), $vlans)) 
          $vlans[$vlan->name()] = array();
        if (!array_key_exists('untagged', $vlans[$vlan->name()]))
          $vlans[$vlan->name()]['untagged'] = array();

        $vlans[$vlan->name()]['untagged'][] = $port;
        if ($vlan->description())
          $vlan_names[$vlan->name()] = $vlan->description();
        else 
          $vlan_names[$vlan->name()] = "VLAN_{$vlan->name()}";
      }
    }
    return array($vlans, $vlan_names);
  }

  function port_descriptions() {
    $ports = array();
    foreach($this->ports() as $port) {
      if ($port->neighbour())
	$name = $port->neighbour()->device()->name();
      else 
	$name = $port->endpoint();

      if ($name)
	$ports[$port->name()] = $name;
    }
    return $ports;
  }

  function generate_config() {
    $res = array();

    $cfg = Configuration::$switch;
    list($vlans, $vlan_names) = $this->get_vlan_names();

    $res[] = "; {$this->devicetype()->model()} (created by " .
      Configuration::$name . " on " . date(DATE_RFC2822) . ")";

    $res[] = "hostname \"{$this->name()}.{$cfg['domain']}\"";
    $res[] = "max-vlans " . (count($vlans) + $cfg['extra_vlans']);
    $res[] = "time timezone {$cfg['timezone']}";
    $res[] = "time daylight-time-rule {$cfg['daylight_savings']}";
    $res[] = "sntp server {$cfg['sntp_server']}";
    $res[] = "timesync sntp";
    $res[] = "sntp unicast";
    $res[] = "logging {$cfg['log']}";
    $res[] = 'snmp-server community "public" Unrestricted';
    $res[] = "snmp-server contact \"{$cfg['contact']}\"";

    if ($this->location())
      $res[] = "snmp-server location \"{$this->location()}\"";

    foreach ($this->modules() as $index => $module)
      $res[] = "module $index type $module";

    foreach ($this->port_descriptions() as $port => $description)
      $res[] = "interface $port\n  name \"$description\"\nexit";

    $dhcp_enabled = false;
    foreach($vlans as $vlan => $tags) {
      $res[] = "vlan $vlan";
      if (array_key_exists($vlan, $vlan_names))
        $res[] = "  name \"{$vlan_names[$vlan]}\"";
      foreach ($tags as $tag => $ports)
        $res[] = "  $tag " . implode(',', $ports);

      $v = new Vlan($vlan);
      $ip = $this->ip($v);
      //Todo: Replace this hackery with something decent.
      $range = substr($v->iprange(), -2);
      if ($range == 24)
	$mask = '255.255.255.0';
      else if ($range == 16)
	$mask = '255.255.0.0';

      $res[] = $ip ? "  ip address $ip $mask" : "  no ip address";
      if ($this->dhcp($v)) {
	$dhcp_enabled = true;
	foreach($cfg['dhcp_helpers'] as $dhcp)
	  $res[] = "  ip helper-address $dhcp";
      }

      #Compulsory hack to avoid switches adding ports to the default VLAN
      #if there are only tagged ports in the configuration.
      if ($vlan == $cfg['default_vlan']) {
        $no_untag = array();
        foreach($this->ports() as $port) {
          $untags = $vlans[$vlan]['untagged'];
          if (!in_array($port, $untags)) {
            $no_untag[] = $port;
          }
        }
        if (!empty($no_untag))
          $res[] = "  no untagged " . implode(',', $no_untag);
      }

      $res[] = "  exit";
    }

    $res[] = "ip ssh";
    $res[] = "ip ssh filetransfer";
    $res[] = "management-vlan {$cfg['mgmt_vlan']}";
    $res[] = "password manager";
    if ($dhcp_enabled)
      $res[] = $cfg['dhcp_option'];

    if ($this->routes())
      $res[] = "ip routing";
    foreach($this->routes() as $route) {
      $res[] = "ip route {$route['ip']} {$route['mask']} {$route['nexthop']}";
    }

    return $res;
  }

  protected function snmp_get($oid) {
    $i = snmp_set_quick_print(1);
    $retval = snmpget($this->mgmtip(), Configuration::$switch['snmp_community'],
                      $oid);
    snmp_set_quick_print($i);
    return $retval;
  }

  protected function snmp_walk($oid) {
    $i = snmp_set_quick_print(1);
    $retval = snmpwalkoid($this->mgmtip(), 
			  Configuration::$switch['snmp_community'], $oid);
    snmp_set_quick_print($i);
    return $retval;
  }

  protected function snmp_get_str($oid) {
    $r = $this->snmp_get($oid);
    return trim(str_replace('"', '', $r));
  }

  function uptime() {
    return $this->snmp_get('1.3.6.1.2.1.1.3.0');
  }
 
  function firmware() {
    return $this->snmp_get_str('1.3.6.1.2.1.47.1.1.1.1.10.1');
  }

  function bootrom() {
    return $this->snmp_get_str('1.3.6.1.2.1.47.1.1.1.1.9.1');
  }

  public function get_vlans() {
    $vlans_oid = "1.3.6.1.2.1.17.7.1.4.3.1.1";

    $res = array();
    foreach($this->snmp_walk($vlans_oid) as $key => $value) {
      $b = split("\.", $key);
      $res[end($b)] = str_ireplace(array("STRING: ", '"'), '', $value);
    }
    
    return $res;
  }
  
  protected function get_default_vlans() {
    $vlans_oid = "1.3.6.1.2.1.17.7.1.4.5.1.1";
    
    $res = array();
    foreach($this->snmp_walk($vlans_oid) as $key => $value) {
      $b = split("\.", $key);
      $res[end($b)] = str_ireplace(array("GAUGE32: ", '"'), '', $value);
    }
    
    return $res;
  }

  #PortList as defined in Q-BRIDGE-MIB
  protected static function PortList_to_binary_string($hexmask) {
    $pairs = explode(' ', $hexmask);
 
    $array = array();
    $s = "";
    foreach($pairs as $hex) {
      $n = sprintf("%'08b", hexdec($hex));
      $s .= $n;
    }
    return $s;
  }

  protected static function binary_string_to_array($s) {
    $array = array();
    $ss = str_split($s);
    for($i=0; $i<count($ss); $i++)
      if ($ss[$i] == '1')
        $array[] = $i+1;

    return $array;
  }

  public function get_untagged_vlans() {
    $oid = "1.3.6.1.2.1.17.7.1.4.3.1.4";

    $port_untagged_vlan = array();
    foreach($this->snmp_walk($oid) as  $key => $value) {
      $mask = str_ireplace(array("HEX-STRING: ", '"'), '', $value);
      $b = split("\.", $key);
      $vlan = end($b);
      $ports = self::binary_string_to_array(self::PortList_to_binary_string($mask), 64);
      foreach($ports as $port)
        $port_untagged_vlan[$port] = $vlan;
    }
    return $port_untagged_vlan;
  }

  protected function get_vlans_on_ports() {
    $oid = "1.3.6.1.4.1.11.2.14.11.5.1.9.16.1.1.3";
    
    $res = array();
    foreach($this->snmp_walk($oid) as $key => $value) {
      if ($value == 2) {
	$b = split("\.", $key);
	$port = end($b);
	$vlan = prev($b);
	if (!array_key_exists($port, $res)) {
	  $res[$port] = array();
	}
	$res[$port][] = $vlan;
      }
    }
    return $res;
  }
  
  public function get_tagged_vlans() {
    $vlans_on_ports = $this->get_vlans_on_ports();
    $untagged_vlans = $this->get_default_vlans();
	
    foreach($vlans_on_ports as $port => $vlans) {
      $key = array_search($untagged_vlans[$port], $vlans);
      if($key !== false) {
	unset($vlans_on_ports[$port][$key]);
      }
    }
    return $vlans_on_ports;
  }
  
  protected function is_port($port) {
    $oid = ".1.3.6.1.2.1.2.2.1.3";
    return ($this->snmp_get_str("$oid.$port") == 'ethernetCsmacd');
  }
  
  public function get_ports_name() {
    $oid = "1.3.6.1.2.1.2.2.1.2";
    
    $res = array();
    foreach($this->snmp_walk($oid) as $key => $value) {
      $splits = split('\.', $key);
      $port = end($splits);
      if ($this->is_port($port)) {	
	$res[$port] = str_ireplace(array("STRING: ", '"'), '', $value);
      }
    }
    return $res;
  }
  
  protected function get_ports_alias() {
    $oid = "1.3.6.1.2.1.31.1.1.1.18";
    
    $res = array();
    foreach($this->snmp_walk($oid) as $key => $value) {
      $port = end(split("\.", $key));
      if ($this->is_port($device, $port)) {	
	$res[$port] = str_ireplace(array("STRING: ", '"'), '', $value);
      }
    }
    return $res;
  }

  public function get_serial() {
    return $this->snmp_get_str("1.3.6.1.2.1.47.1.1.1.1.11.1");
  }
  
  public function get_name() {
    return $this->snmp_get_str("1.3.6.1.2.1.67.1.2.1.1.2.0");
  }

}

?>
