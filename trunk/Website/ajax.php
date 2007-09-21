<?php

/***********************************************************/
/* Senior Design Project - AJAX                            */
/* Handles all AJAX requests amde to the server. Will      */
/* return all data in XML.                                 */
/***********************************************************/

require_once('include/global.inc.php');
if(ob_get_length()) ob_clean();
header('Expires: Mon, 26 Jul 1997 05:00:00 GMT');
header('Last Modified: ' . gmdate('D, d M Y H:i:s') . 'GMT');
header('Cache-Control: no-cache, must-revalidate');
header('Pragma: no-cache');
header('Content-Type: text/xml');

$action = sql($_GET['action']);			// Get action

switch ($action) {
	case 'checkUsername':
		checkUsername();
		break;
	case 'checkEmail':
		checkEmail();
		break;
	default: 
		output(false);
}

function checkUsername(){
	$uN = sql($_GET['username']);
	if (userExists($uN)) {
		output("<userexists>true</userexists>");
	} else {
		output("<userexists>false</userexists>");
	}
}

function checkEmail(){
	$eM = sql($_GET['email']);
	if (emailExists($eM)) {
		output("<emailexists>true</emailexists>");
	} else {
		output("<emailexists>false</emailexists>");
	}
}

function output($msg){
	echo "<response>\n";
	if (!$msg) echo "<valid>false</valid>";
	else echo "<valid>true</valid>";
	echo $msg;
	echo "</response>";
}
?>