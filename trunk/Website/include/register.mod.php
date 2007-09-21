<?php

/***********************************************************/
/* Senior Design Project - Registration Module             */
/* This file contains all of the content for the           */
/* registration module (form and data validation)          */
/***********************************************************/

require_once('register.class.php');

?>
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
	if (username.length < 5) document.getElementById("usernameError").innerHTML = "Your username must contain at least 5 characters";
	else if (username.length > 16) document.getElementById("usernameError").innerHTML = "Your username cannot be longer than 16 characters";
	else if (!isAlphaNum(username)) document.getElementById("usernameError").innerHTML = "Your username can only contain letters and numbers";
	else document.getElementById("usernameError").innerHTML = "";
}

function checkEmail(){
	email = document.regForm.email1.value;
	if (!isEmail(email)) document.getElementById("emailError").innerHTML = "Please enter a valid email address";
	else document.getElementById("emailError").innerHTML = "";
}

function checkEmailMatch(){
	email1 = document.regForm.email1.value;
	email2 = document.regForm.email2.value;
	if (email1 != email2 && email1 != "") document.getElementById("emailMatchError").innerHTML = "Your email addresses do not match";
	else document.getElementById("emailMatchError").innerHTML = "";
}

function checkSecCode(){
	seccode = document.regForm.secCode.value;
	if (seccode.length != 6 || isNaN(seccode)) document.getElementById("codeError").innerHTML = "Please enter the security code";
	else document.getElementById("codeError").innerHTML = "";
}
-->
</script>
<h1>Register</h1>
<form action="" name="regForm" id="regForm" method="post">
<table>
	<tr>
		<td align="right">Name: </td>
		<td><input type="text" name="name" onblur="checkName()" /> <span id="nameError" class="error"></span></td>
	</tr>
	<tr>
		<td align="right">Email: </td>
		<td><input type="text" size="30" name="email1" onblur="checkEmail()" /> <span id="emailError" class="error"></span></td>
	</tr>
	<tr>
		<td align="right">Confirm Email: </td>
		<td><input type="text" size="30" name="email2" onblur="checkEmailMatch()" /> <span id="emailMatchError" class="error"></span></td>
	</tr>
	<tr>
		<td align="right">Desired Username: </td>
		<td><input type="text" name="username" onblur="checkUsername()" /> <span id="usernameError" class="error"></span></td>
	</tr>
	<tr>
		<td align="right">Password:</td>
		<td><input type="text" name="pass1" /></td>
	</tr>
	<tr>
		<td align="right">Confirm Password: </td>
		<td><input type="text" name="pass2" /></td>
	</tr>
	<tr>
		<td align="right">Security Code:</td>
		<td valign="middle"><input type="text" name="secCode" size="6" maxlength="6" onblur="checkSecCode()" /> <?php generateSecurityCode()?> <span id="codeError" class="error"></span></td>
	</tr>
	<tr>
		<td> </td>
		<td>Enter the number you see in the image into the textbox</td>
	</tr>
	<tr>
		<td align="right"><input type="checkbox" name="termsAgree" value="1" /></td>
		<td>I agree to the Terms of Service</td>
	</tr>
	<tr>
		<td> </td>
		<td><input type="submit" name="submit" value="Register" /></td>
	</tr>
</table>
</form>