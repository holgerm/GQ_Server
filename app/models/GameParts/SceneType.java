package models.GameParts;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import models.Game;
import models.help.Scenefield;
import play.db.ebean.Model;

@Entity
public class SceneType extends Model {

	@Id
	private Long id;

	private String name;
	private String xmltype;
	private boolean show;

	private String premiumcodes;

	@ManyToMany
	private List<PartType> possiblePartTypes;
	@ManyToMany
	private List<HotspotType> possibleHotspotTypes;
	@ManyToMany
	private List<AttributeType> attributeTypes;
	@ManyToMany
	private List<Part> defaultParts;
	@ManyToMany
	private List<PartOccurrence> partPermissions;
	@ManyToMany
	private List<Hotspot> defaultHotspots;

	@ManyToMany
	private List<Rule> defaultRulesonMissions;

	@ManyToMany
	private List<Rule> defaultRulesonFirstMission;

	@ManyToMany
	private List<Rule> defaultRulesonLastMission;

	@ManyToMany
	private List<RuleType> possibleRulesonFirstMission;

	@ManyToMany
	private List<RuleType> possibleRulesonAllMissions;

	@ManyToMany
	private List<RuleType> possibleRulesonLastMission;

	private String icon;
	private String iconOpen;
	private boolean seeChildren;

	private boolean canAddMissions;
	private boolean canAddRules;
	private boolean canAddHotspots;

	private boolean canSeeMissions;
	private boolean canSeeRules;
	private boolean canSeeHotspots;

	public SceneType(String n) {

		name = n;
		seeChildren = true;
		icon = "icon-folder-close";
		iconOpen = "icon-folder-open";
		canAddMissions = true;
		canAddRules = true;
		canAddHotspots = true;
		canSeeMissions = true;
		canSeeRules = true;
		canSeeHotspots = true;

	}

	public void addDefaultsFromGame(Game g) {

		SortedMap<Integer, Scenefield> sorter = new TreeMap<Integer, Scenefield>();

		defaultParts.addAll(g.getParts());
		defaultHotspots.addAll(g.getHotspots());

		this.update();

		Integer sortstart = 10000;
		for (Attribute aa : g.getAllSubAttributes()) {

			if (aa.getValue() != null) {
				if (aa.getValue().contains("scenefield(")) {
					Scenefield scHelper = new Scenefield(aa);

					// determine next sort number:
					sortstart++;
					int sort = (scHelper.getSortingOrder() == 0 ? sortstart : scHelper.getSortingOrder());
					while (sorter.containsKey(sort)) {
						sort++;
					}
					sorter.put(sort, scHelper);
				}
			}
		}

		/*
		 * Scenefields für Contents sind auskommentiert.
		 * 
		 * Ich denke sie haben eh nicht funktioniert und man braucht sie auch nicht. Man
		 * kann besser eine Variable definieren und diese dann in dem Content verwenden.
		 * Ist eh flexibler.
		 */
		// for (Content aa : g.getAllContents()) {
		// if (aa.getContent() != null) {
		// if (aa.getContent().contains("scenefield(")) {
		// Scenefield scHelper = new Scenefield(aa);
		//
		// // determine next sort number:
		// sortstart++;
		// String split = aa.getContent();
		// String[] split1 = split.split("scenefield\\(");
		// String[] split2 = split1[1].split("\\)");
		//
		// System.out.println(split1[1]);
		// Integer sort = sortstart;
		// String type = "";
		// String field = split2[0];
		// if (split2[0].contains(",")) {
		// String[] split3 = split2[0].split(",");
		//
		// field = split3[0];
		// type = split3[1];
		//
		// if (split3.length > 2) {
		//
		// try {
		// sort = Integer.valueOf(split3[2]);
		//
		// } catch (NumberFormatException e) {
		// System.out.println(e.getMessage());
		// }
		// }
		// }
		//
		// AttributeType att01 = new AttributeType(field, "content", "String");
		// att01.setOptional(true);
		// att01.save();
		// ObjectReference or = new ObjectReference(aa);
		// or.save();
		// att01.setLink(or);
		// att01.update();
		//
		// sorter.put(sort, att01);
		//
		// // setAttributeType(att01);
		//
		// this.update();
		// }
		// }
		// }

		attributeTypes.clear();
		this.update();

		// create new attributes:
		for (Integer key : sorter.keySet()) {

			Scenefield scHelper = sorter.get(key);
			Attribute att = scHelper.getAttribute();
			try {
				AttributeType att01 = new AttributeType(scHelper.getName(), String.valueOf(att.getId()),
						scHelper.getType());
				att01.setMimeType(att.getType().getMimeType());
				att01.setOptional(true);
				att01.save();
				ObjectReference or = new ObjectReference(att);
				or.save();
				att01.setLink(or);
				for (String p : scHelper.getPossibleValues()) {
					att01.addPossibleValue(p);
				}

				att01.setDefaultValue(scHelper.getDefaultValue());
				att01.update();

				attributeTypes.add(att01);
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
			}

			this.update();
		}

		this.update();
	}

	// SETTER

	public void setSeeRules(boolean x) {
		canSeeRules = x;
	}

	public void setSeeMissions(boolean x) {
		canSeeMissions = x;
	}

	public void setSeeHotspots(boolean x) {
		canSeeHotspots = x;
	}

	public void setAddHotspots(boolean x) {
		canAddHotspots = x;
	}

	public void setAddRules(boolean x) {
		canAddRules = x;
	}

	public void setAddMissions(boolean x) {
		canAddMissions = x;
	}

	public void setIcon(String x) {
		icon = x;
	}

	public void setIconOpen(String x) {
		iconOpen = x;
	}

	public void setSeeChildren(boolean x) {
		seeChildren = x;
	}

	public void setVisibility(boolean x) {
		show = x;
	}

	public void setAttributeType(AttributeType t) {

		try {
			List<AttributeType> copyOfAttributes = new ArrayList<AttributeType>(attributeTypes.size());
			;
			for (AttributeType item : attributeTypes)
				copyOfAttributes.add(item);

			for (AttributeType aatr : copyOfAttributes) {
				if (aatr.getXMLType().equals(t.getXMLType())) {
					attributeTypes.remove(aatr);
				}
			}
			attributeTypes.add(t);

		} catch (RuntimeException e) {

			System.out.println("Problem setting AttributeType.");
			e.printStackTrace();

		}

	}

	public void addDefaultPart(Part p) {
		defaultParts.add(p);
	}

	public void addPossiblePartTypes(PartType x) {
		possiblePartTypes.add(x);
		System.out.println("Trying to add possible Part Type.");
	}

	public void addPossibleHotspotTypes(HotspotType x) {
		possibleHotspotTypes.add(x);
	}

	public void addPartOccurence(PartOccurrence x) {
		partPermissions.add(x);
	}

	public void addDefaultHotspots(Hotspot x) {
		defaultHotspots.add(x);
	}

	// GETTER

	public boolean canSeeMissions() {
		return canSeeMissions;
	}

	public boolean canSeeRules() {
		return canSeeRules;
	}

	public boolean canSeeHotspots() {
		return canSeeHotspots;
	}

	public String getIcon() {
		return icon;
	}

	public String getIconOpen() {
		return iconOpen;
	}

	public boolean canSeeChildren() {
		return seeChildren;
	}

	public List<AttributeType> getAttributeTypes() {
		return attributeTypes;
	}

	public List<Part> getDefaultPart() {
		return defaultParts;
	}

	public List<PartType> getPossiblePartTypes() {
		return possiblePartTypes;
	}

	public List<HotspotType> getPossibleHotspotTypes() {
		return possibleHotspotTypes;
	}

	public List<PartOccurrence> getPartPermissions() {
		return partPermissions;
	}

	public List<Hotspot> getDefaultHotspots() {
		return defaultHotspots;
	}

	/**
	 * Finds a MissionType as part of this SceneType whose name or type name matches
	 * with the given original Mission. We search first for default missions and
	 * second for possible mission types.
	 * 
	 * @param originalM
	 *            the mission we want to find a matching MissionType for.
	 * @return MissionType as direct possible part of this SceneType, or <b>null</b>
	 *         if no match found.
	 */
	public MissionType findMigrationTargetMissionType(Mission originalM) {
		// look for matching mission in default parts:
		for (Part p : getDefaultPart()) {
			if (!p.isScene()) {
				Mission targetM = p.getMission();
				if (originalM.getName().equals(targetM.getName())) {
					return targetM.getType();
				}
			}
		}

		// look for matching mission in possible part types:
		for (PartType pt : getPossiblePartTypes()) {
			if (!pt.isSceneType()) {
				MissionType targetMT = pt.getMissionType();
				if (originalM.getType().getName().equals(targetMT.getName())) {
					return targetMT;
				}
			}
		}

		// no matching mission type found:
		return null;
	}

	public HotspotType findMigrationTargetHotspotType(Hotspot originalH) {
		// look for matching mission in default hotspots:
		for (Hotspot targetH : getDefaultHotspots()) {
			if (originalH.getName().equals(targetH.getName())) {
				return targetH.getType();
			}
		}

		// look for matching mission in possible hotspot types:
		for (HotspotType targetHT : getPossibleHotspotTypes()) {
			if (originalH.getType().getName().equals(targetHT.getName())) {
				return targetHT;
			}
		}

		// no matching mission type found:
		return null;
	}

	public Set<MissionType> getPossibleMissionTypes() {

		Set<MissionType> all = new HashSet<MissionType>();

		for (PartType amt : possiblePartTypes) {

			if (amt.isSceneType()) {

				all.addAll(amt.getSceneType().getPossibleMissionTypes());

			} else {

				all.add(amt.getMissionType());

			}

		}

		return all;
	}

	public String getName() {
		return name;
	}

	public boolean getVisibility() {
		return show;
	}

	public Long getId() {
		return id;
	}

	public Scene createMe(String n, Game g) {

		Scene s = new Scene(n, this);
		s.save();

		Map<Mission, Mission> missionbinder = new HashMap<Mission, Mission>();
		Map<Mission, Mission> missionbinder2 = new HashMap<Mission, Mission>();
		Map<Hotspot, Hotspot> hotspotbinder = new HashMap<Hotspot, Hotspot>();

		// PARTS
		for (Part ap : defaultParts) {

			if (ap.isScene()) {

				s.addPart(ap.copyMe(n, missionbinder2, hotspotbinder));
			} else {

				Part anp = ap.copyMe(n, missionbinder2, hotspotbinder);
				missionbinder.put(ap.getMission(), anp.getMission());
				s.addPart(anp);

			}

		}

		// HOTSPOTS
		for (Hotspot ah : defaultHotspots) {

			s.addHotspot(ah.copyMe(n));
		}

		for (AttributeType atrt : attributeTypes) {

			if (atrt.hasLink()) {

				Attribute atr = new Attribute(atrt);
				atr.save();
				ObjectReference o = g.getAbstractRelinkObject(atrt.getLink(), s);
				atr.setLink(o);
				atr.setValue(atrt.getDefaultValue());
				atr.update();

				s.setAttribute(atr);
				s.update();

			}

		}

		// LINK REBINDING

		for (Map.Entry<Mission, Mission> entry : missionbinder.entrySet()) {
			System.out.println("Ersetze alle $_" + entry.getKey().getId() + " durch $_" + entry.getValue().getId());
		}

		for (Attribute a : s.getAllSubAttributes()) {

			if (a.getValue() != null) {
				if (a.getValue().contains("$_")) {

					for (Map.Entry<Mission, Mission> entry : missionbinder.entrySet()) {

						if (a.getValue().contains("$_" + entry.getKey().getId())) {

							String newvalue = a.getValue();

							newvalue = newvalue.replace("$_" + entry.getKey().getId(), "$_" + entry.getValue().getId());
							newvalue = newvalue.replace("$_mission_" + entry.getKey().getId(),
									"$_mission_" + entry.getValue().getId());
							System.out.println(
									"excecuting in " + a.getType().getName() + " of type " + a.getTypeDescription()
											+ ":$_" + entry.getKey().getId() + " -> $_" + entry.getValue().getId());

							a.setValue(newvalue);
							a.update();
						}
					}

				}

				if (a.getType().getFileType().equals("mission")) {

					String newvalue = a.getValue();

					for (Map.Entry<Mission, Mission> entry : missionbinder.entrySet()) {
						if (a.getValue().equals(String.valueOf(entry.getKey().getId()))) {

							newvalue = newvalue.replace("" + entry.getKey().getId(), "" + entry.getValue().getId());
							System.out.println("excecuting mission attribute:" + entry.getKey().getId() + " -> "
									+ entry.getValue().getId());

						}
						a.setValue(newvalue);
						a.update();

					}

				}

			}
		}

		for (AttributeType atrt : attributeTypes) {

			if (atrt.hasLink()) {

				System.out.println("An attribute wants to link to another object");

				if (atrt.getLink().getObjectId() != null) {

					Attribute atr = new Attribute(atrt);
					atr.save();
					ObjectReference o = g.getAbstractRelinkObject(atrt.getLink(), s);
					if (o != null) {
						System.out.println("and is setting it to " + o.getObjectType() + " (" + o.getObjectId() + ")");

						o.save();
						atr.setLink(o);
						atr.update();

						s.setAttribute(atr);
						s.update();

					} else {

						System.out.println("but didn't find a fitting equivalent.");

					}

				} else {

					System.out.println("but has no object reference specified correctly.");

				}

			}

		}

		// replace scenefield occurences with default values (empty string right
		// now):
		for (Attribute aa : s.getAllSubAttributes()) {
			if (aa.getValue() != null) {
				if (aa.getValue().contains("scenefield(")) {
					Scenefield scHelper = new Scenefield(aa.getValue());
					aa.setValue(scHelper.getDefaultValue());
					aa.update();
				}
			}
		}

		s.update();

		return s;
	}

	public Scene createMe(String n, float lon, float lat, Game g) {

		Scene s = new Scene(n, this);
		s.save();

		// PARTS

		Map<Mission, Mission> missionbinder = new HashMap<Mission, Mission>();
		Map<Hotspot, Hotspot> hotspotbinder = new HashMap<Hotspot, Hotspot>();

		int counter = 1;
		for (Part ap : defaultParts) {

			s.addPart(ap.copyMe("" + counter, missionbinder, hotspotbinder));

			counter++;
		}

		counter = 1;
		// HOTSPOTS
		for (Hotspot ah : defaultHotspots) {

			s.addHotspot(ah.copyMe("" + counter, lon, lat));
			counter++;
		}

		// CHECK FOR ABSTRACT VALUES
		Object nObj = null;
		System.out.println("Searching for Abstract Value ");

		// HOTSPOTS

		for (Hotspot ah : s.getHotspots()) {

			for (RuleType art : ah.getType().getPossibleRuleTypes()) {

				if (ah.getRule(art) != null) {

					System.out.println("Rule: " + ah.getRule(art).getTrigger());

					for (Rule ar : ah.getRule(art).getSubRules()) {

						for (Action aact : ar.getActions()) {

							System.out.println("Action: " + aact.getName());

							for (AttributeType aat : aact.getAllAttributes()) {

								Attribute aa = aact.getAttribute(aat);

								System.out.println("AttributeType: " + aat.getName() + " (" + aat.getXMLType() + ")");

								if (aa != null) {

									System.out.println("Attribute: " + aa.getName());
									System.out.println("Value: " + aa.getValue());

									System.out.println("Abstract:" + aa.hasAbstractValue());

									if (aa.hasAbstractValue()) {

										System.out.println("Found Abstract Value ");

										ObjectReference or = aa.getAbstractValue();

										Attribute newa = new Attribute(aa.getType());

										newa.save();
										newa.setValue(g.getAbstractRelink(or, s));
										newa.update();
										aact.setAttribute(newa);
										aact.update();

									}

								}
							}
						}
					}
				}
			}
		}

		// PARTS

		for (Part ap : s.getParts()) {

			if (!ap.isScene()) {
				Mission am = ap.getMission();
				for (RuleType art : am.getType().getPossibleRuleTypes()) {

					if (am.getRule(art) != null) {

						System.out.println("Rule: " + am.getRule(art).getTrigger());

						for (Rule ar : am.getRule(art).getSubRules()) {

							for (Action aact : ar.getActions()) {

								System.out.println("Action: " + aact.getName());

								for (Attribute aa : aact.getAttributes()) {

									if (aa != null) {

										System.out.println("Attribute: " + aa.getName());
										System.out.println("Value: " + aa.getValue());

										System.out.println("Abstract:" + aa.hasAbstractValue());

										if (aa.hasAbstractValue()) {

											System.out.println("Found Abstract Value ");

											ObjectReference or = aa.getAbstractValue();

											Attribute newa = new Attribute(aa.getType());

											newa.save();
											newa.setValue(g.getAbstractRelink(or, s));
											newa.update();
											aact.setAttribute(newa);
											aact.update();

										}

									}
								}
							}
						}
					}
				}
			}
		}

		s.update();

		// LINK REBINDING

		for (AttributeType atrt : attributeTypes) {

			if (atrt.hasLink()) {

				System.out.println("An attribute wants to link to another object");

				if (atrt.getLink().getObjectId() != null) {

					Attribute atr = new Attribute(atrt);
					atr.save();
					ObjectReference o = g.getAbstractRelinkObject(atrt.getLink(), s);
					if (o != null) {
						System.out.println("and is setting it to " + o.getObjectType() + " (" + o.getObjectId() + ")");

						o.save();
						atr.setLink(o);
						// TODO: perhaps we should set the value of the linked object, too, e.g.:
						// o.getAttribute().setValue(atrt.getDefaultValue());
						atr.setValue(atrt.getDefaultValue());
						atr.update();

						s.setAttribute(atr);
						s.update();

					} else {

						System.out.println("but didn't find a fitting equivalent.");

					}

				} else {

					System.out.println("but has no object reference specified correctly.");

				}

			}

		}

		// LINK REBINDING

		for (Map.Entry<Mission, Mission> entry : missionbinder.entrySet()) {
			System.out.println("Ersetze alle $_" + entry.getKey().getId() + " durch $_" + entry.getValue().getId());
		}

		for (Attribute a : s.getAllSubAttributes()) {

			if (a.getValue() != null) {
				if (a.getValue().contains("$_")) {

					for (Map.Entry<Mission, Mission> entry : missionbinder.entrySet()) {

						if (a.getValue().contains("$_" + entry.getKey().getId())) {

							String newvalue = a.getValue();

							newvalue = newvalue.replace("$_" + entry.getKey().getId(), "$_" + entry.getValue().getId());
							newvalue = newvalue.replace("$_mission_" + entry.getKey().getId(),
									"$_mission_" + entry.getValue().getId());
							System.out.println(
									"excecuting in " + a.getType().getName() + " of type " + a.getTypeDescription()
											+ ":$_" + entry.getKey().getId() + " -> $_" + entry.getValue().getId());

							a.setValue(newvalue);
							a.update();
						}
					}

				}

				if (a.getType().getFileType().equals("mission")) {

					String newvalue = a.getValue();

					for (Map.Entry<Mission, Mission> entry : missionbinder.entrySet()) {
						if (a.getValue().equals(String.valueOf(entry.getKey().getId()))) {

							newvalue = newvalue.replace("" + entry.getKey().getId(), "" + entry.getValue().getId());
							System.out.println("excecuting mission attribute:" + entry.getKey().getId() + " -> "
									+ entry.getValue().getId());

						}
						a.setValue(newvalue);
						a.update();

					}

				}

			}
		}

		// replace scenefield occurences with default values (empty string right
		// now):
		for (Attribute aa : s.getAllSubAttributes()) {
			if (aa.getValue() != null) {
				if (aa.getValue().contains("scenefield(")) {
					Scenefield scHelper = new Scenefield(aa.getValue());
					aa.setValue(scHelper.getDefaultValue());
					aa.update();
				}
			}
		}

		return s;
	}

	public static final Finder<Long, SceneType> find = new Finder<Long, SceneType>(Long.class, SceneType.class);

}
