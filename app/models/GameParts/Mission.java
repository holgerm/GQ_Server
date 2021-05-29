package models.GameParts;

import play.db.ebean.Model;
import util.Global;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import models.Game;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import controllers.Application;
import flexjson.JSON;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Entity
public class Mission extends Model {

	@Id
	private Long id;

	private Long gqid;

	private String name;

	@ManyToOne
	private MissionType type;

	private Mission copiedFrom;

	@ManyToMany
	private List<Attribute> attributes;

	@ManyToMany
	private List<Rule> rules;

	@ManyToMany
	private List<Content> contents;

	private int postop;
	private int posleft;

	/**
	 * Use for refresh in browser when we need new objects to replace old ones to
	 * cheat over db.
	 * 
	 * @param n
	 * @param m
	 */
	public Mission(String n, MissionType m) {

		name = n;
		type = m;
		save();
		gqid = id;
		update();
	}

	/**
	 * Use for migration of templating via scenes when creating new destination
	 * objects.
	 * 
	 * @param n
	 * @param m
	 * @param oldid
	 */
	public Mission(String n, MissionType m, Long oldid) {

		this(n, m);
		gqid = oldid;
		update();
	}

	// SETTER
	public void setCopiedFrom(Mission x) {
		copiedFrom = x;
	}

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

	public void addContent(Content c) {
		contents.add(c);
	}

	// GETTER
	@JSON(include = true)
	public String getName() {
		return name;
	}

	@JSON(include = false)
	public List<Attribute> getAttributes() {
		return attributes;
	}

	@JSON(include = false)
	public List<Rule> getRules() {
		return rules;
	}

	@JSON(include = false)
	public List<Rule> getAllSubRules() {

		List<Rule> all = new ArrayList<Rule>();
		all.addAll(rules);

		for (Rule r : rules) {

			all.addAll(r.getSubRules());

		}

		return all;

	}

	@JSON(include = true)
	public List<Content> getContents() {
		return contents;
	}

	@JSON(include = false)
	public Mission getCopiedFrom() {
		return copiedFrom;
	}

	@JSON(include = false)
	public boolean hasPossibleRuleTypes() {

		if (type.getPossibleRuleTypes().size() > 0) {
			return true;
		} else {
			return false;
		}

	}

	@JSON(include = false)
	public boolean hasPossibleContentTypes() {

		if (type.getPossibleContentTypes().size() > 0) {
			return true;
		} else {
			return false;
		}

	}

	@JSON(include = false)
	public boolean hasAttributeTypes() {

		if (type.getAttributeTypes().size() > 0) {
			return true;
		} else {
			return false;
		}

	}

	@JSON(include = false)
	public boolean showMultipleAchordions() {

		int count = 0;

		if (hasPossibleContentTypes() == true) {
			count = count + 1;
		}
		if (hasAttributeTypes() == true) {
			count = count + 1;
		}

		if (count > 1) {
			return true;
		} else {
			return false;
		}

	}

	@JSON(include = false)
	public List<AttributeType> getAllAttributes() {
		return type.getAttributeTypes();

	}

	@JSON(include = false)
	public Long getAttributeId(AttributeType at) {

		Long x = 0L;

		for (Attribute aa : attributes) {

			if (aa.getXMLType().equals(at.getXMLType())) {

				x = aa.getId();

			}

		}

		return x;

	}

	@JSON(include = false)
	public String getAttributeValue(AttributeType at) {

		String x = at.getDefaultValue();

		for (Attribute aa : attributes) {

			if (aa.getXMLType().equals(at.getXMLType())) {

				x = aa.getValue();

			}

		}

		return x;

	}

	@JSON(include = true)
	public Long getId() {
		return id;
	}

	public Long getPersistentId() {

		return gqid;

	}

	public static final Finder<Long, Mission> find = new Finder<Long, Mission>(Long.class, Mission.class);

	public Mission copyMe(String n, Map<Mission, Mission> missionbinder) {

		return copyMe(n, missionbinder, true);

	}

	public Mission copyMe(String n, Map<Mission, Mission> missionbinder, boolean migrate) {

		String nam = name;

		Mission m;
		if (migrate) {
			m = new Mission(nam, type, id);
		} else {
			m = new Mission(nam, type);
		}

		m.save();
		missionbinder.put(this, m);

		// ATTRIBUTE

		for (Attribute aatr : attributes) {

			m.setAttribute(aatr.copyMe());
			m.update();

		}

		// RULES

		for (Rule ar : rules) {

			m.addRule(ar.copyMe());
			m.update();

		}

		// CONTENTS

		Set<String> in = new HashSet<String>();

		for (Content ac : contents) {

			if (in.contains(ac.getName())) {

				int counter = 1;
				for (String st : in) {
					if (st.equals(ac.getName())) {
						counter++;
					}
				}
				in.add(ac.getName());
				m.addContent(ac.copyMe("" + counter));

			} else {

				in.add(ac.getName());
				m.addContent(ac.copyMe(""));

			}

			m.update();

		}

		return m;
	}

	@JSON(include = false)
	public MissionType getType() {
		return type;
	}

	public void setName(String n) {
		name = n;
	}

	public void removeMe() {

		Set<Attribute> atrs = new HashSet<Attribute>();
		atrs.addAll(attributes);
		Set<Rule> rls = new HashSet<Rule>();
		rls.addAll(rules);
		Set<Content> cnts = new HashSet<Content>();
		cnts.addAll(contents);

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
			for (Content ac : cnts) {
				contents.remove(ac);
				this.update();
				ac.removeMe();
				ac.delete();
			}
		} catch (RuntimeException e) {

			System.out.println("Can't delete Mission.");
			e.printStackTrace();

		}

	}

	public List<Element> createXML(Document doc, Game g, ZipOutputStream zout) {

		List<Element> e = new ArrayList<Element>();

		Element mission = null;

		mission = doc.createElement("mission");

		// / MISSION ATTRIBUTES
		Attr attr = doc.createAttribute("id");
		attr.setValue(String.valueOf(id));
		mission.setAttributeNode(attr);

		Attr attr2 = doc.createAttribute("type");
		attr2.setValue(type.getXMLType());
		mission.setAttributeNode(attr2);

		for (AttributeType aa : getAllAttributes()) {

			if (getAttributeValue(aa) != null) {
				if ((!getAttributeValue(aa).equals("")) && (!getAttributeValue(aa).equals(" "))) {

					Attr attr4 = doc.createAttribute(aa.getXMLType());

					if (aa.getFileType().equals("file") && (!getAttributeValue(aa).contains("@_"))
							&& (!getAttributeValue(aa).contains("$_"))) {

						System.out.println("is File: " + getAttributeValue(aa));
						URL url;
						try {
							url = new URL(getAttributeValue(aa));

							String path = getAttributeValue(aa);
							String[] splitResult = path.split("/");

							path = splitResult[splitResult.length - 1];

							System.out.println("Path specified");

							String portal = String.valueOf(Application.getLocalPortal().getId());
							String gameid = String.valueOf(g.getId());
							if (splitResult.length > 4) {
								portal = splitResult[splitResult.length - 4];
								System.out.println(portal);
								gameid = splitResult[splitResult.length - 2];

							}

							File file = new File("public/uploads/" + portal + "/editor/" + gameid, path);
							System.out.println("public/uploads/" + portal + "/editor/" + gameid);

							if (!file.exists()) {

								System.out.println("File 1 does not exist");

								file = new File("public/uploads/" + portal + "/editor/" + gameid + "/files", path);

								if (!file.exists()) {
									System.out.println("File 2 does not exist");

									try {
										FileUtils.copyURLToFile(url, file);
									} catch (IOException x) {
										System.out.println("Copying file failed");
										x.printStackTrace();

									}

								}

							}

							attr4.setValue("files/" + path);

							System.out.println("File Path set.");

							final int BUFFER = 2048;

							byte data[] = new byte[BUFFER];

							FileInputStream fis = new FileInputStream(file);
							BufferedInputStream bis = new BufferedInputStream(fis, BUFFER);

							ZipEntry ze = new ZipEntry("files/" + path);
							zout.putNextEntry(ze);
							System.out.println("Buffer set");

							int size = -1;
							while ((size = bis.read(data, 0, BUFFER)) != -1) {
								zout.write(data, 0, size);
							}

							System.out.println("File Buffer read.");

							zout.closeEntry();

						} catch (Exception x) {
							x.printStackTrace();

						}

					} else if (aa.getRealFileType().equals("seconds") || aa.getRealFileType().equals("stringseconds")) {

						try {
							int seconds = Integer.parseInt(getAttributeValue(aa));
							seconds = seconds * 1000;
							attr4.setValue(String.valueOf(seconds));
						} catch (NumberFormatException er) {
							attr4.setValue(getAttributeValue(aa));
						}

					} else {
						attr4.setValue(getAttributeValue(aa));
					}
					mission.setAttributeNode(attr4);
				}
			}
		}

		// RULES

		for (Rule r : rules) {

			if (!r.getFirstAction().equals("Keine") && !r.getFirstAction().equals("Deep")) {
				Element rule = r.createXML(doc, 0, this, g, zout);
				mission.appendChild(rule);

			}
		}

		boolean contenthasparent = false;
		Element contentparent = null;

		// CONTENTS
		for (Content c : contents) {
			Element content = c.createXML(doc, zout, g);

			mission.appendChild(content);

		}

		e.add(mission);

		return e;
	}

	public List<Element> createXMLForWeb(Document doc, Game g) {

		List<Element> e = new ArrayList<Element>();

		Element mission = null;

		mission = doc.createElement("mission");

		// / MISSION ATTRIBUTES
		Attr attr = doc.createAttribute("id");
		attr.setValue(String.valueOf(id));
		mission.setAttributeNode(attr);

		Attr attr2 = doc.createAttribute("type");
		attr2.setValue(type.getXMLType());
		mission.setAttributeNode(attr2);

		for (AttributeType aa : getAllAttributes()) {

			if (getAttributeValue(aa) != null) {
				if ((!getAttributeValue(aa).equals("")) && (!getAttributeValue(aa).equals(" "))) {

					Attr attr4 = doc.createAttribute(aa.getXMLType());

					if (aa.getRealFileType().equals("seconds") || aa.getRealFileType().equals("stringseconds")) {

						try {
							int seconds = Integer.parseInt(getAttributeValue(aa));
							seconds = seconds * 1000;
							attr4.setValue(String.valueOf(seconds));
						} catch (NumberFormatException er) {
							attr4.setValue(getAttributeValue(aa));
						}

					} else {
						attr4.setValue(getAttributeValue(aa));
					}
					mission.setAttributeNode(attr4);
				}
			}
		}

		// RULES

		for (Rule r : rules) {

			if (!r.getFirstAction().equals("Keine") && !r.getFirstAction().equals("Deep")) {
				Element rule = r.createXMLForWeb(doc, 0, this, g);
				mission.appendChild(rule);

			}
		}

		boolean contenthasparent = false;
		Element contentparent = null;

		// CONTENTS
		for (Content c : contents) {
			Element content = c.createXMLForWeb(doc, this, g);

			mission.appendChild(content);

		}

		e.add(mission);

		return e;
	}

	public void removeContent(Content c) {

		if (contents.contains(c)) {
			contents.remove(c);
		}
	}

	public Rule getRule(RuleType rt) {
		for (Rule r : rules) {
			if (r.getTrigger().equals(rt.getTrigger())) {
				return r;
			}
		}

		return null;
	}

	public boolean hasRule(RuleType rt) {
		for (Rule r : rules) {
			if (r.getTrigger().equals(rt.getTrigger())) {
				return true;
			}
		}
		return false;
	}

	public Mission migrateTo(MissionType missionType) {

		Mission m = new Mission(name, missionType);
		m.save();

		// ATTRIBUTES

		for (Attribute at : attributes) {

			boolean done = false;
			AttributeType attt = at.getType();

			for (AttributeType atrt : missionType.getAttributeTypes()) {

//				if (atrt.getXMLType().equals(attt.getXMLType())) {
				if (atrt.getName().equals(attt.getName())) {
					m.setAttribute(at.migrateTo(atrt));
					m.update();
					done = true;
				}

			}

			if (done == false) {

				System.out.println("Didn't find AttributeType (Mission) " + at.getName());
			}

			m.update();

		}

		// RULES

		for (Rule r : rules) {

			String oldtrigger = r.getTrigger();

			boolean done = false;

			for (RuleType nrt : missionType.getPossibleRuleTypes()) {

				if (nrt.getTrigger().equals(oldtrigger)) {

					m.addRule(r.migrateTo(nrt));
					m.update();

					done = true;

				}

			}

			if (done == false) {

				System.out.println("Didn't find RuleType " + oldtrigger);
			}

		}

		// CONTENTS

		for (Content c : contents) {

			boolean done = false;
			ContentType ct = c.getType();

			for (ContentType nct : missionType.getPossibleContentTypes()) {

				if (nct.getXMLType().equals(ct.getXMLType())) {

					m.addContent(c.migrateTo(nct));
					m.update();
					done = true;

				}

			}

			if (done == false) {

				System.out.println("Didn't find ContentType " + c.getType().getXMLType());
			}

			m.setCopiedFrom(this);

			m.update();

		}

		return m;

	}

	@JSON(include = true)
	public List<Attribute> getComputedAttributes() {

		List<Attribute> aMap = new ArrayList<Attribute>();

		for (AttributeType x : getAllAttributes()) {

			aMap.add(getComputedAttribute(x));

		}

		return aMap;

	}

	@JSON(include = false)
	public Attribute getComputedAttribute(AttributeType at) {

		Attribute x = null;

		for (Attribute aa : attributes) {

			if (aa.getXMLType().equals(at.getXMLType())) {

				x = aa;

			}

		}

		if (x == null) {

			x = new Attribute(at);
			x.setValue(at.getDefaultValue());

		}

		return x;

	}

	@JSON(include = true)
	public Map<String, Rule> getTrigger() {

		Map<String, Rule> aMap = new HashMap<String, Rule>();

		for (RuleType x : type.getPossibleRuleTypes()) {

			try {

				Rule y = getRule(x).getSubRules().get(0);

				aMap.put(x.getTrigger(), y);

			} catch (Exception e) {

			}

		}

		return aMap;

	}

	public Attribute getAttribute(AttributeType at) {

		for (Attribute aa : attributes) {

			if (aa.getXMLType().equals(at.getXMLType())) {

				return aa;

			}

		}

		return null;
	}

	@JSON(include = false)
	public int getTop() {
		return postop;
	}

	public void setTop(int x) {
		this.postop = x;
	}

	@JSON(include = false)
	public int getLeft() {

		return posleft;

	}

	public void setLeft(int y) {
		this.posleft = y;
	}

	@JSON(include = true)
	public String getTypeDescription() {
		return type.getXMLType();
	}

}
