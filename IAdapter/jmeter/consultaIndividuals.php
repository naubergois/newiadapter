<?php
$con = mysql_connect("127.0.0.1","root","");

if (!$con) {
  die('Could not connect: ' . mysql_error());
}

mysql_select_db("workload", $con);

$parts = parse_url($url);
parse_str($parts['query'], $query);


$firstname = $_GET["testplan"];

// Formulate Query
// This is the best way to perform a SQL query
// For more examples, see mysql_real_escape_string()



$query = sprintf("select NAME, RESPONSETIME, FIT from workload WHERE TESTPLAN='%s' ORDER BY FIT*1 DESC",
    mysql_real_escape_string($firstname));


$result = mysql_query($query);


print "<link rel=\"stylesheet\" type=\"text/css\" href=\"table.css\">";

print "<div class=\"centered\"><a href=\"inicio.php\" > <img src=\"iadapter.png\" align=\"middle\"></a></div>";


print "<table class=\"gridtable\" align=\"center\">";
while ($row = mysql_fetch_assoc($result)) {
print "<tr>";
$name1=$row['NAME'];
$fit1=$row['FIT'];
print "<td><a href=\"graficoSamples.php?testplan=".${firstname}."&individual=".${name1}."\">".${name1}."</a></td><td>".${fit1}."</td>";   
print "</tr>";
}
print "</table>";

print "<form method=\"get\">";
print "Name <input type=\"text\" name=\"name\"><br/>";
print "Kind <input type=\"text\" name=\"kind\"><br/>";
print "Users <input type=\"text\"name=\"users\"><br/>";



mysql_close($con);
?>

