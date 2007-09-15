<?php

/***********************************************************/
/* Senior Design Project - Datebase Include File           */
/* This include file stores the database connection. This  */
/* should be included on every page on the website         */
/***********************************************************/

// Database Connection - If $noConn = true, no database connection is required
if (!$noConn){
	$user = "root";
	$pass = "";
	$host = "localhost";
	$dbname = "sdp";
	$link = mysql_connect($host, $user, $pass);
	$db = mysql_select_db($dbname, $link);
}
?>