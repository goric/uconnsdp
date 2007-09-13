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
 * Removes all expired security codes from the table
 * @param expiration time in seconds(default = 12 minutes)
 * @return true
 */
function deleteSecurityKeys($exp = "720"){
	$exp_time = now() - $exp;
	$query = "DELETE FROM security_codes WHERE date < '" . $exp_time . "'";
	mysql_query($query);
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


/**
 * Checks to make sure the security key entered matched the image
 * @param security code hash
 * @param security code id 
 * @return true or false
 */
function validSecurityKey($hash, $id){
	deleteSecurityKeys();	// First remove all expired keys from the table
	$result = mysql_query("SELECT * FROM security_codes WHERE code_hash = '" . $hash . "'");
	
	// If there are no rows, security code has expires or hack attempt. Return false
	if (mysql_num_rows($result) == 0) return false;
	else $code_id = mysql_result($result, 0 , 'code_id');
	
	// If we get here, the code hash has been found. Compare ID in row to that entered by user
	if ($code_id = $id) return true;
	else return false;
}

generateSecurityImage();
?>