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
		} else {
			System.out.println("Action is null?");
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
		int old_position = actions.indexOf(a);

		if (old_position < 1) return;

		Collections.swap(actions, old_position - 1, old_position);

		update();

//		if (old_position > 0) {
//
//			List<Action> helplist = new ArrayList<Action>();
//			for (Action a_a : actions) {
//
//				helplist.add(a_a);
//
//			}
//
//			actions.clear();
//			update();
//
//			List<Action> new_actions = new ArrayList<Action>();
//
//			for (int i = 0; i < helplist.size(); i++) {
//				if (i == old_position - 1) {
//					new_actions.add(a.copyMe(a.getName(), ));
//					new_actions.add(helplist.get(i).copyMe(helplist.get(i).getName()));
//					helplist.get(i).removeMe();
//					i++;
//				} else {
//					new_actions.add(helplist.get(i).copyMe(helplist.get(i).getName()));
//
//				}
//
//				helplist.get(i).removeMe();
//
//			}
//
//			actions = new_actions;
//			update();
//		}

	}

	public void action_right(Action a) {
		int old_position = actions.indexOf(a);

		if (old_position == -1 || old_position >= actions.size() - 1)
			return;

		Collections.swap(actions, old_position, old_position + 1 );

		update();

//		if (old_position > -1 && old_position < actions.size() - 1) {
//
//			List<Action> helplist = new ArrayList<Action>();
//			for (Action a_a : actions) {
//
//				helplist.add(a_a);
//
//			}
//
//			actions.clear();
//			update();
//
//			List<Action> new_actions = new ArrayList<Action>();
//
//			for (int i = 0; i < helplist.size(); i++) {
//				if (i == old_position) {
//					i++;
//					new_actions.add(helplist.get(i).copyMe(helplist.get(i).getName()));
//					helplist.get(i - 1).removeMe();
//					new_actions.add(a.copyMe(a.getName()));
//				} else {
//					new_actions.add(helplist.get(i).copyMe(helplist.get(i).getName()));
//
//				}
//
//				helplist.get(i).removeMe();
//
//			}
//			actions = new_actions;
//			update();
//		}
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

			System.out.println("Can't delete Rule.");
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
				System.out.println("...not empty");
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

				System.out.println("Conditions");

				// CONDITIONS

				// SUBRULES

				rule = doc.createElement("rule");

				for (Condition ac : conditions) {
					if (ac.isFull() && !ac.getFull().equals("") && !ac.getFull().equals(" ")) {

						rule.appendChild(ac.getXML(doc, g, false));

					}
				}

				// ACTIONS

				System.out.println("Actions");

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
			System.out.println("Trigger Rule");

			// SUBRULES
			if (subrules != null) {
				System.out.println("...not empty");
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

				System.out.println("Conditions");

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

				System.out.println("Actions");

				for (Action a : actions) {

					Element act = a.createXML(doc, g.getLastMission(), g, zout);
					if (act != null) {
						rule.appendChild(act);
					}

				}

			}

		} else {

			rule = doc.createElement(getTrigger());
			System.out.println("Trigger Rule");

			// SUBRULES
			if (subrules != null) {
				System.out.println("...not empty");
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

				System.out.println("Actions");

				for (Action a : actions) {

					Element act = a.createXMLForWeb(doc, g.getLastMission(), g);
					if (act != null) {
						rule.appendChild(act);
					}

				}

			}

		} else {

			rule = doc.createElement(getTrigger());
			System.out.println("Trigger Rule");

			// SUBRULES
			if (subrules != null) {
				System.out.println("...not empty");
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
		System.out.println("Rule.migrateTo: " + nrt.getName());
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

			if (done == false) {

				System.out.println("Didn't find ActionType " + at.getXMLType());
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

			System.out.println("Conditions");

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

			System.out.println("Actions");

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
