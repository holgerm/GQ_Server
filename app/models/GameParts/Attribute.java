package models.GameParts;

import models.help.GameCopyContext;
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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import flexjson.JSON;
import models.Game;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Attribute extends Model {

    @Id
    private Long id;

    @ManyToOne
    private AttributeType type;

    @Lob
    private String value;

    @OneToOne
    private ObjectReference abstractValue;

    @ManyToOne
    private ActionSet subactions;

    @OneToOne
    private ObjectReference linkto;

    @OneToOne
    private Attribute parent;

    public Attribute(AttributeType t) {
        type = t;
    }

    public String toString() {
        return getType().getXMLType() + ":" + value;
    }

    // SETTER

    public void setParent(Attribute x) {
        parent = x;
    }

    public void setValue(String v) {
        value = v;
    }

    public void setAbstractValue(ObjectReference x) {
        abstractValue = x;
    }

    // GETTER
    @JSON(include = false)
    public Attribute getParent() {
        return parent;
    }

    @JSON(include = false)
    public AttributeType getType() {
        return type;
    }

    @JSON(include = false)
    public Long getId() {
        return id;
    }

    @JSON(include = false)
    public boolean hasAbstractValue() {
        return abstractValue != null;
    }

    @JSON(include = false)
    public ObjectReference getAbstractValue() {
        return abstractValue;
    }

    @JSON(include = false)
    public String getXMLType() {

        return type.getXMLType();

    }

    @JSON(include = true)
    public String getTypeDescription() {

        return type.getXMLType();

    }

    @JSON(include = true)
    public String getFiletype() {

        return type.getFileType();

    }

    @JSON(include = true)
    public String getValue() {

        if (abstractValue == null) {

            return value;

        } else {

            return String.valueOf(abstractValue.getObjectId());
        }

    }

    @JSON(include = false)
    public List<String> getPossibleValues() {

        return type.getPossibleValues();

    }

    @JSON(include = false)
    public boolean hasPossibleValues() {

        if (type.getPossibleValues().isEmpty()) {

            return false;

        } else {

            return true;
        }

    }

    @JSON(include = true)
    public List<Action> getActions() {
        if (subactions != null) {
            return subactions.getRules();
        } else {
            return null;
        }
    }

    @JSON(include = false)
    public void addAction(Action a) {

        if (subactions == null) {

            subactions = new ActionSet();
            subactions.save();

        }
        subactions.addAction(a);
        subactions.update();

    }

    public void removeAction(Action a) {
        subactions.remove(a);
        subactions.update();
    }

    @JSON(include = false)
    public String getValue(Game g) {

        if (abstractValue == null) {

            return value;

        } else {

            String who = abstractValue.getObjectType();
            String special = abstractValue.getSpecial();

            if (who.equals("Game")) {
                // GAME

                Game ga = abstractValue.getGame();

                if (special.equals("First Mission")) {

                    return String.valueOf(ga.getFirstMission().getId());

                } else if (special.equals("Last Mission")) {

                    return String.valueOf(ga.getLastMission().getId());

                } else if (special.equals("First Hotspot")) {

                    return String.valueOf(ga.getFirstHotspot().getId());

                }

            } else if (who.equals("Part")) {
                // PART

            }

        }

        return null;
    }

    @JSON(include = true)
    public String getName() {
        return type.getName();
    }

    public void setLink(ObjectReference o) {
        linkto = o;
    }

    @JSON(include = false)
    public ObjectReference getLink() {
        return linkto;
    }

    @JSON(include = false)
    public boolean hasLink() {
        if (linkto != null) {
            return true;
        } else {
            return false;
        }
    }

    public static final Finder<Long, Attribute> find = new Finder<Long, Attribute>(Long.class, Attribute.class);

    public Attribute copyMe(GameCopyContext copyContext) {
        Attribute atr = new Attribute(type);
        atr.setValue(value);
        atr.setAbstractValue(abstractValue);
        atr.setParent(this);
        atr.save();
        if (linkto != null) {
            ObjectReference objRefCopy = linkto.copyMe(copyContext);
            if (objRefCopy != null)
                atr.setLink(objRefCopy);
        }
        atr.update();

        if (subactions != null) {
            for (Action aa : subactions.getRules()) {
                Action na = aa.copyMe("", copyContext);
                na.save();
                atr.addAction(na);
                atr.update();
            }
        }

        copyContext.attributeMap.put(this, atr);
        return atr;
    }

    public Attribute migrateTo(AttributeType atrt, GameCopyContext copyContext) {
        Attribute a = new Attribute(atrt);
        a.save();

        a.setAbstractValue(abstractValue);
        a.setParent(parent);
        a.setValue(value);
        if (linkto != null) {
            ObjectReference objRefCopy = linkto.copyMe(copyContext);
            if (objRefCopy != null)
                a.setLink(objRefCopy);
        }
        a.update();

        copyContext.attributeMap.put(this, a);
        return a;
    }

    public Attribute migrateTo(AttributeType atrt, RuleType rt, GameCopyContext copyContext) {
        Attribute a = new Attribute(type);
        a.save();

        if (subactions != null) {
            for (Action aa : subactions.getRules()) {
                ActionType at = aa.getType();

                for (ActionType nat : rt.getPossibleActionTypes()) {
                    if (nat.getXMLType().equals(at.getXMLType())) {
                        a.addAction(aa.migrateTo(nat, rt, copyContext));
                        a.update();
                    }
                }
            }
        }

        a.setAbstractValue(abstractValue);
        a.setParent(parent);
        a.setValue(value);
        if (linkto != null) {
            ObjectReference objRefCopy = linkto.copyMe(copyContext);
            if (objRefCopy != null)
                a.setLink(objRefCopy);
        }
        a.update();

        copyContext.attributeMap.put(this, a);
        return a;
    }

    @JSON(include = false)
    public Long getParentId() {
        if (parent != null) {
            return parent.getId();
        } else {

            return 0L;
        }
    }

    @JSON(include = false)
    public Node getXML(Document doc) {
        Element valueel = doc.createElement("value");

        if (value.matches("(\\s*)")) {
            // whitespace contents -> empty string:
            Element y = doc.createElement("string");
            y.setTextContent("");
            valueel.appendChild(y);

        } else if (value.matches("\"(.*)\"")) {
            // text -> string
            String x = value.substring(1, value.length() - 1);
            Element y = doc.createElement("string");
            y.setTextContent(x);
            valueel.appendChild(y);

        } else if (value.matches("\\[(.*)\\]")) {
            // text -> string
            Element y = doc.createElement("list");
            y.setTextContent(value);
            valueel.appendChild(y);

        } else if (value.matches(Global.REGEXP_NUM)) {
            // Number

            Element y = doc.createElement("num");
            y.setTextContent(value.trim());
            valueel.appendChild(y);

        } else if (value.matches("(\\s*)(true|false|True|False|TRUE|FALSE)(\\s*)")) {
            // Bool

            Element y = doc.createElement("bool");
            y.setTextContent(value);
            valueel.appendChild(y);

        } else {
            // Variable

            Element y = doc.createElement("var");
            y.setTextContent(value.trim());
            // TODO
            valueel.appendChild(y);
        }

        return valueel;
    }

    public String getComputedValue() {
        String x = value;

        if (type.getFileType().equals("condition")) {
            Condition help = new Condition(true, x);
            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder;

                docBuilder = docFactory.newDocumentBuilder();

                // game element
                Document doc = docBuilder.newDocument();

                Element y = help.getXMLforJSON(doc, true);

                Transformer transformer;
                try {
                    transformer = TransformerFactory.newInstance().newTransformer();

                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                    StreamResult result = new StreamResult(new StringWriter());
                    DOMSource source = new DOMSource(y);
                    try {
                        transformer.transform(source, result);
                    } catch (TransformerException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    x = result.getWriter().toString();
                } catch (TransformerConfigurationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (TransformerFactoryConfigurationError e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } catch (ParserConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return x;
    }

}
