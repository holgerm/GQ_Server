<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "https://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="https://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>geoquest | %%_GEOQUEST_CONTENT_TITLE_%%</title>
<style type="text/css">
html,body {
    height:100%;
}
body,td,th {
	color: #000 !important;
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif !important;
}
a:link {
	text-decoration: underline !important;
	color:#000 !important;
}
a:visited {
	text-decoration: underline !important;
		color:#000 !important;

}
a:hover {
	text-decoration: none !important;
		color:#000 !important;

}
a:active {
	text-decoration: underline !important;
		color:#000 !important;

}

.container{

width:100% !important;	
	
}

.menu{
position:fixed; left:0px; height:100%; z-index:10; white-space:nowrap; overflow-x:hidden; overflow-y:scroll; z-index:20;
	
}


.main{
	
position:absolute; right:0px;  min-height:1500px; background-color:#f6f9fe;	z-index:19;
}

	 @media (max-width: 11139px) {

.menu.fadeout{
	
		 
	  -webkit-transition: .2s;
  -moz-transition: .2s;
  -ms-transition: .2s;
  -o-transition: .2s;
  transition: .2s;
	width:0%;
	
}

.menu.fadein {
  -webkit-transition: .2s;
  -moz-transition: .2s;
  -ms-transition: .2s;
  -o-transition: .2s;
  transition: .2s;
  width:300px;
  background-color:#FFF;
  
}



.main.menufadein{
	 -webkit-transition: .2s;
  -moz-transition: .2s;
  -ms-transition: .2s;
  -o-transition: .2s;
  transition: .2s;
	width:100% ;
	
}

.main.menufadeout{
	 -webkit-transition: .2s;
  -moz-transition: .2s;
  -ms-transition: .2s;
  -o-transition: .2s;
  transition: .2s;
	width:100%;
	
}


	 }
	 
	  @media (min-width: 1140px) {

.menu.fadeout{
	
		 
	  -webkit-transition: .2s;
  -moz-transition: .2s;
  -ms-transition: .2s;
  -o-transition: .2s;
  transition: .2s;
	width:0%;
	
}

.menu.fadein {
  -webkit-transition: .2s;
  -moz-transition: .2s;
  -ms-transition: .2s;
  -o-transition: .2s;
  transition: .2s;
  width:20%;
}



.main.menufadein{
	 -webkit-transition: .2s;
  -moz-transition: .2s;
  -ms-transition: .2s;
  -o-transition: .2s;
  transition: .2s;
	width:80% ;
	
}

.main.menufadeout{
	 -webkit-transition: .2s;
  -moz-transition: .2s;
  -ms-transition: .2s;
  -o-transition: .2s;
  transition: .2s;
	width:100%;
	
}


	 }
	 
	 
	 
	 
</style>
<link href="https://quest-mill.intertech.de/assets/css/bootstrap.css" rel="stylesheet">
      <link href="https://quest-mill.intertech.de/assets/css/bootstrap-responsive.css" rel="stylesheet">
<link href="https://quest-mill.intertech.de/assets/docs/datatables.css" id="dtCss" rel="stylesheet">

<?php




if(isset($_GET['content'])) {
$url = $_GET['content'];

} else {
$url = "Start";	
	
	
}



?>
<base href="https://www.quest-mill.com/tutorials/<?=$url?>/index.html" />
</head>

<body link="#000000" vlink="#000000" alink="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div>




<img src="../icons/menu.png" style="z-index:1000000; position:fixed; top: 10px; left:10px; cursor:pointer;" class="showmenu"  width="30"/>
<div style="position:fixed; top:0; left:0; width:100%; background-image:url(../newlogo_bg.png); background-position:center; height:50px; z-index:100;">
<div style="position:absolute; top:0; left:0; width:100%; background-size: 2000px 50px; background-image:url(../newlogo_banner.jpg); background-position:center; background-repeat: no-repeat; height:50px; z-index:100;">

&nbsp;
</div></div>

<div class="menu" >

<div id="menu_content" style="position:absolute; left:10px; top:55px; width:100%; padding-left:5%;  white-space:nowrap;">
<br/><br/>
<a href="../index.php?content=Start">Wilkommen</a><br />
  <br />
  
  <ul style="list-style-type: none; padding:0; margin: 0 0 0 0;">
              <li>%%_GEOQUEST_NAV_LI_%%</li>
  </ul>
  
<h5>Die erste eigene Quest</h5> 
Anlegen<br />
Von Seiten zu Aktionen
<br />
Der Quest Editor<br /> 
Orte anlegen<br />
Vorschau im Editor<br /> 
Eine Beispiel-Quest
<br />
<br />
<h3>Seiten</h3>
<a href="../index.php?content=Auswahlmenue">Auswahlmenü</a><br />
<a href="../index.php?content=AudioAufnahme">Audio Aufnahme</a><br />
<a href="../index.php?content=FotoAufnahme">Foto Aufnahme</a><br />
<a href="../index.php?content=FrageMultipleChoice">Frage (Multiple Choice)</a><br />
<a href="../index.php?content=FrageText">Frage (Text)</a><br />
<a href="../index.php?content=KarteOpenStreetMap">Karte (Google/OpenStreetMap)</a><br />
<a href="../index.php?content=">NPC Dialog</a><br />
<a href="../index.php?content=">Tag Scanner</a><br />
<a href="../index.php?content=">Text mit Bild</a><br />
<a href="../index.php?content=">Video Wiedergabe</a><br />
<a href="../index.php?content=">Vollformat Bild</a><br />
<a href="../index.php?content=">Webseite</a>
<br />
<br />
<br />
<h3>Aktionen</h3> 
<p>Nächste Seite <br /> 
  Letzte Seite<br />
  Seite aufrufen<br />
  Quest beenden<br />
  Vibrieren<br />
  Variable um 1 erhöhen<br />
  Variable um 1 verringern<br />
  Audio-Datei abspielen
  <br />
  Variable neuen Wert zuweisen<br />
  Nachricht anzeigen<br />
  Hotspot-Zustand verändern<br />
  Score erhöhen<br />
  Karte zentrieren auf<br />
  Wenn-Dann-Bedingung<br />
  Solange-Wie-Schleife<br />
  Von-Bis-Schleife<br />
  Schleife unterbrechen<br />
  Routing hinzufügen
  <br />
  <br />
<h3>Experten-Features</h3>
<p>Variablen<br />
  Text-Formatierung<br />
  Reguläre Ausdrücke<br />
<br />
</p>
<h3>Administration</h3> 
<p>Benutzer Rechte<br />
  Spieltypen<br />
  Spieltyp Migration<br />
  Spieltyp erstellen<br />
  Portal Benutzer<br />
  Portal Design<br />
  <br />
</p>
</div>

</div>

<div class="main">
<div id="tutorial_bump" style="height:55px; width:90%; margin-right:5%;">&nbsp;
</div>
<div id="tutorial_content" style=" width:90%; padding-left:5%;"><div class="row-fluid">
      <h1>%%_GEOQUEST_CONTENT_TITLE_%%  </h1>
	</div>	

<div class="row-fluid">

%%_GEOQUEST_CONTENT_CODE_START_%%{ %general.button.attributes:=!!" class="btn btn-primary" "!!;; %general.speciallinks.attributes:=!!" class="btn btn-success" "!!;; %general.navtabs.attributes:=!!" class="btn btn-success" !!";; %general.tabs.type:=button-group% }
</div>



</div>


</div>
</div>


<script src="https://code.jquery.com/jquery-2.0.0.js"></script>
  <script src="https://quest-mill.intertech.de/assets/js/jquery.dataTables.js"></script>
	<script src="https://quest-mill.intertech.de/assets/js/DT_bootstrap.js"></script>
        <script src="https://quest-mill.intertech.de/assets/js/bootstrap.js"></script>
<script>

var boxOne = document.getElementsByClassName('menu')[0];
var boxTwo = document.getElementsByClassName('main')[0];






var geoquest_menu_in=getCookie("geoquest_menu_in") || 'true';

if(geoquest_menu_in == 'true'){
	   document.getElementsByClassName('showmenu')[0].src = '../icons/back.png';
    boxOne.classList.add('fadein');
	 boxTwo.classList.add('menufadein');
	 		 boxTwo.classList.remove('menufadeout');
			 localStorage['geoquest_menu_in'] = 'true';
} else if(geoquest_menu_in == 'false'){

 boxOne.classList.remove('fadein');
	    boxOne.classList.add('fadeout');
		 boxTwo.classList.remove('menufadein');
		 boxTwo.classList.add('menufadeout');
}




   document.getElementsByClassName('showmenu')[0].onclick = function() {
	   console.log(this.src);
  if(this.src.indexOf('icons/menu.png') > -1) 
  { 
    this.src = '../icons/back.png';
    boxOne.classList.add('fadein');
	 boxTwo.classList.add('menufadein');
	 		 boxTwo.classList.remove('menufadeout');
			 setCookie("geoquest_menu_in","true",365) ;

	 
  } else {
    this.src = '../icons/menu.png';
    boxOne.classList.remove('fadein');
	    boxOne.classList.add('fadeout');
		 boxTwo.classList.remove('menufadein');
		 boxTwo.classList.add('menufadeout');
			 setCookie("geoquest_menu_in","false",365) ;


    
  }  
}





function setCookie(c_name,value,exdays)
{
var exdate=new Date();
exdate.setDate(exdate.getDate() + exdays);
var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
document.cookie=c_name + "=" + c_value;
}




function getCookie(c_name)
{
var i,x,y,ARRcookies=document.cookie.split(";");
for (i=0;i<ARRcookies.length;i++)
{
  x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
  y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
  x=x.replace(/^\s+|\s+$/g,"");
  if (x==c_name)
    {
    return unescape(y);
    }
  }
}


</script>

</body>
</html>
