package models.GameParts;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



@Entity
public class MissionType extends Model {

    @Id
    private Long id;

    private String name;
    private int costs;
    private boolean oeffentlich;
 
    private String xmltype;
    
    @ManyToMany
    private List<RuleType> possibleRuleTypes;
    @ManyToMany
    private List<ContentType> possibleContentTypes;
    @ManyToMany
    private List<AttributeType> attributeTypes;
    @ManyToMany
    private List<Rule> defaultRules;
    @ManyToMany
    private List<Content> defaultContent;
    
    
    public MissionType(String n, String x){
     
    	name = n;
    	xmltype = x;
    	costs = 0;

    }


    
    // SETTER
    
    public void setVisibility(boolean x){ oeffentlich = x; }


	
public void setAttributeType(AttributeType t){
	
	 try{
	 List<AttributeType> copyOfAttributes =  new ArrayList<AttributeType>(attributeTypes.size());;
     for(AttributeType item: attributeTypes) copyOfAttributes.add(item);	

	 for(AttributeType aatr: copyOfAttributes){  		 
		 if(aatr.getXMLType() == t.getXMLType()){	 
			attributeTypes.remove(aatr);
		 }
	 }
	 attributeTypes.add(t);
	 
		} catch (RuntimeException e) {

			System.out.println("Problem setting AttributeType.");
			e.printStackTrace();

		}

}
    
public void  addPossibleRuleTypes(RuleType x){ possibleRuleTypes.add(x); }
public void  addPossibleContentTypes(ContentType x){ possibleContentTypes.add(x); }
public void addDefaultRules(Rule x){ defaultRules.add(x); }
public void addDefaultContent(Content x){ defaultContent.add(x); }
    
    


// GETTER

public List<AttributeType> getAttributeTypes(){ return attributeTypes; }
public List<RuleType> getPossibleRuleTypes(){ return possibleRuleTypes; }
public List<ContentType> getPossibleContentTypes(){ return possibleContentTypes; }
public List<Rule> getDefaultRules(){ return defaultRules; }
public List<Content> getDefaultContent(){ return defaultContent; }
public String getName(){ return name; }
public boolean getVisibility(){ return oeffentlich; }
    
    public String getXMLType(){ return xmltype; }
    
    
    public String getNameEncoded(){
    	
    String n = name;
    	
    	n = n.replaceAll("ä", "ae");
    	n = n.replaceAll("ö", "oe");
    	n = n.replaceAll("ü", "ue");
    	n = n.replaceAll("ß", "ss");
    	n = n.replaceAll(" ", "");
    	n = n.replaceAll("\\(", "");
    	n = n.replaceAll("\\)", "");
    	
    	return n;
    
    }
    
    
    
    
    

    public Long getId(){
        return id;
    }

   
    
    
    
    public Mission createMe(String n){
    	
    	

		Mission m = new Mission(n, this);
		m.save();
		
		
		
		for(Rule ar: defaultRules){
			
			m.addRule(ar.copyMe());
			m.update();
		}
		
		Set<String> in = new HashSet<String>();
    	
    	
		for(Content ac:defaultContent){
			
			
			
			if(in.contains(ac.getName())){
				int counter = 1;
				for(String st:in){ 
    				if(st.equals(ac.getName())){
    					counter++;
    				}
				}
				in.add(ac.getName());
				m.addContent(ac.copyMe(""+counter));
				
			
			} else {
				
				m.addContent(ac.copyMe(""));
				
				in.add(ac.getName());
				
			}
			
			
			m.update();
  

			
		}
		
    	
    	
    	return m;
    	
    }
    
    
    



    public static final Finder<Long, MissionType> find = new Finder<Long, MissionType>(
            Long.class, MissionType.class);




}
