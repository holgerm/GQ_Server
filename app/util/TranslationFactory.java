package util;

public class TranslationFactory {

	
	
	
	
	
	public TranslationFactory(){
		
		
		
		
	}
	
	
	
	public void buildTranslationMaps(){
		
		
		
		// DATE
		
		addTranslation("dd.MM.yyyy HH:mm", "MM/dd/yyyy HH:mm");
		addTranslation("dd.MM.yyyy","MM/dd/yyyy");
		
		
		// NAVIGATION
		
		addTranslation("Neuigkeiten","Newsstream");
		addTranslation("Öffentliche Spiele","Public Games");
		addTranslation("Registrieren","Signup");
		addTranslation("Einloggen","Login");
		addTranslation("Meine Spiele","My games");
		addTranslation("User-Rechte","User Permissions");
		addTranslation("Meine Portale","My Portals");
		addTranslation("Registrierung mit GQ-Account","Signup with GQ Account");
		addTranslation("Abmelden","Logout");
		
		addTranslation("Eingeloggt als","Logged in as");
		addTranslation("Für dieses Portal unbestätigt", "Unverified for this portal");
		addTranslation("Profil","Profile");

		// MY GAMES
		addTranslation("Achtung!","Attention!");
		addTranslation("Aktuell kann es auf Grund von Entwicklungsarbeiten zu kurzzeitigen Serverausfällen oder unerwartetem Verhalten kommen.",
				"Due to development works there may be short server outages or unintended behaviour.");
		addTranslation("Neues Spiel erstellen","Create new game");
		addTranslation("Kopie","Copy");
		
		addTranslation("Editor aufrufen","Open editor");
		addTranslation("Letztes Update","Last Update");
		addTranslation("Name","Name");
		addTranslation("Spieltyp","Gametype");
		addTranslation("Download","Download");
		addTranslation("Optionen","Settings");
		addTranslation("Info","Info");
		addTranslation("Noch keine XML-Daten","No XML-Data yet");
		addTranslation("Editieren","Edit");
		addTranslation("Administration","Administration");
		addTranslation("Duplizieren","Duplicate");
		addTranslation("Wirklich löschen?","Are you sure about deleting this?");
		addTranslation("Wirklich veröffentlichen?","Are you sure about publishing this quest?");

		addTranslation("Löschen","Delete");
		addTranslation("In Spieltyp umwandeln","Transform into Gametype");
		addTranslation("Keine Rechte","No permissions");
		addTranslation("Letztes Update","Last Update");
	
		
		// NEW GAME
		addTranslation("Neues Spiel erstellen","Create new Game");

		addTranslation("Spielname","Gamename");
		addTranslation("Spieltyp","Gametype");
		addTranslation("Öffentlich?","Public?");
		addTranslation("Zip-Datei","Zip-file");
		addTranslation("Erstellen","Create");
		
		// GAME TYPES
		addTranslation("beliebiges Spiel", "any desired game");
		addTranslation("beliebiges Spiel (alt)", "any desired game (old)");
		
		
		
		// NEW GAMETYPE
		
		addTranslation("Spiel in Spieltyp umwandeln","Transform Game into Gametype");
		addTranslation("Spieltyp Name","Gametype Name");
		addTranslation("Szene statt Standard-Inhalt","Scene instead of default content");
		addTranslation("Nur Hotspots in Editor","Only hotspots in editor");
		addTranslation("Ein Hotspot pro Spiel?","One hotspot per game?");
		addTranslation("Hotspottyp Name","Hotspottype Name");
		
		
		
		
		// EDIT PORTAL
		addTranslation("Allgemein","General");
		addTranslation("Verschlüsselung","Encryption");
		addTranslation("Layout","Layout");
		addTranslation("Erweitert","Advanced");
		
		addTranslation("Abspeichern","Save");
		
		addTranslation("Portalname","Portal name");
		addTranslation("User automatisch zulassen","Activate users automatically");
		addTranslation("Template URL","Template URL");
		
		addTranslation("HTML-Usernamefeld-Name","username HTML-field name");
		addTranslation("Username","Username");
		addTranslation("HTML-Passwortfeld-Name","password HTML-field name");
		addTranslation("Passwort (wird nur neu gesetzt, wenn neue Eingabe gemacht wird)","Password (only gets replaced, if there is an input)");
		addTranslation("HTML-Submitbutton-Name","submit button name");
		addTranslation("Nach erfolgreichem Einloggen URL aufrufen:","Access-URL after successful Authentication:");
		
		addTranslation("Mapping-Datei-URL:","Mapping file URL:");
		addTranslation("Eigene Server-URL (Redirecter-URL):","Own server URL (Redirecter-URL)");
		addTranslation("Blog-Connector-URL:","Blog-Connector URL:");
		
		addTranslation("Standard-Farbe","Default color");
		addTranslation("Komplementär-Farbe","Complementary color");
		addTranslation("Verlaufs-Farbe","Gradient color");
		addTranslation("Hintergrund-Farbe","Background color");
		addTranslation("Link-Farbe","Link color");
		addTranslation("Logo-Datei (*.jpg,*.png)","Logo file (*.jpg,*.png)");
		addTranslation("CSS (wird in Header eingefügt):","CSS (is added to <head>)");
		
		
		
		// GAME RIGHTS
		
		
		addTranslation("Administration von","Administration of");
		
		addTranslation("Update verfügbar!","Update avalilable!");
		addTranslation("Eine neue Version dieses Spieltyps ist verfügbar! Bitte beachte, dass ein Update auch Datenverluste mit sich bringen kann, falls sich Attributnamen im Spieltyp geändert haben. Bitte beachte dazu den Changelog der GeoQuest-Version.",
				"A new version of this gametype is available! Please remember, that an update can also produce loss of data, if attribute names in the gametype have changed. Please consider looking at the changelog for the current geoquest-version.");
		addTranslation("Update ausführen","Proceed with update");
		
		addTranslation("Veröffentlichen","Publish");
		
		addTranslation("Bisher keine Rechte eingetragen.","No permissions added yet.");
		addTranslation("Rechte","Permissions");
		addTranslation("Schreibrechte","Writing Permission");
		addTranslation("Auf Leserechte reduzieren","Reduce to reading permissions");
		addTranslation("Alle Rechte entziehen","Take away all rights");
		addTranslation("Nur Leserechte","Just Reading Permission");
		addTranslation("Schreibrechte geben","Give writing permission");
		addTranslation("Öffentliche Leserechte","Public reading permission");
		addTranslation("Leserechte geben","Give reading permission");
		addTranslation("Lese/Schreibrechte geben","Give reading & writing permission");
		addTranslation("User hinzufügen","Add user");
		addTranslation("Suche nach E-Mail-Adresse","Search for E-Mail adress");
		addTranslation("Suche nach Name","Search for name");
		addTranslation("Suche starten","Start searching");
		addTranslation("Keine User gefunden.","No users found.");
		addTranslation("Leserechte","Reading Permission");
		addTranslation("Lese/Schreibrechte","Reading & Writing Permission");
		
		
		
		// MY PORTALS
		
		addTranslation("Du bist nicht auf Portalen angemeldet.","You're not registered on this portal.");
		addTranslation("Aufrufen","Open");
		addTranslation("Spieltypen","Gametypes");
		
		
		
		// PAYMENT
		
		addTranslation("Zahlung fehlgeschlagen","Payment failed");
		addTranslation("Die Bezahlung war nicht erfolgreich.","The payment was unsuccessful.");
		addTranslation("Zahlung erfolgreich","Payment successful");
		addTranslation("Die Bezahlung war erfolgreich.","The payment was successful.");
		
		
		
		// PORTAL GAMETYPES
		addTranslation("Keine Spieltypen","No gametypes");
		addTranslation("Von diesem Portal entfernen","Delete from this portal");
		addTranslation("Aktuelle Spieltypen","Up-to-date Gametypes");
		addTranslation("Hinzufügen","Add");
		
		
		// PORTAL USER-RIGHTS
		
		addTranslation("Keine User","No users");
		addTranslation("bis","until");
		addTranslation("Einen Monat All Access hinzufügen","Add one month of All Access");
		addTranslation("Administrator","Administrator");
		addTranslation("Auf Benutzer reduzieren","Reduce to User");
		addTranslation("Benutzer","User");
		addTranslation("Admin-Rechte geben","Give Admin Permissions");
		addTranslation("Nicht verifiziert","Unverified");
		addTranslation("User-Rechte geben","Give User Permissions");
		addTranslation("Antrag wirklich ablehnen?","Are you sure about rejecting the application?");
		addTranslation("Antrag ablehnen","Reject Application");
	


		// NEWSSTREAM
		
		addTranslation("Keine Neuigkeiten.","No news.");
		
		
		// Öffentliche Spiele
		addTranslation("Bisher keine öffentlichen Spiele für dieses Portal eingetragen.","There are no public games on this portal yet.");
		addTranslation("Starten","Start");
		addTranslation("In geoquest App öffnen","Open in geoquest App");
		
		
		// LOGIN
		addTranslation("Passwort vergessen","Forgot password");
		addTranslation("Informationen zum Zurücksetzten senden","Send password reset information");
		addTranslation("Es ist mir wieder eingefallen","I just remembered my password");
		addTranslation("Deine E-Mail-Adresse","Your e-mail address");
		addTranslation("Login","Login");
		addTranslation("E-Mail","E-Mail");
		addTranslation("Passwort","Password");
		addTranslation("Neuen Account anlegen","Create a new account");
		addTranslation("Account anlegen","Create account");
		addTranslation("Vor- und Nachname","Full name");
		addTranslation("Passwort (mindestens 5 Zeichen)","Password (at least 5 chars)");
		addTranslation("Passwort wiederholen","Repeat password");
		addTranslation("Account erstellen","Create account");
		addTranslation("Ich habe schon einen Account","I already have an account");
	

		// ERRORS
		
		addTranslation("Die Rechteverwaltung hat den Zugriff auf diese Seite mit der folgenden Begründung verwehrt:",
				"The security controller has rejected the access of this page with the following explanation:");
		addTranslation("Anscheinend bist du nicht für dieses Portal freigeschaltet.",
				"It seems like your geoquest account is not registered on this portal.");
		addTranslation("Mit diesem Account anmelden","Register with this account");
		addTranslation("Anderen Account benutzen","Use another account");
		addTranslation("<b>Bitte habe noch ein wenig Gedud.</b><br/>Deine Anfrage befindet sich noch in der Bearbeitung.<br/>Solltest du nach einer Woche noch keine Antwort erhalten, melde dich bitte per E-Mail unter<br/><a href='mailto:contact@@quest-mill.com'>contact@@quest-mill.com</a>",
				"<b>Please be patient.</b><br/>Your application is still being processed.<br/>If this message doesn't change after a week, please send an E-Mail to <br/><a href='mailto:contact@@quest-mill.com'>contact@@quest-mill.com</a>");
		addTranslation("Die Seite","The page");
		addTranslation("konnte nicht gefunden werden. Kontaktieren Sie den Webmaster oder versuchen Sie es erneut. Benutzen Sie den <b>Zurück</b>-Button Ihres Browsers um zu der Seite, von der sie ursprünglich kommen, zurückzugelangen.",
				"couldn't be found. Please contact the webmaster or try it again. Use the <b>Back</b>-Button of your browser to access the page you come from.");
		addTranslation("Oder Sie könnten diesen Button verwenden:","Or you could use this button:");
		addTranslation("Zur Startseite","Open start page");
		
		
		
		// EDITOR
		addTranslation("Speichern","Speichern");
		addTranslation("Veröffentlichen","Publish");
		addTranslation("Karte auf Hotspots zentrieren","Center Map on Hotspots");
		addTranslation("Alle Fenster schließen","Close all windows");
		addTranslation("Erzeugtes XML anzeigen","Display produced XML");
		addTranslation("<p>Die Benutzung von Internet Explorer wird von GeoQuest nicht unterstützt. </p><p>Bitte öffnen Sie diese Seite erneut in einem der unterstützten Browser:</p>",
						"<p>Die Usage of Internet Explorer is not supported with geoquest.</p><p>Please reopen the page in one of our supported browsers:</p>");
		addTranslation("Display-Größe","Display size");
		addTranslation("Hilfe","Help");
		addTranslation("Die GPS-Position kann durch die Pfeiltasten simuliert werden. <br/><br/>Durch Klicken in den Player und ein darauf folgendes gleichzeitiges Drücken der Tasten 'G','E','O' und 'Q' kann der Player zurückgesetzt werden.",
						"The GPS position can be simulated with the arrow keys. <br/><br/> By clicking into the player and holding down the keys 'G','E','O' and 'Q' at the same time, the player can be reset.");
		addTranslation("Variablen","Variables");
		addTranslation("Log","Log");
		addTranslation("Kartenansicht verlassen","Exit map view");
		
		
		addTranslation("Seiten","Pages");
		addTranslation("Neue Seite","New Page");
		addTranslation("neue_seite.png","new_page.png");
		addTranslation("Hotspots","Hotspots");
		addTranslation("Menü-Einträge","Menu Entries");
		addTranslation("Einstellungen","Settings");
		addTranslation("Aktuelle Datei anzeigen","Show current file");
		
		addTranslation("Anleitung","HowTo");
		addTranslation("Erweitert","Advanced");
		
		addTranslation("Resultat","Result");
		addTranslation("Status","State");
		addTranslation("Sichtbarkeit","Visibility");
		addTranslation("Aktivität","Activity");
		addTranslation("Longitude","Longitude");
		addTranslation("Latitude","Latitude");
		addTranslation("Operatoren","Operators");
		addTranslation("Gleich","Equal");
		addTranslation("Ungleich","Unequal");
		addTranslation("Kleiner als","Smaller than");
		addTranslation("Größer als","Bigger than");
		addTranslation("Kleiner oder gleich","Smaller or equal");
		addTranslation("Größer oder gleich","Bigger or equal");
		addTranslation("logische Verknüpfungen","logic conjunctions");
		addTranslation("Alle Bedingungen müssen eintreten","All conditions have to be true");
		addTranslation("Eine Bedingung muss eintreten","One condition has to be true");
		addTranslation("Alle Bedingungen in der Klammer negieren","Negate all conditions in the brackets");
		addTranslation("Aktuelle Position","Current position");
		addTranslation("Score","Score");
		addTranslation("Variable","Variable");
		addTranslation("GPS-Position","GPS position");
		addTranslation("Variabel","variable");
		addTranslation("Datei","file");
		addTranslation("Hinzufügen","Add");
		
		
		
		addTranslation("Du benötigst die Vollversion des Geoquest-Editors, um diese Funktion zu nutzen.",
						"You need the full version of the geoquest editor to access this function.");
		addTranslation("Jetzt für die Laufzeit von <b>12 Monaten</b> für <b>29,99€</b> kostenpflichtig bestellen.",
						"Order now for the duration of <b>12 months</b> for only <b>29,99€</b>.");
		addTranslation("ACHTUNG! NUR TESTIMPLEMENTIERUNG! NICHT BENUTZEN!",
					"ATTENTION! ONLY IMPLEMENTED FOR TESTING PURPOSES! DON'T USE!");
		
		addTranslation("Inhalte","Contents");
		addTranslation("Ereignisse","Events");
		addTranslation("Neue Aktion","New Action");
		addTranslation("Neuer Inhalt","New Content");
		addTranslation("in Content","in Content");
		addTranslation("Inhaltstyp","Contenttype");
		addTranslation("Neue Seite","New page");
		addTranslation("Vorschau","Preview");
		addTranslation("Inhalt","Content");
		addTranslation("neuer_inhalt.png","new_content.png");
		addTranslation("in Seite","in Page");
		addTranslation("Hotspottyp","Hotspottype");
		addTranslation("in","in");
		addTranslation("Neue Regel","New rule");
		addTranslation("Seite","Page");
		addTranslation("Einstellungen für Unterseiten","Settings for subpages");
		addTranslation("Noch nicht gespeichert.","Not saved yet.");
		addTranslation("Setzen","Place");
		addTranslation("Sorry, die Adresse wurde nicht gefunden.","Sorry, the address wasn't found.");
		addTranslation("Wo ist der Ort den du suchst?","Type in an address to search");
		
		addTranslation("Hochgeladenes Bild nicht gefunden","Uploaded image not found");

		
		
		
		
		
		// PAGE TYPES + SCENE TYPES
		addTranslation("Vollformat Bild","Fullscreen Image");
		addTranslation("Frage (Multiple Choice)","Question (Multiple Choice)");
		addTranslation("Auswahlmenü","Menu");
		addTranslation("Text mit Bild","Text with Image");
		addTranslation("NPC Dialog","NPC Dialog");
		addTranslation("Karte","Map");
		addTranslation("Audio Aufnahme","Audio Record");
		addTranslation("Frage (Text)","Question (Text)");
		addTranslation("QR Tag Scanner","QR Tag Scanner");
		addTranslation("QR Tag Scanner (veraltet)","QR Tag Scanner (old)");
		addTranslation("Video Wiedergabe","Video Playback");
		addTranslation("Webseite","Website");
		addTranslation("Foto Aufnahme","Photo Camera");
		addTranslation("Meta-Daten","Meta Data");
		addTranslation("Missions-Container","Page Folder");
	
		
		
		// GAME SETTINGS
		addTranslation("Startseite","Start Page");
		addTranslation("Autor","Author");
		addTranslation("Version","Version");
		addTranslation("Impressum","Imprint");
		addTranslation("Altersfreigabe","Age Restriction");
		addTranslation("Icon","Icon");
		addTranslation("Featured Bild","Featured Image");
		addTranslation("Hintergrundbild","Background Image");
		addTranslation("Hintergrundfarbe","Background Color");
		addTranslation("Rückkehrverhalten selbst definieren","Custom Return Behaviour");
		addTranslation("Start Hotspot","Start Hotspot");
		addTranslation("An Ort des Benutzers transferieren","Transfer to user location");
		addTranslation("Transfer Hotspot","Transfer Hotspot");

		
		
		// ACTION TYPES
		addTranslation("Questverlauf","Course of Quest");

		addTranslation("Nächste Seite","Next Page");
		addTranslation("Letzte Seite","Last Page");
		addTranslation("Seite aufrufen","Specific Page");
		addTranslation("Quest beenden","End Quest");
		
		addTranslation("Hotspot-Zustand verändern","Change Hotspot State");
		addTranslation("Karte zentrieren","Center Map");

		addTranslation("Routing anzeigen","Show route");
		addTranslation("Route löschen","Delete route");
		
		addTranslation("Variable um 1 verringern","Subtract 1 from variable");
		addTranslation("Variable um 1 erhöhen","Add 1 to variable");
		addTranslation("Variable neuen Wert zuweisen","Allocate new value to variable");
		addTranslation("Score erhöhen","Increase Score");
		addTranslation("Variable abspeichern","Save variable");
		addTranslation("Variable laden","Load variable");
		addTranslation("Variablen-Ansicht anzeigen","Show variable view");
		addTranslation("Variablen-Ansicht ausblenden","Hide variable view");
		
		addTranslation("Bedingt","Conditional");
		
		addTranslation("Wenn-Dann-Bedingung","If-Then-Condition");
		addTranslation("Solange-Wie-Schleife","While-Loop");
		addTranslation("Von-Bis-Schleife","For-To-Loop");
		addTranslation("Schleife unterbrechen","Break out of loop");
		
		
		addTranslation("Sonstiges","Other");
		
		addTranslation("Vibrieren","Vibrate");
		addTranslation("Audio-Datei abspielen","Play audio file");
		addTranslation("Nachricht anzeigen","Display message");
		
		addTranslation("Variable an Server senden","Send Variable to server");
		addTranslation("Variable von Server laden","Load Variable from server");
		addTranslation("Quest starten","Start quest");

		
		// RULE TRIGGER
		
		addTranslation("Ende der Mission","End of page");
		addTranslation("Start der Mission","Start of page");
		addTranslation("Antippen des Bildschirms","Tap on screen");
		
		addTranslation("Betreten des Hotspots","Enter Hospot Radius");
		addTranslation("Verlassen des Hotspots","Leave Hotspot Radius");
		addTranslation("Antippen des Hotspots","Tap on Hotspot");
		
		addTranslation("Nach einer erfolgreichen Interaktion","Successful Interaction");
		addTranslation("Nach einer erfolglosen Interaktion","Failed Interaction");
		
		
		
			
		
		
		
		
		
		// SETTING FIELDS (FOR ANYTHING)
		addTranslation("Bild (*.jpg, *.png, *.gif, *.zip)","Image (*.jpg, *.png, *.gif, *.zip)");
		addTranslation("Anzeigedauer(s)","Display duration (s)");
		addTranslation("Bildfrequenz (frames per second)","Image Frequency (frames per second) ");
		addTranslation("Wiederholen?","Loop?");
		addTranslation("Antwort","Answer");
		addTranslation("Richtig?","Correct?");
		addTranslation("Bild","Image");
		addTranslation("Fragetext","Question text");
		addTranslation("Bis zum Erfolg wiederholen?","Repeat until success?");
		addTranslation("Feedback bei Wiederholung","Feedback on Repeat");
		addTranslation("Antworten zu Beginn mischen","Shuffle answers");
		addTranslation("Nur Bilder anzeigen?","Show only images?");
		addTranslation("Hintergrund","Background");
		addTranslation("Eintrag","Entry");
		addTranslation("Überschrift","Headline");
		addTranslation("Text","Text");
		addTranslation("Beenden-Button-Beschriftung","End-Button caption");
		addTranslation("Textgröße","Text size");
		addTranslation("Dialog-Item","Dialog Item");
		addTranslation("Sprecher","Speaker");
		addTranslation("Audio-Datei","Audio file");
		addTranslation("Bis Ende von Audio-Datei blockieren?","Block until audio finished?");
		addTranslation("Modus","Mode");
		addTranslation("Komplett anzeigen","Whole text");
		addTranslation("Wordticker","Wordticker");
		addTranslation("Weiter-Button-Beschriftung","Next-Button caption");
		addTranslation("Wordticker überspringbar?","Wordticker skippable?");
		addTranslation("Wordticker-Geschwindigkeit (ms)","Wordticker speed (ms)");
		addTranslation("Zoom-Stufe","Zoom level");
		addTranslation("Abzuspeicherne Datei (Variable)","File to save (Variable)");
		addTranslation("Aufgabenbeschreibung","Task description");
		addTranslation("Antwortfeld, wenn keine Antwort","Answer field placeholder");
		addTranslation("Erwarteter Inhalt","Expected Content");
		addTranslation("Inhalt des gescannten Codes anzeigen?","Show content of scanned code?");
		addTranslation("Video-Datei","Video file");
		addTranslation("Kontrollierbar?","Controllable?");
		addTranslation("Online-URL","Online URL");
		addTranslation("Lokale Datei","Local file");
		addTranslation("Meta-Eintrag","Meta-Entry");
		addTranslation("Schlüssel","Key");
		addTranslation("Wert","Value");
		addTranslation("Rückkehr erlauben?","Allow return?");
		addTranslation("Neue Aktivität","New Activity");
		addTranslation("Neue Sichtbarkeit","New Visibility");
		addTranslation("Für alle Hotspots anwenden","Apply to all hotspots");
		addTranslation("Ort","Hotspot");
		addTranslation("Eigene Position einschließen","Include user position");
		addTranslation("Aktive Hotspots einschließen","Include active hotspots");
		addTranslation("Sichtbare Hotspots einschließen","Include visible hotspots");
		addTranslation("Von","From");
		addTranslation("Zu","To");
		addTranslation("Betrag","Amount");
		addTranslation("Bezeichnung","Caption");
		addTranslation("Wenn","If");
		addTranslation("Dann","Then");
		addTranslation("Sonst","Else");
		addTranslation("Solange Wie","While");
		addTranslation("Unendlichen Durchlauf erlauben","Allow infinite loop");
		addTranslation("Für Variable","For Variable");
		addTranslation("Bis","To");
		addTranslation("Audio-Datei (*.mp3,*.ogg)","Audio file (*.mp3)");
		addTranslation("Loop","Loop");
		addTranslation("Andere Sounds stoppen","Stop other sounds");
		addTranslation("Nachricht","Message");
		addTranslation("Button-Beschriftung","Button caption");
		addTranslation("unverändert","unchanged");
		addTranslation("aktiv","active");
		addTranslation("inaktiv","inactive");
		addTranslation("sichtbar","visible");
		addTranslation("unsichtbar","invisible");
		

		// Datatables - please keep "_MENU_" etc unchanged in translation
		
		addTranslation("Zeige _MENU_ Einträge pro Seite","Showing _MENU_ entries per page");
		addTranslation("Suche: ","Search: ");
		addTranslation("Zeige _START_ bis _END_ von _TOTAL_ Quests","Showing _START_ to _END_ of _TOTAL_ quests");
		addTranslation("Vorherige","Previous");
		addTranslation("Nächste","Next");

		
		
		
	}
	
	
	
	public void addTranslation(String german,String english){
		
		Global.en_Translation.put(german, english);	
		
	}
	
	
	public void addTranslation(String german,String english, String french){
		
		
		Global.en_Translation.put(german, english);
		Global.fr_Translation.put(german, french);

		
		
	}
	
	public void addTranslation(String german,String english, String french,String spanish){
	
	
		Global.en_Translation.put(german, english);
		Global.fr_Translation.put(german, french);
		Global.es_Translation.put(german, spanish);

	
	}

	
	
	
}
