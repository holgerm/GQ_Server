<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "https://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="https://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>geoquest</title>
<style type="text/css">
html,body {
    height:100%;
}
body,td,th {
	color: #000;
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
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

<link rel="icon" href="favicon.png" type="image/x-icon" />

</head>

<body link="#000000" vlink="#000000" alink="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">


    
    
    
<div style="position:fixed; top:0px; left:0px; width:100%; height:100%; background-image:url(bg_login.jpg); background-size:cover; z-index:0;"> &nbsp;
</div>



<div style=" position:absolute;
    top: 350px;
    left: 50%;
   
     opacity: 0.4;
    margin-top: -150px; /*set to a negative number 1/2 of your height*/
    margin-left: -150px; /*set to a negative number 1/2 of your width*/
    border: 1px solid #ccc;
     border-radius: 15px;
 padding:10px;
    width: 300px;
    height: 300px; 
    background-color: #f3f3f3;
   
    text-align:center;    z-index:1001;"> &nbsp;
    </div>
<div style=" position:absolute;
    top: 350px;
    left: 50%;
    width:30em;
    height:18em;
    margin-top: -150px; /*set to a negative number 1/2 of your height*/
    margin-left: -150px; /*set to a negative number 1/2 of your width*/
     border-radius: 15px;
 padding:10px;
    width: 300px;
    height: 300px; 
      z-index:1001;
    text-align:center; ">
    
    
    
    <img src="logo.png" width="500" height="281" alt="geoquest" style=" position:absolute;
    top: -220px;
    left: 50%;
    margin-left:-250px;
    
   z-index:1000;" />
   
<form action="https://quest-mill.intertech.de/61/login" method="POST" >
    
    	
        
        
        





<h1>Login</h1>




<br/>


<div class="input-group">
  <span class="input-group-addon" id="basic-addon1">  <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
</span>
  <input type="text" class="form-control" placeholder="E-Mail" aria-describedby="basic-addon1"  id="email" name="email" style="border-radius:2px;" >
</div>


          
        

<br/>


<div class="input-group" >
  <span class="input-group-addon" id="basic-addon1"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
</span>
  <input type="password" class="form-control" placeholder="Password" aria-describedby="basic-addon1"  id="password" name="password" style="border-radius:2px;">
</div>



<br/>

          

<div class="btn-group">
  <button class="btn btn-primary " type="submit"   aria-haspopup="true" aria-expanded="false">
    Einloggen
  </button>
</div>
          <br/>
        <br/><div style="font-size:x-small"><a href="https:/quest-mill.intertech.de/assets/signup.php">Neuen Account anlegen</a> | 
        <a href="https://quest-mill.intertech.de/61/login/password/forgot">Passwort vergessen?</a></div>

    	
</form>

<br/><br/><br/>

</div>


<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>
