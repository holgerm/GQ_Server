package gametypes;

import java.util.ArrayList;
import java.util.List;

import play.api.Routes;
import play.db.ebean.Model;
import models.Game;
import models.GameParts.*;
import util.Global;

public class GeoQuestDefaultsFactory {

	public GameType defaults;
	public MissionType generic;
	public MissionType screen;
	public MissionType frage;
	public MissionType menu;
	public MissionType textimage;

	public MissionType npctalk;
	public MissionType osmap;
	public MissionType googlemap;
	public MissionType audiorecord;
	public MissionType textquestion;
	public MissionType tagreading;

	public MissionType qrtagreading;
	public MissionType videoplay;
	public MissionType webpage;
	public MissionType imagecapture;
	public MissionType questlist;

	public MissionType metadata;

	public List<RuleType> allMissionRuleTypes;
	public List<RuleType> allHotspotRuleTypes;
	public HotspotType hpt1;
	public List<ActionType> allActionTypes;
	public List<ActionType> hotspotActionTypes;

	public GeoQuestDefaultsFactory() {

	}

	public List<RuleType> getHotspotRuleTypes() {

		return allHotspotRuleTypes;

	}

	public List<RuleType> getMissionRuleTypes() {

		return allMissionRuleTypes;

	}

	public List<ActionType> getActionTypes() {

		return allActionTypes;

	}

	public List<ActionType> getHotspotActionTypes() {

		return hotspotActionTypes;

	}

	public GameType addGameToDatabase() {

		// GAME TYPE

		GameType gt = new GameType("beliebiges Spiel");
		gt.save();

		AttributeType att62 = new AttributeType("Startseite",
				"Editor.StartMission", "mission");

		att62.save();

		gt.setAttributeType(att62);

		AttributeType gt_att2 = new AttributeType("Autor", "author", "String");
		gt_att2.save();

		gt.setAttributeType(gt_att2);

		AttributeType gt_att3 = new AttributeType("Version", "version",
				"String");
		gt_att3.save();

		gt.setAttributeType(gt_att3);

		AttributeType gt_att4 = new AttributeType("Impressum", "imprint",
				"TextArea");
		gt_att4.save();

		gt.setAttributeType(gt_att4);

		AttributeType gt_att5 = new AttributeType("Altersfreigabe", "age",
				"int");
		gt_att5.save();
		gt_att5.setDefaultValue("0");
		gt_att5.update();
		gt.setAttributeType(gt_att5);

		AttributeType gt_att6 = new AttributeType("Icon", "icon", "file");
		gt_att6.save();

		gt.setAttributeType(gt_att6);

		AttributeType gt_att7 = new AttributeType("Featured Bild",
				"featuredimage", "file");
		gt_att7.save();

		gt.setAttributeType(gt_att7);

		AttributeType gt_att1 = new AttributeType("Hintergrundbild", "bgimage",
				"file");
		gt_att1.save();

		gt.setAttributeType(gt_att1);

		AttributeType att61 = new AttributeType("Hintergrundfarbe", "bgcolor",
				"Color");

		att61.save();

		gt.setAttributeType(att61);

		AttributeType gt_att8 = new AttributeType(
				"Rückkehrverhalten selbst definieren",
				"individualReturnDefinitions", "boolean");
		gt_att8.save();
		gt_att8.setDefaultValue("false");
		gt_att8.update();
		gt.setAttributeType(gt_att8);

		AttributeType gt_att9 = new AttributeType("Start Hotspot",
				"startHotspot", "hotspot");
		gt_att9.save();
		gt.setAttributeType(gt_att9);

		AttributeType gt_att10 = new AttributeType(
				"An Ort des Benutzers transferieren", "transferToUserPosition",
				"boolean");
		gt_att10.save();
		gt_att10.setDefaultValue("false");
		gt_att10.update();
		gt.setAttributeType(gt_att10);

		AttributeType gt_att11 = new AttributeType("Transfer Hotspot",
				"transferHotspot", "hotspot");
		gt_att11.save();
		gt.setAttributeType(gt_att11);

		gt.update();

		// // TYPE SECTION

		// RULE TYPES

		allMissionRuleTypes = new ArrayList<RuleType>();

		RuleType rtOnSuccess = new RuleType("Nach einer erfolgreichen Interaktion",
				"onSuccess");
		rtOnSuccess.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/trigger/onsuccess.png");
		rtOnSuccess.save();
		allMissionRuleTypes.add(rtOnSuccess);

		RuleType rtOnFailure = new RuleType("Nach einer erfolglosen Interaktion",
				"onFailure");
		rtOnFailure.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/trigger/onfailure.png");
		rtOnFailure.save();
		allMissionRuleTypes.add(rtOnFailure);

		RuleType rtOnEnd = new RuleType("Ende der Mission", "onEnd");
		rtOnEnd.setSymbol(Global.SERVER_URL_2 + "/assets/icons/trigger/onend.png");
		rtOnEnd.save();
		allMissionRuleTypes.add(rtOnEnd);

		RuleType rtOnStart = new RuleType("Start der Mission", "onStart");
		rtOnStart.setSymbol(Global.SERVER_URL_2 + "/assets/icons/trigger/onstart.png");
		rtOnStart.save();
		allMissionRuleTypes.add(rtOnStart);

		allHotspotRuleTypes = new ArrayList<RuleType>();

		RuleType rtOnEnterHotSpot = new RuleType("Betreten des HotSpots", "onEnter");
		rtOnEnterHotSpot.setSymbol(Global.SERVER_URL_2 + "/assets/icons/trigger/onenter.png");
		rtOnEnterHotSpot.save();
		allHotspotRuleTypes.add(rtOnEnterHotSpot);

		RuleType rtOnLeaveHotSpot = new RuleType("Verlassen des HotSpots", "onLeave");
		rtOnLeaveHotSpot.setSymbol(Global.SERVER_URL_2 + "/assets/icons/trigger/onleave.png");
		rtOnLeaveHotSpot.save();
		allHotspotRuleTypes.add(rtOnLeaveHotSpot);

		RuleType rtOnTapHotSpot = new RuleType("Antippen des HotSpots", "onTap");
		rtOnTapHotSpot.setSymbol(Global.SERVER_URL_2 + "/assets/icons/trigger/ontap.png");
		rtOnTapHotSpot.save();
		allHotspotRuleTypes.add(rtOnTapHotSpot);

		RuleType rtOnTapScreen = new RuleType("Antippen des Bildschirms", "onTap");
		rtOnTapScreen.setSymbol(Global.SERVER_URL_2 + "/assets/icons/trigger/ontap.png");
		rtOnTapScreen.save();

		RuleType rt9 = new RuleType("Antippen des Objekts", "onTap");
		rt9.setSymbol(Global.SERVER_URL_2 + "/assets/icons/trigger/ontap.png");
		rt9.save();

		// ACTION TYPES

		allActionTypes = new ArrayList<ActionType>();
		hotspotActionTypes = new ArrayList<ActionType>();

		ActionType at1 = new ActionType("Nächste Seite", "next");
		at1.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/nextmission.png");
		at1.setCategory("page");
		at1.save();

		AttributeType at1a2 = new AttributeType("Rückkehr erlauben?",
				"allowReturn", "boolean");
		at1a2.save();
		at1a2.setDefaultValue("false");
		at1a2.update();
		at1.setAttributeType(at1a2);
		at1.update();

		allActionTypes.add(at1);

		ActionType at2 = new ActionType("Letzte Seite", "last");
		at2.setCategory("page");
		at2.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/lastmission.png");
		at2.save();

		AttributeType at2a2 = new AttributeType("Rückkehr erlauben?",
				"allowReturn", "boolean");
		at2a2.save();
		at2a2.setDefaultValue("false");
		at2a2.update();
		at2.setAttributeType(at2a2);
		at2.update();

		allActionTypes.add(at2);

		ActionType at3 = new ActionType("Seite aufrufen", "StartMission");
		at3.setCategory("page");
		at3.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/callmission.png");
		at3.save();

		AttributeType at3a1 = new AttributeType("Seite", "id", "mission");
		at3a1.save();
		at3.setAttributeType(at3a1);

		AttributeType at3a2 = new AttributeType("Rückkehr erlauben?",
				"allowReturn", "boolean");
		at3a2.save();
		at3a2.setDefaultValue("false");
		at3a2.update();
		at3.setAttributeType(at3a2);

		at3.update();
		allActionTypes.add(at3);
		hotspotActionTypes.add(at3);

		ActionType at4 = new ActionType("Quest beenden", "EndGame");
		at4.setCategory("page");
		at4.setSymbol(Global.SERVER_URL_2 + "/assets/icons/actions/endgame.png");
		at4.save();
		allActionTypes.add(at4);
		hotspotActionTypes.add(at4);

		ActionType at5 = new ActionType("Vibrieren", "Vibrate");
		at5.setCategory("other");
		at5.setSymbol(Global.SERVER_URL_2 + "/assets/icons/actions/vibrate.png");
		at5.save();

		allActionTypes.add(at5);
		hotspotActionTypes.add(at5);

		ActionType at6 = new ActionType("Variable um 1 verringern",
				"DecrementVariable");
		at6.setCategory("var");
		at6.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/decreasevar.png");
		at6.save();
		AttributeType at6a1 = new AttributeType("Variable", "var", "var");
		at6a1.save();
		at6.setAttributeType(at6a1);
		at6.update();
		allActionTypes.add(at6);
		hotspotActionTypes.add(at6);

		ActionType at7 = new ActionType("Variable um 1 erhöhen",
				"IncrementVariable");
		at7.setCategory("var");
		at7.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/increasevar.png");
		at7.save();
		AttributeType at7a1 = new AttributeType("Variable", "var", "var");
		at7a1.save();
		at7.setAttributeType(at7a1);
		at7.update();
		allActionTypes.add(at7);
		hotspotActionTypes.add(at7);

		ActionType at8 = new ActionType("Audio-Datei abspielen", "PlayAudio");
		at8.setCategory("other");
		at8.setSymbol(Global.SERVER_URL_2 + "/assets/icons/actions/sound.png");
		at8.save();
		AttributeType at8a1 = new AttributeType("Audio-Datei (*.mp3, *.ogg)",
				"file", "file");
		at8a1.save();
		at8.setAttributeType(at8a1);
		AttributeType at8a2 = new AttributeType("Loop", "loop", "boolean");
		at8a2.save();
		at8a2.setDefaultValue("false");
		at8a2.update();
		at8.setAttributeType(at8a2);
		AttributeType at8a3 = new AttributeType("Andere Sounds stoppen",
				"stopOthers", "boolean");
		at8a3.save();
		at8a3.setDefaultValue("true");
		at8a3.update();
		at8.setAttributeType(at8a3);

		at8.update();
		allActionTypes.add(at8);
		hotspotActionTypes.add(at8);

		ActionType at9 = new ActionType("Variable neuen Wert zuweisen",
				"SetVariable");
		at9.setCategory("var");
		at9.setSymbol(Global.SERVER_URL_2 + "/assets/icons/actions/setvar.png");
		at9.save();
		AttributeType at9a1 = new AttributeType("Variable", "var", "var");
		at9a1.save();
		at9.setAttributeType(at9a1);
		AttributeType at9a2 = new AttributeType("Wert", "value", "expression");
		at9a2.save();
		at9.setAttributeType(at9a2);
		at9.update();
		allActionTypes.add(at9);
		hotspotActionTypes.add(at9);

		ActionType at10 = new ActionType("Nachricht anzeigen", "ShowMessage");
		at10.setCategory("other");
		at10.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/message.png");
		at10.save();
		AttributeType at10a1 = new AttributeType("Nachricht", "message",
				"String");
		at10a1.save();
		at10.setAttributeType(at10a1);
		AttributeType at10a2 = new AttributeType("Button-Beschriftung",
				"buttontext", "String");
		at10a2.save();
		at10.setAttributeType(at10a2);
		at10.update();
		allActionTypes.add(at10);
		hotspotActionTypes.add(at10);

		ActionType hotspotzustand = new ActionType("Hotspot-Zustand verändern",
				"SetHotspotState");
		hotspotzustand.setCategory("map");
		hotspotzustand.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/hotspotactivity.png");
		hotspotzustand.save();
		AttributeType hotspotzustand_att2 = new AttributeType("Hotspot",
				"hotspot", "hotspot");
		hotspotzustand_att2.save();
		hotspotzustand.setAttributeType(hotspotzustand_att2);
		hotspotzustand.update();

		AttributeType hotspotzustand_att1 = new AttributeType("Neue Aktivität",
				"activity", "String");
		hotspotzustand_att1.setDefaultValue("unverändert");
		hotspotzustand_att1.addPossibleValue("unverändert");
		hotspotzustand_att1.addPossibleValue("aktiv");
		hotspotzustand_att1.addPossibleValue("inaktiv");
		hotspotzustand_att1.save();
		hotspotzustand.setAttributeType(hotspotzustand_att1);

		AttributeType hotspotzustand_att3 = new AttributeType(
				"Neue Sichtbarkeit", "visibility", "String");
		hotspotzustand_att3.setDefaultValue("unverändert");
		hotspotzustand_att3.addPossibleValue("unverändert");
		hotspotzustand_att3.addPossibleValue("sichtbar");
		hotspotzustand_att3.addPossibleValue("unsichtbar");
		hotspotzustand_att3.save();
		hotspotzustand.setAttributeType(hotspotzustand_att3);

		AttributeType hotspotzustand_att4 = new AttributeType(
				"Für alle Hotspots anwenden", "applyToAll", "boolean");
		hotspotzustand_att4.setDefaultValue("false");
		hotspotzustand_att4.save();
		hotspotzustand.setAttributeType(hotspotzustand_att4);

		hotspotzustand.update();

		allActionTypes.add(hotspotzustand);
		hotspotActionTypes.add(hotspotzustand);

		ActionType at11 = new ActionType(
				"Hotspot-Aktivität verändern (veraltet)", "SetHotspotActivity");
		at11.setCategory("hidden");
		at11.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/hotspotactivity.png");
		at11.save();
		AttributeType at11a1 = new AttributeType("Neue Aktivität", "mode",
				"boolean");
		at11a1.setDefaultValue("false");

		at11a1.save();
		at11.setAttributeType(at11a1);
		AttributeType at11a2 = new AttributeType("Hotspot", "id", "hotspot");
		at11a2.save();
		at11.setAttributeType(at11a2);
		at11.update();
		allActionTypes.add(at11);
		hotspotActionTypes.add(at11);

		ActionType at12 = new ActionType(
				"Hotspot-Sichtbarkeit verändern (veraltet)",
				"SetHotspotVisibility");
		at12.setCategory("hidden");
		at12.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/hotspotvisibility.png");
		at12.save();
		AttributeType at12a1 = new AttributeType("Neue Sichtbarkeit",
				"visible", "boolean");
		at12a1.setDefaultValue("false");

		at12a1.save();
		at12.setAttributeType(at12a1);
		AttributeType at12a2 = new AttributeType("Hotspot", "id", "hotspot");
		at12a2.save();
		at12.setAttributeType(at12a2);
		at12.update();
		allActionTypes.add(at12);
		hotspotActionTypes.add(at12);

		ActionType at13 = new ActionType("Score erhöhen", "AddToScore");
		at13.setCategory("var");
		at13.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/addxvar.png");
		at13.save();
		AttributeType at13a1 = new AttributeType("Betrag", "value", "int");
		at13a1.setDefaultValue("0");
		at13a1.save();

		at13.setAttributeType(at13a1);
		at13.update();
		allActionTypes.add(at13);
		hotspotActionTypes.add(at13);

		ActionType at15 = new ActionType("Karte zentrieren", "CenterMap");
		at15.setCategory("map");
		at15.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/centermap.png");
		at15.save();

		AttributeType at15a0 = new AttributeType("Ort", "hotspot", "hotspot");

		at15a0.save();

		at15.setAttributeType(at15a0);
		at15.update();

		AttributeType at15a1 = new AttributeType(
				"Eigene Position einschließen", "position", "boolean");
		at15a1.setDefaultValue("false");

		at15a1.save();

		at15.setAttributeType(at15a1);
		at15.update();

		AttributeType at15a2 = new AttributeType(
				"Aktive Hotspots einschließen", "activeHotspots", "boolean");
		at15a2.setDefaultValue("false");

		at15a2.save();

		at15.setAttributeType(at15a2);
		at15.update();
		AttributeType at15a3 = new AttributeType(
				"Sichtbare Hotspots einschließen", "visibleHotspots", "boolean");
		at15a3.setDefaultValue("false");
		at15a3.save();

		at15.setAttributeType(at15a3);
		at15.update();

		allActionTypes.add(at15);
		hotspotActionTypes.add(at15);

		ActionType at17 = new ActionType("Wenn-Dann-Bedingung", "If");
		at17.setCategory("condition");
		at17.setSymbol(Global.SERVER_URL_2 + "/assets/icons/actions/if.png");
		at17.save();
		AttributeType at17a1 = new AttributeType("Wenn", "condition",
				"condition");

		at17a1.save();

		at17.setAttributeType(at17a1);
		at17.update();

		AttributeType at17a2 = new AttributeType("Dann", "then", "actions");

		at17a2.save();

		at17.setAttributeType(at17a2);
		at17.update();

		AttributeType at17a3 = new AttributeType("Sonst", "else", "actions");

		at17a3.save();

		at17.setAttributeType(at17a3);
		at17.update();

		allActionTypes.add(at17);
		hotspotActionTypes.add(at17);

		ActionType at18 = new ActionType("Solange-Wie-Schleife", "Loop");
		at18.setCategory("condition");
		at18.setSymbol(Global.SERVER_URL_2 + "/assets/icons/actions/while.png");
		at18.setPremiumRequirement("All Access");
		at18.save();
		AttributeType at18a1 = new AttributeType("Solange Wie", "condition",
				"condition");

		at18a1.save();

		at18.setAttributeType(at18a1);
		at18.update();

		AttributeType at18a2 = new AttributeType("Dann", "then", "actions");

		at18a2.save();

		at18.setAttributeType(at18a2);
		at18.update();

		AttributeType at18a3 = new AttributeType(
				"Unendlichen Durchlauf erlauben", "unlimitedLoops", "boolean");
		at18a3.setDefaultValue("false");

		at18a3.save();

		at18.setAttributeType(at18a3);
		at18.update();

		allActionTypes.add(at18);
		hotspotActionTypes.add(at18);
		ActionType at22 = new ActionType("Von-Bis-Schleife", "Loop");
		at22.setCategory("condition");
		at22.setPremiumRequirement("All Access");
		at22.save();
		at22.setSymbol(Global.SERVER_URL_2 + "/assets/icons/actions/while.png");
		at22.save();
		AttributeType at22a1 = new AttributeType("Für Variable",
				"loopVariable", "String");
		at22a1.setDefaultValue("i");

		at22a1.save();

		at22.setAttributeType(at22a1);
		at22.update();

		AttributeType at22a2 = new AttributeType("Von", "from", "int");

		at22a2.setDefaultValue("1");
		at22a2.save();

		at22.setAttributeType(at22a2);
		at22.update();

		AttributeType at22a3 = new AttributeType("Bis", "to", "int");
		at22a3.setDefaultValue("10");

		at22a3.save();

		at22.setAttributeType(at22a3);
		at22.update();

		AttributeType at22a4 = new AttributeType("Dann", "then", "actions");

		at22a4.save();

		at22.setAttributeType(at22a4);
		at22.update();

		allActionTypes.add(at22);
		hotspotActionTypes.add(at22);

		ActionType at19 = new ActionType("Schleife unterbrechen", "Break");
		at19.setCategory("condition");
		at19.setPremiumRequirement("All Access");
		at19.setSymbol(Global.SERVER_URL_2 + "/assets/icons/actions/break.png");
		at19.save();

		allActionTypes.add(at19);
		hotspotActionTypes.add(at19);

		// ActionType at20 = new ActionType("Antwort hinzufügen","AddAnswer");
		// at20.setSymbol(Global.SERVER_URL_2+"/assets/icons/actions/addanswer.png");
		// at20.save();

		// AttributeType at20a1 = new
		// AttributeType("Seite","mission","mission");

		// at20a1.save();

		// at20.setAttributeType(at20a1);
		// at20.update();

		// AttributeType at20a2 = new
		// AttributeType("Antworttyp","answertype","String");

		// at20a2.save();

		// at20a2.addPossibleValue("answer");
		// at20a2.addPossibleValue("imageanswer");
		// at20a2.update();

		// at20.setAttributeType(at20a2);
		// at20.update();

		// AttributeType at20a3 = new
		// AttributeType("Korrekt?","correct","boolean");

		// at20a3.save();

		// at20a3.setDefaultValue("false");
		// at20a3.update();

		// at20.setAttributeType(at20a3);
		// at20.update();

		// AttributeType at20a4 = new AttributeType("Bild","image","file");

		// at20a4.save();

		// at20.setAttributeType(at20a4);
		// at20.update();

		// AttributeType at20a5 = new
		// AttributeType("Bei Auswahl anzeigen","onChoose","String");

		// at20a5.save();

		// at20.setAttributeType(at20a5);
		// at20.update();

		// AttributeType at20a6 = new
		// AttributeType("Buttontext","nextbuttontext","String");

		// at20a6.save();

		// at20.setAttributeType(at20a6);
		// at20.update();

		// allActionTypes.add(at20);
		// hotspotActionTypes.add(at20);

		ActionType at21 = new ActionType("Routing anzeigen", "AddRoute");
		at21.setCategory("map");
		at21.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/addroute.png");
		at21.save();

		AttributeType at21a1 = new AttributeType("Von", "from", "hotspotstring");

		at21a1.save();

		at21.setAttributeType(at21a1);
		at21.update();

		AttributeType at21a2 = new AttributeType("Zu", "to", "hotspotstring");

		at21a2.save();

		at21.setAttributeType(at21a2);
		at21.update();

		allActionTypes.add(at21);
		hotspotActionTypes.add(at21);

		ActionType at23 = new ActionType("Variable abspeichern", "SaveVar");
		at23.setCategory("var");
		at23.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/savevar.png");

		at23.save();

		AttributeType at23a1 = new AttributeType("Variable", "var", "String");

		at23a1.save();

		at23.setAttributeType(at23a1);
		at23.update();

		allActionTypes.add(at23);
		hotspotActionTypes.add(at23);

		ActionType at24 = new ActionType("Variable laden", "LoadVar");
		at24.setCategory("var");
		at24.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/loadvar.png");

		at24.save();

		AttributeType at24a1 = new AttributeType("Variable", "var", "String");

		at24a1.save();

		at24.setAttributeType(at24a1);
		at24.update();

		allActionTypes.add(at24);
		hotspotActionTypes.add(at24);

		ActionType at25 = new ActionType("Route löschen", "RemoveRoute");
		at25.setCategory("map");
		at25.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/removeroute.png");

		at25.save();

		allActionTypes.add(at25);
		hotspotActionTypes.add(at25);

		ActionType at26 = new ActionType("Variablen-Ansicht anzeigen",
				"ShowVar");
		at26.setCategory("var");
		at26.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/varoverlay.png");

		at26.save();

		AttributeType at26a1 = new AttributeType("Variable", "var",
				"expression");
		at26a1.save();
		at26.setAttributeType(at26a1);
		at26a1.update();

		AttributeType at26a2 = new AttributeType("Bezeichnung", "description",
				"String");

		at26a2.save();

		at26.setAttributeType(at26a2);
		at26.update();

		AttributeType at26a3 = new AttributeType("Ausrichtung", "position",
				"String");
		at26a3.save();
		at26a3.setDefaultValue("oben mittig");
		at26a3.addPossibleValue("oben mittig");
		at26a3.update();

		at26.setAttributeType(at26a3);

		allActionTypes.add(at26);
		hotspotActionTypes.add(at26);

		ActionType at27 = new ActionType("Variablen-Ansicht ausblenden",
				"HideVar");
		at27.setCategory("var");
		at27.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/varoverlay_hide.png");

		at27.save();

		allActionTypes.add(at27);
		hotspotActionTypes.add(at27);

		ActionType at28 = new ActionType("Variable an Server senden",
				"SendVarToServer");
		at28.setCategory("var");
		at28.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/savevar.png");

		at28.save();

		AttributeType at28a1 = new AttributeType("Variable", "var", "String");

		at28a1.save();

		at28.setAttributeType(at28a1);
		at28.update();

		AttributeType at28a2 = new AttributeType("IP-Adresse", "ip", "String");

		at28a2.save();

		at28.setAttributeType(at28a2);
		at28.update();

		allActionTypes.add(at28);
		hotspotActionTypes.add(at28);

		ActionType at29 = new ActionType("Variable von Server laden",
				"SendVarToServer");
		at29.setCategory("var");
		at29.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/savevar.png");

		at29.save();

		AttributeType at29a1 = new AttributeType("Variable", "var", "String");

		at29a1.save();

		at29.setAttributeType(at29a1);
		at29.update();

		AttributeType at29a2 = new AttributeType("IP-Adresse", "ip", "String");

		at29a2.save();

		at29.setAttributeType(at29a2);
		at29.update();

		allActionTypes.add(at29);
		hotspotActionTypes.add(at29);

		ActionType at30 = new ActionType("Quest starten", "StartQuest");
		at30.setCategory("page");
		at30.setSymbol(Global.SERVER_URL_2
				+ "/assets/icons/actions/callmission.png");

		at30.save();

		AttributeType at30a1 = new AttributeType("Quest", "quest", "int");

		at30a1.save();

		at30.setAttributeType(at30a1);
		at30.update();

		allActionTypes.add(at30);
		hotspotActionTypes.add(at30);

		// for (ActionType aat : allActionTypes) {

		// if (!aat.getXMLType().equals("next")) {
		// gt.addPossibleMenuItemActionType(aat);
		// }

		// }

		// / HOTSPOTS

		hpt1 = new HotspotType("Standard", "hotspot");
		hpt1.save();

		hpt1.addPossibleRuleType(rtOnEnterHotSpot);
		hpt1.addPossibleRuleType(rtOnLeaveHotSpot);
		hpt1.addPossibleRuleType(rtOnTapHotSpot);
		hpt1.update();

		AttributeType ha1 = new AttributeType("Marker Bild", "img", "file");
		// ha1.setDefaultValue(Global.SERVER_URL_2+"/assets/img/marker.png");
		ha1.setDefaultValue(Global.SERVER_URL_2
				+ "/assets/img/erzbistummarker.png");

		ha1.save();

		hpt1.setAttributeType(ha1);

		AttributeType ha2 = new AttributeType("Radius (m)", "radius", "int");
		ha2.setDefaultValue("20");
		ha2.save();

		hpt1.setAttributeType(ha2);

		AttributeType ha3 = new AttributeType("Sichtbarkeit zu Beginn",
				"initialVisibility", "boolean");
		ha3.setDefaultValue("true");
		ha3.save();

		hpt1.setAttributeType(ha3);

		AttributeType ha4 = new AttributeType("Aktivität zu Beginn",
				"initialActivity", "boolean");
		ha4.setDefaultValue("true");
		ha4.save();

		hpt1.setAttributeType(ha4);

		AttributeType ha5 = new AttributeType("iBeacon ID",
				"iBeacon", "String");
		ha5.setOptional(true);
		ha5.save();

		hpt1.setAttributeType(ha5);

		AttributeType ha6 = new AttributeType("NFC ID",
				"nfc", "String");
		ha6.setOptional(true);
		ha6.save();

		hpt1.setAttributeType(ha6);


		AttributeType ha7 = new AttributeType("QR Code",
				"qrcode", "String");
		ha7.setOptional(true);
		ha7.save();

		hpt1.setAttributeType(ha7);
		hpt1.update();

		gt.addPossibleHotspotType(hpt1);

		// // HOOK

		for (RuleType art : allMissionRuleTypes) {

			for (ActionType aat : allActionTypes) {

				art.addPossibleActionType(aat);

			}

			art.update();

		}

		for (ActionType aat : allActionTypes) {

			rtOnTapScreen.addPossibleActionType(aat);

		}

		rtOnTapScreen.update();

		for (RuleType art : allHotspotRuleTypes) {

			for (ActionType aat : hotspotActionTypes) {

				art.addPossibleActionType(aat);

			}

			art.update();

		}

		List<MissionType> mt = new ArrayList<MissionType>();

		// MISSION TYPE: QuestList

		// generic = new MissionType("beliebige Seite","Generic");
		// generic.save();

		// AUSBLENDEN
		// mt.add(generic);

		// SAVE MISSIONTYPE TO GAMETYPE
		// PartType pt122 = new PartType(generic);
		// pt122.save();

		// gt.addPossiblePartType(pt122);
		// gt.update();

		// ATTRIBIUTE

		// AttributeType att41pt122 = new
		// AttributeType("Hintergrund-Bild","background-image", "file");
		// att41pt122.save();

		// generic.setAttributeType(att41pt122);

		// RULES
		// generic.addPossibleRuleTypes(rt4);
		// generic.addPossibleRuleTypes(rt3);
		// generic.addPossibleRuleTypes(rt8);

		// generic.update();

		// CONTENTS (generic view types)

		List<AttributeType> gvtattributes = new ArrayList<AttributeType>();

		AttributeType gvtats1 = new AttributeType("X", "x", "int");
		gvtats1.save();
		gvtattributes.add(gvtats1);

		AttributeType gvtats2 = new AttributeType("Y", "y", "int");
		gvtats2.save();
		gvtattributes.add(gvtats2);

		AttributeType gvtats3 = new AttributeType("Höhe", "height", "int");
		gvtats3.save();
		gvtattributes.add(gvtats3);

		AttributeType gvtats4 = new AttributeType("Breite", "width", "int");
		gvtats4.save();
		gvtattributes.add(gvtats4);

		ContentType gvt1 = new ContentType("Bild", "image");
		gvt1.save();
		// generic.addPossibleContentTypes(gvt1);

		for (AttributeType aatrgv : gvtattributes) {
			gvt1.setAttributeType(aatrgv);
		}

		AttributeType att1gvt1 = new AttributeType("Bild", "image", "file");

		att1gvt1.setDefaultValue(Global.SERVER_URL
				+ "assets/img/defaultimages/menu-bg.jpg");

		att1gvt1.save();
		gvt1.setAttributeType(att1gvt1);

		AttributeType att2gvt1 = new AttributeType("Skalierung", "scale",
				"String");
		att2gvt1.save();

		att2gvt1.addPossibleValue("stretch");
		att2gvt1.addPossibleValue("fill");
		att2gvt1.addPossibleValue("fit");
		att2gvt1.setDefaultValue("fill");
		att2gvt1.update();

		gvt1.setAttributeType(att2gvt1);

		AttributeType att3gvt1 = new AttributeType("Fokus X", "focus_x", "int");
		att3gvt1.save();
		gvt1.setAttributeType(att3gvt1);

		AttributeType att4gvt1 = new AttributeType("Fokus Y", "focus_y", "int");
		att4gvt1.save();
		gvt1.setAttributeType(att4gvt1);

		gvt1.addPossibleRuleTypes(rt9);

		gvt1.update();

		ContentType gvt2 = new ContentType("Text", "text");
		gvt2.save();
		// generic.addPossibleContentTypes(gvt2);

		for (AttributeType aatrgv : gvtattributes) {
			gvt2.setAttributeType(aatrgv);
		}

		AttributeType att1gvt2 = new AttributeType("Schrifttyp", "fonttype",
				"String");
		att1gvt2.save();

		gvt2.setAttributeType(att1gvt2);

		AttributeType att2gvt2 = new AttributeType("Schriftfarbe", "fontcolor",
				"Color");
		att2gvt2.setDefaultValue("#000000");
		att2gvt2.save();

		gvt2.setAttributeType(att2gvt2);

		AttributeType att3gvt2 = new AttributeType("Schriftgröße", "fontsize",
				"int");
		att3gvt2.setDefaultValue("18");
		att3gvt2.save();

		gvt2.setAttributeType(att3gvt2);

		AttributeType att4gvt2 = new AttributeType("Scrollbar?", "scrollable",
				"boolean");
		att4gvt2.setDefaultValue("false");
		att4gvt2.save();

		gvt2.setAttributeType(att4gvt2);

		AttributeType att5gvt2 = new AttributeType("Skalierung", "scale",
				"String");
		att5gvt2.save();

		att5gvt2.addPossibleValue("right");
		att5gvt2.addPossibleValue("left");
		att5gvt2.addPossibleValue("center");
		att5gvt2.setDefaultValue("right");
		att5gvt2.update();

		gvt2.setAttributeType(att5gvt2);

		gvt2.addPossibleRuleTypes(rt9);

		gvt2.update();

		ContentType gvt3 = new ContentType("Button", "button");
		gvt3.save();
		// generic.addPossibleContentTypes(gvt3);

		for (AttributeType aatrgv : gvtattributes) {
			gvt3.setAttributeType(aatrgv);
		}

		AttributeType att1gvt3 = new AttributeType("Bild", "image", "file");

		att1gvt3.save();
		gvt3.setAttributeType(att1gvt3);

		AttributeType att2gvt3 = new AttributeType("Aktiviert", "enabled",
				"boolean");
		att2gvt3.save();
		gvt3.setAttributeType(att2gvt3);

		gvt3.addPossibleRuleTypes(rt9);

		gvt3.update();

		/*
		 * ContentType gvt4 = new ContentType("Geo Karte","map"); gvt4.save();
		 * 
		 * ContentType gvt5 = new ContentType("Liste","list"); gvt5.save();
		 */

		// generic.update();

		// MISSION TYPE: StartAndExitScreen
		screen = new MissionType("Vollformat Bild", "StartAndExitScreen");
		screen.save();

		// SAVE MISSIONTYPE TO GAMETYPE
		PartType pt1 = new PartType(screen);
		pt1.save();
		mt.add(screen);

		// ATTRIBUTE
		AttributeType att5 = new AttributeType(
				"Bild (*.jpg, *.png, *.gif, *.zip)", "image", "file");
		att5.setMimeType("image");
		att5.setDefaultValue("http://qeevee.org:9091/assets/img/gq_defaults/startandexit.png");
		att5.save();
		screen.setAttributeType(att5);
		screen.update();

		AttributeType att6 = new AttributeType("Anzeigedauer(s)", "duration",
				"stringseconds");
		att6.save();
		att6.setOptional(true);
		att6.setDefaultValue("interactive");
		att6.update();
		screen.setAttributeType(att6);
		screen.update();

		AttributeType att7 = new AttributeType(
				"Bildfrequenz (frames per second)", "fps", "int");
		att7.save();
		att7.setDefaultValue("24");
		att7.update();
		screen.setAttributeType(att7);
		screen.update();

		AttributeType att8 = new AttributeType("Wiederholen?", "loop",
				"boolean");
		att8.save();
		att8.setDefaultValue("true");
		att8.update();
		screen.setAttributeType(att8);
		screen.update();

		// RULE TYPES
		screen.addPossibleRuleTypes(rtOnStart);
		screen.addPossibleRuleTypes(rtOnEnd);
		screen.addPossibleRuleTypes(rtOnTapScreen);

		screen.update();

		// MISSION TYPE: Multiple Choice Question

		frage = new MissionType("Frage (Multiple Choice)",
				"MultipleChoiceQuestion");
		frage.save();

		mt.add(frage);

		// SAVE MISSIONTYPE TO GAMETYPE

		PartType pt2 = new PartType(frage);
		pt2.save();

		// ATTRIBIUTE

		AttributeType frage_att1 = new AttributeType("Fragetext", "question",
				"String");
		frage_att1.setOptional(false);
		frage_att1.save();
		frage_att1.setDefaultValue("???");
		frage_att1.update();

		frage.setAttributeType(frage_att1);

		AttributeType att4 = new AttributeType("Bis zum Erfolg wiederholen?",
				"loopUntilSuccess", "boolean");
		att4.setOptional(true);
		att4.save();
		att4.setDefaultValue("false");
		att4.update();

		frage.setAttributeType(att4);

		AttributeType frage_att4 = new AttributeType(
				"Feedback bei Wiederholung", "loopText", "String");
		frage_att4.setOptional(false);
		frage_att4.save();
		frage_att4.setDefaultValue("X");
		frage_att4.update();

		frage.setAttributeType(frage_att4);

		AttributeType frage_att2 = new AttributeType(
				"Antworten zu Beginn mischen?", "shuffle", "boolean");
		frage_att2.save();
		frage_att2.setDefaultValue("false");
		frage_att2.setOptional(true);
		frage_att2.update();

		frage.setAttributeType(frage_att2);

		AttributeType frage_att3 = new AttributeType("Nur Bilder anzeigen?",
				"showOnlyImages", "boolean");
		frage_att3.save();
		frage_att3.setDefaultValue("false");
		frage_att3.update();

		frage.setAttributeType(frage_att3);

		AttributeType att480 = new AttributeType("Hintergrund", "bg", "file");
		att480.setMimeType("image");
		att480.setOptional(true);
		att480.save();

		frage.setAttributeType(att480);

		frage.update();

		// POSSIBLE CONTENT TYPES

		ContentType ct7 = new ContentType("Frage", "question");
		ct7.save();

		ContentType ct1 = new ContentType("Antwort", "answer");
		ct1.save();

		ContentType ct101 = new ContentType("Bild-Antwort", "imageanswer");
		ct101.save();

		ContentType ct6 = new ContentType("Fragetext", "questiontext");
		ct6.save();

		ct7.addPossibleContentType(ct1);
		ct7.addPossibleContentType(ct101);
		ct7.addPossibleContentType(ct6);
		ct7.update();

		ContentTypeOccurrence cto1 = new ContentTypeOccurrence(ct6);
		cto1.setMin(1);
		cto1.setMax(1);
		cto1.save();

		ct7.addContentTypeOccurrence(cto1);

		ct7.update();

		// STANDARDFRAGE
		Content standardfrage = new Content("Frage", ct7);
		standardfrage.save();

		Content fragetext = new Content("Text", ct6);
		fragetext.setContent("???");
		fragetext.save();

		standardfrage.addSubContent(fragetext);

		Content antwort1 = new Content("A", ct1);
		antwort1.setContent("A");
		antwort1.save();

		standardfrage.addSubContent(antwort1);

		Content antwort2 = new Content("B", ct1);
		antwort2.setContent("B");
		antwort2.save();

		standardfrage.addSubContent(antwort2);

		standardfrage.update();

		frage.addPossibleContentTypes(ct1);
		frage.addDefaultContent(antwort1);
		frage.addDefaultContent(antwort2);

		frage.update();

		// ATTRIBUTE
		AttributeType att2 = new AttributeType("Richtig?", "correct", "boolean");
		att2.setDefaultValue("false");
		att2.setShowInParent(true);
		att2.save();
		ct1.setAttributeType(att2);
		ct1.update();

		AttributeType att4ct1 = new AttributeType("Bild", "image", "file");
		att4ct1.save();

		ct1.setAttributeType(att4ct1);
		ct1.update();

		// ATTRIBUTE

		AttributeType att4ct101 = new AttributeType("Bild", "image", "file");
		att4ct101.save();

		ct101.setAttributeType(att4ct101);
		ct101.update();

		AttributeType att201 = new AttributeType("Richtig?", "correct",
				"boolean");
		att201.setDefaultValue("false");
		att201.setShowInParent(true);
		att201.save();
		ct101.setAttributeType(att201);
		ct101.update();

		AttributeType att301 = new AttributeType("Text bei Auswahl",
				"onChoose", "String");
		att301.save();

		ct101.setAttributeType(att301);
		ct101.update();

		AttributeType att1001 = new AttributeType("Buttontext",
				"nextbuttontext", "String");
		att1001.save();
		ct101.setAttributeType(att1001);
		ct101.update();

		frage.update();

		// RULE TYPES

		frage.addPossibleRuleTypes(rtOnSuccess);
		frage.addPossibleRuleTypes(rtOnFailure);
		frage.addPossibleRuleTypes(rtOnEnd);
		frage.addPossibleRuleTypes(rtOnStart);
		frage.update();

		// MISSION TYPE: Multiple Choice Question

		menu = new MissionType("Auswahlmenü", "Menu");
		menu.save();

		mt.add(menu);

		// SAVE MISSIONTYPE TO GAMETYPE
		PartType pt2111 = new PartType(menu);
		pt2111.save();

		// ATTRIBIUTE

		AttributeType menu_att1 = new AttributeType("Überschrift", "question",
				"String");
		menu_att1.setOptional(false);
		menu_att1.save();
		menu_att1.setDefaultValue("");
		menu_att1.update();

		menu.setAttributeType(menu_att1);

		AttributeType att911 = new AttributeType("Einträge zu Beginn mischen?",
				"shuffle", "boolean");

		att911.setDefaultValue("false");
		att911.setOptional(true);
		att911.setVisibility(false);
		att911.save();

		menu.setAttributeType(att911);

		AttributeType att4811 = new AttributeType("Hintergrund", "bg", "file");
		att4811.setMimeType("image");
		att4811.setOptional(true);
		att4811.setDefaultValue(Global.SERVER_URL
				+ "assets/img/defaultimages/menu-bg.jpg");
		att4811.save();

		menu.update();

		// POSSIBLE CONTENT TYPES

		ContentType ct711 = new ContentType("Menu-Items-Container", "question");
		ct711.save();

		ContentType ct111 = new ContentType("Eintrag", "answer");
		ct111.save();

		ContentType ct611 = new ContentType("Überschrift", "questiontext");
		ct611.save();

		ct711.addPossibleContentType(ct111);
		ct711.addPossibleContentType(ct611);
		ct711.update();

		ContentTypeOccurrence cto111 = new ContentTypeOccurrence(ct611);
		cto111.setMin(1);
		cto111.setMax(1);
		cto111.save();

		ContentTypeOccurrence cto211 = new ContentTypeOccurrence(ct1);
		cto211.setMin(1);
		cto211.save();

		ct711.addContentTypeOccurrence(cto111);
		ct711.addContentTypeOccurrence(cto211);
		ct711.update();

		// STANDARDFRAGE
		Content standardmenu = new Content("Menu-Items-Container", ct711);
		standardmenu.save();

		Content heading = new Content("Text", ct6);
		heading.setContent("...");
		heading.save();

		standardmenu.addSubContent(heading);

		Content item1 = new Content("A", ct111);
		item1.setContent("A");
		item1.save();

		standardmenu.addSubContent(item1);

		Content item2 = new Content("B", ct111);
		item2.setContent("B");
		item2.save();

		standardmenu.addSubContent(item2);

		standardmenu.update();

		menu.addPossibleContentTypes(ct111);
		menu.addDefaultContent(item1);
		menu.addDefaultContent(item2);

		menu.update();

		// RULE TYPES

		menu.addPossibleRuleTypes(rtOnEnd);
		menu.addPossibleRuleTypes(rtOnStart);
		menu.update();

		// MISSION TYPE: NPCTalk

		textimage = new MissionType("Text mit Bild", "ImageWithText");
		textimage.save();

		mt.add(textimage);

		// SAVE MISSIONTYPE TO GAMETYPE
		PartType textimage_pt1 = new PartType(textimage);
		textimage_pt1.save();

		// ATTRIBIUTE

		AttributeType textimage_att2 = new AttributeType("Bild", "image",
				"file");
		textimage_att2.setMimeType("image");
		textimage_att2.setOptional(true);
		textimage_att2.save();

		textimage.setAttributeType(textimage_att2);

		AttributeType textimage_att1 = new AttributeType("Text", "text",
				"String");
		textimage_att1.setOptional(false);
		textimage_att1.save();

		textimage.setAttributeType(textimage_att1);

		AttributeType textimage_att4 = new AttributeType(
				"Beenden-Button-Beschriftung", "endbuttontext", "String");
		textimage_att4.setOptional(true);
		textimage_att4.setDefaultValue(">");
		textimage_att4.save();

		textimage.setAttributeType(textimage_att4);

		AttributeType textimage_att5 = new AttributeType("Textgröße",
				"textsize", "String");
		textimage_att5.setOptional(true);
		textimage_att5.setDefaultValue("20");
		textimage_att5.save();

		textimage.setAttributeType(textimage_att5);

		textimage.update();

		// RULES

		textimage.addPossibleRuleTypes(rtOnStart);
		textimage.addPossibleRuleTypes(rtOnEnd);

		textimage.update();

		// MISSION TYPE: CUSTOM PAGE

		MissionType customPageType = new MissionType("Individuelle Seite",
				"Custom");
		customPageType.save();

		mt.add(customPageType);

		// SAVE MISSIONTYPE TO GAMETYPE
		PartType customPageType_pt1 = new PartType(customPageType);
		customPageType_pt1.save();

		// ATTRIBIUTE PARAMETERS:
		AttributeType customPageType_att0 = new AttributeType("Modul ID",
				"modul", "String");
		customPageType_att0.setOptional(true);
		customPageType_att0.save();
		customPageType.setAttributeType(customPageType_att0);

		AttributeType customPageType_att1 = new AttributeType("Parameter 1",
				"param1", "String");
		customPageType_att1.setOptional(true);
		customPageType_att1.save();
		customPageType.setAttributeType(customPageType_att1);

		AttributeType customPageType_att2 = new AttributeType("Parameter 2",
				"param2", "String");
		customPageType_att2.setOptional(true);
		customPageType_att2.save();
		customPageType.setAttributeType(customPageType_att2);

		AttributeType customPageType_att3 = new AttributeType("Parameter 3",
				"param3", "String");
		customPageType_att3.setOptional(true);
		customPageType_att3.save();
		customPageType.setAttributeType(customPageType_att3);

		AttributeType customPageType_att4 = new AttributeType("Parameter 4",
				"param4", "String");
		customPageType_att4.setOptional(true);
		customPageType_att4.save();
		customPageType.setAttributeType(customPageType_att4);

		AttributeType customPageType_att5 = new AttributeType("Parameter 5",
				"param5", "String");
		customPageType_att5.setOptional(true);
		customPageType_att5.save();
		customPageType.setAttributeType(customPageType_att5);

		AttributeType customPageType_att6 = new AttributeType("Parameter 6",
				"param6", "String");
		customPageType_att6.setOptional(true);
		customPageType_att6.save();
		customPageType.setAttributeType(customPageType_att6);

		AttributeType customPageType_att7 = new AttributeType("Parameter 7",
				"param7", "String");
		customPageType_att7.setOptional(true);
		customPageType_att7.save();
		customPageType.setAttributeType(customPageType_att7);

		AttributeType customPageType_att8 = new AttributeType("Parameter 8",
				"param8", "String");
		customPageType_att8.setOptional(true);
		customPageType_att8.save();
		customPageType.setAttributeType(customPageType_att8);

		AttributeType customPageType_att9 = new AttributeType("Parameter 9",
				"param9", "String");
		customPageType_att9.setOptional(true);
		customPageType_att9.save();
		customPageType.setAttributeType(customPageType_att9);

		AttributeType customPageType_att10 = new AttributeType("Parameter 10",
				"param10", "String");
		customPageType_att10.setOptional(true);
		customPageType_att10.save();
		customPageType.setAttributeType(customPageType_att10);

		customPageType.update();

		// RULES

		customPageType.addPossibleRuleTypes(rtOnStart);
		customPageType.addPossibleRuleTypes(rtOnEnd);
		customPageType.addPossibleRuleTypes(rtOnFailure);
		customPageType.addPossibleRuleTypes(rtOnSuccess);
		customPageType.addPossibleRuleTypes(rtOnTapScreen);

		customPageType.update();
		

		// MISSION TYPE: NPCTalk

		npctalk = new MissionType("NPC Dialog", "NPCTalk");
		npctalk.save();

		mt.add(npctalk);

		// SAVE MISSIONTYPE TO GAMETYPE
		PartType pt4 = new PartType(npctalk);
		pt4.save();

		// ATTRIBIUTE

		AttributeType att11 = new AttributeType("Bild", "image", "file");
		att11.setMimeType("image");
		att11.setOptional(true);
		att11.save();

		npctalk.setAttributeType(att11);

		AttributeType att43 = new AttributeType("Modus", "mode", "String");
		att43.setOptional(true);
		att43.save();
		att43.setDefaultValue("Komplett anzeigen");
		att43.addPossibleValue("Wordticker");
		att43.addPossibleValue("Komplett anzeigen");
		att43.update();

		npctalk.setAttributeType(att43);

		AttributeType att12 = new AttributeType("Weiter-Button-Beschriftung",
				"nextdialogbuttontext", "String");
		att12.setOptional(true);
		att12.setDefaultValue(">");
		att12.save();

		npctalk.setAttributeType(att12);

		AttributeType att13 = new AttributeType("Beenden-Button-Beschriftung",
				"endbuttontext", "String");
		att13.setOptional(true);
		att13.setDefaultValue(">");
		att13.save();

		npctalk.setAttributeType(att13);

		AttributeType att14 = new AttributeType("Textgröße", "textsize",
				"String");
		att14.setOptional(true);
		att14.setDefaultValue("20");
		att14.save();

		npctalk.setAttributeType(att14);

		AttributeType npc_att1 = new AttributeType("Wordticker überspringbar?",
				"skipwordticker", "boolean");
		npc_att1.setOptional(true);
		npc_att1.setDefaultValue("true");
		npc_att1.save();

		npctalk.setAttributeType(npc_att1);

		AttributeType npc_att2 = new AttributeType(
				"Wordticker-Geschwindigkeit (ms)", "tickerspeed", "int");
		npc_att2.setDefaultValue("50");
		npc_att2.setOptional(true);
		npc_att2.save();
		npctalk.setAttributeType(npc_att2);

		npctalk.update();

		// CONTENTTYPES

		ContentType dialogitem = new ContentType("Dialog-Item", "dialogitem");
		dialogitem.save();

		AttributeType att15 = new AttributeType("Sprecher", "speaker", "String");
		att15.setOptional(true);
		att15.save();

		dialogitem.setAttributeType(att15);

		AttributeType att16 = new AttributeType("Audio-Datei", "sound", "file");
		att16.setOptional(true);
		att16.save();

		dialogitem.setAttributeType(att16);

		AttributeType att17 = new AttributeType(
				"Bis Ende von Audio-Datei blockieren?", "blocking", "boolean");

		att17.setDefaultValue("false");
		att17.save();

		dialogitem.setAttributeType(att17);

		dialogitem.update();

		npctalk.addPossibleContentTypes(dialogitem);

		// RULES

		npctalk.addPossibleRuleTypes(rtOnStart);
		npctalk.addPossibleRuleTypes(rtOnEnd);

		npctalk.update();

		// MISSION TYPE: MapOSM

		osmap = new MissionType("Karte", "MapOSM");
		osmap.save();

		mt.add(osmap);

		// SAVE MISSIONTYPE TO GAMETYPE
		PartType pt5 = new PartType(osmap);
		pt5.save();

		// ATTRIBIUTE

		AttributeType att18 = new AttributeType("Zoom-Stufe", "zoomlevel",
				"int");
		att18.setDefaultValue("18");
		att18.setOptional(true);
		att18.save();

		osmap.setAttributeType(att18);

		osmap.addPossibleRuleTypes(rtOnStart);

		osmap.update();

		// // MISSION TYPE: MapGoogle
		//
		// googlemap = new MissionType("Karte (Google)", "MapGoogle");
		// googlemap.save();
		//
		// mt.add(googlemap);
		//
		// // SAVE MISSIONTYPE TO GAMETYPE
		// PartType pt6 = new PartType(googlemap);
		// pt6.save();
		//
		// // ATTRIBIUTE
		//
		// AttributeType att47 = new AttributeType("Map-Ansicht", "makind",
		// "String");
		// att47.setOptional(true);
		// att47.save();
		// att47.setDefaultValue("map");
		// att47.addPossibleValue("map");
		// att47.addPossibleValue("satellite");
		// att47.update();
		//
		// osmap.setAttributeType(att47);
		//
		// // Zoom-Stufe
		// googlemap.setAttributeType(att18);
		//
		// googlemap.addPossibleRuleTypes(rt4);
		//
		// googlemap.update();

		// MISSION TYPE: AudioRecord

		audiorecord = new MissionType("Audio Aufnahme", "AudioRecord");
		audiorecord.save();

		mt.add(audiorecord);

		// SAVE MISSIONTYPE TO GAMETYPE

		PartType pt7 = new PartType(audiorecord);
		pt7.save();

		// ATTRIBIUTE

		AttributeType att20 = new AttributeType(
				"Abzuspeichernde Datei (Variable)", "file", "String");
		att20.setDefaultValue("audiorecord");
		att20.save();

		audiorecord.setAttributeType(att20);

		AttributeType att19 = new AttributeType("Aufgabenbeschreibung", "task",
				"String");
		att19.setDefaultValue("");
		att19.setOptional(true);
		att19.save();

		audiorecord.setAttributeType(att19);

		// RULES
		audiorecord.addPossibleRuleTypes(rtOnStart);
		audiorecord.addPossibleRuleTypes(rtOnEnd);

		audiorecord.update();

		// MISSION TYPE: TextQuestion

		textquestion = new MissionType("Frage (Text)", "TextQuestion");
		textquestion.save();

		mt.add(textquestion);

		// SAVE MISSIONTYPE TO GAMETYPE
		PartType pt8 = new PartType(textquestion);
		pt8.save();

		// ATTRIBIUTE

		AttributeType att21 = new AttributeType("Fragetext", "question",
				"String");
		att21.setDefaultValue("???");
		att21.save();

		textquestion.setAttributeType(att21);

		AttributeType att22 = new AttributeType(
				"Antwortfeld, wenn keine Antwort", "prompt", "String");
		att22.setDefaultValue("…");
		att22.save();

		textquestion.setAttributeType(att22);

		AttributeType tf_att4 = new AttributeType(
				"Bis zum Erfolg wiederholen?", "loopUntilSuccess", "boolean");
		tf_att4.setOptional(true);
		tf_att4.save();
		tf_att4.setDefaultValue("false");
		tf_att4.update();

		textquestion.setAttributeType(tf_att4);

		AttributeType tf_att480 = new AttributeType("Hintergrund", "bg", "file");
		tf_att480.setMimeType("image");
		tf_att480.setOptional(true);
		tf_att480.save();

		textquestion.setAttributeType(tf_att480);

		// RULES
		textquestion.addPossibleRuleTypes(rtOnSuccess);
		textquestion.addPossibleRuleTypes(rtOnFailure);
		textquestion.addPossibleRuleTypes(rtOnEnd);
		textquestion.addPossibleRuleTypes(rtOnStart);

		textquestion.update();

		// CONTENT

		ContentType ct5 = new ContentType("Antwort", "answer");
		ct5.save();

		textquestion.addPossibleContentTypes(ct5);

		// DEFAULT

		Content c4 = new Content("Antwort", ct5);
		c4.setContent("");
		c4.save();

		textquestion.addDefaultContent(c4);

		textquestion.update();

		// MISSION TYPE: TagReading

		tagreading = new MissionType("QR Tag Scanner", "TagScanner");
		tagreading.save();

		mt.add(tagreading);

		// SAVE MISSIONTYPE TO GAMETYPE
		PartType tagreading_pt = new PartType(tagreading);
		tagreading_pt.save();

		// ATTRIBIUTE

		AttributeType tagreading_att26 = new AttributeType("Modus", "mode",
				"String");
		tagreading_att26.save();
		tagreading_att26.setDefaultValue("QR-Code");
		tagreading_att26.addPossibleValue("QR-Code");
		tagreading_att26.setEditable(false);
		tagreading_att26.update();

		tagreading.setAttributeType(tagreading_att26);

		AttributeType tagreading_att29 = new AttributeType(
				"Aufgabenbeschreibung", "taskdescription", "String");
		tagreading_att29.save();

		tagreading.setAttributeType(tagreading_att29);

		AttributeType tagreading_att1 = new AttributeType(
				"Inhalt des gescannten Codes anzeigen?", "showTagContent",
				"boolean");
		tagreading_att1.setOptional(true);
		tagreading_att1.save();
		tagreading_att1.setDefaultValue("false");
		tagreading_att1.update();
		tagreading.setAttributeType(tagreading_att1);

		// RULES
		tagreading.addPossibleRuleTypes(rtOnSuccess);
		tagreading.addPossibleRuleTypes(rtOnFailure);
		tagreading.addPossibleRuleTypes(rtOnStart);
		tagreading.addPossibleRuleTypes(rtOnEnd);

		tagreading.update();

		// CONTENTTYPES

		ContentType expectedcontent = new ContentType("Erwarteter Inhalt",
				"expectedCode");
		expectedcontent.save();

		tagreading.addPossibleContentTypes(expectedcontent);

		tagreading.update();

		// MISSION TYPE: QRTagReading

		qrtagreading = new MissionType("QR Tag Scanner (veraltet)",
				"QRTagReading");
		qrtagreading.save();

		mt.add(qrtagreading);

		// SAVE MISSIONTYPE TO GAMETYPE
		PartType pt9 = new PartType(qrtagreading);
		pt9.save();

		// ATTRIBIUTE

		AttributeType att26 = new AttributeType("Modus", "mode", "String");
		att26.save();
		att26.setDefaultValue("bestimmter Tag (mit onSuccess)");
		att26.addPossibleValue("bestimmter Tag (mit onSuccess)");
		att26.addPossibleValue("beliebiger Tag (mit Wenn-Dann-Bedingung)");
		att26.update();

		qrtagreading.setAttributeType(att26);

		AttributeType att29 = new AttributeType("Aufgabenbeschreibung",
				"taskdescription", "String");
		att29.save();

		qrtagreading.setAttributeType(att29);

		AttributeType att112 = new AttributeType("Erwarteter Inhalt",
				"expected_content", "String");
		att112.save();

		qrtagreading.setAttributeType(att112);

		AttributeType att25 = new AttributeType("Bild zu Beginn",
				"initial_image", "file");
		att25.setMimeType("image");
		att25.save();

		qrtagreading.setAttributeType(att25);

		AttributeType att27 = new AttributeType("Bild bei richtigem Code",
				"if_right_image", "file");
		att27.setMimeType("image");
		att27.save();

		qrtagreading.setAttributeType(att27);

		AttributeType att28 = new AttributeType("Bild bei falschem Code",
				"if_wrong_image", "file");
		att28.setMimeType("image");
		att28.save();

		qrtagreading.setAttributeType(att28);

		AttributeType att30 = new AttributeType("Scanner-Button-Beschriftung",
				"buttontext", "String");
		att30.setDefaultValue("Scannen");
		att30.save();

		qrtagreading.setAttributeType(att30);

		AttributeType att31 = new AttributeType("Beenden-Button-Beschriftung",
				"endbuttontext", "String");
		att31.setDefaultValue("Weiter");
		att31.save();

		qrtagreading.setAttributeType(att31);

		// RULES
		qrtagreading.addPossibleRuleTypes(rtOnSuccess);
		qrtagreading.addPossibleRuleTypes(rtOnFailure);
		qrtagreading.addPossibleRuleTypes(rtOnStart);
		qrtagreading.addPossibleRuleTypes(rtOnEnd);

		qrtagreading.update();

		// MISSION TYPE: VideoPlay

		videoplay = new MissionType("Video Wiedergabe", "VideoPlay");
		videoplay.save();

		mt.add(videoplay);

		// SAVE MISSIONTYPE TO GAMETYPE
		PartType pt10 = new PartType(videoplay);
		pt10.save();

		// ATTRIBIUTE

		AttributeType att39 = new AttributeType("Video-Datei", "file", "file");
		att39.setMimeType("video");
		att39.save();

		videoplay.setAttributeType(att39);

		AttributeType att32 = new AttributeType("Kontrollierbar?",
				"controllable", "boolean");
		att32.setDefaultValue("true");
		att32.save();

		videoplay.setAttributeType(att32);

		// RULES

		videoplay.addPossibleRuleTypes(rtOnStart);
		videoplay.addPossibleRuleTypes(rtOnEnd);

		videoplay.update();

		// MISSION TYPE: WebPage

		webpage = new MissionType("Webseite", "WebPage");
		webpage.save();

		mt.add(webpage);

		// SAVE MISSIONTYPE TO GAMETYPE
		PartType pt11 = new PartType(webpage);
		pt11.save();

		// ATTRIBIUTE

		AttributeType att41 = new AttributeType("Online-URL", "url", "String");
		att41.save();

		webpage.setAttributeType(att41);

		AttributeType att42 = new AttributeType("Lokale Datei", "file", "file");
		att42.setMimeType("html");
		att42.save();

		webpage.setAttributeType(att42);

		AttributeType webpage_att1 = new AttributeType(
				"Beenden-Button-Beschriftung", "endbuttontext", "String");
		webpage_att1.setDefaultValue(">");
		webpage_att1.save();

		webpage.setAttributeType(webpage_att1);

		// RULES
		webpage.addPossibleRuleTypes(rtOnStart);
		webpage.addPossibleRuleTypes(rtOnEnd);

		webpage.update();

		// MISSION TYPE: ImageCapture

		imagecapture = new MissionType("Foto Aufnahme", "ImageCapture");
		imagecapture.save();

		mt.add(imagecapture);

		// SAVE MISSIONTYPE TO GAMETYPE
		PartType pt12 = new PartType(imagecapture);
		pt12.save();

		// ATTRIBIUTE

		AttributeType att44 = new AttributeType(
				"Abzuspeichernde Datei (Variable)", "file", "String");
		att44.setDefaultValue("imagecapture");
		att44.save();

		imagecapture.setAttributeType(att44);

		AttributeType att45 = new AttributeType("Aufgabentext", "task",
				"String");
		att45.setOptional(true);
		att45.setDefaultValue("");
		att45.save();

		imagecapture.setAttributeType(att45);

		AttributeType att46 = new AttributeType("Button Beschriftung",
				"buttontext", "String");
		att46.setOptional(true);
		att46.setDefaultValue("");
		att46.save();

		imagecapture.setAttributeType(att46);

		// RULES
		imagecapture.addPossibleRuleTypes(rtOnStart);
		imagecapture.addPossibleRuleTypes(rtOnEnd);

		imagecapture.update();

		// MISSION TYPE: Meta-Daten

		metadata = new MissionType("Meta-Daten", "MetaData");
		metadata.save();

		// SAVE MISSIONTYPE TO GAMETYPE
		PartType metadata_pt1 = new PartType(metadata);
		metadata_pt1.save();

		gt.addPossiblePartType(metadata_pt1);
		gt.update();

		// POSSIBLE CONTENT TYPES

		ContentType metadata_ct1 = new ContentType("Meta-Eintrag", "stringmeta");
		metadata_ct1.save();

		ContentTypeOccurrence metadata_ct1_o = new ContentTypeOccurrence(
				metadata_ct1);
		metadata_ct1_o.setMin(1);
		metadata_ct1_o.save();

		// ATTRIBUTE
		AttributeType metadata_ct1_at1 = new AttributeType("Schlüssel", "key",
				"String");
		metadata_ct1_at1.setShowInParent(true);
		metadata_ct1_at1.save();
		metadata_ct1.setAttributeType(metadata_ct1_at1);
		metadata_ct1.update();

		// ATTRIBUTE
		AttributeType metadata_ct1_at2 = new AttributeType("Wert", "value",
				"String");
		metadata_ct1_at2.setShowInParent(true);
		metadata_ct1_at2.save();
		metadata_ct1.setAttributeType(metadata_ct1_at2);
		metadata_ct1.update();

		metadata.addPossibleContentTypes(metadata_ct1);

		metadata.update();

		// MISSION TYPE: QuestList

		// questlist = new MissionType("Quest-Liste","QuestList");
		// questlist.save();

		// mt.add(questlist);

		// SAVE MISSIONTYPE TO GAMETYPE
		// PartType pt120 = new PartType(questlist);
		// pt120.save();

		// gt.addPossiblePartType(pt120);
		// gt.update();

		// ATTRIBIUTE

		// AttributeType att42pt120 = new AttributeType("Titel","title",
		// "String");
		// att42pt120.save();

		// questlist.setAttributeType(att42pt120);

		// AttributeType att44pt120 = new
		// AttributeType("Quests von Portal","portal_id", "int");
		// att44pt120.save();

		// questlist.setAttributeType(att44pt120);

		// ATTRIBIUTE

		// AttributeType att43pt120 = new
		// AttributeType("Spieltyp-Filter","gametype_id", "int");
		// att43pt120.save();

		// questlist.setAttributeType(att43pt120);

		// AttributeType att41pt120 = new
		// AttributeType("Hintergrund-Bild","background-image", "file");
		// att41pt120.save();

		// questlist.setAttributeType(att41pt120);

		// RULES
		// questlist.addPossibleRuleTypes(rt4);
		// questlist.addPossibleRuleTypes(rt3);

		// questlist.update();

		// / ADD ALL MISSION TYPES IN ORDER TO GAME TYPE

		// Auswahlmenü [Multiple Choice Question]
		gt.addPossiblePartType(pt2111);

		// Audio Aufnahme [AudioRecord]
		gt.addPossiblePartType(pt7);

		// Foto Aufnahme [ImageCapture]
		gt.addPossiblePartType(pt12);

		// Frage (Multiple Choice) [Multiple Choice Question]
		gt.addPossiblePartType(pt2);

		// Frage (Text) [TextQuestion]
		gt.addPossiblePartType(pt8);

		// Karte (Google) [MapGoogle]
		// gt.addPossiblePartType(pt6);

		// Karte (OSM) [MapOSM]
		gt.addPossiblePartType(pt5);

		// NPC Dialog [NPCTalk]
		gt.addPossiblePartType(pt4);

		// QR Tag Scanner [QRTagReading]
		gt.addPossiblePartType(pt9);

		// Tag Scanner [TagScanner]
		gt.addPossiblePartType(tagreading_pt);

		// Text mit Bild [NPCTalk]
		gt.addPossiblePartType(textimage_pt1);

		// Video Wiedergabe [VideoPlay]
		gt.addPossiblePartType(pt10);

		// Vollformat-Bild [StartAndExitScreen]
		gt.addPossiblePartType(pt1);

		// Webseite [WebPage]
		gt.addPossiblePartType(pt11);
		
		// Individuelle Seite [CustomPage]
		gt.addPossiblePartType(customPageType_pt1);

		gt.update();

		SceneType st = new SceneType("Missions-Container");
		st.save();

		for (MissionType amt : mt) {

			PartType apt = new PartType(amt);
			apt.save();
			st.addPossiblePartTypes(apt);

		}

		st.update();

		gt.addPossibleSceneType(st);
		gt.update();

		gt.update();
		return gt;

	}

}
