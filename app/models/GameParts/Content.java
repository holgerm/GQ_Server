package models.GameParts;

import play.db.ebean.Model;
import util.Global;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Entity
public class Content extends Model {

	@Id
	private Long id;
	private String name;

	@ManyToOne
	private ContentType type;

	@Lob
	private String content;

	@OneToOne
	private Content parent;

	@ManyToOne
	private ContentSet subcontent;

	@ManyToMany
	private List<Rule> rules;

	@ManyToMany
	private List<Attribute> attributes;

	public Content(String n, ContentType t) {

		name = n;
		type = t;
		subcontent = new ContentSet();
		subcontent.save();
	}

	/// SETTER

	public void setParent(Content x) {
		parent = x;
	}

	public void setContent(String x) {
		content = x;
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

			System.out.println("Problem setting Attribute.");
			e.printStackTrace();

		}

	}

	public void addSubContent(Content c) {
		subcontent.addContent(c);
		subcontent.update();
	}

	public void removeSubContent(Content c) {

		if (subcontent.contains(c)) {
			subcontent.remove(c);
			subcontent.update();
		}

	}

	// GETTER

	@JSON(include = false)
	public List<Content> getSubContent() {
		return subcontent.getContents();
	}

	@JSON(include = false)
	public List<AttributeType> getAllAttributes() {
		return type.getAttributeTypes();

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

	@JSON(include = false)
	public ContentType getType() {
		return type;
	}

	@JSON(include = true)
	public boolean hasSubContent() {
		if (subcontent.getContents().isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

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
	public String getContent() {
		return content;
	}

	@JSON(include = false)
	public List<Content> getSubContents() {
		return subcontent.getContents();
	}

	@JSON(include = true)
	public Long getId() {
		return id;
	}

	public static final Finder<Long, Content> find = new Finder<Long, Content>(Long.class, Content.class);

	public Content copyMe(String n) {

		Content c = new Content(name + " " + n, type);

		// CONTENT

		c.setContent(content);
		c.setParent(this);
		c.save();

		// ATTRIBUTES

		for (Attribute aatr : attributes) {

			c.setAttribute(aatr.copyMe());
			c.update();
		}

		// SUBCONTENT

		for (Content sc : subcontent.getContents()) {
			c.addSubContent(sc.copyMe(""));
		}
		c.save();

		return c;
	}

	public void setName(String n) {
		name = n;
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

			System.out.println("Can't delete Mission.");
			e.printStackTrace();

		}

	}

	public Element createXMLForWeb(Document doc, Game g) {
		Element content = null;

		content = doc.createElement(type.getXMLType());

		content.setTextContent(getContent());

		for (Content c : subcontent.getContents()) {
			Element sub = c.createXMLForWeb(doc, g);

			content.appendChild(sub);

		}

		Attr attr = doc.createAttribute("id");
		attr.setValue(String.valueOf(id));
		content.setAttributeNode(attr);

		for (AttributeType aa : getAllAttributes()) {

			if (getAttributeValue(aa) != null) {
				if ((!getAttributeValue(aa).equals("")) && (!getAttributeValue(aa).equals(" "))) {

					Attr attr4 = doc.createAttribute(aa.getXMLType());

					if (aa.getFileType().equals("boolean") && aa.getXMLType().equals("correct")) {

						if (getAttributeValue(aa).equals("true")) {
							attr4.setValue("1");
						} else {
							attr4.setValue("0");
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
					content.setAttributeNode(attr4);
				}
			}

		}

		return content;
	}

	public Element createXML(Document doc, ZipOutputStream zout, Game g) {
		Element content = null;

		content = doc.createElement(type.getXMLType());

		content.setTextContent(getContent());

		for (Content c : subcontent.getContents()) {
			Element sub = c.createXML(doc, zout, g);

			content.appendChild(sub);

		}

		Attr attr = doc.createAttribute("id");
		attr.setValue(String.valueOf(id));
		content.setAttributeNode(attr);

		for (AttributeType aa : getAllAttributes()) {

			if (getAttributeValue(aa) != null) {
				if ((!getAttributeValue(aa).equals("")) && (!getAttributeValue(aa).equals(" "))) {

					Attr attr4 = doc.createAttribute(aa.getXMLType());

					if (aa.getFileType().equals("boolean") && aa.getXMLType().equals("correct")) {

						if (getAttributeValue(aa).equals("true")) {
							attr4.setValue("1");
						} else {
							attr4.setValue("0");
						}

					} else if (aa.getFileType().equals("file")) {

						System.out.println("is File!");
						URL url;
						try {
							url = new URL(getAttributeValue(aa));

							String path = getAttributeValue(aa);
							String[] splitResult = path.split("/");

							path = splitResult[splitResult.length - 1];

							File file = new File("public/uploads/" + Application.getLocalPortal().getId() + "/editor/"
									+ g.getId() + "/files", path);
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

						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
					content.setAttributeNode(attr4);
				}
			}

		}

		return content;

	}

	public Content migrateTo(ContentType nct) {
		Content c = new Content(name, nct);

		// CONTENT

		c.setContent(content);
		c.setParent(parent);
		c.save();

		// ATTRIBUTES
		for (Attribute at : attributes) {

			boolean done = false;
			AttributeType attt = at.getType();

			for (AttributeType atrt : nct.getAttributeTypes()) {

				if (atrt.getXMLType().equals(attt.getXMLType())) {

					c.setAttribute(at.migrateTo(atrt));
					c.update();

				}

			}

			if (done == false) {

				System.out.println("Didn't find AttributeType " + at.getName());
			}

			c.update();

		}

		// SUBCONTENT

		for (Content sc : subcontent.getContents()) {

			boolean done = false;

			ContentType sct = sc.getType();

			for (ContentType nsct : nct.getPossibleContentTypes()) {

				if (sct.getXMLType().equals(nsct.getXMLType())) {

					c.addSubContent(sc.migrateTo(nsct));
					c.update();
					done = true;

				}

			}

		}
		c.update();

		return c;
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

	@JSON(include = false)
	public Content getParent() {
		return parent;
	}

	@JSON(include = true)
	public String getTypeDescription() {
		return type.getXMLType();
	}

	@JSON(include = false)
	public Long getParentId() {
		if (parent != null) {
			return parent.getId();
		} else {

			return 0L;
		}
	}

}
