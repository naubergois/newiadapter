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



$query = sprintf("select NAME, RESPONSETIME, FIT from workload WHERE TESTPLAN='%s'",
    mysql_real_escape_string($firstname));


$result = mysql_query($query);



$category = array();
$category['name'] = 'name';

$series1 = array();
$series1['responsetime'] = 'responsetime';

$series2 = array();
$series2['fit'] = 'fit';


while ($row = mysql_fetch_assoc($result)) {

    $category['data'][] = $row['NAME'];
    $series1['data'][] = $row['RESPONSETIME'];
    $series2['data'][] = $row['FIT'];

}



$result1 = array();
array_push($result1,$category);
array_push($result1,$series1);
array_push($result1,$series2);


print json_encode($result1, JSON_NUMERIC_CHECK);

mysql_close($con);
?>
