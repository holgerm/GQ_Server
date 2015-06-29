package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import models.GameParts.Attribute;
import models.GameParts.AttributeType;
import models.GameParts.Content;
import models.GameParts.Hotspot;
import models.GameParts.Mission;
import models.GameParts.Scene;

public class GameInfo {

	public long id;
	public long typeID;
	public String name;
	public String iconPath;
	public String featuredImagePath;
	public String version;

	public Date lastUpdate;

	public List<Hotspot> hotspots = new ArrayList<Hotspot>();
	public List<Metadata> metadata = new ArrayList<Metadata>();

	public GameInfo(Game game) {

		id = game.id;
		typeID = game.getType().getId();
		name = game.name;
		lastUpdate = game.getLastUpdate();

		hotspots.addAll(game.getAllHotspots());
		for (Scene curScene : game.getAllScenes()) {
			hotspots.addAll(curScene.getHotspots());
		}
		List<String> staticMetadata = new ArrayList<String>(Arrays.asList(
				"author", "age"));

		Set<Attribute> gameAttributes = game.getAttributes();
		System.err.println("Game " + game.getName() + " has " + gameAttributes.size() + " attributes.");


		for (Attribute curAtt : gameAttributes) {

			// Metadata can be searched in client to filter and sort list of
			// quests.

			if (staticMetadata.contains(curAtt.getXMLType())) {
				String key = curAtt.getType().getName();
				String value = curAtt.getValue();
				metadata.add(new Metadata(key, value));
			}

			// This is data that is not used for search

			if (curAtt.getXMLType().equals("icon")) {
				iconPath = curAtt.getValue();
			}
			if (curAtt.getXMLType().equals("featuredimage")) {
				featuredImagePath = curAtt.getValue();
			}
			if (curAtt.getXMLType().equals("version")) {
				version = curAtt.getValue();
			}
		}

		// Metadata can be searched in client to filter and sort list of quests.

		for (Mission curMission : game.getAllMissions()) {
			if (curMission.getType().getXMLType().equals("MetaData")) {
				for (Content curContent : curMission.getContents()) {
					String key = null;
					String value = null;
					for (AttributeType curAttType : curContent
							.getAllAttributes()) {
						if (curAttType.getXMLType().equals("key")) {
							key = curContent.getAttributeValue(curAttType);
						}
						if (curAttType.getXMLType().equals("value")) {
							value = curContent.getAttributeValue(curAttType);
						}
					}
					if (key != null && value != null) {
						metadata.add(new Metadata(key, value));
					}
				}
			}
		}

	}

	public class Metadata {
		public Metadata(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String key;
		public String value;
	}

}
