<?php

/***********************************************************/
/* Senior Design Project - Support Class                   */
/* This support class will handle validating required data */
/* and sending an email to us to report the issue          */
/***********************************************************/

class passreq {

	// Declare the !$&%ing variables
	var $email;
	var $error;
	
	// PHP4 will call the register() function as the default constructor. 
	// PHP5 will call __construct() first, then register() if __construct DNE
	// We will try to make this work on both, since this project will be huge and we may have to migrate to PHP5...
	function passreq(){
		// Call the other constructor used in PHP5
		$this->__construct();
	}
	
	function __construct(){
		// All that work and we dont need a constructor, go figure
	}
	
	// getData() will retrieve all posted data and store in variables
	function getData(){
		$this->email = sql(trim($_POST['email']));
	}

	// Functions to return submitted information in the event something is not valid
	function getEmail() { return $this->email; }
		
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
		// Email - Make sure it is in the user@domain.ext formatgoogle; both emails must match
		if(!eregi("^[_+a-z0-9-]+(\.[_+a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]{1,})*\.([a-z]{2,}){1}$", $this->email))  $this->error['invalidEmail'] = "Please enter a valid email address";
		if (!emailExists($this->email)) $this->error['emailExists'] = "Email address not found.";

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