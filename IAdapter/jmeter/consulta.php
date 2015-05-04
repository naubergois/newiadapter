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



$query = sprintf("select NAME, RESPONSETIME, FIT,PERCENT90,PERCENT80,PERCENT70 from workload WHERE TESTPLAN='%s' ORDER BY FIT*1 DESC ",
    mysql_real_escape_string($firstname));


$result = mysql_query($query);



$category = array();
$category['name'] = 'name';

$series1 = array();
$series1['responsetime'] = 'responsetime';
$series1['name']='max response time';

$series2 = array();
$series2['fit'] = 'fit';
$series2['name']='fit value';

$series3 = array();
$series3['percent90'] = 'percent90';
$series3['name']='90 percent line';

$series4 = array();
$series4['percent80'] = 'percent80';
$series4['name']='80 percent line';

$series5 = array();
$series5['percent70'] = 'percent70';
$series5['name'] = '70 percent line';



while ($row = mysql_fetch_assoc($result)) {

    $category['data'][] = $row['NAME'];
    $series1['data'][] = $row['RESPONSETIME'];
    $series2['data'][] = $row['FIT'];
    $series3['data'][] = $row['PERCENT90'];
    $series4['data'][] = $row['PERCENT80'];
    $series5['data'][] = $row['PERCENT70'];

}



$result1 = array();
array_push($result1,$category);
array_push($result1,$series1);
array_push($result1,$series2);
array_push($result1,$series3);
array_push($result1,$series4);
array_push($result1,$series5);


print json_encode($result1, JSON_NUMERIC_CHECK);

mysql_close($con);
?>
