package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.GameParts.AttributeType;
import models.GameParts.Content;
import models.GameParts.Hotspot;
import models.GameParts.Mission;
import models.GameParts.Scene;

public class GameInfo {

	public long id;
	public long typeID;
	public String name;

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
