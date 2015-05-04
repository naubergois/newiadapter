<?php
$con = mysql_connect("127.0.0.1","root","");

if (!$con) {
  die('Could not connect: ' . mysql_error());
}

mysql_select_db("workload", $con);

$parts = parse_url($url);
parse_str($parts['query'], $query);




// Formulate Query
// This is the best way to perform a SQL query
// For more examples, see mysql_real_escape_string()



$query = sprintf("select distinct TESTPLAN from workload ORDER BY TESTPLAN ");


$result = mysql_query($query);
print "<head><link rel=\"stylesheet\" type=\"text/css\" href=\"table.css\"></head>";

print "<div class=\"centered\">";
print "<a href=\"inicio.php\" > <img src=\"iadapter.png\" align=\"middle\"></a>";
print "<a href=\"consultaAgents.php\">Agents</a>";
print "</div>";


print "<table class=\"gridtable\" align=\"center\">";
while ($row = mysql_fetch_assoc($result)) {
print "<TR>";
    $plan=$row['TESTPLAN'];
    print "<TD>".$plan."</TD><TD><a href=\"grafico.php?testplan=".$plan."\"><img src=\"graph.png\"/></a></td><TD><a href=\"consultaIndividuals.php?testplan=".$plan."\"><img src=\"dna.png\"/></a></td><TD><a href=\"deleteTestPlan.php?testplan=".$plan."\"><img src=\"delete.png\"/></a></td>";
     print "</TR>";
}
print "</table>";



mysql_close($con);
?>

