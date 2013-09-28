<?php

class HTML {
  private static $shown_header = false;

  static function show_message($message, $failure) {
    if ($failure)
      $css = 'error';
    else
      $css = 'success';

    echo "<p id='$css'>$message</p>";
  }

  static function header() {
    if (self::$shown_header) {
      DB::log('Emitting header again ...');
      return;
    }

    $title = Configuration::$name . " v" . Configuration::$version;
    echo '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"';
    echo '  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">';
    echo '<html xmlns="http://www.w3.org/1999/xhtml">';
    echo '<head>';
    echo "<title>$title</title>";
    echo <<<EOT
<style type="text/css">
table {
  border-width: 1px 1px 1px 1px;
  border-spacing: 0px 0px;
  border-collapse: collapse;
}

#error {
  border-width: thin;
  border-style: dotted;
  border-color: red;
  background-color: pink;
  text-align: center;
}

#success {
  border-width: thin;
  border-style: dotted;
  border-color: green;
  background-color: LightGreen;
  text-align: center;
}

#msg {
  border-width: thin;
  border-style: dotted;
  border-color: blue;
  background-color: LightBlue;
  text-align: center;
}

#crumble {
  font-size: smaller;
}

#menu {
  border-bottom-style: solid;
  border-width: 1px;
  font-size: small;
  width: 100%;
  left: 0px;
}

.menu {
  display: inline;
}


.search_i {
  border: 1px solid grey;
  font-size: xx-small;
}

.search_s {
 border: 1px solid black;
 background: grey;
 font-size: xx-small;
}

</style>
</head>
<body>
EOT;

    self::$shown_header = true;
    View::menu(); //TODO: Move from View to somewhere else ...

  }
  
}