<?php

/***********************************************************/
/* Senior Design Project - Support Class                   */
/* This support class will handle validating required data */
/* and sending an email to us to report the issue          */
/***********************************************************/

class support {

	// Declare the !$&%ing variables
	var $name;
	var $email;
	var $os;
	var $version;
	var $subject;
	var $desc;
	var $error;
	
	// PHP4 will call the register() function as the default constructor. 
	// PHP5 will call __construct() first, then register() if __construct DNE
	// We will try to make this work on both, since this project will be huge and we may have to migrate to PHP5...
	function support(){
		// Call the other constructor used in PHP5
		$this->__construct();
	}
	
	function __construct(){
		// All that work and we dont need a constructor, go figure
	}
	
	// getData() will retrieve all posted data and store in variables
	function getData(){
		$this->name = sql(trim($_POST['name']));
		$this->email = sql(trim($_POST['email']));
		$this->os = sql(trim($_POST['os']));
		$this->version = sql(trim($_POST['version']));
		$this->subject = sql(trim($_POST['subject']));
		$this->desc = sql(trim($_POST['desc']));
	}

	// Functions to return submitted information in the event something is not valid
	function getName() { return $this->name; }
	function getEmail() { return $this->email; }
	function getOS() { return $this->os; }	
	function getVersion() { return $this->version; }
	function getSubject() { return $this->subject; }
	function getDesc() { return $this->desc; }
	
	function insertData(){
		//$query = "INSERT INTO users SET name = '" . $this->name . "', username = '" . $this->username . "', password = '" . sha1($this->pass1) . "', email = '" . $this->email1 . "', confirm_key = '" . $this->emailConfirmKey . "', date_joined = '" . time() . "'";
		//$result = mysql_query($query);
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
		if(!eregi("^[_+a-z0-9-]+(\.[_+a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]{1,})*\.([a-z]{2,}){1}$", $this->email))  $this->error['invalidEmail'] = "Please enter a valid email address";
		
		// Description - Make sure the length is somewhat long
		if (strlen($this->desc) < 20) $this->error['shortDesc'] = "Please enter a longer description";
	
		if(empty($this->error)) return true;
		else return false;
	}
		
	function sendData(){
		$subject = "You must confirm your " . PROJ_NAME . " registration";
		$body = $this->name . ", \n\nYour registration is almost complete. Please click on the following link to confirm your email address:\n\n" . SITE_ADDR . "confirm.php?email=" . $this->email1 . "&key=" . $this->emailConfirmKey . "\n\nIf the URL above does not appear as a link, simply copy and paste it into your browser.\n\nThank you\nThe " . PROJ_NAME . " Team";
		//mail($this->email1, $subject, $body, "From: " . SEND_EMAIL);
		//echo $body;
	}
}

class faq {

	// Declare variables
	var $done = false;
	var $data;
	
	// Constructors
	function faq(){
		// Call the other constructor used in PHP5
		$this->__construct();
	}
	
	function __construct(){
		// All that work and we dont need a constructor, go figure
	}
	
	// Query to the DB. This will only be called once per page load
	function queryDB(){
		$query = "SELECT * FROM faq ORDER BY question ASC";
		$result = mysql_query($query);
		if (mysql_num_rows($result) == 0) $this->data = false;
		else $this->data = $result;
		$this->done = true;
	}
	
	// Print out the questions
	function printQuestions(){
		if (!$this->done) $this->queryDB();
		mysql_data_seek($this->data, 0);
		echo "<ol>\n";
		$i = 1;
		while ($data = mysql_fetch_array($this->data)) {
			echo "<li><a href=\"#" . $i . "\">" . $data['question'] . "</a></li>";
			$i++;
		}
		echo "</ol>\n";
	}
	
	// Print out the questions and answers
	function printQA(){
		if (!$this->done) $this->queryDB();
		mysql_data_seek($this->data, 0);
		$i = 1;
		while ($data = mysql_fetch_array($this->data)) {
			echo "<a name=\"" . $i . "\"></a>\n";
			echo "<div style='border: 1px solid #000000; margin: 20px; padding: 5px'><table><tr><td><b>" . $data['question'] . "</b></td></tr><tr><td>" . $data['answer'] . "</td></tr></table></div>\n";
			$i++;
		}
	}
}
?>