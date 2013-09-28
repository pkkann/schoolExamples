<?php

#Setup
ini_set("include_path", ".:models/:views/:controllers/");
error_reporting(E_ALL | E_STRICT);
ini_set('display_errors', '1');
date_default_timezone_set('Europe/Copenhagen');

function __autoload($class_name) {
    require_once $class_name . '.php';
}

#Run
Router::run();
?>

<?php

if (Configuration::$db_stats) {
  echo "<p id='stats'>";
  foreach(DB::statistics() as $k=>$v){
    echo "$k: $v<br/>";
  }
  echo "</p>";  
}

?>

</body>
</html>
