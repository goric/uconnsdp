<?php

/***********************************************************/
/* Senior Design Project - Index Page                      */
/* This is the main index page, responsible for the main   */
/* design and loading of all of the content modules        */
/***********************************************************/

require_once('include/global.inc.php');

$module = $_GET['module'];
$page = $_GET['page'];
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><?php echo PROJ_NAME?></title>
<style type="text/css" media="screen,projection">/*<![CDATA[*/ @import "<?php echo SITE_ADDR ?>css/style.css"; /*]]>*/</style>
<link rel="stylesheet" href="<?php echo SITE_ADDR ?>css/style.css" type="text/css" />
</head>

<body>
<table>
	<tr>
		<td valign="top" style="padding-right: 50px; padding-top: 10px">
			<ul>
				<li><a href="<?php echo pageLink("home")?>">Home</a></li>
				<li><a href="<?php echo pageLink("register")?>">Register</a></li>
				<li><a href="<?php echo pageLink("password")?>">Forgot Password</a></li>
				<li><a href="<?php echo pageLink("download")?>">Download</a></li>
				<li><a href="<?php echo pageLink("support", "faq")?>">F.A.Q.</a></li>
				<li><a href="<?php echo pageLink("support")?>">Support</a></li>
			</ul>
		</td>
		<td>
<?php require_once(showModule($module)); ?>
		</td>
	</tr>
</table>
</body>
</html>
