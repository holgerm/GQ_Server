<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "https://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="https://www.w3.org/1999/xhtml">
<head>


<?php




if(isset($_GET['content'])) {
$url = $_GET['content'];
echo $lang = ($_COOKIE['PLAY_LANG']!='' ? $_COOKIE['PLAY_LANG'] : substr($_SERVER['HTTP_ACCEPT_LANGUAGE'], 0, 2));



} else {
	
	?>
	<script>
	
	  	  window.location.replace("https://quest-mill.intertech.de/61/my");

  
</script>
	<?


	
die();
	
	
}



?>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>geoquest <? if($url == "template"){ ?>| %%_GEOQUEST_CONTENT_TITLE_%%<? } ?></title>
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

.en{
	
	display:none;
}
.de{
display:none;	
}


.btn-success {
  background-color: hsl(218, 23%, 27%) !important;
  background-repeat: repeat-x;
  	text-decoration: none !important;

  background-color: hsl(207, 54%, 70%) !important;
  background-repeat: repeat-x;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr="#b0cee7", endColorstr="#89b6db")!important;
  background-image: -khtml-gradient(linear, left top, left bottom, from(#b0cee7), to(#89b6db))!important;
  background-image: -moz-linear-gradient(top, #b0cee7, #89b6db)!important;
  background-image: -ms-linear-gradient(top, #b0cee7, #89b6db)!important;
  background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #b0cee7), color-stop(100%, #89b6db))!important;
  background-image: -webkit-linear-gradient(top, #b0cee7, #89b6db)!important;
  background-image: -o-linear-gradient(top, #b0cee7, #89b6db)!important;
  background-image: linear-gradient(#b0cee7, #89b6db)!important;
  border-color: #89b6db #89b6db hsl(207, 54%, 67.5%)!important;
  color: #333 !important;
  text-shadow: 0 1px 1px rgba(255, 255, 255, 0.16)!important;
  -webkit-font-smoothing: antialiased!important;

}

.container{

width:100% !important;	
	
}

.menu{
position:fixed; left:0px; height:100%; z-index:10; white-space:nowrap; overflow-x:hidden; overflow-y:scroll; z-index:20;
	
}


.main{
	
position:absolute; right:0px;  min-height:100%; background-color:#f6f9fe;	z-index:19;
}

	 @media (max-width: 1139px) {

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
<? if($url == "template"){ ?>
<link href="https://quest-mill.intertech.de/assets/css/bootstrap.css" rel="stylesheet">
      <link href="https://quest-mill.intertech.de/assets/css/bootstrap-responsive.css" rel="stylesheet">
<link href="https://quest-mill.intertech.de/assets/docs/datatables.css" id="dtCss" rel="stylesheet">
<? } ?>

<base href="https:/quest-mill.intertech.de/assets/<?=$url?>/" />
<link rel="icon" href="../favicon.png" type="image/x-icon" />

</head>

<body  onload="javascript:load();" link="#000000" vlink="#000000" alink="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div>




<img src="../icons/menu.png" style="z-index:1000000; position:fixed; top: 10px; left:10px; cursor:pointer;" class="showmenu"  width="30"/>
<div style="position:fixed; top:0; left:0; width:100%; background-image:url(../newlogo_bg.png); background-position:center; height:50px; z-index:100;">
<div style="position:absolute; top:0; left:0; width:100%; background-size: 2000px 50px; background-image:url(../newlogo_banner.jpg); background-position:center; background-repeat: no-repeat; height:50px; z-index:100;">

&nbsp;
</div></div>

<div class="menu" >

<div id="menu_content" style="position:absolute; left:10px; top:55px; width:100%; padding-left:5%;  white-space:nowrap;">
<br/><br/>

  
 <script type="text/javascript">
    function SetLanguage(l)
    {

	var url ="https://quest-mill.intertech.de/setLanguage/"+l;
$.ajax({
url: url,
dataType: 'html',
success: function(data){


    document.location.reload();
    
       
   }
   
   });
   
   }

</script>

<img style="cursor:pointer;" onClick="javascript:SetLanguage('de');" alt="Deutsch" src="https:/quest-mill.intertech.de/assets/flags/de.png" width="30" />
<img style="cursor:pointer;" onClick="javascript:SetLanguage('en');" alt="English" src="https:/quest-mill.intertech.de/assets/flags/en.jpg" width="30" />


<br/><br/>		
  <ul style="list-style-type: none; padding:0; margin: 0 0 0 0;">
  
  <? if($url == "template"){ ?>
              <li>%%_GEOQUEST_NAV_LI_%%</li>
            <?  } else {
				
				
				
				if(!isset($_GET['portal'])) {
              
?> 






<li %NOT_INCLUDED%"><a href="https://quest-mill.intertech.de/61/my">
Meine Spiele
</a></li>









<li %NOT_INCLUDED%>   
          <a href="https://quest-mill.intertech.de/61/logout" >Abmelden</a>
</li>






<?         } 
	
	  
              } 
			  
			  
			  
			  	if(!isset($_GET['portal'])) { ?>
              <li><a href="../index.php?content=Download">App Download</a></li>
              <? } ?>

  </ul>
  <br/>
  <h4>Tutorials</h4>
  <span class="en">
  English translation coming soon
  </span>
  <span class="de">
<h5><span class="de">Die erste eigene Quest</span></h5> 
<a href="../index.php?content=ErstelleneinerQuest<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>"><span class="de">Erstellen einer Quest</span><span class="en">Creating a quest</span></a>
<a href="../index.php?content=VonSeitenzuAktionen<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>"><span class="de">Von Seiten zu Aktionen</span></a>
<a href="../index.php?content=DerQuestEditor<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>"><span class="de">Der Quest Editor</span></a>
<a href="../index.php?content=Seiten<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>"><span class="de">Seiten</span></a>
<a href="../index.php?content=Orte<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">Orte</a><br />
<a href="../index.php?content=Ereignisse<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">Ereignisse</a><br />
<a href="../index.php?content=Bedingungen<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">Bedingungen</a><br/>
<br />
<h5>Seiten</h5>
<a href="../index.php?content=Auswahlmenue<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">Auswahlmenü</a><br />
<a href="../index.php?content=AudioAufnahme<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">Audio Aufnahme</a><br />
<a href="../index.php?content=FotoAufnahme<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">Foto Aufnahme</a><br />
<a href="../index.php?content=FrageMultipleChoice<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">Frage (Multiple Choice)</a><br />
<a href="../index.php?content=FrageText<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">Frage (Text)</a><br />
<a href="../index.php?content=KarteOpenStreetMap<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">Karte (Google/OpenStreetMap)</a><br />
<a href="../index.php?content=NPCDialog<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">NPC Dialog</a><br />
<a href="../index.php?content=TagScanner<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">Tag Scanner</a><br />
<a href="../index.php?content=TextmitBild<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">Text mit Bild</a><br />
<a href="../index.php?content=VideoWiedergabe<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">Video Wiedergabe</a><br />
<a href="../index.php?content=VollformatBild<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">Vollformat Bild</a><br />
<a href="../index.php?content=Webseite<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">Webseite</a>
<br />
<h5>Aktionen</h5> 
<p>
<a href="../index.php?content=NaechsteSeite<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">Nächste Seite</a> <br /> 
 <a href="../index.php?content=LetzteSeite<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>"> Letzte Seite</a><br />
 <a href="../index.php?content=Seiteaufrufen<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>"> Seite aufrufen</a><br />
 <a href="../index.php?content=Questbeenden<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>"> Quest beenden</a><br />
 <a href="../index.php?content=Vibrieren<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>"> Vibrieren</a><br />
  <a href="../index.php?content=Variableum1verringern<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>"> Variable um 1 verringern</a><br />
 <a href="../index.php?content=Variableum1erhoehen<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>"> Variable um 1 erhöhen</a><br />
  <a href="../index.php?content=Audio-Dateiabspielen<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">Audio-Datei abspielen</a>
  <br />
 <a href="../index.php?content=VariableneuenWertzuweisen<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>"> Variable neuen Wert zuweisen</a><br />
 <a href="../index.php?content=Nachrichtanzeigen<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>"> Nachricht anzeigen</a><br />
  <a href="../index.php?content=Hotspot-Zustandveraendern<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">Hotspot-Zustand verändern</a><br />
 <a href="../index.php?content=Scoreerhoehen<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>"> Score erhöhen</a><br />
  <a href="../index.php?content=Kartezentrierenauf<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">Karte zentrieren auf</a><br />
  <a href="../index.php?content=Wenn-Dann-Bedingung<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>">Wenn-Dann-Bedingung</a><br />
 <a href="../index.php?content=Solange-Wie-Schleife<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>"> Solange-Wie-Schleife</a><br />
 <a href="../index.php?content=Von-Bis-Schleife<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>"> Von-Bis-Schleife</a><br />
 <a href="../index.php?content=Schleifeunterbrechen<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>"> Schleife unterbrechen</a><br />
 <a href="../index.php?content=Routinghinzufuegen<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>"> Routing hinzufügen</a>
  <br />
<div style="display:none">
<h5>Experten-Features</h5>
<p>Variablen<br />
  Text-Formatierung<br />
  Reguläre Ausdrücke<br />
<br />
</p>
<h5>Administration</h5> 
<p>Benutzer Rechte<br />
  Spieltypen<br />
  Spieltyp Migration<br />
  Spieltyp erstellen<br />
  Portal Benutzer<br />
  Portal Design<br />
 
 
  </div>
  </span>
  <br />
 <br/>
 <br/>
 <hr/>
   <a href="../index.php?content=Impressum<? if(isset($_GET['portal'])){?>&portal=<?=$_GET['portal']?><? } ?>"> <span class="de">Impressum</span><span class="en">Imprint</span></a>

 
  
</p>
</div>

</div>

<div class="main">
<div id="tutorial_bump" style="height:55px; width:90%; margin-right:5%;">&nbsp;
</div>
<div id="tutorial_content" style=" width:90%; padding-left:5%;">
<? if($url == "template"){  ?>
	<div class="row-fluid">
      <h1>%%_GEOQUEST_CONTENT_TITLE_%%  </h1>
	</div>	

<div class="row-fluid">

%%_GEOQUEST_CONTENT_CODE_START_%%{ %general.button.attributes:=!!" class="btn btn-primary" "!!;; %general.speciallinks.attributes:=!!" class="btn btn-success" "!!;; %general.navtabs.attributes:=!!" class="btn btn-success" !!";; %general.tabs.type:=button-group% }
</div>
<? } else {
	
	
	 include($url."/index.html"); 
	 include($url."/index.php"); 

 } ?>


</div>


</div>
</div>
<? if($url == "template"){ ?>
<script src="https://code.jquery.com/jquery-2.0.0.js"></script>


  <script src="https://quest-mill.intertech.de/assets/js/jquery.dataTables.js"></script>
	<script src="https://quest-mill.intertech.de/dataTables/geoquest.js"></script>
        <script src="https://quest-mill.intertech.de/assets/js/bootstrap.js"></script>
        <? } else { ?>
        <script src="https://code.jquery.com/jquery-2.0.0.js"></script>

<? } ?>
<script>

var boxOne = document.getElementsByClassName('menu')[0];
var boxTwo = document.getElementsByClassName('main')[0];






var geoquest_menu_in=getCookie("geoquest_menu_in") || 'true';



if ($(window).width() < 1139) {
 boxOne.classList.remove('fadein');
	    boxOne.classList.add('fadeout');
		 boxTwo.classList.remove('menufadein');
		 boxTwo.classList.add('menufadeout');
} else {
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




function getElementsByClassName(classname, node)  {
    if(!node) node = document.getElementsByTagName("body")[0];
    var a = [];
    var re = new RegExp('\\b' + classname + '\\b');
    var els = node.getElementsByTagName("*");
    for(var i=0,j=els.length; i<j; i++)
        if(re.test(els[i].className))a.push(els[i]);
    return a;
}







function changeLanguage(lang){

var elements = new Array();
elements = getElementsByClassName('en');
for(i in elements ){
     elements[i].style.display = "none";
}

var elements = new Array();
elements = getElementsByClassName('de');
for(i in elements ){
     elements[i].style.display = "none";
}


if(lang.indexOf("de") > -1){

var elements = new Array();
elements = getElementsByClassName('de');
for(i in elements ){
     elements[i].style.display = "block";
}

}

if(lang.indexOf("en") > -1){

var elements = new Array();
elements = getElementsByClassName('en');
for(i in elements ){
     elements[i].style.display = "block";
}

}





}



function setCookie(c_name,value,exdays)
{
var exdate=new Date();
exdate.setDate(exdate.getDate() + exdays);
var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
document.cookie=c_name + "=" + c_value;
}


function load(){





var l = getCookieValue('PLAY_LANG');
console.log(l);
if(l != "de" && l != "en"){
	
    
    
        var ua = window.navigator.userAgent;
            var msie = ua.indexOf("MSIE ");

            if (msie > 0)      // If Internet Explorer, return version number
	l = window.navigator.systemLanguage;
                else         
            l = window.navigator.language;
            
            


}



changeLanguage(l);



}


function getCookieValue(c_name)
        {
        var c_value = document.cookie;
        var c_start = c_value.indexOf(" " + c_name + "=");
        if (c_start == -1)
          {
          c_start = c_value.indexOf(c_name + "=");
          }
        if (c_start == -1)
          {
          c_value = null;
          }
        else
          {
          c_start = c_value.indexOf("=", c_start) + 1;
          var c_end = c_value.indexOf(";", c_start);
          if (c_end == -1)
          {
        c_end = c_value.length;
        }
        c_value = unescape(c_value.substring(c_start,c_end));
        }
        return c_value;
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
