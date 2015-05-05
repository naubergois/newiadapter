<?php
$con = mysql_connect("127.0.0.1","root","");

if (!$con) {
  die('Could not connect: ' . mysql_error());
}

mysql_select_db("workload", $con);

$parts = parse_url($url);
parse_str($parts['query'], $query);


$page = $_SERVER['PHP_SELF'];
$sec = "10";
header("Refresh: $sec; url=$page");



// Formulate Query
// This is the best way to perform a SQL query
// For more examples, see mysql_real_escape_string()



$query = sprintf("select name, running, ip from agent");


$result = mysql_query($query);


print "<link rel=\"stylesheet\" type=\"text/css\" href=\"table.css\">";

print "<div class=\"centered\"><a href=\"inicio.php\" > <img src=\"iadapter.png\" align=\"middle\"></a></div>";


print "<table class=\"gridtable\" align=\"center\">";
while ($row = mysql_fetch_assoc($result)) {
print "<tr>";
$name1=$row['name'];
$running1=$row['running'];
$ip1=$row['ip'];
print "<td>".${name1}."</td><td>".${running1}."</td><td>".${ip1}."</td>";
print "</tr>";
}
print "</table>";

?>
