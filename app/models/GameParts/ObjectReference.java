package models.GameParts;

import models.help.GameCopyContext;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import models.Game;


@Entity
public class ObjectReference extends Model {

    @Id
    private Long id;

    @OneToOne
    private Part part;
    @OneToOne
    private Rule rule;
    @OneToOne
    private Content content;

    @OneToOne
    private Game game;

    @OneToOne
    private Action action;

    @OneToOne
    private Attribute attribute;


    @OneToOne
    private Hotspot hotspot;

    private String stringvalue;
    private String special;
    private int sort;


    private String who;


    public ObjectReference(Part p) {
        part = p;
        who = "Part";
        System.out.println("ObjectReference: " + who + " id: " + p.getId());
    }

    public ObjectReference(Rule r) {
        rule = r;
        who = "Rule";
        System.out.println("ObjectReference: " + who + " id: " + r.getId());
    }

    public ObjectReference(Content c) {
        content = c;
        who = "Content";

        System.out.println("ObjectReference: " + who + ": " + c.getContent());
    }

    public ObjectReference(Game g) {
        game = g;
        who = "Game";
        System.out.println("ObjectReference: " + who + ": " + g.getName());
    }

    public ObjectReference(Action ac) {
        action = ac;
        who = "Action";
        System.out.println("ObjectReference: " + who + ": " + ac.getName());
    }

    public ObjectReference(Attribute at) {
        attribute = at;
        who = "Attribute";
        System.out.println("ObjectReference: " + who + ": " + at.getName() + ", attrib-id: " + at.getId());
    }

    public ObjectReference(Hotspot h) {
        hotspot = h;
        who = "Hotspot";
        System.out.println("ObjectReference: " + who + ": " + h.getName());
    }

    public ObjectReference(String s) {
        stringvalue = s;
        who = "String";
        System.out.println("ObjectReference: " + who + ": " + s);
    }

    public String toString() {
        if (null == who) return "ObjectReference typ null id: " + id;

        switch (who) {
            case "String":
                return "ObjectReference: " + who + ": " + stringvalue;
            case "Hotspot":
                return "ObjectReference: " + who + ": " + hotspot.getName();
            case "Attribute":
                return "ObjectReference: " + who + ": " + attribute.getName() + " id: " + attribute.getId();
            case "Content":
                return "ObjectReference: " + who + ": " + content.getContent();
            case "Part":
                return "ObjectReference: " + who + " id: " + part.getId();
            case "Game":
                return "ObjectReference: " + who + " id: " + game.getName();
            case "Action":
                return "ObjectReference: " + who + ": " + action.getName();
            case "Rule":
                return "ObjectReference: " + who + " id: " + rule.getId();
            default:
                return "ObjectReference: unknown object type: " + who;
        }
    }


    public void setSpecial(String s) {
        special = s;
    }

    public void setSort(int i) {

        sort = i;
    }


    public Long getId() {
        return id;
    }


    public String getObjectType() {

        return who;
    }

    public Part getPart() {
        return part;
    }

    public Rule getRule() {
        return rule;
    }

    public Content getContent() {
        return content;
    }

    public Game getGame() {
        return game;
    }

    public Action getAction() {
        return action;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public String getString() {
        return stringvalue;
    }

    public String getSpecial() {
        // TODO Auto-generated method stub
        return special;
    }

    public Long getObjectId() {
        switch (who) {
            case "Part":
                if (part != null) return part.getId();
                break;
            case "Rule":
                if (rule != null) return rule.getId();
                break;
            case "Content":
                if (content != null) return content.getId();
                break;
            case "Game":
                if (game != null) return game.getId();
                break;
            case "Action":
                if (action != null) return action.getId();
                break;
            case "Attribute":
                if (attribute != null) return attribute.getId();
                break;
            case "Hotspot":
                if (hotspot != null) return hotspot.getId();
                break;
            default:
                System.err.println("WARNING: unknown ObjectReferenceType " + who + " with id: " + id);
                return null;
        }
        System.err.println("WARNING: ObjectReferenceType " + who + " with id: " + id + " has object specific id of null");
        return null;
    }


    public void setObjectValue(String x) {

        switch (who) {
            case "Content":
                System.out.println("Setting val '" + x + "' to content id " + content.getId());
                content.setContent(x);
                content.update();
                return;
            case "Attribute":
                System.out.println("Setting val '" + x + "' to attribute id " + attribute.getId());
                attribute.setValue(x);
                attribute.update();
                return;
            default:
                return;
        }
    }

    public void setAttribute(Attribute a) {
        if (!who.equals("Attribute")) return;

        attribute = a;
    }

    public ObjectReference copyMe(GameCopyContext copyContext) {
        ObjectReference copy;

        switch (who) {
            case "Part":
                if (part == null) return null;
                copy = new ObjectReference(part);
                break;
            case "Rule":
                if (rule == null) return null;
                copy = new ObjectReference(rule);
                break;
            case "Content":
                if (content == null) return null;
                copy = new ObjectReference(content);
                break;
            case "Game":
                if (game == null) return null;
                copy = new ObjectReference(game);
                break;
            case "Action":
                if (action == null) return null;
                copy = new ObjectReference(action);
                break;
            case "Attribute":
                if (attribute == null) return null;
                copy = new ObjectReference(attribute);
                System.out.println("Copying objRef " + who + " origin-id: " + attribute.getId());
                break;
            case "Hotspot":
                if (hotspot == null) return null;
                copy = new ObjectReference(hotspot);
                break;
            default:
                System.err.println("WARNING: cannot copy unknown ObjectReferenceType " + who + " with id: " + id);
                return null;
        }

        copy.save();
        copy.update();
        copyContext.objRefsWithTargetToRenew.add(copy);
        System.out.println("Added ObjRef to RenewList: id: "+ copy.getId() + "copy of objref-id: " + id +
                ";  attrib-id: " + copy.attribute.getId() +
                " which is a copy of id: " + attribute.getId());
        return copy;
    }

}
