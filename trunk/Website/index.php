<?php

/***********************************************************/
/* Senior Design Project - Index Page                      */
/* This is the main index page, responsible for the main   */
/* design and loading of all of the content modules        */
/***********************************************************/

require_once('include/global.inc.php');

$module = $_GET['module'];
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Untitled Document</title>
<style type="text/css" media="screen,projection">/*<![CDATA[*/ @import "<?php echo SITE_ADDR ?>css/style.css"; /*]]>*/</style>
<link rel="stylesheet" href="<?php echo SITE_ADDR ?>css/style.css" type="text/css" />
</head>

<body>
<?php require_once(showModule($module)); ?>
</body>
</html>
