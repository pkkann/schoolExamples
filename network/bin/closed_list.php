<html>
<head>
  <title>Oversigt over lukkede porte</title>
</head>
<body>

<?php

ini_set("include_path", ".:..:../models/:../views/:../controllers/");

function __autoload($class_name) {
  require_once $class_name . '.php';
}

echo "<h2>Lukkede porte</h2>";

$ports = Port::find_closed_ports();
echo "<ul>";
foreach ($ports as $port) {
  $disabled = $port->disabled();
  echo "<li>" . $port->endpoint() . ": {$disabled['why']} ({$disabled['who']} {$disabled['when']})</li>";

}
echo "</ul>";
?>

<p>Kontakt <a href="mailto:help@aub.dk">Netværksgruppen</a>.</p>

<p align="right"><i>Generated: <?php echo date(DATE_RFC822); ?></i></p>

</body>
</html>
