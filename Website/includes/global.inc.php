<?php

/***********************************************************/
/* Senior Design Project - Global Include File             */
/* This include file will house many of the functions and  */
/* settings that will be used in multiple files.           */
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

/**
 * Formats a string for querying, protects against SQL Injection. Should be called for all user-inputted data
 * @param string s 
 * @return escaped string s
 */
function sql($s){
	if(get_magic_quotes_gpc()) $s = stripslashes($s); 
	if(function_exists("mysql_real_escape_string")) $s = mysql_real_escape_string($s); 
	else $s = addslashes(trim($s)); 
	return $s; 
}

/**
 * Determines whether or not the entered username exists or not
 * @param username 
 * @return true or false
 */
function userExists($username){
	$result = mysql_query("SELECT * FROM users WHERE username = '" . $username . "'");
	if (mysql_num_rows($result) == 0) return false;
	else return true;
}
?>