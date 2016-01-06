package models.GameParts;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import models.Game;
import models.help.Scenefield;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
public class GameType extends Model {

	@Id
	private Long id;

	private String name;
	private boolean oeffentlich;
	private int costs;
	private boolean mapview;

	private String premiumcodes;

	@ManyToMany
	private List<PartType> possiblePartTypes;

	@ManyToMany
	private List<HotspotType> possibleHotspotTypes;

	@ManyToMany
	private List<SceneType> possibleSceneTypes;

	@ManyToMany
	private List<AttributeType> attributeTypes;

	@ManyToMany
	private List<Part> defaultParts;

	@ManyToMany
	private List<Hotspot> defaultHotspots;

	@ManyToMany
	private List<ActionType> possibleMenuItemActionTypes;

	@OneToOne
	public SceneType editor_only_scene_type;

	public boolean multiple_only_scene_type;

	public boolean easy_editor;

	public boolean show_only_hotspots_in_sidemenu;

	public GameType(String n) {

		name = n;
		oeffentlich = true;
		costs = 0;
		mapview = true;

	}

	// SETTER

	public void addPossiblePartType(PartType pt) {
		possiblePartTypes.add(pt);
	}

	public void addPossibleHotspotType(HotspotType ht) {
		possibleHotspotTypes.add(ht);
	}

	public void addPossibleSceneType(SceneType st) {
		possibleSceneTypes.add(st);
	}

	public void addDefaultPart(Part p) {
		defaultParts.add(p);
	}

	public void addDefaultHotspot(Hotspot h) {
		defaultHotspots.add(h);
	}

	public void setOnlySceneType(SceneType st) {
		editor_only_scene_type = st;
		multiple_only_scene_type = true;
	}

	public void setOnlySceneTypeBool() {
		multiple_only_scene_type = true;
	}

	public void addPossibleMenuItemActionType(ActionType pt) {
		possibleMenuItemActionTypes.add(pt);
	}

	public void setVisibility(boolean x) {
		oeffentlich = x;
	}

	public void setAttributeType(AttributeType t) {

		try {
			List<AttributeType> copyOfAttributes = new ArrayList<AttributeType>(
					attributeTypes.size());
			;
			for (AttributeType item : attributeTypes)
				copyOfAttributes.add(item);

			for (AttributeType aatr : copyOfAttributes) {
				if (aatr.getXMLType() == t.getXMLType()) {
					attributeTypes.remove(aatr);
				}
			}
			attributeTypes.add(t);

		} catch (RuntimeException e) {

			System.out.println("Problem setting AttributeType.");
			e.printStackTrace();

		}

	}

	// GETTER

	public List<AttributeType> getAttributeTypes() {
		return attributeTypes;
	}

	public List<PartType> getPossiblePartTypes() {
		return possiblePartTypes;
	}

	public List<MissionType> getPossibleMissionTypes() {
		List<MissionType> possibleMissionTypes = new ArrayList<MissionType>();
		for (PartType pt : possiblePartTypes) {

			if (!pt.isSceneType()) {

				possibleMissionTypes.add(pt.getMissionType());

			}

		}
		return possibleMissionTypes;
	}

	public List<HotspotType> getPossibleHotspotTypes() {
		return possibleHotspotTypes;
	}

	public List<SceneType> getPossibleSceneTypes() {
		return possibleSceneTypes;
	}

	public List<Part> getDefaultPart() {
		return defaultParts;
	}

	public List<Hotspot> getDefaultHotspot(Hotspot h) {
		return defaultHotspots;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public boolean hasOnlyOneScene() {
		if (multiple_only_scene_type == true) {
			return true;
		} else {
			return false;
		}
	}

	// /////// CREATION

	public Game createMe(String n) {

		Game g = new Game(n, this);
		g.save();

		System.out.println("Game Parts Start");
		String add = "";

		Set<String> in = new HashSet<String>();
		int counter = 1;
		// DEFAULT PARTS

		Map<Mission, Mission> missionbinder = new HashMap<Mission, Mission>();
		Map<Hotspot, Hotspot> hotspotbinder = new HashMap<Hotspot, Hotspot>();

		for (Part ap : defaultParts) {

			counter = 1;
			if (ap.isScene()) {

				if (in.contains(ap.getScene().getName())) {

					for (String st : in) {
						if (st.equals(ap.getScene().getName())) {
							counter++;
						}

					}
				}

				in.add(ap.getScene().getName());

			} else {

				if (in.contains(ap.getMission().getName())) {

					for (String st : in) {
						if (st.equals(ap.getMission().getName())) {
							counter++;
						}

					}
				}

				in.add(ap.getMission().getName());

			}

			if (counter > 1) {
				add = "" + counter;
			}

			Part copiedPart = ap.copyMe(add, missionbinder, hotspotbinder);
			g.addPart(copiedPart);
			g.update();

			if (copiedPart.isScene()) {
				copiedPart.getScene().redoLinking(g);
			}

		}

		System.out.println("Game Parts End");
		System.out.println("Hotspots Start");

		// DEFAULT HOTSPOTS
		counter = 1;
		for (Hotspot ah : defaultHotspots) {

			g.addHotspot(ah.copyMe("" + counter));
			g.update();

			counter++;
		}

		System.out.println("Hotspots End");

		// Abstract Value Rebinding

		List<AttributeType> allattr = g.getAllAttributes();

		// replace scenefield occurences with default values (empty string right
		// now):
		for (Attribute aa : g.getAllSubAttributes()) {
			if (aa.getValue() != null) {
				if (aa.getValue().contains("scenefield(")) {
					Scenefield scHelper = new Scenefield(aa.getValue());
					aa.setValue(scHelper.getDefaultValue());
					aa.update();
				}
			}
		}

		return g;

	}

	public static final Finder<Long, GameType> find = new Finder<Long, GameType>(
			Long.class, GameType.class);

	public void wipeClean() {

		possiblePartTypes.clear();
		possibleSceneTypes.clear();
		possibleHotspotTypes.clear();
		attributeTypes.clear();

	}

	public void setName(String string) {
		name = string;
	}

	public List<ActionType> getPossibleMenuItemActionTypes() {
		return possibleMenuItemActionTypes;
	}

	public void deletePossibleSceneType(SceneType s) {

		List<SceneType> stl = new ArrayList<SceneType>();
		stl.addAll(possibleSceneTypes);

		for (SceneType ast : stl) {

			if (s.getId().equals(ast.getId())) {

				possibleSceneTypes.remove(ast);

			}

		}

	}

}
