<?php 
$dbhost = ';
$dbuser = '';
$dbpass = '';
$conn = mysql_connect($dbhost, $dbuser, $dbpass);
if(! $conn )
{
  die('Could not connect: ' . mysql_error());
}

$tuduText = mysql_real_escape_string( $_GET['text'] );
$sql = 'INSERT INTO tudus '.
       '(text,created_date,status,updated_date) '.
       'VALUES ( "'.$tuduText.'", NOW(), "A", NOW() )';

mysql_select_db('gilmorec_pda');
$retval = mysql_query( $sql, $conn );
if(! $retval )
{
  die('Could not enter data: ' . mysql_error());
}
echo "Entered data successfully<br>\n[".$tuduText."]";
mysql_close($conn);
?> 