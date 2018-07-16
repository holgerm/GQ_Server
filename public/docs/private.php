<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "https://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="https://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>geoquest</title>
<link rel="icon" href="favicon.png" type="image/x-icon" />

<style type="text/css">
html,body {
    height:100%;
}
body,td,th {
	color: #000;
	font-family: Arial, Helvetica, sans-serif;
}
a:link {
	text-decoration: underline;
}
a:visited {
	text-decoration: underline;
}
a:hover {
	text-decoration: none;
}
a:active {
	text-decoration: underline;
}
</style>
<?php




if(isset($_GET['content'])) {
$url = $_GET['content'];

} else {
$url = "Start";	
	
	
}



?>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

</head>

<body link="#000000" vlink="#000000" alink="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">



    
    
    
<div style="position:fixed; top:0px; left:0px; width:100%; height:100%; background-image:url(https://quest-mill.intertech.de/assets/docs/bg_login.jpg); background-size:cover; z-index:0;"> &nbsp;
</div>



<div style=" position:absolute;
    top: 150px;
    left: 50%;
   
     opacity: 0.8;
    margin-left: -200px; /*set to a negative number 1/2 of your width*/
  
 padding:10px;
    width: 400px;
    height: 300px; 
   color:#FFF;
    text-align:center;    z-index:1001;"> <img src="https://quest-mill.intertech.de/assets/docs/private.png" height="200" />
    <h1>Diese Seite ist privat.</h1>
    <br/>  <br/>  <br/>
    <a href="https://quest-mill.intertech.de/61/login">Weiter zum öffentlichen Login</a>
    </div>

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>
