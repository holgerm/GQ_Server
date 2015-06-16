package gametypes;

import java.util.ArrayList;
import java.util.List;

import play.api.Routes;
import models.GameParts.*;
import util.Global;

public class WerWirdMillionaerFactory {

	
	
	public WerWirdMillionaerFactory(){

		
	}
	
	
	public GameType addGameToDatabase(){
		
		
		
		
		
		
		// GAME TYPE
		
		GameType gt = new GameType("Wer Wird Millionär");
		gt.save();
		
		
		
		//// TYPE SECTION
		
		
		// ACTION TYPES
		
		
		
		
		

		ArrayList<ActionType> allActionTypes = new ArrayList<ActionType>();

		ActionType at1 = new ActionType("Nächste Mission","next");
		at1.save();
		allActionTypes.add(at1);
		
		ActionType at2 = new ActionType("Letzte Mission","last");
		at2.save();
		allActionTypes.add(at2);
		
		ActionType at3 = new ActionType("Mission aufrufen","StartMission");
		at3.save();				
				AttributeType at3a1 = new AttributeType("Mission","id","mission");
				at3a1.save();
		at3.setAttributeType(at3a1);
		at3.update();
		allActionTypes.add(at3);
		
		ActionType at4 = new ActionType("Spiel beenden", "EndGame");
		at4.save();
		allActionTypes.add(at4);
		
		ActionType at5 = new ActionType("Vibrieren","Vibrate");
		at5.save();				
				AttributeType at5a1 = new AttributeType("Länge(ms)","duration","int");
				at5a1.setDefaultValue("4000");
				at5a1.save();
		at5.setAttributeType(at5a1);
		at5.update();
		allActionTypes.add(at5);
		
		ActionType at6 = new ActionType("Variable um 1 verringern","DecrementVariable");
		at6.save();				
				AttributeType at6a1 = new AttributeType("Variable","var","var");
				at6a1.save();
		at6.setAttributeType(at6a1);
		at6.update();
		allActionTypes.add(at6);
		
		ActionType at7 = new ActionType("Variable um 1 erhöhen","IncrementVariable");
		at7.save();				
				AttributeType at7a1 = new AttributeType("Variable","var","var");
				at7a1.save();
		at7.setAttributeType(at7a1);
		at7.update();
		allActionTypes.add(at7);
		
		ActionType at8 = new ActionType("Audio-Datei abspielen","PlayAudio");
		at8.save();				
				AttributeType at8a1 = new AttributeType("Audio-Datei","file","file");
				at8a1.save();
		at8.setAttributeType(at8a1);
		at8.update();
		allActionTypes.add(at8);
		
		ActionType at9 = new ActionType("Variable neuen Wert zuweisen","SetVariable");
		at9.save();				
				AttributeType at9a1 = new AttributeType("Variable","var","var");
				at9a1.save();
		at9.setAttributeType(at9a1);
				AttributeType at9a2 = new AttributeType("Wert","actionContent","expression");
				at9a2.save();
		at9.setAttributeType(at9a2);
		at9.update();
		allActionTypes.add(at9);
		
		ActionType at10 = new ActionType("Nachricht anzeigen","ShowMessage");
		at10.save();				
				AttributeType at10a1 = new AttributeType("angezeigter Text","message","String");
				at10a1.save();
		at10.setAttributeType(at10a1);
		at10.update();
		allActionTypes.add(at10);
		
		
		
		
		ActionType at13 = new ActionType("Score ändern","SetScore");
		at13.save();				
				AttributeType at13a1 = new AttributeType("Zu Score hinzufügen","value","int");
				at13a1.save();
		at13.update();
		allActionTypes.add(at13);
	
		
		
		
		
		
		
		// RULE TYPES
List<RuleType> allMissionRuleTypes = new ArrayList<RuleType>();

		RuleType rt1 = new RuleType("Richtige Antwort", "onSuccess");
		rt1.save();
		
		allMissionRuleTypes.add(rt1);
		RuleType rt2 = new RuleType("Falsche Antwort", "onFailure");
		rt2.save();
		allMissionRuleTypes.add(rt2);

		RuleType rt3 = new RuleType("Beim Fertigstellen", "onEnd");
		rt3.save();
		allMissionRuleTypes.add(rt3);

		
		rt1.setVisibility(false);
		rt1.update();
		rt2.setVisibility(false);
		rt2.update();
		rt3.setVisibility(false);
		rt3.update();
		
		//// HOOK
		
		
		for(RuleType art: allMissionRuleTypes){
			
		
				for(ActionType aat: allActionTypes){
					
						art.addPossibleActionType(aat);

				}
				
				art.update();
			
			
		}
		
		
		
		// MISSION TYPE: StartAndExitScreen
		MissionType screen = new MissionType("StartAndExitScreen","StartAndExitScreen");
		screen.save();
		
		// SAVE MISSIONTYPE TO GAMETYPE
		PartType pt1 = new PartType(screen);
		pt1.save();
		gt.addPossiblePartType(pt1);
		gt.update();
		
		
				// ATTRIBUTE
				AttributeType att5 = new AttributeType("Bild","image","file");
				att5.setMimeType("image");
				
				att5.save();
		screen.setAttributeType(att5);
		screen.update();
		
				AttributeType att6 = new AttributeType("Anzeigedauer(ms)","duration","String");
				att6.save();
				att6.setDefaultValue("interactive");
				att6.update();
		screen.setAttributeType(att6);
		screen.update();
		
				// RULE TYPES
		screen.addPossibleRuleTypes(rt3);
		screen.update();
		
		
		
		

		
		
		
		// MISSION TYPE: WWM Frage
		
		MissionType frage = new MissionType("WWM Frage","MultipleChoiceQuestion");
		frage.save();
		
		
		// SAVE MISSIONTYPE TO GAMETYPE
				PartType pt2 = new PartType(frage);
				pt2.save();
		
				// SCENE TYPE: QUESTIONBLOCK
				
				SceneType questions = new SceneType("Fragenblock");
				questions.save();
				
				questions.addPossiblePartTypes(pt2);
				questions.update();
				
				
				PartType pt3 = new PartType(questions);
				
				pt3.save();
				
				gt.addPossiblePartType(pt3);
				
				PartOccurrence po1 = new PartOccurrence(pt2);
				po1.setMin(1);
				po1.setMax(15);
				po1.save();
				questions.addPartOccurence(po1);
				questions.update();
				
				
				gt.update();
		
		
				// ATTRIBIUTE
				
				
				AttributeType att4 = new AttributeType("Bis zum Erfolg wiederholen?","loopUntilSuccess", "boolean");
				att4.save();
				att4.setDefaultValue("false");
				att4.update();
		
		frage.setAttributeType(att4);
		frage.update();

				
				
				// POSSIBLE CONTENT TYPES
				ContentType ct1 = new ContentType("Antwort","answer");
				ct1.save();
				
				ContentType ct6 = new ContentType("Fragetext","questiontext");
				ct6.save();
				
				frage.addPossibleContentTypes(ct6);
				frage.update();
				
				
						// ATTRIBUTE
						AttributeType att2 = new AttributeType("Richtig?", "correct", "boolean");						
						att2.setDefaultValue("false");
						att2.setShowInParent(true);
						att2.save();
				ct1.setAttributeType(att2);
				ct1.update();
				
						AttributeType att3 = new AttributeType("Text bei Auswahl", "onChoose", "String");
						att3.save();

				ct1.setAttributeType(att3);
				ct1.update();
				
				
		frage.addPossibleContentTypes(ct1);
		frage.update();
		
		
				// RULE TYPES
		
	
		frage.addPossibleRuleTypes(rt1);
		frage.addPossibleRuleTypes(rt2);
		frage.update();
		
		
		
		
		
		
		
		
		///// DEFAULTVALUES SECTION
		
		
	
		
		
		
		// MISSIONS
		
		
		// MISSION 1
		
		Mission m1 = new Mission("StartScreen",screen);
		m1.save();
		
		// SAVE MISSION TO GAMETYPE
				Part p1 = new Part(m1);
				p1.setDeleteable(false);
				p1.save();
				
				gt.addDefaultPart(p1);
				gt.update();
		
		
				
		
				// RULES ON MISSION 1
	
					
	
					
					
					
					Rule r12 = new Rule();
					r12.save();
					Condition c10 = new Condition("onEnd");
					c10.save();
					Rule r11 = new Rule(c10,r12);
					r11.save();
					
				
					Action a1 = new Action("Nächste Mission",at1);
					a1.save();
					
					
					r12.addAction(a1);
					r12.update();
					
					
					
					
					m1.addRule(r11);
					m1.update();
				
			// ALWAYS USE THIS AS DEFAULT FOR ONEND RULES
					Rule r13 = r11.copyMe();
					r13.save();
					rt3.addDefaultImplementation(r13);
					rt3.update();
					
					
				// ATTRIBUTES ON MISSION 1
					
					Attribute atr1 = new Attribute(att5);
					atr1.setValue(Global.SERVER_URL+"assets/img/gametypes/WWM/startscreen.jpg");
					atr1.save();
			m1.setAttribute(atr1);	
			m1.update();
			
			
	
					
		// SCENE 1
			
			Scene s1 = new Scene("Fragen",questions);
			s1.save();
			Part p2 = new Part(s1);
			p2.setDeleteable(false);
			p2.save();
			
			gt.addDefaultPart(p2);
			gt.update();
			
			
			
			
			// STANDARDFRAGE
			
			Mission m2 = new Mission("Standardfrage",frage);
			m2.save();
			
			Part p7 = new Part(m2);
			p7.save();
			
			
			questions.addDefaultPart(p7);
			questions.update();
			
			
			s1.addPart(p7);
			s1.update();
			
			
			 // ATTRIBUTE
			
			
			
			
			
					// CONTENT
			
					// FRAGE
			
					Content fragetext1 = new Content("Frage",ct6);
					fragetext1.setContent("Was ist 1+1?");
					fragetext1.save();
					m2.addContent(fragetext1);
					
					m2.update();
					frage.addDefaultContent(fragetext1);
					frage.update();
			
					// STANDARDANTWORTEN (CONTENT)
			
					Content antwort1 = new Content("A",ct1);
					antwort1.setContent("4");
					antwort1.save();
					m2.addContent(antwort1);
					m2.update();
			
					Content antwort2 = new Content("B",ct1);
					antwort2.setContent("142343");
					antwort2.save();
					m2.addContent(antwort2);
					m2.update();
					
					Content antwort3 = new Content("C",ct1);
					antwort3.setContent("1");
					antwort3.save();
					m2.addContent(antwort3);
					m2.update();
					
					Content antwort4 = new Content("D",ct1);
					antwort4.setContent("2");
					antwort4.save();
					
					
					
							// CORRECT ANSWER
							Attribute atr3 = new Attribute(att2);
							atr3.setValue("true");
							atr3.save();
					antwort4.setAttribute(atr3);
							
							Attribute atr4 = new Attribute(att3);
							
							atr4.save();
					antwort4.setAttribute(atr4);
					
					antwort4.update();
					
					
					
					
					m2.addContent(antwort4);
					m2.update();
					
					
					frage.addDefaultContent(antwort1.copyMe(""));
					frage.addDefaultContent(antwort2.copyMe(""));
					frage.addDefaultContent(antwort3.copyMe(""));
					frage.addDefaultContent(antwort4.copyMe(""));
					frage.update();
					
			
					
					// RULES
					
					
					
					Action a5 = new Action("Nächste Mission",at1);
					a5.save();
					Action a6 = new Action("Letztze Mission",at2);
					a6.save();
					
					
					
					
					
					
					Rule r4 = new Rule();
					r4.save();
					Condition c2 = new Condition("onSuccess");
					c2.save();
					Rule r3 = new Rule(c2,r4);
					r3.save();
					
					
					r4.addAction(a5);
					
					r4.update();
					
					
					Rule r5 = new Rule(a6);
					r5.save();
					Condition c3 = new Condition("onFailure");
					c3.save();
					Rule r6 = new Rule(c3,r5);
					r6.save();
					
					m2.addRule(r3);
					m2.addRule(r6);
					m2.update();
					
					rt1.addDefaultImplementation(r3.copyMe());
					rt2.addDefaultImplementation(r6.copyMe());
					rt1.update();
					rt2.update();

					frage.addDefaultRules(r3.copyMe());
					frage.addDefaultRules(r6.copyMe());
					frage.update();
		
		
					
					
					
					// MISSION 3: WIN
					
					Mission m3 = new Mission("WinScreen",screen);
					m3.save();
					
					// SAVE MISSION TO GAMETYPE
							Part p3 = new Part(m3);
							p3.setDeleteable(false);
							p3.save();
							gt.addDefaultPart(p3);
							gt.update();
					
					
							
					
							// RULES ON MISSION 1
				
							
							
							Action a8 = new Action("Spiel beenden",at4);
							a8.save();
							
								
					
								Rule r7 = new Rule(a8);
								r7.save();
									
								Rule r8 = new Rule(c10,r7);
								r8.save();
						
								m3.addRule(r8);
								m3.update();
								
							// ATTRIBUTES ON MISSION 1
								
								Attribute atr5 = new Attribute(att5);
								atr5.save();
								atr5.setValue(Global.SERVER_URL+"assets/img/gametypes/WWM/gewonnen.jpg");
								atr5.update();
						m3.setAttribute(atr5);
						m3.update();
										
					
					
						// MISSION 4: LOOSE
						
						Mission m4 = new Mission("LooseScreen",screen);
						m4.save();
						
						// SAVE MISSION TO GAMETYPE
								Part p4 = new Part(m4);
								p4.setDeleteable(false);
								p4.save();
								gt.addDefaultPart(p4);
								gt.update();
						
								
						
								// RULES ON MISSION 1
					
									

								
								Action a9 = new Action("Spiel beenden",at4);
								a9.save();
								
									Rule r9 = new Rule();
									r9.save();
										
									Rule r10 = new Rule(c10.copyMe(),r9);
									r10.save();
									m4.addRule(r10);
									m4.update();
									
									
									r9.addAction(a9);
									r9.update();
									
									
								// ATTRIBUTES ON MISSION 1
									
									Attribute atr6 = new Attribute(att5);
									atr6.save();
									atr6.setValue(Global.SERVER_URL+"assets/img/gametypes/WWM/verloren.jpg");
									atr6.update();
							m4.setAttribute(atr6);	
							m4.update();
					
					
					
			return gt;		
		
	}
	
	
	
}
