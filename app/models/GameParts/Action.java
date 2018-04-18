package models.GameParts;

import play.db.ebean.Model;
import util.Global;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import controllers.Application;
import flexjson.JSON;
import models.Game;
import models.NewsstreamItem;
import models.TemplateParameter;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Entity
public class Action extends Model {

	@Id
	private Long id;

	private String name;

	@ManyToOne
	private ActionType type;

	@ManyToMany
	private List<Attribute> attributes;

	@ManyToOne
	private ActionSet subactions;

	public Action(String n, ActionType t) {

		name = n;
		type = t;

	}

	@OneToOne
	private Action parent;

	// SETTER

	public void setAttribute(Attribute t) {

		try {
			List<Attribute> copyOfAttributes = new ArrayList<Attribute>(attributes.size());
			;
			for (Attribute item : attributes)
				copyOfAttributes.add(item);

			for (Attribute aatr : copyOfAttributes) {
				if (aatr.getXMLType().equals(t.getXMLType())) {
					attributes.remove(aatr);
					System.out.println("Replacing attribute.");

				}
			}
			attributes.add(t);

		} catch (RuntimeException e) {

			System.out.println("Problem setting Attribute.");
			e.printStackTrace();

		}

	}

	public void addSubAction(Action r) {

		if (subactions != null) {

			subactions.addAction(r);
			subactions.update();

		} else {

			subactions = new ActionSet(r);
			subactions.save();
			this.update();

		}

	}

	public void setParent(Action x) {
		parent = x;
	}

	// GETTER
	@JSON(include = false)
	public List<Attribute> getAttributes() {
		return attributes;
	}

	@JSON(include = true)
	public Long getId() {
		return id;
	}

	public static final Finder<Long, Action> find = new Finder<Long, Action>(Long.class, Action.class);

	@JSON(include = false)
	public Action copyMe(String n) {

		Action a = new Action(name, type);
		a.save();

		// ATTRIBUTES

		for (Attribute aatr : attributes) {

			a.setAttribute(aatr.copyMe());
			a.update();
		}

		a.setParent(this);
		a.update();

		return a;
	}

	@JSON(include = false)
	public String getName() {
		return name;
	}

	@JSON(include = false)
	public List<AttributeType> getAllAttributes() {
		return type.getAttributeTypes();

	}

	@JSON(include = false)
	public List<Attribute> getAllSubAttributes() {
		List<Attribute> re = new ArrayList<Attribute>();

		re.addAll(attributes);

		for (Attribute a : attributes) {

			if (a.getActions() != null) {

				for (Action asa : a.getActions()) {

					re.addAll(asa.getAllSubAttributes());

				}

			}

		}

		return re;

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

	@JSON(include = false)
	public Attribute getAttribute(AttributeType at) {

		Attribute x = null;

		for (Attribute aa : attributes) {

			if (aa.getXMLType().equals(at.getXMLType())) {

				x = aa;

			}

		}

		return x;

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

	public String getAttributeValue(AttributeType at) {

		String x = at.getDefaultValue();

		for (Attribute aa : attributes) {

			if (aa.getXMLType().equals(at.getXMLType())) {

				x = aa.getValue();

			}

		}

		return x;

	}

	public String getComputedAttributeValue(AttributeType at) {

		String x = at.getDefaultValue();

		for (Attribute aa : attributes) {

			if (aa.getXMLType().equals(at.getXMLType())) {

				x = aa.getComputedValue();

			}

		}

		return x;

	}

	public Element createXMLForWeb(Document doc, Mission m, Game g) {
		Element action = null;

		boolean saveme = true;

		action = doc.createElement("action");

		if (type.getXMLType().equals("next")) {

			if (g.getNextMission(m) != null) {
				Attr attr2 = doc.createAttribute("type");
				attr2.setValue("StartMission");
				action.setAttributeNode(attr2);

				Attr attr3 = doc.createAttribute("id");
				attr3.setValue(String.valueOf(g.getNextMission(m).getId()));
				action.setAttributeNode(attr3);
			} else {

				Attr attr2 = doc.createAttribute("type");
				attr2.setValue("EndGame");
				action.setAttributeNode(attr2);

			}

		} else if (type.getXMLType().equals("last")) {

			Attr attr2 = doc.createAttribute("type");
			attr2.setValue("StartMission");
			action.setAttributeNode(attr2);

			Attr attr3 = doc.createAttribute("id");
			attr3.setValue(String.valueOf(g.getLastMission().getId()));
			action.setAttributeNode(attr3);

		} else {

			Attr attr2 = doc.createAttribute("type");
			attr2.setValue(type.getXMLType());
			action.setAttributeNode(attr2);

		}

		for (AttributeType aa : getAllAttributes()) {

			if (getAttributeValue(aa) != null) {

				if (!aa.getFileType().equals("expression") && !aa.getFileType().equals("condition")
						&& !aa.getFileType().equals("actions")) {
					if ((!getAttributeValue(aa).equals("")) && (!getAttributeValue(aa).equals(" "))) {

						Attr attr4 = doc.createAttribute(aa.getXMLType());

						if (aa.getFileType().equals("boolean")) {

							if (getAttributeValue(aa).equals("true")) {
								attr4.setValue("1");
							} else {
								attr4.setValue("0");
							}

						} else if (aa.getRealFileType().equals("seconds")
								|| aa.getRealFileType().equals("stringseconds")) {

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

						action.setAttributeNode(attr4);

					}

				} else if (aa.getFileType().equals("expression")) {

					Node the_if = action;

					if (!getAttributeValue(aa).contains("<value")) {

						if ((!getAttributeValue(aa).equals("")) && (!getAttributeValue(aa).equals(" "))) {

							the_if.appendChild(getAttribute(aa).getXML(doc));

						}
					} else {

						InputStream inputStream = new ByteArrayInputStream(getAttributeValue(aa).getBytes());
						DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
						newInstance.setNamespaceAware(true);

						DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder docBuilder = null;
						try {
							docBuilder = docFactory.newDocumentBuilder();
						} catch (ParserConfigurationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						Document parse = docBuilder.newDocument();
						try {
							parse = newInstance.newDocumentBuilder().parse(inputStream);

							the_if.appendChild(doc.adoptNode(parse.getFirstChild()));
						} catch (SAXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParserConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				} else if (aa.getFileType().equals("condition")) {

					Condition help = new Condition(true, getAttributeValue(aa));

					action.appendChild(help.getXML(doc, g, true));

				}
			} else if (aa.getFileType().equals("actions")) {

				System.out.println("##### ACTION ATTRIBUTE #####");

				Element subactions = doc.createElement(aa.getXMLType());

				action.appendChild(subactions);

				if (getAttribute(aa) != null) {
					System.out.println("##### ATTRIBUTE EXISTS #####");

					if (getAttribute(aa).getActions() != null) {

						System.out.println("##### SUBACTIONS EXISTS #####");

						if (!getAttribute(aa).getActions().isEmpty()) {

							System.out.println("##### SUBACTIONS OF ATTRIBUTE NOT EMPTY #####");

							for (Action a : getAttribute(aa).getActions()) {

								Element act = a.createXMLForWeb(doc, m, g);
								if (act != null) {
									subactions.appendChild(act);
								}

							}

						}

					}

				}

			}

		}

		if (saveme) {

			return action;
		} else {

			return null;

		}

	}

	public Element createXML(Document doc, Mission m, Game g, ZipOutputStream zout) {

		Element action = null;

		boolean saveme = true;

		action = doc.createElement("action");

		if (type.getXMLType().equals("next")) {

			if (g.getNextMission(m) != null) {
				Attr attr2 = doc.createAttribute("type");
				attr2.setValue("StartMission");
				action.setAttributeNode(attr2);

				Attr attr3 = doc.createAttribute("id");
				attr3.setValue(String.valueOf(g.getNextMission(m).getId()));
				action.setAttributeNode(attr3);
			} else {

				Attr attr2 = doc.createAttribute("type");
				attr2.setValue("EndGame");
				action.setAttributeNode(attr2);

			}

		} else if (type.getXMLType().equals("last")) {

			Attr attr2 = doc.createAttribute("type");
			attr2.setValue("StartMission");
			action.setAttributeNode(attr2);

			Attr attr3 = doc.createAttribute("id");
			attr3.setValue(String.valueOf(g.getLastMission().getId()));
			action.setAttributeNode(attr3);

		} else {

			Attr attr2 = doc.createAttribute("type");
			attr2.setValue(type.getXMLType());
			action.setAttributeNode(attr2);

		}

		for (AttributeType aa : getAllAttributes()) {

			if (getAttributeValue(aa) != null) {

				if (!aa.getFileType().equals("expression") && !aa.getFileType().equals("condition")
						&& !aa.getFileType().equals("actions")) {
					if ((!getAttributeValue(aa).equals("")) && (!getAttributeValue(aa).equals(" "))) {

						Attr attr4 = doc.createAttribute(aa.getXMLType());

						if (aa.getFileType().equals("boolean")) {

							if (getAttributeValue(aa).equals("true")) {
								attr4.setValue("1");
							} else {
								attr4.setValue("0");
							}

						} else if (aa.getFileType().equals("file") && (!getAttributeValue(aa).contains("@_"))) {

							System.out.println("is File: " + getAttributeValue(aa));
							URL url;
							try {
								url = new URL(getAttributeValue(aa));

								String path = getAttributeValue(aa);
								String[] splitResult = path.split("/");

								path = splitResult[splitResult.length - 1];

								File file = new File("public/uploads/" + Application.getLocalPortal().getId()
										+ "/editor/" + g.getId() + "/files", path);
								file.deleteOnExit();

								FileUtils.copyURLToFile(url, file);

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

							} catch (MalformedURLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								attr4.setValue(getAttributeValue(aa));

							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
								attr4.setValue(getAttributeValue(aa));

							}

						} else if (aa.getRealFileType().equals("seconds")
								|| aa.getRealFileType().equals("stringseconds")) {

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

						action.setAttributeNode(attr4);

					}

				} else if (aa.getFileType().equals("expression")) {

					Node the_if = action;

					if (!getAttributeValue(aa).contains("<value")) {

						if ((!getAttributeValue(aa).equals("")) && (!getAttributeValue(aa).equals(" "))) {

							the_if.appendChild(getAttribute(aa).getXML(doc));

						}
					} else {

						InputStream inputStream = new ByteArrayInputStream(getAttributeValue(aa).getBytes());
						DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
						newInstance.setNamespaceAware(true);

						DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder docBuilder = null;
						try {
							docBuilder = docFactory.newDocumentBuilder();
						} catch (ParserConfigurationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						Document parse = docBuilder.newDocument();
						try {
							parse = newInstance.newDocumentBuilder().parse(inputStream);

							the_if.appendChild(doc.adoptNode(parse.getFirstChild()));
						} catch (SAXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParserConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				} else if (aa.getFileType().equals("condition")) {

					Condition help = new Condition(true, getAttributeValue(aa));

					action.appendChild(help.getXML(doc, g, true));

				}
			} else if (aa.getFileType().equals("actions")) {

				System.out.println("##### ACTION ATTRIBUTE #####");

				Element subactions = doc.createElement(aa.getXMLType());

				action.appendChild(subactions);

				if (getAttribute(aa) != null) {
					System.out.println("##### ATTRIBUTE EXISTS #####");

					if (getAttribute(aa).getActions() != null) {

						System.out.println("##### SUBACTIONS EXISTS #####");

						if (!getAttribute(aa).getActions().isEmpty()) {

							System.out.println("##### SUBACTIONS OF ATTRIBUTE NOT EMPTY #####");

							for (Action a : getAttribute(aa).getActions()) {

								Element act = a.createXML(doc, m, g, zout);
								if (act != null) {
									subactions.appendChild(act);
								}

							}

						}

					}

				}

			}

		}

		if (saveme) {

			return action;
		} else {

			return null;

		}

	}

	@JSON(include = false)
	public void removeMe() {
		// TODO Auto-generated method stub

	}

	@JSON(include = false)
	public ActionType getType() {
		return type;
	}

	public Action migrateTo(ActionType nat, RuleType rt) {

		Action a = new Action(name, nat);
		a.save();

		// ATTRIBUTES

		for (Attribute at : attributes) {

			boolean done = false;
			AttributeType attt = at.getType();

			for (AttributeType atrt : nat.getAttributeTypes()) {

				if (atrt.getXMLType().equals(attt.getXMLType())) {

					a.setAttribute(at.migrateTo(atrt, rt));

					a.update();
					done = true;

				}
			}

			if (!done) {

				System.out.println("Didn't find AttributeType " + at.getName());
			}

		}

		if (subactions != null) {
			for (Action aa : subactions.getRules()) {

				boolean done1 = false;

				ActionType at1 = aa.getType();

				for (ActionType nat1 : rt.getPossibleActionTypes()) {

					if (nat1.getXMLType().equals(at1.getXMLType())) {

						a.addSubAction(aa.migrateTo(nat1, rt));
						a.update();
						done1 = true;

					}

				}

				if (done1 == false) {

					System.out.println("Didn't find ActionType " + at1.getXMLType());
				}

			}

		}

		a.update();

		a.setParent(parent);

		return a;
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
	public Action getParent() {
		return parent;
	}

	@JSON(include = true)
	public String getTypeDescription() {
		return type.getXMLType();
	}

}
