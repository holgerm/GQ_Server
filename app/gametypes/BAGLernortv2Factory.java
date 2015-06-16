package gametypes;

import java.util.ArrayList;
import java.util.List;

import play.api.Routes;
import models.Game;
import models.GameParts.*;
import util.Global;

public class BAGLernortv2Factory {

	
	
	public BAGLernortv2Factory(){

		
	}
	
	
	public GameType addGameToDatabase(){
		
		
		
		
		
		GameType gt = Global.defaultportal.getGameType("beliebiges Spiel");
		
		
		GameType gt2 = new GameType("Lernort (BAG)");
		gt2.save();
		
		List<PartType> parts = new ArrayList<PartType>();
		parts.addAll(gt.getPossiblePartTypes());
		
		
		List<HotspotType> hotspots = new ArrayList<HotspotType>();
		hotspots.addAll(gt.getPossibleHotspotTypes());
		
		
		List<SceneType> scenes = new ArrayList<SceneType>();
		scenes.addAll(gt.getPossibleSceneTypes());
		
		
		List<AttributeType> attributes = new ArrayList<AttributeType>();
		attributes.addAll(gt.getAttributeTypes());
		
		
		
		
		
		
		SceneType st = null;
		
		for(SceneType s:scenes){
			
			if(s.getName().equals("Lernort")){
				
				st = s;
				gt2.addPossibleSceneType(s);
				gt2.update();
				
			}
			
			
		}
		
		
		
		gt2.setOnlySceneType(st);
		gt2.update();
		
		
			return gt2;		
		
	}
	
	
	
}
