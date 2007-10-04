<?php

/***********************************************************/
/* Senior Design Project - Registration Module             */
/* This file contains all of the content for the           */
/* registration module (form and data validation)          */
/***********************************************************/
?>
<h1>Register</h1>
<?php
require_once('register.class.php');
$reg = new register();

if (isset($_POST['submit'])){
	$reg->getData();
	if ($reg->validateData()) {
		$reg->insertData();
		$regSuccess = true;
	} else {
		$reg->printErrors();
	}
}

if ($regSuccess){
?>
<h2>You're almost done!</h2>
<p>An email has been sent to the email address you used to register. Please follow the instructions in that email to complete your registration.</p>
<?
} else {
?>
<script type="text/javascript" src="<?php echo SITE_ADDR?>js/ajax.js"></script>
<script type="text/javascript">
<!--
function isAlphaNum(value){
	if (value.match(/^[a-zA-Z0-9]+$/)) return true;
	else return false;
}

function isEmail(value){
	if (value.match(/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/)) return true
	else return false;
}
function checkName(){
	name = document.regForm.name.value;
	if (name.length < 3) document.getElementById("nameError").innerHTML = "Please enter your name";
	else document.getElementById("nameError").innerHTML = "";
}

function checkUsername(){
	username = document.regForm.username.value;
	if (username.length < 5) {
		document.getElementById("usernameValid").innerHTML = "";
		document.getElementById("usernameError").innerHTML = "Your username must contain at least 5 characters";
	} else if (username.length > 16) {
		document.getElementById("usernameValid").innerHTML = "";
		document.getElementById("usernameError").innerHTML = "Your username cannot be longer than 16 characters";
	} else if (!isAlphaNum(username)) {
		document.getElementById("usernameValid").innerHTML = "";
		document.getElementById("usernameError").innerHTML = "Your username can only contain letters and numbers";
	} else checkUsernameExists();
}

function checkEmail(){
	email = document.regForm.email1.value;
	if (!isEmail(email)) {
		document.getElementById("emailValid").innerHTML = "";
		document.getElementById("emailError").innerHTML = "Please enter a valid email address";
	} else if (!checkEmailMatch()){
		// 
	} else checkEmailExists();

}

function checkEmailMatch(){
	email1 = document.regForm.email1.value;
	email2 = document.regForm.email2.value;
	if (email1 != email2 && (email1 != "")) {
		document.getElementById("emailValid").innerHTML = "";
		document.getElementById("emailMatchError").innerHTML = "Your email addresses do not match";
		return false;
	} else {
		checkEmailExists();
		document.getElementById("emailMatchError").innerHTML = "";
		return true;
	}
}

function checkSecCode(){
	seccode = document.regForm.secCode.value;
	if (seccode.length != 6 || isNaN(seccode)) document.getElementById("codeError").innerHTML = "Please enter the security code";
	else document.getElementById("codeError").innerHTML = "";
}
-->
</script>
<form action="" name="regForm" id="regForm" method="post">
<table>
	<tr>
		<td align="right"><span class="error">*</span>Name: </td>
		<td><input type="text" name="name" onblur="checkName()" value="<?php echo $reg->getName()?>" /> <span id="nameError" class="error"></span></td>
	</tr>
	<tr>
		<td align="right"><span class="error">*</span>Email: </td>
		<td><input type="text" size="30" name="email1" onblur="checkEmail()" value="<?php echo $reg->getEmail()?>" /> <span id="emailError" class="error"></span><span id="emailValid" class="valid"></span></td>
	</tr>
	<tr>
		<td align="right"><span class="error">*</span>Confirm Email: </td>
		<td><input type="text" size="30" name="email2" onblur="checkEmailMatch()" value="<?php echo $reg->getEmail()?>" /> <span id="emailMatchError" class="error"></span></td>
	</tr>
	<tr>
		<td align="right"><span class="error">*</span>Desired Username: </td>
		<td><input type="text" name="username" onblur="checkUsername()" value="<?php echo $reg->getUsername()?>" /> <span id="usernameError" class="error"></span><span id="usernameValid" class="valid"></span></td>
	</tr>
	<tr>
		<td align="right"><span class="error">*</span>Password:</td>
		<td><input type="password" name="pass1" /></td>
	</tr>
	<tr>
		<td align="right"><span class="error">*</span>Confirm Password: </td>
		<td><input type="password" name="pass2" /></td>
	</tr>
	<tr>
		<td align="right"><span class="error">*</span>Security Code:</td>
		<td valign="middle"><input type="text" name="secCode" size="6" maxlength="6" onblur="checkSecCode()" /> <?php generateSecurityCode()?> <span id="codeError" class="error"></span></td>
	</tr>
	<tr>
		<td> </td>
		<td>Enter the number you see in the image into the textbox</td>
	</tr>
	<tr>
		<td align="right"><input type="checkbox" name="termsAgree" value="1" /></td>
		<td><span class="error">*</span>I agree to the Terms of Service</td>
	</tr>
	<tr>
		<td> </td>
		<td><input type="submit" name="submit" value="Register" /></td>
	</tr>
</table>
</form>
<span class="error"><em>*Required field</em></span>
<?php
}
?>