<?php

class DB {
  private static $db;
  private static $queries = array('fail' => 0, 'success' => 0);

  public static function user() {
    return isset($_SERVER['PHP_AUTH_USER']) ? $_SERVER['PHP_AUTH_USER'] : 'cli';
  }

  public static function log($t) {
    DB::logger("Manual logging: '$t'");
  }

  private static function logger($t) {
    $fp = fopen('log', 'a+');
    $user = DB::user();
    fwrite($fp, date(DATE_ATOM) . " ($user): $t\n");
    fclose($fp);
  }

  function __construct() {
    if (!isset(DB::$db)) {
      DB::logger("OPEN db");
      $cfg = Configuration::$database;
      DB::$db = mysqli_init();
      DB::$db->ssl_set(null, null, $cfg['cacert'], null, null);
      DB::$db->real_connect($cfg['server'], $cfg['user'], $cfg['password'], $cfg['database'], 3306, null, MYSQLI_CLIENT_SSL | MYSQLI_CLIENT_COMPRESS);

    } else {
      DB::logger("Reusing db connection");
    }

  }

  function close() {
    $db->close();
    DB::$db = null;
    DB::logger("CLOSE db");
  }

  function escape_string($string) {
    return DB::$db->escape_string($string);
  }

  function query($q) {
    DB::logger("query[$q]");
    $res = DB::$db->query($q);
    if (is_bool($res) || ($res && $res->num_rows > 0)) {
      self::$queries['success']++;
      return $res;
    }
    self::$queries['fail']++;
  }

  static function statistics() {
    return self::$queries;
  }
}

?>
