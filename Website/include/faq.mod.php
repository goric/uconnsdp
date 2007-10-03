<?php

/***********************************************************/
/* Senior Design Project - Support Module                  */
/* This file contains all of the content for the support   */
/* module (contact form, contact info, etc)                */
/***********************************************************/
?>
<h1>Support</h1>
<?php
require_once('support.class.php');
$help = new support();
if (isset($_POST['submit'])){
	$help->getData();
	if ($help->validateData()) {
		$help->sendData();
		$emailSuccess = true;
	} else {
		$help->printErrors();
	}
}

if ($emailSuccess){
?>
<h2>Your request has been sent.</h2>
<p>Please allow 24 - 48 hours to receive a response.</p>
<?
} else {
?>
<form action="" name="helpForm" id="helpForm" method="post">
<table>
	<tr>
		<td align="right"><span class="error">*</span>Name: </td>
		<td><input type="text" name="name" value="<?php echo $help->getName()?>" /> <span id="nameError" class="error"></span></td>
	</tr>
	<tr>
		<td align="right"><span class="error">*</span>Email: </td>
		<td><input type="text" size="30" name="email" value="<?php echo $help->getEmail()?>" /> <span id="emailError" class="error"></span><span id="emailValid" class="valid"></span></td>
	</tr>
	<tr>
		<td align="right">Operating System: </td>
		<td><input type="text" name="os" value="<?php echo $help->getOS()?>" /></td>
	</tr>
	<tr>
		<td align="right">Client Version: </td>
		<td><input type="text" name="version" value="<?php echo $help->getVersion()?>" /></td>
	</tr>
	<tr>
		<td align="right"><span class="error">*</span>Subject: </td>
		<td>
		<select name="subject">
			<option value="Question/Comment"<?php if ($help->getSubject() == "Question/Comment") echo ' selected="selected"'?>>Question/Comment</option>
			<option value="Bug"<?php if ($help->getSubject() == "Bug") echo ' selected="selected"'?>>Report a Bug</option>
			<option value="Installation Trouble"<?php if ($help->getSubject() == "Installation Trouble") echo ' selected="selected"'?>>Trouble Installing Software</option>
		</select>
		</td>
	</tr>
	<tr>
		<td align="right"><span class="error">*</span>Comment: </td>
		<td><textarea name="desc" rows="5" cols="40"><?php echo $help->getDesc()?></textarea></td>
	</tr>
	<tr>
		<td> </td>
		<td><input type="submit" name="submit" value="Submit Support Ticket" /></td>
	</tr>
</table>
</form>
<span class="error"><em>*Required field</em></span>

<?php
}
?>