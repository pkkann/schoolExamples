<?php

class Configuration {
  public static $database = array('server' => 'mysql.aub.dk',
				  'database' => 'network',
				  'user' => 'network',
				  'password' => 'XBESqRc9z2GZvQht',
				  'cacert' => '/usr/local/etc/ssl/certs/cacert2.pem');
    
  public static $name = "mbhNetwork";
  public static $default_text = "<p>See the <a href='https://wiki.aub.dk/mbhResident'>documentation</a> for hints and tips.</p>";


  public static $logfile = 'log';

  public static $version = '0.5.3';

  public static $switch = array('contact' => 'help@aub.dk',
				'sntp_server' => '10.2.0.254',
				'log' => '10.2.0.254',
				'snmp_community' => 'public',
				'extra_vlans' => 8,
				'netmask' => '255.255.0.0',
				'mgmt_vlan' => 2,
				'default_vlan' => 1,
				'default_disabled_vlan' => '10',
				'domain' => 'switch.aub.dk',
				'timezone' => '60', // CET
				'daylight_savings' => 'Western-Europe',
				'dhcp_helpers' => array('10.0.0.2', '10.0.0.3'),
				'dhcp_option' => 'dhcp-relay option 82 append ip');

#
  
  public static $default_user = 'cli';

  public static $db_stats = false;

  #These must be lower case
  public static $visible_models = array('port', 'device', 'devicetype',
					'endpoint', 'vlan');
  public static $verbose_validation = false;

}

?>
