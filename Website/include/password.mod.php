<?php

/***********************************************************/
/* Senior Design Project - Forgotten Password Module       */
/* This file contains all of the content for the forgotten */
/* password module (both request form and change form)     */
/***********************************************************/

?>
<h1>Forgot Password</h1>
<?php
require_once('password.class.php');
$pass = new passreq();

if (isset($_POST['submit'])){
	$pass->getData();
	if ($pass->validateData()) {
		$pass->sendEmail();
		$passSuccess = true;
	} else {
		$pass->printErrors();
	}
}

if ($passSuccess){
?>
<p>An email has been sent to <strong><?php echo $pass->getEmail()?></strong> with instructions on how to reset your password. This link in this email will expire in 24 hours. If you should remember your password before receiving the email, simply disregard the email.</p>
<?php
} else {
?>
<form action="" name="passReqForm" id="passReqForm" method="post">
<table>
	<tr>
		<td align="right"><span class="error">*</span>Email Address: </td>
		<td><input type="text" name="email" size="30" value="<?php echo $pass->getEmail()?>" /></td>
	</tr>
	<tr>
		<td> </td>
		<td><input type="submit" name="submit" value="Reset Password" /></td>
	</tr>
</table>

<?php
}
?>