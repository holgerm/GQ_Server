package models;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import models.GameParts.Action;
import models.GameParts.Attribute;
import models.GameParts.AttributeType;
import models.GameParts.Content;
import models.GameParts.GameType;
import models.GameParts.Hotspot;
import models.GameParts.HotspotType;
import models.GameParts.MenuItem;
import models.GameParts.Mission;
import models.GameParts.MissionType;
import models.GameParts.ObjectReference;
import models.GameParts.Part;
import models.GameParts.PartType;
import models.GameParts.Rule;
import models.GameParts.Scene;
import models.GameParts.SceneType;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import play.data.format.Formats;
import play.db.ebean.Model;
import util.Global;
import controllers.Application;
import flexjson.JSON;

@Entity
@Table(name = "games")
@JSON(include = false)
public class Game extends Model {

	/**
	 * Variables
	 * 
	 */

	@Id
	public Long id;
	public String name;
	public String zip;
	@Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date lastUpdate;

	@OneToMany(mappedBy = "game")
	private Set<ProviderGames> portals;

	@OneToMany(mappedBy = "game")
	private Set<GameRights> rights;

	@ManyToMany
	@OrderBy("datum")
	private List<NewsstreamItem> Newsstream;

	public User owner;
	public boolean publish;
	
	public Long userlastupdated;

	private int version;

	@OneToOne
	private Game oldversion;

	/**
	 * Game Contents
	 * 
	 */

	@ManyToOne
	private GameType type;

	@ManyToMany
	@OrderBy("id")
	private List<Part> parts;
	@ManyToMany
	private Set<Attribute> attributes;
	@ManyToMany
	private List<Hotspot> hotspots;
	@ManyToMany
	private List<MenuItem> menuItems;

	@OneToOne
	private SceneType editor_only_scene_type;

	/**
	 * 
	 * Constructors
	 * 
	 */

	public Game(String startName, String startZip) {

		name = startName;
		zip = startZip;

		publish = true;

		lastUpdate = new Date();
		version = 1;

	}

	/**
	 * GAME EDITOR CONSTRUCTOR
	 * 
	 */
	public Game(String n, GameType t) {
		name = n;
		type = t;
		zip = "none";
		version = 1;
		lastUpdate = new Date();
	}

	@JSON(include = false)
	public boolean hasGameType() {

		if (type != null) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 
	 * Functions
	 * 
	 */

	public void addOwner(User u) {

		if (owner == null) {
			owner = u;

			addUser(u, "write");

		}

	}

	public void addUser(User u, String rights) {

		GameRights grights = new GameRights();
		grights.setUser(u);
		grights.setGame(this);

		grights.setRights(rights);

		grights.save();

		this.rights.add(grights);
		// Also add the association object to the employee.
		u.getGames().add(grights);

		this.update();
		u.update();
		grights.update();

	}

	
	
	
	public void editUser(User u, String newright) {

		Set<GameRights> todelete = new HashSet<GameRights>();
		for (GameRights agr : rights) {

			if (rights.contains(agr)) {

				if (agr.getUser().getId().equals(u.getId())) {

					todelete.add(agr);

				}

			}

		}
		for (GameRights agr : todelete) {

			User us = agr.getUser();
			us.deleteGame(agr);
			us.update();
			rights.remove(agr);
			agr.delete();

		}

		GameRights grights = new GameRights();
		grights.setUser(u);
		grights.setGame(this);

		grights.setRights(newright);

		grights.save();

		this.rights.add(grights);
		// Also add the association object to the employee.
		u.getGames().add(grights);

		this.save();
		u.save();
		grights.save();

	}

	public void deleteUser(User u) {

		Set<GameRights> todelete = new HashSet<GameRights>();
		for (GameRights agr : rights) {

			if (rights.contains(agr)) {

				if (agr.getUser().getId().equals(u.getId())) {

					todelete.add(agr);

				}

			}

		}
		for (GameRights agr : todelete) {

			User us = agr.getUser();
			us.deleteGame(agr);
			us.update();
			rights.remove(agr);
			agr.delete();

		}

	}

	@JSON(include = false)
	public Game copyMe(String n) {

		Map<Mission, Mission> missionbinder = new HashMap<Mission, Mission>();
		Map<Hotspot, Hotspot> hotspotbinder = new HashMap<Hotspot, Hotspot>();

		String nam = name + " " + n;

		try {
			double d = Double.parseDouble(n);
		} catch (NumberFormatException nfe) {

			if (n.contains("(1)")) {

				n.replace("(1)", "");

			}

			nam = name + " " + String.valueOf(n);
		}

		Game s = new Game(nam, type);
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

			s.addPart(ap.copyMe(add, missionbinder, hotspotbinder));
			s.update();
			counter++;
		}

		// ATTRIBUTES

		for (Attribute aart : attributes) {
			s.setAttribute(aart.copyMe());
			s.update();
		}

		// HOTSPOTS
		counter = 1;
		for (Hotspot ah : hotspots) {
			s.addHotspot(ah.copyMe("" + counter));
			counter++;
		}

		s.relinkMissionAndHotspots(missionbinder, hotspotbinder);

		s.update();

		return s;

	}

	public void relinkMissionAndHotspots(Map<Mission, Mission> missionbinder,
			Map<Hotspot, Hotspot> hotspotbinder) {

		for (Attribute a : this.getAllSubAttributes()) {

			if (a.getValue() != null) {
				if (a.getValue().contains("$_")) {

					for (Map.Entry<Mission, Mission> entry : missionbinder
							.entrySet()) {

						if (a.getValue()
								.contains("$_" + entry.getKey().getId())
								|| a.getValue().contains(
										"$_mission_" + entry.getKey().getId())) {

							String newvalue = a.getValue();

							newvalue = newvalue.replace("$_"
									+ entry.getKey().getId(), "$_mission_"
									+ entry.getValue().getId());
							newvalue = newvalue.replace("$_mission_"
									+ entry.getKey().getId(), "$_mission_"
									+ entry.getValue().getId());

							a.setValue(newvalue);
							a.update();
						}
					}

					for (Map.Entry<Hotspot, Hotspot> entry : hotspotbinder
							.entrySet()) {

						if (a.getValue()
								.contains("$_" + entry.getKey().getId())
								|| a.getValue().contains(
										"$_hotspot_" + entry.getKey().getId())) {

							String newvalue = a.getValue();

							newvalue = newvalue.replace("$_hotspot_"
									+ entry.getKey().getId(), "$_hotspot_"
									+ entry.getValue().getId());

							a.setValue(newvalue);
							a.update();
						}
					}

				}

				if (a.getType().getFileType().equals("mission")) {

					String newvalue = a.getValue();

					for (Map.Entry<Mission, Mission> entry : missionbinder
							.entrySet()) {
						if (a.getValue().equals(
								String.valueOf(entry.getKey().getId()))) {

							newvalue = newvalue.replace(""
									+ entry.getKey().getId(), ""
									+ entry.getValue().getId());
							System.out.println("excecuting mission attribute:"
									+ entry.getKey().getId() + " -> "
									+ entry.getValue().getId());

						}
						a.setValue(newvalue);
						a.update();

					}

				}

				if (a.getType().getFileType().equals("hotspot")) {

					String newvalue = a.getValue();

					for (Map.Entry<Hotspot, Hotspot> entry : hotspotbinder
							.entrySet()) {
						if (a.getValue().equals(
								String.valueOf(entry.getKey().getId()))) {

							newvalue = newvalue.replace(""
									+ entry.getKey().getId(), ""
									+ entry.getValue().getId());
							System.out.println("excecuting hotspot attribute:"
									+ entry.getKey().getId() + " -> "
									+ entry.getValue().getId());

						}
						a.setValue(newvalue);
						a.update();

					}

				}

			}
		}

	}

	@JSON(include = false)
	public boolean removeMe() {

		List<ProviderGames> pg = new ArrayList<ProviderGames>(portals.size());
		for (ProviderGames item : portals)
			pg.add(item);

		List<GameRights> gr = new ArrayList<GameRights>(rights.size());
		for (GameRights item : rights)
			gr.add(item);

		List<NewsstreamItem> ni = new ArrayList<NewsstreamItem>(
				Newsstream.size());
		for (NewsstreamItem item : Newsstream)
			ni.add(item);

		try {
			for (NewsstreamItem oneni : ni) {
				Newsstream.remove(oneni);
				update();
				if (oneni.getPosterClass() == "user") {

					if (User.find.where().eq("id", oneni.getPosterid())
							.findRowCount() == 1) {

						User.find.byId(oneni.getPosterid())
								.deleteNewsstreamItem(oneni);
					}

				}

				try {

					oneni.delete();

				} catch (RuntimeException e) {

					System.out.println("Can't delete NewsstreamItem:");
					e.printStackTrace();

				}

			}

		} catch (ConcurrentModificationException e) {

			System.out.println("Iterator Exception:");
			e.printStackTrace();

		}
		try {
			for (ProviderGames onepg : pg) {

				portals.remove(onepg);
				update();
				if (ProviderPortal.find.where()
						.eq("id", onepg.getPortal().getId()).findRowCount() == 1) {
					ProviderPortal p = ProviderPortal.find.byId(onepg
							.getPortal().getId());

					p.deleteGame(onepg);

					p.update();
				}

				try {
					onepg.delete();

				} catch (RuntimeException e) {

					System.out.println("Can't delete ProviderGames:");
					e.printStackTrace();

				}

			}

		} catch (ConcurrentModificationException e) {

			System.out.println("Iterator Exception:");
			e.printStackTrace();

		}

		try {

			for (GameRights onegr : gr) {

				rights.remove(onegr);
				onegr.getUser().deleteGame(onegr);

				onegr.getUser().update();

				try {
					onegr.delete();
					System.out.println("Game deleted GameRights.");

				} catch (RuntimeException e) {

					System.out.println("Can't delete GemRights:");
					e.printStackTrace();

				}

			}
		} catch (ConcurrentModificationException e) {

			System.out.println("Iterator Exception:");
			e.printStackTrace();

		}

		File todelete = new File(getFile());
		FileUtils.deleteQuietly(todelete);

		Set<Attribute> atrs = new HashSet<Attribute>();
		atrs.addAll(attributes);

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

			System.out.println("Can't delete Game-Contents.");
			e.printStackTrace();

		}

		System.out.println("Done with Game-intern links.");

		try {

			System.out.println("Deleting Portal-Links to be sure.");

			for (ProviderPortal app : ProviderPortal.find.all()) {

				if (app.getGame(this) != null) {

					app.deleteGame(app.getGame(this));

					app.update();

				}

			}

		} catch (ConcurrentModificationException e) {

			System.out.println("Iterator Exception:");
			e.printStackTrace();

		}

		System.out.println("Remove Me complete.");

		return true;

	}

	
	

	@JSON(include = false)
	public int getAmountOfUsers() {
		
		int i = 0;
	for(GameRights gr : rights){
		
		if(gr.getRights().equals("write")){
			
			
			i++;
			
		}
		
	}
		return i;
	}
	
	
	@JSON(include = false)
	public String getPortalBackLink() {

		String x = "";

		for (ProviderGames p : portals) {

			if (portals.size() == 1) {

				return p.getPortal().getTemplateServerURLDropSlash();

			} else {

				if (!p.getPortal().getId().equals(Global.defaultportal.getId())) {

					return p.getPortal().getTemplateServerURLDropSlash();

				}

			}

		}

		return x;

	}

	public boolean existsUser(User u) {

		boolean exists = false;

		for (GameRights agameright : rights) {

			if (agameright.getUser().getId().equals(u.getId())) {

				exists = true;

			}

		}

		return exists;

	}

	public GameRights getUser(User u) {

		GameRights gr = new GameRights();

		for (GameRights agameright : rights) {

			if (agameright.getUser().getId().equals(u.getId())) {

				if (gr.getRights().equals("write")) {

				} else {

					gr = agameright;
				}

			}

		}

		return gr;

	}

	public ProviderGames getPortal(ProviderPortal p) {

		ProviderGames pg = new ProviderGames();

		for (ProviderGames aprovidergame : portals) {

			if (aprovidergame.getPortal().getIdentifier() == p.getIdentifier()) {

				pg = aprovidergame;

			}

		}

		return pg;

	}

	public boolean existsPortal(ProviderPortal p) {

		boolean exists = false;

		for (ProviderGames aprovidergame : portals) {

			if (aprovidergame.getPortal().getIdentifier() == p.getIdentifier()) {

				exists = true;

			}

		}

		return exists;

	}

	public Set<GameRights> getRightsOnPortal(ProviderPortal p) {

		Set<GameRights> onPortal = new HashSet<GameRights>();
		for (GameRights agameright : rights) {

			if (p.existsUser(agameright.getUser()) == true) {
				onPortal.add(agameright);
			}

		}

		boolean visibility = false;

		for (ProviderGames visible : portals) {

			visibility = visible.getVisibility();

		}

		if (visibility == true) {
			for (ProviderUsers aprovideruser : p.getUsers()) {

				boolean contained = false;

				for (GameRights acontainedgr : onPortal) {
					if (acontainedgr.getUser().getId() == aprovideruser
							.getUser().getId()) {
						contained = true;
					}

				}

				if (contained == false) {
					GameRights newgr = new GameRights();
					newgr.setUser(aprovideruser.getUser());
					newgr.setGame(this);
					newgr.setRights("public");

					onPortal.add(newgr);

				}
			}
		}

		return onPortal;

	}

	public void deleteNewsstreamItem(NewsstreamItem ni) {

		if (Newsstream.contains(ni) == true) {

			Newsstream.remove(ni);

		}

	}

	/**
	 * EDITOR FUNCTIONS
	 * 
	 */

	@JSON(include = false)
	public GameType getType() {
		return type;
	}

	public void addHotspot(Hotspot h) {
		hotspots.add(h);
	}

	// DELETE HOTSPOT

	public void addPart(Part p) {
		parts.add(p);
	}

	public void setAttribute(Attribute t) {

		try {
			List<Attribute> copyOfAttributes = new ArrayList<Attribute>(
					attributes.size());
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

			System.out.println("Problem setting Attribute.");
			e.printStackTrace();

		}

	}

	public Scene getSceneForHotspot(Hotspot h) {

		for (Part p : parts) {

			if (p.isScene()) {

				if (p.getScene().getHotspots().contains(h)) {

					return p.getScene();

				}

			}

		}

		return null;

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

	public void part_up(Part p) {
		int old_position = parts.indexOf(p);

		if (old_position > 0) {

			List<Part> helplist = new ArrayList<Part>();
			for (Part curP : parts) {

				helplist.add(curP);

			}

			parts.clear();
			update();

			List<Part> new_parts = new ArrayList<Part>();

			for (int i = 0; i < helplist.size(); i++) {

				if (i == old_position - 1) {

					if (p.isScene()) {
						new_parts.add(new Part(p.getScene()));
					} else {
						new_parts.add(new Part(p.getMission()));
					}

					if (helplist.get(i).isScene()) {
						new_parts.add(new Part(helplist.get(i).getScene()));
					} else {
						new_parts.add(new Part(helplist.get(i).getMission()));
					}
					// TODO helplist.get(i).removeMe();
					i++;
				} else {
					if (helplist.get(i).isScene()) {
						new_parts.add(new Part(helplist.get(i).getScene()));
					} else {
						new_parts.add(new Part(helplist.get(i).getMission()));
					}

				}

				// TODO helplist.get(i).removeMe();

			}

			parts = new_parts;
			update();
		}

	}

	public void part_down(Part p) {
		int old_position = parts.indexOf(p);
		System.out.println("Oldposi = " + parts.indexOf(p));

		if (old_position > -1 && old_position < parts.size() - 1) {

			List<Part> helplist = new ArrayList<Part>();
			for (Part curP : parts) {

				helplist.add(curP);

			}

			System.out.println("Helplist.Size = " + helplist.size());
			parts.clear();
			update();

			List<Part> new_parts = new ArrayList<Part>();

			for (int i = 0; i < helplist.size(); i++) {

				if (i == old_position) {
					i++;

					if (helplist.get(i).isScene()) {
						new_parts.add(new Part(helplist.get(i).getScene()));
					} else {
						new_parts.add(new Part(helplist.get(i).getMission()));
					}

					if (p.isScene()) {
						new_parts.add(new Part(p.getScene()));
					} else {
						new_parts.add(new Part(p.getMission()));
					}
					// TODO helplist.get(i).removeMe();
				} else {

					System.out.println("Running through list");

					if (helplist.get(i).isScene()) {
						new_parts.add(new Part(helplist.get(i).getScene()));
					} else {
						new_parts.add(new Part(helplist.get(i).getMission()));
						System.out.println("Adding new Mission");

					}

				}

				// TODO helplist.get(i).removeMe();

			}

			System.out.println("New_parts.Size = " + new_parts.size());

			parts = new_parts;
			update();
		}

	}

	public void createXML() {

		String xmlString = createXMLForWeb(getFirstMission());
		String sid = String.valueOf(id);

		try {

			File theDir3 = new File("public/uploads/"
					+ Application.getLocalPortal().getId());
			if (!theDir3.exists())
				theDir3.mkdir();

			File theDir2 = new File("public/uploads/"
					+ Application.getLocalPortal().getId() + "/editor");
			if (!theDir2.exists())
				theDir2.mkdir();

			File theDir = new File("public/uploads/"
					+ Application.getLocalPortal().getId() + "/editor/" + sid
					+ "/");
			if (!theDir.exists())
				theDir.mkdir();

			File f = new File("public/uploads/"
					+ Application.getLocalPortal().getId() + "/editor/" + sid
					+ "/", "game.xml");

			String xmlFilePath = f.getAbsolutePath();

			Files.write(Paths.get(xmlFilePath), xmlString.getBytes());

			zip = xmlFilePath;
			this.update();

			System.out.println("File saved!");

		} catch (IOException ioe) {

			ioe.printStackTrace();
		}

	}

	public String createXMLForWeb(Mission m) {

		String sid = String.valueOf(id);
		String content = "";
		try {

			File theDir3 = new File("public/uploads/"
					+ Application.getLocalPortal().getId());
			if (!theDir3.exists())
				theDir3.mkdir();

			File theDir2 = new File("public/uploads/"
					+ Application.getLocalPortal().getId() + "/editor");
			if (!theDir2.exists())
				theDir2.mkdir();

			File theDir = new File("public/uploads/"
					+ Application.getLocalPortal().getId() + "/editor/" + sid
					+ "/");
			if (!theDir.exists())
				theDir.mkdir();

			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder;

			docBuilder = docFactory.newDocumentBuilder();

			// game element
			Document doc = docBuilder.newDocument();
			Element game = doc.createElement("game");
			doc.appendChild(game);

			// / GAME ATTRIBUTES
			Attr attr = doc.createAttribute("id");
			attr.setValue(sid);
			game.setAttributeNode(attr);

			Attr attr2 = doc.createAttribute("name");
			attr2.setValue(name);
			game.setAttributeNode(attr2);

			Attr attr3 = doc.createAttribute("xmlformat");
			attr3.setValue("5");
			game.setAttributeNode(attr3);

			for (Attribute aa : attributes) {

				if (!aa.getXMLType().contains(".")) {
					Attr attr4 = doc.createAttribute(aa.getXMLType());
					attr4.setValue(aa.getValue());
					game.setAttributeNode(attr4);
				}

			}

			if (!parts.isEmpty()) {

				// Missions

				List<Part> partmirror = new ArrayList<Part>();
				partmirror.addAll(parts);

				Mission first = m;

				// / FIRST

				for (Element e : first.createXMLForWeb(doc, this)) {

					game.appendChild(e);

				}

				// / OTHERS

				for (Part p : parts) {

					for (Element e : p.createXMLForWeb(doc, this)) {

						if (!p.isScene()) {
							if (!p.getMission().equals(first)) {
								game.appendChild(e);
							}

						} else {

							game.appendChild(e);

						}

					}
				}

			}

			for (Hotspot h : getAllHotspots()) {
				Element spot = h.createXMLForWeb(doc, this);
				game.appendChild(spot);

			}

			for (MenuItem h : this.getMenuItems()) {
				// Element mi = h.createXML(doc, this, zout);
				// game.appendChild(mi);

			}
			try {
				try {
					TransformerFactory tf = TransformerFactory.newInstance();
					Transformer transformer;

					transformer = tf.newTransformer();

					transformer.setOutputProperty(
							OutputKeys.OMIT_XML_DECLARATION, "yes");
					StringWriter writer = new StringWriter();

					transformer.transform(new DOMSource(doc), new StreamResult(
							writer));

					content = writer.getBuffer().toString()
							.replaceAll("\n|\r", "");

				} catch (TransformerConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return content;

	}

	// GETTER

	@JSON(include = false)
	public Set<Attribute> getAttributes() {
		return attributes;
	}

	@JSON(include = true)
	public Long getId() {
		return id;
	}

	@JSON(include = true)
	public String getName() {

		return name;
	}

	@JSON(include = true)
	public Map<String, String> getComputedAttributes() {

		Map<String, String> aMap = new HashMap<String, String>();

		for (AttributeType x : getAllAttributes()) {

			aMap.put(x.getXMLType(), getAttributeValue(x));

		}

		return aMap;

	}

	@JSON(include = false)
	public Set<Attribute> getAllSubAttributes() {

		Set<Attribute> all = new HashSet<Attribute>();

		all.addAll(attributes);

		// / PARTS

		for (Part ap : parts) {

			if (!ap.isScene()) {
				// // MISSION

				Mission am = ap.getMission();
				all.addAll(am.getAttributes());

				// /// CONTENTS

				for (Content ac : am.getContents()) {

					for (AttributeType act : ac.getAllAttributes()) {

						if (ac.getAttribute(act) != null) {
							all.add(ac.getAttribute(act));
						}
					}

					for (Content asc : ac.getSubContent()) {

						for (AttributeType act : asc.getAllAttributes()) {

							if (asc.getAttribute(act) != null) {
								all.add(asc.getAttribute(act));
							}
						}
					}

				}

				// /// TRIGGER RULES

				for (Rule atr : am.getRules()) {

					// //// RULES

					for (Rule ar : atr.getSubRules()) {

						// ///// ACTIONS

						for (Action aa : ar.getActions()) {

							all.addAll(aa.getAllSubAttributes());

						}

					}

				}

			} else {
				// // SCENES
				Scene as = ap.getScene();
				all.addAll(as.getAllSubAttributes());

			}
		}

		// / HOTSPOTS

		for (Hotspot ah : hotspots) {

			all.addAll(ah.getAttributes());

			// /// TRIGGER RULES

			for (Rule atr : ah.getRules()) {

				// //// RULES

				for (Rule ar : atr.getSubRules()) {

					// ///// ACTIONS

					for (Action aa : ar.getActions()) {

						all.addAll(aa.getAttributes());

					}

				}

			}

		}

		return all;

	}

	@JSON(include = true)
	public Set<String> getFilesToLoad() {

		Set<String> files = new HashSet<String>();
		Set<Attribute> all = getAllSubAttributes();

		for (Attribute at : all) {

			if (at.getFiletype().equals("file")) {

				files.add(at.getValue());

			}

		}

		return files;

	}

	@JSON(include = false)
	public List<Hotspot> getHotspots() {
		return hotspots;
	}

	@JSON(include = false)
	public String getZip() {
		return zip;
	}

	@JSON(include = false)
	public List<Part> getParts() {
		return parts;
	}

	@JSON(include = false)
	public Hotspot getHotspot(Long hid) {

		List<Hotspot> x = getAllHotspots();

		for (Hotspot y : x) {

			if (y.getId().equals(hid)) {
				return y;
			}
		}

		return null;

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

	@JSON(include = false)
	public Mission getNextMission(Mission m) {

		Mission n = null;

		boolean current = false;
		boolean done = false;
		int counter = 0;
		for (Part p : parts) {
			counter++;
			if (p.isScene()) {

				if (current == false) {

					if (p.getScene().containsMission(m)) {

						if (p.getScene().isLastMission(m)) {

							current = true;

						} else {

							n = p.getScene().getNextMission(m);

							current = false;
							done = true;

						}

					}

				} else {

					n = p.getScene().getFirstMission();
					current = false;
					done = true;

				}

			} else {

				if (current == false) {

					if (p.getMission().equals(m)) {
						current = true;
					}

				} else {

					if (!p.getMission().getType().getXMLType()
							.equals("MetaData")) {
						n = p.getMission();
						current = false;
						done = true;
					}
				}

			}

		}

		return n;

	}

	@JSON(include = false)
	public Mission getLastMission() {

		Mission m = null;

		if (!parts.isEmpty()) {
			int i = 1;
			System.out.println("parts.size: "+parts.size());

			while (((m == null) || i <= parts.size()
					|| m.getType().getXMLType().equals("MetaData")) && (parts.size() - i) >= 0) {
				
				
				
				System.out.println("looking for: "+(parts.size() - i));

				if(parts.get(parts.size() - i) != null){
				Part p = parts.get(parts.size() - i);
				if (p.getLastMission() != null) {
					m = p.getLastMission();

				}
			
				} 
				i++;
			}
		}
		return m;

	}

	@JSON(include = false)
	public Mission getFirstMission() {

		Mission ret = null;
		if (parts.isEmpty()) {
			return null;
		} else {
			ArrayList<Part> pp = new ArrayList<Part>();
			pp.addAll(parts);

			while (!pp.isEmpty() && pp.get(0).isScene()) {

				pp.addAll(0, pp.get(0).getScene().getParts());
			}

			ret = pp.get(0).getMission();

		}

		for (AttributeType att : type.getAttributeTypes()) {

			if (att.getXMLType().equals("Editor.StartMission")) {
				System.out.println("found StartMission Attribute for game");
				if (!this.getAttributeValue(att).equals("")) {

					Long testid = Long.valueOf(this.getAttributeValue(att));

					if (Mission.find.where().eq("id", testid).findRowCount() != 1) {

						System.out.println("Start-Mission " + testid
								+ " not found.");
					} else {

						ret = Mission.find.byId(testid);
					}

				}
			}

		}

		return ret;

	}

	@JSON(include = false)
	public Hotspot getFirstHotspot() {

		for (Part p : parts) {

			if (p.isScene()) {

				if (p.getScene().getHotspots() != null) {

					if (!p.getScene().getHotspots().isEmpty()) {

						return p.getScene().getHotspots().get(0);

					}

				}

			}

		}

		if (hotspots == null) {
			return null;
		} else {

			if (hotspots.isEmpty()) {
				return null;
			} else {

				return hotspots.get(0);
			}
		}
	}

	@JSON(include = false)
	public String getNPCTalkText() {

		List<Mission> missions = new ArrayList<Mission>();
		missions.addAll(getAllMissions());

		String x = "";
		List<String> vars = new ArrayList<String>();
		boolean done = false;

		for (Mission current : missions) {

			if (done == false) {

				if (current.getType().getXMLType().equals("NPCTalk")) {

					// CHECK CONTENT

					if (!current.getContents().isEmpty()) {

						for (Content y : current.getContents()) {

							if (y.getContent() != null) {

								String str2 = y.getContent().replaceAll("<",
										"&lt;");
								str2 = str2.replaceAll(">", "&gt;");
								str2 = str2.replaceAll("\"", "&quot;");
								str2 = str2.replaceAll("&", "&amp;");

								str2 = str2.replaceAll("\n", "<br/>");

								x = x + "<br/><br/>" + str2;

							}

						}

						done = true;

					}

				}

			}

		}

		boolean done2 = false;

		for (Mission current : missions) {

			if (done2 == false) {

				if (current.getType().getXMLType().equals("NPCTalk")) {

					if (!current.getRules().isEmpty()) {

						for (Rule r : current.getRules()) {

							if (!r.getSubRules().isEmpty()) {

								for (Rule sr : r.getSubRules()) {

									if (!sr.getActions().isEmpty()) {

										for (Action ac : sr.getActions()) {

											if (ac.getType().getXMLType()
													.equals("SetVariable")) {

												String var = "";
												String value = "";

												for (Attribute at : ac
														.getAttributes()) {

													if (at.getXMLType().equals(
															"var")) {

														var = at.getValue();

													} else if (at.getXMLType()
															.equals("value")) {

														value = at.getValue();

													}

												}

												if (!var.equals("")
														&& !value.equals("")) {

													value = value.replaceAll(
															"\"", "");

													x = x.replace("@" + var
															+ "@", value);

												}

											}

										}

									}

								}

							}

						}

					}
				}
			}
		}

		return x;
	}

	@JSON(include = false)
	public String getNPCTalkTextForDevices() {

		List<Mission> missions = new ArrayList<Mission>();
		missions.addAll(getAllMissions());

		String x = "";
		List<String> vars = new ArrayList<String>();
		boolean done = false;

		for (Mission current : missions) {

			if (done == false) {

				if (current.getType().getXMLType().equals("NPCTalk")) {

					// CHECK CONTENT

					if (!current.getContents().isEmpty()) {

						for (Content y : current.getContents()) {

							if (y.getContent() != null) {

								String str2 = y.getContent().replaceAll("<",
										"&lt;");
								str2 = str2.replaceAll(">", "&gt;");
								str2 = str2.replaceAll("\"", "&quot;");
								str2 = str2.replaceAll("&", "&amp;");

								x = x + "\n \n" + str2;

							}

						}

						done = true;

					}

				}

			}

		}

		boolean done2 = false;

		for (Mission current : missions) {

			if (done2 == false) {

				if (current.getType().getXMLType().equals("NPCTalk")) {

					if (!current.getRules().isEmpty()) {

						for (Rule r : current.getRules()) {

							if (!r.getSubRules().isEmpty()) {

								for (Rule sr : r.getSubRules()) {

									if (!sr.getActions().isEmpty()) {

										for (Action ac : sr.getActions()) {

											if (ac.getType().getXMLType()
													.equals("SetVariable")) {

												String var = "";
												String value = "";

												for (Attribute at : ac
														.getAttributes()) {

													if (at.getXMLType().equals(
															"var")) {

														var = at.getValue();

													} else if (at.getXMLType()
															.equals("value")) {

														value = at.getValue();

													}

												}

												if (!var.equals("")
														&& !value.equals("")) {

													value = value.replaceAll(
															"\"", "");

													x = x.replace("@" + var
															+ "@", value);

												}

											}

										}

									}

								}

							}

						}

					}
				}
			}
		}

		return x;
	}

	@JSON(include = false)
	public String getNPCTalkSoundURL() {

		List<Mission> missions = new ArrayList<Mission>();
		missions.addAll(getAllMissions());

		String x = "";

		boolean done = false;

		for (Mission current : missions) {

			if (done == false) {

				if (current.getType().getXMLType().equals("NPCTalk")) {

					// CHECK CONTENT

					if (!current.getContents().isEmpty()) {

						for (Content y : current.getContents()) {

							if (y.getContent() != null) {

								for (Attribute aatr : y.getAttributes()) {

									if (aatr.getXMLType().equals("sound")) {

										x = aatr.getValue();

									}

								}

							}

						}

						done = true;

					}

				}

			}

		}

		return x;
	}

	@JSON(include = false)
	public String getNPCTalkPictureURL() {

		String y = "";
		List<Mission> missions = new ArrayList<Mission>();
		missions.addAll(getAllMissions());

		for (Mission current : missions) {

			if (current.getType().getXMLType().equals("NPCTalk")) {

				// CHECK IMAGE

				for (AttributeType atr : current.getAllAttributes()) {

					if (atr.getXMLType().equals("image")) {

						if (current.getAttributeValue(atr) != null) {

							y = current.getAttributeValue(atr);

						}

					}

				}

			}

		}

		return y;
	}

	@JSON(include = false)
	public boolean hasFilledNPCTalk() {

		List<Mission> missions = new ArrayList<Mission>();
		missions.addAll(getAllMissions());

		for (Mission current : missions) {

			if (current.getType().getXMLType().equals("NPCTalk")) {

				// CHECK CONTENT

				if (!current.getContents().isEmpty()) {

					Content x = current.getContents().get(0);

					if (x.getContent() != null) {

						// CHECK IMAGE

						for (AttributeType atr : current.getAllAttributes()) {

							if (atr.getXMLType().equals("image")) {

								if (current.getAttributeValue(atr) != null) {

									return true;

								}

							}

						}

					}

				}

			}

		}

		return false;
	}

	@JSON(include = false)
	public Set<Scene> getAllScenes() {

		Set<Scene> all = new HashSet<Scene>();

		for (Part ap : parts) {

			if (ap.isScene()) {
				all.add(ap.getScene());
				all.addAll(ap.getScene().getAllScenes());

			}

		}

		return all;

	}

	@JSON(include = false)
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

	@JSON(include = true)
	public List<Mission> getMissions() {

		return getAllMissions();
	}

	@JSON(include = false)
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

	/**
	 * 
	 * Getter & Setter
	 * 
	 */

	public void setName(String n) {

		name = n;

	}

	@JSON(include = false)
	public Set<ProviderGames> getPortals() {

		return portals;

	}

	@JSON(include = false)
	public String getIdentifier() {
		return Long.toString(id);
	}

	@JSON(include = true)
	public List<Hotspot> getAllHotspots() {

		List<Hotspot> x = new ArrayList<Hotspot>();
		x.addAll(hotspots);

		for (Part p : parts) {

			if (p.isScene()) {

				x.addAll(p.getScene().getHotspots());

			}

		}

		return x;

	}

	@JSON(include = false)
	public Set<GameRights> getRights() {

		return rights;

	}

	@JSON(include = false)
	public Date getLastUpdate() {
		return lastUpdate;
	}

	@JSON(include = false)
	public String getFile() {

		if (version == 1) {

			return zip;
		} else {

			Long gametofind = getVersion();

			if (Game.find.where().eq("id", gametofind).findRowCount() != 1) {

				return zip;

			} else {

				Game g = Game.find.byId(gametofind);

				if (g.hasFile()) {

					return g.getFile();

				} else {

					if (!g.getVersion().equals(1L)) {

						return g.getFile();

					} else {

						return zip;

					}

				}

			}

		}
	}

	public NewsstreamItem createNewsstreamItem(String title, String content,
			String vis) {

		NewsstreamItem nsi = new NewsstreamItem(title, content, vis, "game",
				getId());

		return nsi;

	}

	public void addNewsstream(NewsstreamItem nsi) {

		Newsstream.add(nsi);

		this.update();

	}

	@JSON(include = false)
	public User getOwner() {

		return owner;
	}

	public void setFile(String file) {

		zip = file;
	}

	@JSON(include = false)
	public boolean hasFile() {

		boolean help = false;
		try {
			if (!zip.equals("none")) {
				help = true;
			}
		} catch (RuntimeException e) {

			System.out.println("Can't look for zip.");
			e.printStackTrace();

		}

		return help;
	}

	/**
	 * 
	 * Finder is a Play Framework Class that lets other classes find a specific
	 * object of this class, in this case, searching for objects with a Long
	 * value is enabled.
	 * 
	 */

	public static final Finder<Long, Game> find = new Finder<Long, Game>(
			Long.class, Game.class);

	public void setPublish(boolean b) {
		publish = b;

	}
	
	
	
	public void setPublishOnAllPortals(boolean b){
		
		
		
		
		for(ProviderGames pg : portals){
			
			pg.setVisibility(b);
			pg.update();
			
		}
		
	
	}

	@JSON(include = true)
	public boolean getPublish() {
		return publish;

	}

	public String getAbstractRelink(ObjectReference or, Scene s) {
		String x = "";

		if (or.getObjectType().equals("Part")) {

			System.out.println("Relinking Abstract Value");
			System.out.println("Searching for: " + or.getObjectId());

			boolean done = false;
			List<Part> allparts = s.getParts();

			int count = 0;
			while (done == false && count < 1000) {
				for (Part p : allparts) {

					if (p.isScene() == false) {

						System.out.println("Found: " + p.getParent());

						if (or.getObjectId().equals(p.getParent().getId())) {

							x = String.valueOf(p.getMission().getId());
							System.out.println("Relinking to:" + x + ";");
							done = true;

						}

					}

				}

				if (done == false) {
					allparts = getAllParts();
				}

				count++;
			}

		}

		return x;
	}

	public ObjectReference getAbstractRelinkObject(ObjectReference or, Scene s) {
		ObjectReference x = null;

		if (or.getObjectType().equals("Part")) {

			System.out.println("Relinking Abstract Value");
			System.out.println("Searching for: " + or.getObjectId());

			boolean done = false;
			List<Part> allparts = s.getParts();

			int count = 0;
			while (done == false && count < 1000) {
				for (Part p : allparts) {

					if (p.isScene() == false) {

						System.out.println("Found: " + p.getParent());

						if (or.getObjectId().equals(p.getParent().getId())) {

							x = new ObjectReference(p);
							System.out.println("Relinking to:" + x + ";");
							done = true;

						}

					}

				}

				if (done == false) {
					allparts = getAllParts();
				}
				count++;
			}

		} else if (or.getObjectType().equals("Content")) {

			List<Content> allcontents = s.getAllSubContents();

			for (Content c : allcontents) {

				if (or.getObjectId().equals(c.getParentId())) {

					x = new ObjectReference(c);

				}

			}

		} else if (or.getObjectType().equals("Attribute")) {

			List<Attribute> allattr = s.getAllSubAttributes();

			for (Attribute at : allattr) {

				if (at != null) {
					if (or.getObjectId().equals(at.getParentId())) {

						x = new ObjectReference(at);

					}
				}

			}

		}

		return x;
	}

	@JSON(include = false)
	private List<Part> getAllParts() {

		List<Part> allparts = new ArrayList<Part>();
		for (Part ap : parts) {

			allparts.add(ap);
			if (ap.isScene()) {

				allparts.addAll(ap.getScene().getParts());

			}

		}

		return allparts;
	}

	public Game migrateTo(GameType gt) {

		Map<Mission, Mission> missionbinder = new HashMap<Mission, Mission>();
		Map<Hotspot, Hotspot> hotspotbinder = new HashMap<Hotspot, Hotspot>();

		Game g = new Game(name, gt);
		g.save();

		// PARTS

		for (Part p : parts) {

			boolean done = false;

			if (p.isScene()) {

				SceneType old = p.getScene().getType();
				System.out.println("SceneType search: " + old.getName());

				for (SceneType npt : gt.getPossibleSceneTypes()) {

					if (npt.getName().equals(old.getName())) {

						done = true;
						Scene nss = p.getScene().migrateTo(npt, gt, missionbinder,
								hotspotbinder);
						nss.save();
						Part ns = new Part(nss);
						ns.save();

						g.addPart(ns);
						g.update();

					}

				}

				if (done == false) {

					System.out
							.println("Didn't find SceneType " + old.getName());
				}

			} else {

				MissionType oldMissionType = p.getMission().getType();

				PartType targetPartType = null;

				String xmlTypeOld = oldMissionType.getXMLType();
				if (xmlTypeOld.equals("NPCTalk")
						&& oldMissionType.getName().equals("Text mit Bild")) {
					xmlTypeOld = "ImageWithText";
				}
				if (xmlTypeOld.equals("MultipleChoiceQuestion")
						&& oldMissionType.getName().equals("Auswahlmenü")) {
					xmlTypeOld = "Menu";
				}

				for (PartType npt : gt.getPossiblePartTypes()) {

					if (!npt.isSceneType()) {

						// TODO special solution for migration e.g. from
						// textmitbild to whatever or auswahlmenu

						if (npt.getMissionType().getXMLType()
								.equals(xmlTypeOld)) {

							targetPartType = npt;

							break;
						}

					}

				}

				if (targetPartType != null) {
					Part np = p.migrateTo(targetPartType.getMissionType(),
							missionbinder);

					g.addPart(np);
					g.update();

				}

				else {

					System.out.println("Didn't find MissionType "
							+ oldMissionType.getName());
				}

			}

			g.update();

		}
		System.out.println("Done with Part Migration");

		// HOTSPOTS

		for (Hotspot hs : hotspots) {

			boolean done = false;

			HotspotType old = hs.getType();

			for (HotspotType hst : gt.getPossibleHotspotTypes()) {

				if (hst.getName().equals(old.getName())) {

					done = true;

					g.addHotspot(hs.migrateTo(hst, hotspotbinder));
					g.update();

				}

			}

			if (done == false) {

				System.out.println("Didn't find HotspotType " + old.getName());
			}

			g.update();

		}

		System.out.println("Done with Hotspot Migration");

		// ATTRIBUTES

		for (Attribute at : attributes) {

			boolean done = false;
			AttributeType attt = at.getType();

			for (AttributeType atrt : gt.getAttributeTypes()) {

				if (atrt.getName().equals(attt.getName())) {

					g.setAttribute(at.migrateTo(atrt));
					g.update();

				}

			}

			if (done == false) {

				System.out.println("Didn't find AttributeType " + at.getName());
			}

			g.update();

		}
		System.out.println("Done with Attribute Migration");

		System.out.println("Starting Attribute Relinking");

		for (Attribute atr : g.getAllSubAttributes()) {

			if (atr.getType().getFileType().equals("mission")) {

				for (Mission x : getAllMissions()) {

					if (atr.getValue() != null) {
						if (x.getCopiedFrom() != null) {
							if (atr.getValue()
									.equals(String.valueOf(x.getId()))) {

								atr.setValue(x.getName());
								atr.update();

							}
						}

					}
				}

			} else if (atr.getType().getFileType().equals("hotspot")) {

				for (Hotspot x : getAllHotspots()) {

					if (atr.getValue() != null) {
						if (x.getParent() != null) {
							if (atr.getValue().equals(
									String.valueOf(x.getParent().getId()))) {

								atr.setValue(String.valueOf(x.getId()));
								atr.update();

							}

						}
					}

				}

			}

		}

		System.out.println("Created new Game: " + g.getId());

		// / RELINK

		g.relinkMissionAndHotspots(missionbinder, hotspotbinder);
		g.update();

		return g;
	}

	@JSON(include = false)
	public Set<GameRights> getUsers() {

		return rights;
	}

	public void deletePortal(ProviderGames pg) {

		portals.remove(pg);

	}

	public void setVersion(Long id2) {
		if (id2 < Integer.MIN_VALUE || id2 > Integer.MAX_VALUE) {

			System.out.println("CANNOT SAVE NEW VERSION ID");

		} else {
			version = new BigDecimal(id2).intValueExact();
		}

	}

	@JSON(include = false)
	public Long getVersion() {

		Long l = new Long(version);
		return l;
	}

	public void removeHotspot(Hotspot p) {

		if (hotspots.contains(p)) {

			hotspots.remove(p);

		}

	}

	@JSON(include = true)
	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void addMenuItem(MenuItem z) {
		menuItems.add(z);

	}

	public void removeMenuItem(MenuItem p) {

		List<MenuItem> all = new ArrayList<MenuItem>();
		all.addAll(menuItems);

		for (MenuItem am : all) {

			if (am.getId().equals(p.getId())) {

				menuItems.remove(am);

			}

		}

	}

}
