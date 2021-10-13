<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "https://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="https://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>geoquest tutorial</title>
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
<base href="https://www.quest-mill.com/tutorials/<?=$url?>/index.html" />
</head>

<body link="#000000" vlink="#000000" alink="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div>
<div style="position:absolute; top:0; left:0; width:100%;background-scale:2000px 50px; background-image:url(../newlogo_banner.jpg); background-position:center; height:92px; z-index:100;">
&nbsp;</div>

<div id="menu" style="position:absolute; left:0px; width:20%; height:100%; z-index:10;">

<div id="menu_content" style="position:absolute; left:10px; top:95px; width:100%; padding-left:5%;">
<br/><br/>
<a href="../index.php?content=Start">Wilkommen</a><br />
  <br />
<h3>Die erste eigene Quest</h3> 
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
  Typen<br />
  Typ Migration<br />
  Typ erstellen<br />
  Portal Benutzer<br />
  Portal Design<br />
  <br />
</p>
</div>

</div>

<div id="tutorial" style="position:absolute; right:0px; width:76%; min-height:1500px; background-color:#f6f9fe;">
<div id="tutorial_bump" style="height:95px; width:90%; margin-right:5%;">&nbsp;
</div>
<div id="tutorial_content" style=" width:90%; padding-left:5%;">&nbsp;

<? include($url."/index.html"); ?>
</div>


</div>
</div>
</body>
</html>
