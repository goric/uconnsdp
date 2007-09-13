<?php

/***********************************************************/
/* Senior Design Project - Security Code Include File      */
/* This include file is used to generate the security code */
/* hash, id, and display the image to the user             */
/***********************************************************/

$imgNum = imagecreate(65, 20);
$white = imagecolorallocate($imgNum, 255, 255, 255);
$black = imagecolorallocate($imgNum, 0, 0, 0);
Imagestring($imgNum,5,5,2,"123456",$black);
header("Content-type:image/jpeg"); 
imagejpeg($imgNum);
?>