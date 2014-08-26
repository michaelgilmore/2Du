<?php
$dbhost = '';
$dbuser = '';
$dbpass = '';
$conn = mysqli_connect($dbhost, $dbuser, $dbpass);
// Check connection
if (mysqli_connect_errno()) {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$sql = 'SELECT text,created_date,status,updated_date FROM tudus';

mysqli_select_db($conn, 'gilmorec_pda');
$retval = mysqli_query( $conn, $sql );
if(! $retval )
{
  die('Could not get data: ' . mysql_error());
}

echo "<table border=1>\n";
echo "<tr><th>text</th><th>status</th><th>created</th><th>updated</th></tr>\n";
while($row = mysqli_fetch_array($retval)) {
  echo "<tr>\n";
  echo "<td>".$row['text']."</td><td>".$row['status']."</td><td>".$row['created_date']."</td><td>".$row['updated_date']."</td>";
  echo "</tr>\n";
}
echo "</table>\n";

mysqli_close($conn);
?>