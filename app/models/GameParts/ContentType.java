package models.GameParts;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
public class ContentType extends Model {

    @Id
    private Long id;

    private String name;
    private String xmltype;
    private String content;
    private String defaultvalue;
    private boolean show;

    private List<ContentTypeOccurrence> contentoccurrences;

    @ManyToMany
    private List<AttributeType> attributeTypes;
    @ManyToMany
    private List<RuleType> possibleRuleTypes;

    @ManyToOne
    private ContentTypeSet possibleContentTypes;

    @ManyToOne
    private ContentSet defaultContent;


    private String premiumcodes;


    public ContentType(String n, String xml) {

        name = n;
        xmltype = xml;
        show = true;
        possibleContentTypes = new ContentTypeSet();
        possibleContentTypes.save();
        possibleContentTypes.save();
        defaultContent = new ContentSet();
        defaultContent.save();
        contentoccurrences = new ArrayList<ContentTypeOccurrence>();
    }


    // SETTER

    public void addPossibleRuleTypes(RuleType x) {
        possibleRuleTypes.add(x);
    }

    public void addPossibleContentType(ContentType ct) {
        possibleContentTypes.addContent(ct);
        possibleContentTypes.update();
    }

    public void addContentTypeOccurrence(ContentTypeOccurrence cto) {

        contentoccurrences.add(cto);


    }


    public void addDefaultContent(Content c) {
        defaultContent.addContent(c);
    }


    public void setDefaultValue(String x) {
        defaultvalue = x;
    }

    public void setVisibility(boolean x) {
        show = x;
    }


    public void setAttributeType(AttributeType t) {

        try {
            List<AttributeType> copyOfAttributes = new ArrayList<AttributeType>(attributeTypes.size());
            ;
            for (AttributeType item : attributeTypes) copyOfAttributes.add(item);

            for (AttributeType aatr : copyOfAttributes) {
                if (aatr.getXMLType() == t.getXMLType()) {
                    attributeTypes.remove(aatr);
                }
            }
            attributeTypes.add(t);

        } catch (RuntimeException e) {

            System.out.println("Problem setting AttributeType.");
            e.printStackTrace();

        }

    }


//GETTER

    public boolean canHaveSubContent() {
        if (possibleContentTypes != null) {
            if (possibleContentTypes.getContents().isEmpty()) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public String getXMLType() {
        return xmltype;
    }

    public List<RuleType> getPossibleRuleTypes() {
        return possibleRuleTypes;
    }

    public List<ContentType> getPossibleContentTypes() {
        return possibleContentTypes.getContents();
    }

    public List<Content> getDefaultContents() {
        return defaultContent.getContents();
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getDefaultValue() {
        return defaultvalue;
    }

    public boolean getVisibility() {
        return show;
    }

    public List<AttributeType> getAttributeTypes() {
        return attributeTypes;
    }


    public Long getId() {
        return id;
    }


    public static final Finder<Long, ContentType> find = new Finder<Long, ContentType>(
            Long.class, ContentType.class);


    public Content createMe() {


        Content c = new Content(name, this);
        c.setContent(defaultvalue);


        for (Content sc : defaultContent.getContents()) {
            c.addSubContent(sc.copyMe(""));
        }
        c.save();


        return c;


    }

    public Content createMe(String n) {


        Content c = new Content(n, this);
        c.setContent(defaultvalue);
        c.save();

        return c;


    }


}
