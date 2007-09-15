<?php

/***********************************************************/
/* Senior Design Project - Global Include File             */
/* This include file will house many of the functions and  */
/* settings that will be used in multiple files.           */
/***********************************************************/

// Connect to database
require_once('db.inc.php');

/**
 * Removes all expired security codes from the table
 * @param expiration time in seconds(default = 12 minutes)
 * @return true
 */
function deleteSecurityCodes($exp = "720"){
	$exp_time = time() - $exp;
	$query = "DELETE FROM security_codes WHERE date < '" . $exp_time . "'";
	mysql_query($query);
}

/**
 * Generates the security code and displays the image
 * @return image
 */
function generateSecurityCode(){
	deleteSecurityCodes();	// Delete all expired keys first
	$codeHash = randomString(32);
	$codeID = randomString(6, "num");
	$query = "INSERT INTO security_codes SET code_hash = '" . $codeHash . "', code_id = '" . $codeID . "', date = '" . time() . "'";
	$result = mysql_query($query);
	echo "<img src='securityCode.inc.php?key=" . $codeHash . "'>";
}

/**
 * Generates a random string
 * @param length l
 * @param type (alpha, alnum, num)
 * @return random string with length l
 */
function randomString($l, $type = "alnum") {
	$randstring = "";
	if ($type == "alpha") $string = "abcdefghijklmnopqrstuvwxyz";
	else if ($type == "num") $string = "1234567890";
	else $string = "1234567890abcdefghijklmnopqrstuvwxyz";
	for ($a = 0; $a < $l; $a++) {
		$b = rand(0, strlen($string) - 1);
		$randstring .= $string[$b];
	}
	return $randstring;
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
 * Checks to make sure the security code entered matched the image
 * @param security code hash
 * @param security code id 
 * @return true or false
 */
function validSecurityCode($hash, $id){
	deleteSecurityKeys();	// First remove all expired keys from the table
	$result = mysql_query("SELECT * FROM security_codes WHERE code_hash = '" . $hash . "'");
	
	// If there are no rows, security code has expires or hack attempt. Return false
	if (mysql_num_rows($result) == 0) return false;
	else $code_id = mysql_result($result, 0 , 'code_id');
	
	// If we get here, the code hash has been found. Compare ID in row to that entered by user
	if ($code_id = $id) return true;
	else return false;
}
?>