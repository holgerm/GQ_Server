package models.GameParts;

import models.help.GameCopyContext;
import play.db.ebean.Model;
import util.Global;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import models.Game;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import flexjson.JSON;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipOutputStream;

@Entity
public class Rule extends Model {

    @Id
    private Long id;

    @ManyToMany
    private List<Condition> conditions;

    @ManyToOne
    private RuleSet subrules;

    @ManyToOne
    private Action firstaction;

    @ManyToMany
    private List<Action> actions;

    @OneToOne
    private Rule parent;

    public Rule(Condition c, Rule r) {

        subrules = new RuleSet(r);
        subrules.save();
        conditions = new ArrayList<Condition>();
        conditions.add(c);

    }

    public Rule(Action a) {

        if (a != null) {
            actions = new ArrayList<Action>();
            actions.add(a);
        }
    }

    public Rule() {

    }

    // SETTER

    public void setParent(Rule x) {
        parent = x;
    }

    public boolean addCondition(Condition c) {
        conditions = new ArrayList<Condition>();
        conditions.add(c);
        return true;
    }

    public boolean addSubRule(Rule r) {
        boolean help = false;
        if (actions.isEmpty()) {

            if (subrules != null) {

                subrules.addRule(r);
                subrules.update();

            } else {

                subrules = new RuleSet(r);
                subrules.save();
                this.update();

            }

            help = true;
        }
        return help;
    }

    public void addAction(Action a) {
        actions.add(a);
        update();
    }

    public void action_left(Action a) {
        int leftPos = actions.indexOf(a);
        moveAction(leftPos, leftPos - 1);
    }

    public void action_right(Action a) {
        int rightPos = actions.indexOf(a);
        moveAction(rightPos, rightPos + 1);
    }

    private void moveAction(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex < 0 ||
                fromIndex >= actions.size() || toIndex >= actions.size() ||
                fromIndex == toIndex
        )
            return;

        if (fromIndex > toIndex) {
            int tmp = fromIndex;
            fromIndex = toIndex;
            toIndex = tmp;
        }

        // Do swap actions at oldPos and oldPos+1,
        // but we need to do it manually and update in between since we must keep ids unique:
        Action actionMoved = actions.get(fromIndex);
        actions.remove(actionMoved);
        update();
        actions.add(toIndex, actionMoved);
        update();
    }

    // GETTER

    @JSON(include = false)
    public String getFirstAction() {

        if (actions == null) {

            return "Nicht initialisiert->Actions is null";
        } else {

            if (actions.isEmpty()) {

                if (!(subrules == null)) {
                    if (!(subrules.isEmpty())) {

                        if (subrules.get(0).getActions().isEmpty()) {

                            return "Keine";

                        } else {

                            return subrules.get(0).getActions().get(0).getName();
                        }
                    } else {
                        return "Nicht initialisiert->Subrules is empty";
                    }
                } else {
                    return "Nicht initialisiert-> Subrules is null";
                }

            } else {

                return actions.get(0).getName();

            }
        }

    }

    @JSON(include = false)
    public String getTrigger() {
        if (actions.isEmpty()) {
            return conditions.get(0).getTrigger();
        } else {
            return "Aktion";
        }
    }

    @JSON(include = false)
    public boolean isSimpleRule() {
        boolean help = true;
        if (actions.isEmpty()) {
            help = false;
        }
        return help;
    }

    @JSON(include = false)
    public List<Condition> getConditions() {
        return conditions;
    }

    @JSON(include = false)
    public List<Rule> getSubRules() {

        if (subrules != null) {
            return subrules.getRules();
        } else {

            List<Rule> x = new ArrayList<Rule>();
            return x;

        }
    }

    @JSON(include = true)
    public List<Action> getActions() {
        return actions;
    }

    @JSON(include = true)
    public Long getId() {
        return id;
    }

    public static final Finder<Long, Rule> find = new Finder<Long, Rule>(Long.class, Rule.class);

    @JSON(include = false)
    public Rule copyMe(GameCopyContext copyContext) {

        Rule r = new Rule();
        r.save();

        int counter = 1;
        for (Action aa : actions) {

            r.addAction(aa.copyMe("" + counter, copyContext));
            counter++;

        }

        r.update();

        for (Condition ac : conditions) {

            r.addCondition(ac.copyMe(copyContext));
            r.update();
        }

        // SUBRULES
        if (subrules != null) {
            for (Rule ar : subrules.getRules()) {

                if (ar != null) {
                    r.addSubRule(ar.copyMe(copyContext));

                    r.update();
                }
            }
        }

        r.setParent(this);
        r.update();

        return r;
    }

    @JSON(include = false)
    public boolean hasSubRules() {

        if (subrules == null) {
            return false;
        } else {
            return true;
        }

    }

    @JSON(include = false)
    public void removeMe() {

        try {

            if (isSimpleRule()) {
                // ACTIONS

                Set<Action> acts = new HashSet<Action>();
                acts.addAll(actions);

                for (Action aa : acts) {
                    actions.remove(aa);
                    this.update();
                    aa.delete();
                }
            } else {

                // CONDITIONS

                Set<Condition> cnds = new HashSet<Condition>();
                cnds.addAll(conditions);

                for (Condition ac : cnds) {
                    conditions.remove(ac);
                    this.update();
                    ac.delete();
                }

                // SUBRULES

                Set<Rule> rls = new HashSet<Rule>();
                rls.addAll(subrules.getRules());

                for (Rule ar : rls) {
                    subrules.remove(ar);
                    subrules.update();
                    this.update();
                    ar.removeMe();
                    ar.delete();
                }

                RuleSet x = subrules;
                subrules = null;
                this.update();

                x.delete();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public Element createXMLForWeb(Document doc, int i, Mission m, Game g) {

        Element rule = null;

        if (i != 0) {

            if (!actions.isEmpty()) {

                // SUBRULES

                rule = doc.createElement("rule");

                for (Condition ac : conditions) {
                    if (ac.isFull() && !ac.getFull().equals("") && !ac.getFull().equals(" ")) {

                        rule.appendChild(ac.getXML(doc, g, false));

                    }
                }

                // ACTIONS
                for (Action a : actions) {
                    Element act = a.createXMLForWeb(doc, m, g);
                    if (act != null) {
                        rule.appendChild(act);
                    }
                }
            }
        } else {
            String triggername = getTrigger();

            if (triggername.equals("onFailure")) {
                triggername = "onFail";
            }

            rule = doc.createElement(triggername);

            // SUBRULES
            if (subrules != null) {
                for (Rule ar : subrules.getRules()) {

                    Element rule2 = ar.createXMLForWeb(doc, i + 1, m, g);
                    if (rule2 != null) {
                        rule.appendChild(rule2);
                    }
                }
            }
        }

        return rule;
    }

    public Element createXML(Document doc, int i, Mission m, Game g, ZipOutputStream zout) {

        Element rule = null;

        if (i != 0) {

            if (!actions.isEmpty()) {
                // CONDITIONS

                // SUBRULES

                rule = doc.createElement("rule");

                for (Condition ac : conditions) {
                    if (ac.isFull() && !ac.getFull().equals("") && !ac.getFull().equals(" ")) {

                        rule.appendChild(ac.getXML(doc, g, false));

                    }
                }

                // ACTIONS

                for (Action a : actions) {

                    Element act = a.createXML(doc, m, g, zout);
                    if (act != null) {
                        rule.appendChild(act);
                    }

                }

            }

        } else {

            String triggername = getTrigger();

            if (triggername.equals("onFailure")) {
                triggername = "onFail";
            }

            rule = doc.createElement(triggername);

            // SUBRULES
            if (subrules != null) {
                for (Rule ar : subrules.getRules()) {

                    Element rule2 = ar.createXML(doc, i + 1, m, g, zout);
                    if (rule2 != null) {
                        rule.appendChild(rule2);
                    }

                }
            }

        }

        return rule;

    }

    public Element createXML(Document doc, int i, Hotspot h, Game g, ZipOutputStream zout) {

        Element rule = null;

        if (i != 0) {

            if (!actions.isEmpty()) {
                // CONDITIONS

                // SUBRULES

                rule = doc.createElement("rule");

                for (Condition ac : conditions) {
                    if (ac.isFull() && !ac.getFull().equals("") && !ac.getFull().equals(" ")) {

                        Element the_if = doc.createElement("if");

                        InputStream inputStream = new ByteArrayInputStream(ac.getFull().getBytes());
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

                        the_if.appendChild(doc.adoptNode(parse.getFirstChild()));
                        rule.appendChild(the_if);
                        try {
                            parse = newInstance.newDocumentBuilder().parse(inputStream);
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
                }

                // ACTIONS

                for (Action a : actions) {

                    Element act = a.createXML(doc, g.getLastMission(), g, zout);
                    if (act != null) {
                        rule.appendChild(act);
                    }

                }

            }
        } else {
            rule = doc.createElement(getTrigger());

            // SUBRULES
            if (subrules != null) {
                for (Rule ar : subrules.getRules()) {

                    Element rule2 = ar.createXML(doc, i + 1, g.getLastMission(), g, zout);
                    if (rule2 != null) {
                        rule.appendChild(rule2);
                    }
                }
            }
        }

        return rule;

    }

    public Element createXMLForWeb(Document doc, int i, Hotspot h, Game g) {

        Element rule = null;

        if (i != 0) {

            if (!actions.isEmpty()) {
                // SUBRULES

                rule = doc.createElement("rule");

                for (Condition ac : conditions) {
                    if (ac.isFull() && !ac.getFull().equals("") && !ac.getFull().equals(" ")) {

                        Element the_if = doc.createElement("if");

                        InputStream inputStream = new ByteArrayInputStream(ac.getFull().getBytes());
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

                        the_if.appendChild(doc.adoptNode(parse.getFirstChild()));
                        rule.appendChild(the_if);
                        try {
                            parse = newInstance.newDocumentBuilder().parse(inputStream);
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
                }

                // ACTIONS
                for (Action a : actions) {

                    Element act = a.createXMLForWeb(doc, g.getLastMission(), g);
                    if (act != null) {
                        rule.appendChild(act);
                    }
                }
            }

        } else {
            rule = doc.createElement(getTrigger());

            // SUBRULES
            if (subrules != null) {
                for (Rule ar : subrules.getRules()) {

                    Element rule2 = ar.createXMLForWeb(doc, i + 1, g.getLastMission(), g);
                    if (rule2 != null) {
                        rule.appendChild(rule2);
                    }

                }
            }

        }

        return rule;

    }

    public void deleteAction(Action a) {

        if (isSimpleRule()) {

            if (actions.contains(a)) {

                actions.remove(a);
                this.update();
            }

        }

    }

    public void deleteSubRule(Rule z) {

        subrules.remove(z);
        subrules.update();

    }

    public Rule migrateTo(RuleType nrt, GameCopyContext copyContext) {
        Rule r = new Rule();
        r.save();

        for (Action aa : actions) {

            boolean done = false;

            ActionType at = aa.getType();

            for (ActionType nat : nrt.getPossibleActionTypes()) {

                if (nat.getXMLType().equals(at.getXMLType())) {

                    r.addAction(aa.migrateTo(nat, nrt, copyContext));
                    r.update();
                    done = true;

                }

            }

        }

        r.update();

        for (Condition ac : conditions) {

            r.addCondition(ac.copyMe(copyContext));
            r.update();

        }

        // SUBRULES
        if (subrules != null) {
            for (Rule ar : subrules.getRules()) {

                if (ar != null) {
                    r.addSubRule(ar.migrateTo(nrt, copyContext));

                    r.update();
                }

            }
        }

        r.setParent(parent);
        r.update();

        return r;
    }

    @JSON(include = false)
    public Rule getParent() {
        return parent;
    }

    public Element createXML(Document doc, int i, MenuItem menuItem, Game g, ZipOutputStream zout) {

        Element rule = null;
        Element rule1 = null;

        if (!actions.isEmpty()) {
            // CONDITIONS

            // SUBRULES

            rule1 = doc.createElement("rule");
            rule = doc.createElement("onSelect");

            rule1.appendChild(rule);

            for (Condition ac : conditions) {
                if (ac.isFull() && !ac.getFull().equals("") && !ac.getFull().equals(" ")) {

                    Element the_if = doc.createElement("if");

                    InputStream inputStream = new ByteArrayInputStream(ac.getFull().getBytes());
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

                    the_if.appendChild(doc.adoptNode(parse.getFirstChild()));
                    rule.appendChild(the_if);
                    try {
                        parse = newInstance.newDocumentBuilder().parse(inputStream);
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
            }

            // ACTIONS

            for (Action a : actions) {

                Element act = a.createXML(doc, g.getLastMission(), g, zout);
                if (act != null) {
                    rule.appendChild(act);
                }

            }

        }

        return rule1;

    }

}
