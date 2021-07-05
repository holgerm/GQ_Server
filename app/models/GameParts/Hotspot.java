package models.GameParts;

import models.help.GameCopyContext;
import play.db.ebean.Model;
import util.Global;

import javax.imageio.ImageIO;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import models.Game;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.feth.play.module.pa.providers.password.UsernamePasswordAuthUser;

import controllers.Application;
import flexjson.JSON;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Entity
public class Hotspot extends Model {

	@Id
	private Long id;

	@ManyToOne
	private HotspotType type;

	@OneToOne
	private Hotspot parent;

	private String name;
	private int sort;
	private Float longitude;
	private Float latitude;

	@ManyToMany
	private List<Attribute> attributes;

	@ManyToMany
	private List<Rule> rules;

	public Hotspot(HotspotType t, Float lon, Float lat, String n) {

		type = t;
		longitude = lon;
		latitude = lat;
		name = n;

	}

	// SETTER

	public void setParent(Hotspot x) {
		parent = x;
	}

	public void setAttribute(Attribute t) {

		try {
			List<Attribute> copyOfAttributes = new ArrayList<Attribute>(attributes.size());
			;
			for (Attribute item : attributes)
				copyOfAttributes.add(item);

			for (Attribute aatr : copyOfAttributes) {
				if (aatr.getXMLType().equals(t.getXMLType())) {
					attributes.remove(aatr);
				}
			}
			attributes.add(t);

		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@JSON(include = true)
	public String getRadius() {

		return getAttributeValue(getAttributeType("radius"));

	}

	private Image getMarkerImage(Game g) {
		try {

			String path = getAttributeValue(getAttributeType("img"));

			if (path.equals("/assets/img/marker.png")) {

				String[] splitResult = path.split("/");

				path = splitResult[splitResult.length - 1];

				Image image = ImageIO.read(new File("public/img", path));

				return image;

			} else {

				String[] splitResult = path.split("/");

				path = splitResult[splitResult.length - 1];

				String portal = String.valueOf(Application.getLocalPortal().getId());
				String gameid = String.valueOf(g.getId());
				if (splitResult.length > 4) {
					portal = splitResult[splitResult.length - 4];
					gameid = splitResult[splitResult.length - 2];

				}
				Image image = ImageIO.read(new File("public/uploads/" + portal + "/editor/" + gameid + "/", path));

				return image;
			}

		} catch (IOException e) {
			return null;
		}
	}

	private float getMarkerScale(Game g) {
		final int RESULTING_MAX_MARKER_SIZE = 60;

		Image image = getMarkerImage(g);

		if (image == null) {
			return 1f;
		}

		int maxSize = (image.getHeight(null) >= image.getWidth(null))
				? image.getHeight(null)
				: image.getWidth(null);
		float scale = ((float) RESULTING_MAX_MARKER_SIZE) / maxSize;
		return scale;
	}

	public int getMarkerHeight(Game g) {
		final int RESULTING_MAX_MARKER_SIZE = 60;

		Image image = getMarkerImage(g);

		if (image == null) {
			return RESULTING_MAX_MARKER_SIZE;
		}

		float scaledHeight = image.getHeight(null) * getMarkerScale(g);
		return (int) scaledHeight;
	}

	public int getMarkerWidth(Game g) {
		final int RESULTING_MAX_MARKER_SIZE = 60;

		Image image = getMarkerImage(g);
		if (image == null) {
			return (int) (RESULTING_MAX_MARKER_SIZE * 0.8f);
		}

		float scaledWidth = image.getWidth(null) * getMarkerScale(g);
		return (int) scaledWidth;
	}

	public int getMarkerAnchorWidth(Game g) {

		return getMarkerWidth(g) / 2;
	}

	public int getMarkerAnchorHeight(Game g) {

		return getMarkerHeight(g) - 1;
	}

	@JSON(include = false)
	public List<AttributeType> getAllAttributes() {
		return type.getAttributeTypes();

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

	public Attribute getAttribute(AttributeType at) {

		for (Attribute aa : attributes) {

			if (aa.getXMLType().equals(at.getXMLType())) {

				return aa;

			}

		}

		return null;

	}

	public AttributeType getAttributeType(String xml) {

		AttributeType x = null;

		for (AttributeType aa : type.getAttributeTypes()) {

			if (aa.getXMLType().equals(xml)) {

				x = aa;

			}

		}

		return x;

	}

	public String getAttributeValue(AttributeType at) {

		if (at != null) {
			String x = at.getDefaultValue();

			for (Attribute aa : attributes) {

				if (aa.getXMLType().equals(at.getXMLType())) {

					x = aa.getValue();

				}

			}

			return x;
		} else {

			return null;
		}

	}

	public void setLongitude(Float x) {
		longitude = x;
	}

	public void setLatitude(Float x) {
		latitude = x;
	}

	public void addRule(Rule r) {
		rules.add(r);
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
	public Float getLatitude() {
		return latitude;
	}

	@JSON(include = true)
	public Float getLongitude() {
		return longitude;
	}

	@JSON(include = false)
	public HotspotType getType() {
		return type;
	}

	@JSON(include = true)
	public Long getId() {
		return id;
	}

	@JSON(include = false)
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

	public static final Finder<Long, Hotspot> find = new Finder<Long, Hotspot>(Long.class, Hotspot.class);

	public Hotspot copyMe(GameCopyContext copyContext) {

		Hotspot h = new Hotspot(type, longitude, latitude, name);
		h.setParent(this);
		h.save();

		/// ATTRIBUTES

		for (Attribute aatr : attributes) {

			h.setAttribute(aatr.copyMe(copyContext));
			h.update();
		}

		for (Rule ar : rules) {

			h.addRule(ar.copyMe(copyContext));
			h.update();
		}

		return h;
	}

	public Hotspot copyMe(float lon, float lat, GameCopyContext copyContext) {
		Hotspot h = new Hotspot(type, lon, lat, name);
		h.save();

		/// ATTRIBUTES

		for (Attribute aatr : attributes) {

			h.setAttribute(aatr.copyMe(copyContext));
			h.update();
		}

		for (Rule ar : rules) {

			h.addRule(ar.copyMe(copyContext));
			h.update();
		}

		return h;
	}

	@JSON(include = false)
	public void removeMe() {
		Set<Attribute> atrs = new HashSet<Attribute>();
		atrs.addAll(attributes);

		try {
			for (Attribute aa : atrs) {
				attributes.remove(aa);
				this.update();
				aa.delete();
			}

		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	public Element createXMLForWeb(Document doc, Game g) {

		Element hotspot = doc.createElement("hotspot");

		/// MISSION ATTRIBUTES
		Attr attr = doc.createAttribute("id");
		attr.setValue(String.valueOf(id));
		hotspot.setAttributeNode(attr);

		Attr attr2 = doc.createAttribute("latlong");
		attr2.setValue(latitude + "," + longitude);
		hotspot.setAttributeNode(attr2);

		for (AttributeType aa : getAllAttributes()) {

			if (getAttributeValue(aa) != null) {
				if ((!getAttributeValue(aa).equals("")) && (!getAttributeValue(aa).equals(" "))) {

					Attr attr4 = doc.createAttribute(aa.getXMLType());

					if (aa.getRealFileType().equals("seconds") || aa.getRealFileType().equals("stringseconds")) {

						try {
							int seconds = Integer.parseInt(getAttributeValue(aa));
							seconds = seconds * 1000;
							attr4.setValue(String.valueOf(seconds));
						} catch (NumberFormatException e) {
							attr4.setValue(getAttributeValue(aa));
						}

					} else {
						attr4.setValue(getAttributeValue(aa));
					}
					hotspot.setAttributeNode(attr4);
				}
			}
		}

		// RULES

		for (Rule r : rules) {
			Element rule = r.createXMLForWeb(doc, 0, this, g);
			hotspot.appendChild(rule);

		}

		// TODO Auto-generated method stub
		return hotspot;
	}

	public Element createXML(Document doc, Game g, ZipOutputStream zout) {

		Element hotspot = doc.createElement("hotspot");

		/// MISSION ATTRIBUTES
		Attr attr = doc.createAttribute("id");
		attr.setValue(String.valueOf(id));
		hotspot.setAttributeNode(attr);

		Attr attr2 = doc.createAttribute("latlong");
		attr2.setValue(latitude + "," + longitude);
		hotspot.setAttributeNode(attr2);

		for (AttributeType aa : getAllAttributes()) {

			if (getAttributeValue(aa) != null) {
				if ((!getAttributeValue(aa).equals("")) && (!getAttributeValue(aa).equals(" "))) {

					Attr attr4 = doc.createAttribute(aa.getXMLType());

					if (aa.getFileType().equals("file")) {
						URL url;
						try {
							url = new URL(getAttributeValue(aa));

							String path = getAttributeValue(aa);
							String[] splitResult = path.split("/");

							path = splitResult[splitResult.length - 1];

							String portal = String.valueOf(Application.getLocalPortal().getId());
							String gameid = String.valueOf(g.getId());
							if (splitResult.length > 4) {
								portal = splitResult[splitResult.length - 4];
								gameid = splitResult[splitResult.length - 2];

							}
							File file = new File("public/uploads/" + portal + "/editor/" + gameid, path);

							if (!file.exists()) {

								file = new File("public/uploads/" + Application.getLocalPortal().getId() + "/editor/"
										+ g.getId() + "/files", path);

								file.deleteOnExit();

								try {
									FileUtils.copyURLToFile(url, file);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}

							attr4.setValue("files/" + path);

							final int BUFFER = 2048;

							byte data[] = new byte[BUFFER];

							FileInputStream fis = new FileInputStream(file);

							BufferedInputStream bis = new BufferedInputStream(fis, BUFFER);

							ZipEntry ze = new ZipEntry("files/" + path);
							zout.putNextEntry(ze);

							int size = -1;
							while ((size = bis.read(data, 0, BUFFER)) != -1) {
								zout.write(data, 0, size);
							}

							zout.closeEntry();
						} catch (Exception x) {
							x.printStackTrace();

						}

					} else if (aa.getRealFileType().equals("seconds") || aa.getRealFileType().equals("stringseconds")) {

						try {
							int seconds = Integer.parseInt(getAttributeValue(aa));
							seconds = seconds * 1000;
							attr4.setValue(String.valueOf(seconds));
						} catch (NumberFormatException e) {
							attr4.setValue(getAttributeValue(aa));
						}

					} else {
						attr4.setValue(getAttributeValue(aa));
					}
					hotspot.setAttributeNode(attr4);
				}
			}
		}

		// RULES

		for (Rule r : rules) {
			Element rule = r.createXML(doc, 0, this, g, zout);
			hotspot.appendChild(rule);

		}

		// TODO Auto-generated method stub
		return hotspot;
	}

	public void setName(String n) {
		name = n;
	}

	public Hotspot migrateTo(HotspotType hst, GameCopyContext copyContext) {

		Hotspot h = new Hotspot(hst, longitude, latitude, name);
		h.save();

		copyContext.hotspotMap.put(this, h);

		/// Rules

		for (Rule r : rules) {

			String oldtrigger = r.getTrigger();

			boolean done = false;

			for (RuleType nrt : hst.getPossibleRuleTypes()) {

				if (nrt.getTrigger().equals(oldtrigger)) {

					h.addRule(r.migrateTo(nrt, copyContext));
					h.update();

					done = true;

				}

			}
		}

		// ATTRIBUTES

		for (Attribute at : attributes) {

			boolean done = false;
			AttributeType attt = at.getType();

			for (AttributeType atrt : h.getType().getAttributeTypes()) {

//					if (atrt.getXMLType().equals(attt.getXMLType())) {
					if (atrt.getName().equals(attt.getName())) {

					h.setAttribute(at.migrateTo(atrt, copyContext));
					h.update();
					done = true;

				}

			}
			h.update();
		}

		h.setParent(parent);
		h.update();

		return h;
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

	@JSON(include = false)
	public Hotspot getParent() {
		// TODO Auto-generated method stub
		return parent;
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

}
