<?php

/***********************************************************/
/* Senior Design Project - Security Code Include File      */
/* This include file is used to generate the security code */
/* hash, id, and display the image to the user             */
/***********************************************************/

require_once('db.inc.php');

$hash = $_GET['key'];		// Get the hask
$query = "SELECT * FROM security_codes WHERE code_hash = '" . $hash . "' AND used = '0'";		// Make sure the hash is valid
$result = mysql_query($query);
if (mysql_num_rows($result) != 1) $codeID = "INVALID";	// If invalid, die
else $codeID = mysql_result($result, 0, "code_id");		// If its valid, save the code id for the image, set code as used
$query2 = "UPDATE security_codes SET used = '1' WHERE code_hash = '" . $hash . "' AND code_id = '" . $codeID . "'";
$result2 = mysql_query($query2);

// Generate the image
$imgNum = imagecreate(65, 20);
$white = imagecolorallocate($imgNum, 255, 255, 255);
$black = imagecolorallocate($imgNum, 0, 0, 0);
Imagestring($imgNum,5,5,2,$codeID,$black);
header("Content-type:image/jpeg"); 
imagejpeg($imgNum);
?>