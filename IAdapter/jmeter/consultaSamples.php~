<?php
$con = mysql_connect("127.0.0.1","root","");

if (!$con) {
  die('Could not connect: ' . mysql_error());
}

mysql_select_db("workload", $con);

$parts = parse_url($url);
parse_str($parts['query'], $query);


$firstname = $_GET["testplan"];
$secondname = $_GET["individual"];

// Formulate Query
// This is the best way to perform a SQL query
// For more examples, see mysql_real_escape_string()



$query = sprintf("select LABEL, RESPONSETIME from samples WHERE TESTPLAN='%s' AND INDIVIDUAL='%s' ORDER BY RESPONSETIME*1 DESC LIMIT 10 ",
    mysql_real_escape_string($firstname),mysql_real_escape_string($secondname));


$result = mysql_query($query);



$category = array();
$category['label'] = 'label';

$series1 = array();
$series1['responsetime'] = 'responsetime';




while ($row = mysql_fetch_assoc($result)) {

    $category['data'][] = $row['LABEL'];
    $series1['data'][] = $row['RESPONSETIME'];
    

}



$result1 = array();
array_push($result1,$category);
array_push($result1,$series1);



print json_encode($result1, JSON_NUMERIC_CHECK);

mysql_close($con);
?>

