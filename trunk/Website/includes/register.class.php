<?php

/***********************************************************/
/* Senior Design Project - Registration Class              */
/* This Registration class will handle the data validation */
/* and insertion of new users into the database.           */
/***********************************************************/

require_once('global.inc.php');

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
	var $emailConfirmKey;
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
		$this->username = sql(trim($_POST['username']));
		$this->pass1 = sql(trim($_POST['pass1']));
		$this->pass2 = sql(trim($_POST['pass2']));
		$this->name = sql(trim($_POST['name']));
		$this->email1 = sql(trim($_POST['email1']));
		$this->email2 = sql(trim($_POST['email2']));
		$this->birthMonth = sql(trim($_POST['birthMonth']));
		$this->birthDay = sql(trim($_POST['birthDay']));
		$this->birthYear = sql(trim($_POST['birthYear']));
		$this->secHash = sql(trim($_POST['secHash']));
		$this->secCode = sql(trim($_POST['secCode']));
		$this->termsAgree = sql(trim($_POST['termsAgree']));
	}
	
	// 80% of our users will probably enter invalid data. Lets stop them here.
	function validateData(){
		// Username - Can only contain between 5 and 16 letters/numbers.
		if (strlen($this->username) < 5) $error['shortUsername'] = "Your username must contain at least 5 characters";
		if (strlen($this->username) > 16) $error['longUsername'] = "Your username cannot be longer than 16 characters";
		if (!ctype_alnum($this->username)) $error['alnumUsername'] = "Your username can only contain letters and numbers";
		if (userExists($this->username)) $error['userExists'] = "This username already exists.";
		
		// Password - Must be between 5 and 16 characters; both passwords must match
		if (strlen($this->pass1) < 5) $error['shortPassword'] = "Your password must contain at least 5 characters";
		if (strlen($this->pass1) > 16) $error['longPasswrd'] = "Your password cannot be longer than 16 characters";
		if ($pass1 != $pass2) $error['passNoMatch'] = "Your passwords do not match.";
		
		// Name - Just make sure it is 3 or more characters; can't really do much checking here
		if (strlen($this->name) < 3) $error['shortName'] = "Please enter your name";
		
		// Email - Make sure it is in the user@domain.ext formatgoogle; both emails must match
		if (!preg_match("/^( [a-zA-Z0-9] )+( [a-zA-Z0-9\._-] )*@( [a-zA-Z0-9_-] )+( [a-zA-Z0-9\._-] +)+$/" , $email1)) $error['invalidEmail'] = "Please enter a valid email address";
		if ($email1 != $email2) $error['emailNoMatch'] = "Your email addresses do not match";
		
		// Birthday - Make sure the user is at least 13 years old
		$now_ts = now();
		$age_ts = mktime(0, 0, 0, $this->birthMonth, $this->birthDay, $this->birthYear);
		if (floor(($now_ts - $age_ts)/31556926) < 13) $error['tooYoung'] = "Your must be at least 13 years old to register";
		
		/////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////
		//////////////// CODE ABOVE IS MESSY, FIX LATER ON //////////////////
		/////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////
		
		// Security Code - Make sure the security code matches the image
		if (!validSecurityCode($this->secHash, $this->secCode)) $error['secCode'] = "You have entered an invalid security code";
		
		// Terms - Make sure the user has agreed to the terms
		if ($this->termsAgree != '1') $error['mustAgree'] = "You must agree to the Terms of Service".
	}
	
	function generateEmailKey(){ $this->emailConfirmKey = randomString(40); }
	
	function sendEmail(){
		
	}
}

?>