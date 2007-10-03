<?php

/***********************************************************/
/* Senior Design Project - Registration Class              */
/* This Registration class will handle the data validation */
/* and insertion of new users into the database.           */
/***********************************************************/

class register {

	// Declare the !$&%ing variables
	var $username;
	var $pass1;
	var $pass2;
	var $name;
	var $email1;
	var $email2;
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
	
	function generateEmailKey(){ $this->emailConfirmKey = randomString(40); }
	
	// getData() will retrieve all posted data and store in variables
	function getData(){
		$this->username = sql(trim($_POST['username']));
		$this->pass1 = sql(trim($_POST['pass1']));
		$this->pass2 = sql(trim($_POST['pass2']));
		$this->name = sql(trim($_POST['name']));
		$this->email1 = sql(trim($_POST['email1']));
		$this->email2 = sql(trim($_POST['email2']));
		$this->secHash = sql(trim($_POST['secHash']));
		$this->secCode = sql(trim($_POST['secCode']));
		$this->termsAgree = sql(trim($_POST['termsAgree']));
	}

	// Functions to return submitted information in the event something is not valid
	function getEmail() { return $this->email1; }
	function getName() { return $this->name; }
	function getUsername() { return $this->username; }
	
	function insertData(){
		$this->generateEmailKey();
		$query = "INSERT INTO users SET name = '" . $this->name . "', username = '" . $this->username . "', password = '" . sha1($this->pass1) . "', email = '" . $this->email1 . "', confirm_key = '" . $this->emailConfirmKey . "', date_joined = '" . time() . "'";
		$result = mysql_query($query);
		$this->sendEmail();
	}
	
	// Function to print all errors
	function printErrors(){
		if (!empty($this->error)) {
			echo "<div class='error'><ul>\n";
			foreach($this->error AS $error){
				echo "<li>$error</li>\n";
			}
			echo "</ul></div>\n";
		}
	}
	
	// 80% of our users will probably enter invalid data. Lets stop them here.
	function validateData(){
		// Name - Just make sure it is 3 or more characters; can't really do much checking here
		if (strlen($this->name) < 3) $this->error['shortName'] = "Please enter your name";

		// Email - Make sure it is in the user@domain.ext formatgoogle; both emails must match
		if(!eregi("^[_+a-z0-9-]+(\.[_+a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]{1,})*\.([a-z]{2,}){1}$", $this->email1))  $this->error['invalidEmail'] = "Please enter a valid email address";
		if (emailExists($this->email1)) $this->error['emailExists'] = "This email address has already been used to register an account.";
		if ($this->email1 != $this->email2) $this->error['emailNoMatch'] = "Your email addresses do not match";
		
		// Username - Can only contain between 5 and 16 letters/numbers.
		if (strlen($this->username) < 5) $this->error['shortUsername'] = "Your username must contain at least 5 characters";
		if (strlen($this->username) > 16) $this->error['longUsername'] = "Your username cannot be longer than 16 characters";
		if (!ctype_alnum($this->username)) $this->error['alnumUsername'] = "Your username can only contain letters and numbers";
		if (userExists($this->username)) $this->error['userExists'] = "This username already exists.";
		
		// Password - Must be between 5 and 16 characters; both passwords must match
		if (strlen($this->pass1) < 5) $this->error['shortPassword'] = "Your password must contain at least 5 characters";
		if (strlen($this->pass1) > 16) $this->error['longPasswrd'] = "Your password cannot be longer than 16 characters";
		if ($this->pass1 != $this->pass2) $this->error['passNoMatch'] = "Your passwords do not match.";
		
		// Security Code - Make sure the security code matches the image
		if (!validSecurityCode($this->secHash, $this->secCode)) $this->error['secCode'] = "You have entered an invalid security code";
		
		// Terms - Make sure the user has agreed to the terms
		if ($this->termsAgree != '1') $this->error['mustAgree'] = "You must agree to the Terms of Service";
		
		if(empty($this->error)) return true;
		else return false;
	}
		
	function sendEmail(){
		$subject = "You must confirm your " . PROJ_NAME . " registration";
		$body = $this->name . ", \n\nYour registration is almost complete. Please click on the following link to confirm your email address:\n\n" . SITE_ADDR . "confirm.php?email=" . $this->email1 . "&key=" . $this->emailConfirmKey . "\n\nIf the URL above does not appear as a link, simply copy and paste it into your browser.\n\nThank you\nThe " . PROJ_NAME . " Team";
		//mail($this->email1, $subject, $body, "From: " . SEND_EMAIL);
		//echo $body;
	}
}
?>