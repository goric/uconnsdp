<?php

/***********************************************************/
/* Senior Design Project - Registration Class              */
/* This Registration class will handle the data validation */
/* and insertion of new users into the database.           */
/***********************************************************/

require('global.inc.php');

class register {

	// Declare the !$&%ing variables
	var $username;
	var $pass1;
	var $pass2;
	var $name;
	var $email1;
	var $email2;
	var $birthMonth;
	var $birthDay;
	var $birthYear;
	var $secCode;
	var $termsAgree;
	var $error;
	
	// PHP4 will call the register() function as the default constructor. 
	// PHP5 will call __construct() first, then register() if __construct DNE
	// We will try to make this work on both, since this project will be huge and we may have to migrate to PHP5...
	function register(){
		// Call the other constructor used in PHP5
		$this->__construct();
	}
	
	function __construct(){
		// All that work and we dont need a constructor, go figure
	}
	
	// getData() will retrieve all posted data and store in variables
	function getData(){
		$this->username = sql($_POST['username']);
		$this->pass1 = sql($_POST['pass1']);
		$this->pass2 = sql($_POST['pass2']);
		$this->name = sql($_POST['name']);
		$this->email1 = sql($_POST['email1']);
		$this->email2 = sql($_POST['email2']);
		$this->birthMonth = sql($_POST['birthMonth']);
		$this->birthDay = sql($_POST['birthDay']);
		$this->birthYear = sql($_POST['birthYear']);
		$this->secCode = sql($_POST['secCode']);
		$this->termsAgree = sql($_POST['termsAgree']);
	}
	
	// 80% of our users will probably enter invalid data. Lets stop them here.
	function validateData(){
		// Username - Can only contain between 5 and 16 letters/numbers.
		if (strlen($this->username) < 5) $error['shortUsername'] = "Your username must contain at least 5 characters";
		if (strlen($this->username) > 16) $error['longUsername'] = "Your username cannot be longer than 16 characters";
		if (!ctype_alnum($this->username)) $error['alnumUsername'] = "Your username can only contain letters and numbers";
		if (userExists($this->username)) $error['userExists'] = "This username already exists.";
	}
}

?>