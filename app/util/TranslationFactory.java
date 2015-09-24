package util;

public class TranslationFactory {

	
	
	
	
	
	public TranslationFactory(){
		
		
		
		
	}
	
	
	
	public void buildTranslationMaps(){
		
		
		
		// DATE
		
		addTranslation("dd.MM.yyyy HH:mm", "MM.dd.yyyy HH:mm");

		
		
		// NAVIGATION
		
		addTranslation("Neuigkeiten","Newsstream");
		addTranslation("Öffentliche Spiele","Public Games");
		addTranslation("Registrieren","Signup");
		addTranslation("Einloggen","Login");
		addTranslation("Meine Spiele","My games");
		addTranslation("User-Rechte","User Rights");
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
		addTranslation("Löschen","Delete");
		addTranslation("In Spieltyp umwandeln","Transform into Gametype");
		addTranslation("Keine Rechte","No rights");
		addTranslation("Letztes Update","Last Update");
	
		
		// NEW GAME
		
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
