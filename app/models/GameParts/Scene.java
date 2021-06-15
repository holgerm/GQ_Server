package models.GameParts;

import models.help.GameCopyContext;
import play.db.ebean.Model;
import util.Global;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import models.Game;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipOutputStream;

import org.w3c.dom.Document;

@Entity
public class Scene extends Model {

	@Id
	private Long id;
	@ManyToOne
	private SceneType type;
	private String name;

	@ManyToMany
	private List<Part> parts;
	@ManyToMany
	private List<Attribute> attributes;
	@ManyToMany
	private List<Rule> rules;

	@ManyToMany
	private List<Rule> rulesFirst;

	@ManyToMany
	private List<Rule> rulesLast;

	@ManyToMany
	private List<Hotspot> hotspots;

	public Scene(String n, SceneType t) {

		name = n;
		type = t;

	}

	// SETTER
	public void setAttribute(Attribute t) {

		try {
			List<Attribute> copyOfAttributes = new ArrayList<Attribute>(attributes.size());

			for (Attribute item : attributes)
				copyOfAttributes.add(item);

			for (Attribute aatr : copyOfAttributes) {
				if (aatr.getXMLType().equals(t.getXMLType())) {
					attributes.remove(aatr);
				}
			}
			attributes.add(t);

		} catch (RuntimeException e) {

			System.out.println("Problem setting Attribute.");
			e.printStackTrace();

		}

	}

	public void addRule(Rule r) {
		rules.add(r);
	}

	public void addPart(Part p) {
		parts.add(p);
	}

	public void addHotspot(Hotspot h) {
		hotspots.add(h);
	}

	// GETTER

	public String getName() {
		return name;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public List<Hotspot> getHotspots() {
		return hotspots;
	}

	public List<Part> getParts() {
		return parts;
	}

	public boolean hasPossibleParts() {

		if (type.getPossiblePartTypes() != null) {
			if (type.getPossiblePartTypes().size() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	public void debug(String s) {

		System.out.println(s);

	}

	public boolean hasPossibleHotspots() {

		if (type.getPossibleHotspotTypes().size() > 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean hasAttributeTypes() {

		if (type.getAttributeTypes().size() > 0) {
			return true;
		} else {
			return false;
		}

	}

	public void setLinkedAttribute(ObjectReference o) {

		if (o.getObjectType().equals("Content")) {

		} else if (o.getObjectType().equals("Attribute")) {

		}

	}

	public boolean showMultipleAchordions() {

		int count = 0;

		if (hasAttributeTypes() == true) {

			count = count + 1;
		}

		if (hasPossibleParts() == true) {
			if (type.canSeeMissions() == true) {
				count = count + 1;
			}
		}

		if (hasPossibleHotspots()) {
			if (getHotspots().size() > 1) {

				if (type.canSeeHotspots()) {
					count = count + 1;
				}

			}
		}

		if (count > 1) {
			return true;
		} else {
			return false;
		}

	}

	public Long getId() {
		return id;
	}

	// / CREATION

	public Scene copyMe(String n, GameCopyContext copyContext) {

		String nam = name + " " + n;

		try {
			double d = Double.parseDouble(n);
		} catch (NumberFormatException nfe) {

			if (n.contains("(1)")) {

				n.replace("(1)", "");

			}

			nam = name + " " + String.valueOf(n);
		}

		Scene s = new Scene(name, type);
		s.save();

		// PARTS

		int counter = 1;
		String add = "";

		Set<String> in = new HashSet<String>();

		for (Part ap : parts) {

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

			try {
				double d = Double.parseDouble(n);
			} catch (NumberFormatException nfe) {

				if (ap.isScene()) {

					add = ap.getScene().getName();
				} else {
					if (counter > 1) {
						add = "" + counter;
					}
				}
			}

			s.addPart(ap.copyMe(add, copyContext));
			s.update();
			counter++;
		}

		// ATTRIBUTES
		for (Attribute aart : attributes) {
			s.setAttribute(aart.copyMe(copyContext));
			s.update();
		}

		// RULES
		for (Rule ar : rules) {
			s.addRule(ar.copyMe(copyContext));
			s.update();
		}

		// HOTSPOTS
		counter = 1;
		for (Hotspot ah : hotspots) {
			Hotspot newhotspot = ah.copyMe(copyContext);
			newhotspot.save();
			copyContext.hotspotMap.put(ah, newhotspot);
			s.addHotspot(newhotspot);
			s.update();

			counter++;
		}

		// Update Links to the copies of attributes etc.
		return s;
	}

	public void redoLinking(Game g) {

		List<AttributeType> attributeTypes = new ArrayList<AttributeType>();
		attributeTypes.addAll(getAllAttributes());

		for (AttributeType atrttype : attributeTypes) {
			Attribute atrt = this.getAttribute(atrttype);

			if (atrt != null) {
				if (atrt.hasLink()) {

					System.out.println("An attribute wants to link to another object");

					if (atrt.getLink().getObjectId() != null) {

						Attribute atr = new Attribute(atrttype);
						atr.save();
						ObjectReference o = g.getAbstractRelinkObject(atrt.getLink(), this);
						if (o != null) {
							System.out.println(
									"and is setting it to " + o.getObjectType() + " (" + o.getObjectId() + ")");

							o.save();
							atr.setLink(o);
							atr.update();

							this.setAttribute(atr);
							this.update();

						} else {
							System.out.println("but didn't find a fitting equivalent.");
						}
					} else {
						System.out.println("but has no object reference specified correctly.");
					}
				}
			}
		}

	}

	public boolean listAttributeContainsKey(String list, String key, boolean quotes) {
		System.out.println("'" + list + "' contains" + key + "?");

		if (quotes) {
			list = list.substring(1, list.length() - 1);
		}

		if (list != null && list != "") {

			if (list.contains(", ")) {

				String[] split = list.split(", ");

				for (String s : split) {

					if (s.equals(key)) {

						return true;

					}

				}

			} else {

				System.out.println("single");

				if (list.contains(key)) {
					System.out.println("true");
					return true;

				}

			}
		}

		return false;

	}

	public static final Finder<Long, Scene> find = new Finder<Long, Scene>(Long.class, Scene.class);

	public SceneType getType() {
		return type;
	}

	public List<Mission> getAllMissions() {

		List<Mission> all = new ArrayList<Mission>();

		for (Part ap : parts) {

			if (!ap.isScene()) {

				all.add(ap.getMission());

			} else {

				all.addAll(ap.getScene().getAllMissions());

			}

		}

		return all;
	}

	public Set<Scene> getAllScenes() {

		Set<Scene> all = new HashSet<Scene>();

		for (Part ap : parts) {

			if (ap.isScene()) {
				all.addAll(ap.getScene().getAllScenes());
				all.add(ap.getScene());

			}

		}

		return all;
	}

	public void removeMe() {

		Set<Attribute> atrs = new HashSet<Attribute>();
		atrs.addAll(attributes);
		Set<Rule> rls = new HashSet<Rule>();
		rls.addAll(rules);
		Set<Part> prts = new HashSet<Part>();
		prts.addAll(parts);
		Set<Hotspot> htsps = new HashSet<Hotspot>();
		htsps.addAll(hotspots);

		try {
			for (Attribute aa : atrs) {
				attributes.remove(aa);
				this.update();
				aa.delete();
			}
			for (Rule ar : rls) {
				rules.remove(ar);
				this.update();
				ar.removeMe();
				ar.delete();
			}
			for (Part ap : prts) {
				parts.remove(ap);
				this.update();
				ap.removeMe();
				ap.delete();
			}
			for (Hotspot ah : htsps) {
				hotspots.remove(ah);
				this.update();
				ah.removeMe();
				ah.delete();
			}
		} catch (RuntimeException e) {

			System.out.println("Can't delete Mission.");
			e.printStackTrace();

		}

	}

	public void removePart(Part x) {

		if (parts.contains(x)) {
			parts.remove(x);
			this.update();
			x.removeMe();
			x.delete();
		} else {

			for (Part ap : parts) {

				if (ap.isScene()) {

					ap.getScene().removePart(x);

				}

			}

		}

	}

	public List<Element> createXML(Document doc, Game g, ZipOutputStream zout) {
		List<Element> e = new ArrayList<Element>();

		for (Part ap : parts) {

			if (!ap.isScene()) {
				if (!ap.getMission().equals(g.getFirstMission())) {
					e.addAll(ap.createXML(doc, g, zout));
				}

			} else {

				e.addAll(ap.createXML(doc, g, zout));

			}

		}

		return e;
	}

	public List<Element> createXMLForWeb(Document doc, Game g) {
		System.out.println("createXMLForWeb: Scene: " + id);
		List<Element> e = new ArrayList<Element>();

		for (Part ap : parts) {
			if (!ap.isScene()) {
				if (!ap.getMission().equals(g.getFirstMission())) {
					e.addAll(ap.createXMLForWeb(doc, g));
				}
			} else {
				e.addAll(ap.createXMLForWeb(doc, g));
			}
		}

		return e;
	}

	public Mission getFirstMission() {

		if (!parts.isEmpty()) {
			Part first = parts.get(0);

			if (first.isScene()) {

				return first.getScene().getFirstMission();

			} else {

				return first.getMission();

			}

		} else {

			return null;
		}

	}

	public boolean containsMission(Mission m) {

		boolean contains = false;

		for (Part p : parts) {
			if (!contains) {
				if (p.isScene()) {

					contains = p.getScene().containsMission(m);

				} else {

					if (p.getMission().equals(m)) {
						contains = true;
					}

				}
			}
		}

		return contains;

	}

	public Mission getNextMission(Mission m) {

		Mission n = m;
		boolean current = false;
		boolean done = false;

		for (Part p : parts) {

			if (p.isScene()) {

				if (current == true) {
					n = p.getScene().getFirstMission();
				} else {

					if (p.getScene().containsMission(m)) {

						if (p.getScene().isLastMission(m)) {

							current = true;
						} else {

							n = p.getScene().getNextMission(m);
							current = false;
							done = true;
						}

					}

				}

			} else {

				if (current == true) {

					n = p.getMission();
					current = false;
					done = true;
				} else {

					if (p.getMission().equals(m)) {
						current = true;
					}

				}

			}

		}

		// TODO Auto-generated method stub
		return n;
	}

	public boolean isLastMission(Mission m) {

		Part lastPart = parts.get(parts.size() - 1);
		if (lastPart.isScene()) {

			return lastPart.getScene().isLastMission(m);

		} else {

			if (lastPart.getMission().equals(m)) {
				return true;
			} else {
				return false;
			}

		}

	}

	public List<AttributeType> getAllAttributes() {
		return type.getAttributeTypes();

	}

	public List<Attribute> getAllSubAttributes() {

		List<AttributeType> allsubsty = new ArrayList<AttributeType>();
		List<Attribute> allsubs = new ArrayList<Attribute>();

		// Sub Parts

		for (Part ap : parts) {

			if (!ap.isScene()) {

				Mission am = ap.getMission();

				System.out.println("searching in page " + am.getName() + " (" + am.getType().getXMLType() + ")");
				for (AttributeType att : am.getAllAttributes()) {

					if (am.getAttribute(att) != null) {

						allsubs.add(am.getAttribute(att));

					}

				}

				// Contents

				for (Content ac : am.getContents()) {
					System.out.println("searching in content " + ac.getName() + " (" + ac.getType().getXMLType() + ")");

					for (AttributeType att1 : ac.getAllAttributes()) {

						if (ac.getAttribute(att1) != null) {
							System.out.println(
									"Attr:" + ac.getAttribute(att1).getName() + "=" + ac.getAttributeValue(att1));

							allsubs.add(ac.getAttribute(att1));

						}

					}

					for (Content asc : ac.getSubContents()) {

						for (AttributeType att2 : asc.getAllAttributes()) {

							if (asc.getAttribute(att2) != null) {

								allsubs.add(asc.getAttribute(att2));

							}

						}

					}

				}

				// Rules

				for (Rule ar : am.getRules()) {

					for (Rule asr : ar.getSubRules()) {

						for (Action asrac : asr.getActions()) {

							allsubs.addAll(asrac.getAllSubAttributes());

						}

					}

				}

			}

		}

		// Sub Hotspots

		for (Hotspot ah : hotspots) {

			// /// TRIGGER RULES

			for (Rule atr : ah.getRules()) {

				// //// RULES

				for (Rule ar : atr.getSubRules()) {

					// ///// ACTIONS

					for (Action aa : ar.getActions()) {

						allsubs.addAll(aa.getAttributes());

					}

				}

			}

		}

		return allsubs;

	}

	public Long getAttributeId(AttributeType at) {

		Long x = 0L;

		for (Attribute aa : attributes) {

			if (aa.getXMLType().equals(at.getXMLType())) {

				x = aa.getId();

			}

		}

		return x;

	}

	/**
	 * TODO implement a generic implementation of this method for all uses (in many
	 * different quest elements)
	 * 
	 * @param at
	 * @return
	 */
	public String getAttributeValue(AttributeType at) {

		String x = at.getDefaultValue();
		long attId = -1l;

		for (Attribute aa : attributes) {
			if (aa.getValue() == null)
				continue;

			if (aa.getXMLType().equals(at.getXMLType())) {

				x = aa.getValue();
				attId = aa.getId();

				if (aa.getType().getFileType().equals("QuoteString")
						|| aa.getType().getFileType().equals("QuoteStringTextArea")) {
					if (x.length() >= 2 && x.charAt(0) == ('"') && x.charAt(x.length() - 1) == '"') {
						x = x.substring(1, x.length() - 1);
						x = x.replace("<br>", "\n");
					}
				}
			}
		}

		System.out.print("Scene (" + id + ").getAttributeValue() --> >" + x + "<  id: " + attId + "\n");
		return x;
	}

	public Attribute getAttribute(AttributeType at) {

		Attribute x = null;

		for (Attribute aa : attributes) {

			if (aa.getXMLType().equals(at.getXMLType())) {

				x = aa;

			}

		}

		return x;

	}

	public void setName(String n) {
		name = n;
	}

	public Scene migrateTo(SceneType sceneType, GameCopyContext copyContext) {
		Scene s = new Scene(name, sceneType);
		s.save();

		// PARTS
		for (Part p : parts) {
			boolean done = false;

			if (p.isScene()) {
				SceneType old = p.getScene().getType();
				System.out.println("SceneType search: " + old.getName());

				for (PartType tnpt : type.getPossiblePartTypes()) {

					if (tnpt.isSceneType()) {
						SceneType npt = tnpt.getSceneType();

						if (npt.getName().equals(old.getName())) {
							done = true;
							Scene nss = p.getScene().migrateTo(npt, copyContext);
							nss.save();
							Part ns = new Part(nss);
							ns.save();

							s.addPart(ns);
							s.update();
						}
					}
				}

				if (done == false) {
					System.out.println("Didn't find SceneType " + old.getName());
				}

			} else {
				// Part p is a MISSION:
//				MissionType old = p.getMission().getType();
				Mission originalM = p.getMission();

				Global.Log("Scene migrateTo(" + sceneType.getName() + "): mission: " + originalM.getName());
				
				MissionType targetMT = sceneType.findMigrationTargetMissionType(originalM);
				if (targetMT != null) {
					s.addPart(p.migrateTo(targetMT, copyContext));
					s.update();
					done = true;
				} else {
					System.out.println("Didn't find MissionType " + originalM.getType().getName());
				}
//				for (PartType npt : gameType.getPossiblePartTypes()) {
//
//					Global.Log("Possible part type: id: " + npt.getId());
//
//					if (!npt.isSceneType()) {
//						Global.Log("  is mission type: " + npt.getMissionType());
//
//						if (npt.getMissionType().getXMLType().equals(old.getXMLType())) {
//
//							done = true;
//
//							Global.Log("  going to add migrated mission type to mapping: "
//									+ npt.getMissionType().getXMLType());
//
//							s.addPart(p.migrateTo(npt.getMissionType(), missionbinder));
//							s.update();
//
//						} else {
//							Global.Log("  xml types are different - old: " + old.getXMLType() + " new: "
//									+ npt.getMissionType().getXMLType());
//
//						}
//
//					} else {
//						Global.Log("  is scene type");
//					}
//
//				}
//
//				if (done == false) {
//
//					System.out.println("Didn't find MissionType " + old.getXMLType());
//				}
			}

			s.update();

		}

		// ATTRIBUTES
		for (Attribute at : attributes) {
			boolean done = false;
			AttributeType attt = at.getType();

			for (AttributeType atrt : sceneType.getAttributeTypes()) {
//				if (atrt.getXMLType().equals(attt.getXMLType())) {
				/*
				 * We use names instead of xmlTypeNames here. In the xmlName slot of the attributeTypes the id of linked attributes is stored.
				 * This could also be used, but names are more intuitive to use here. They are given manually during the game type creation.
				 * They should of course be unique throughout this scene, but we can expect that anyway, since otherwise they would be shown
				 * as different entry fields with the same name in the editor.
				 */
				if (atrt.getName().equals(attt.getName())) {
					s.setAttribute(at.migrateTo(atrt, copyContext));
					s.update();
					done = true;
				}
			}

			if (done == false) {
				System.out.println("Didn't find AttributeType (Scene) " + at.getName());
			}

			s.update();
		}

		// HOTSPOTS
		for (Hotspot hs : hotspots) {
			HotspotType targetHT = sceneType.findMigrationTargetHotspotType(hs);
			if (targetHT != null) {
				s.addHotspot(hs.migrateTo(targetHT, copyContext));
				s.update();
			} else {
				System.out.println("Didn't find HotspotType " + hs.getType().getName());
			}
		}

		return s;
	}

	public Set<Content> getAllContents() {

		Set<Content> allc = new HashSet<Content>();

		for (Mission am : getAllMissions()) {

			for (Content ac : am.getContents()) {

				allc.add(ac);

				if (ac.hasSubContent()) {

					for (Content asc : ac.getSubContents()) {

						allc.add(asc);

					}

				}

			}

		}

		return allc;

	}

	public List<Content> getAllSubContents() {

		List<Content> allcs = new ArrayList<Content>();

		for (Part ap : parts) {

			if (!ap.isScene()) {

				Mission am = ap.getMission();

				// Contents

				allcs.addAll(am.getContents());

				for (Content ac : am.getContents()) {

					allcs.addAll(ac.getSubContents());

				}

			}
		}

		return allcs;
	}

}
