<?php

/***********************************************************/
/* Senior Design Project - AJAX                            */
/* Handles all AJAX requests amde to the server. Will      */
/* return all data in XML.                                 */
/***********************************************************/

require_once('global.inc.php');

$action = sql($_GET['action']);			// Get action

switch ($action) {
	case 'checkUsername':
		checkUsername();
		break;
	case 'checkEmail':
		checkEmail();
		break;
	default: die();
}

function checkUsername(){
	$uN = sql($_GET['username']);
	if (userExists($uN)) {
	
	} else {
	
	}
}
?>