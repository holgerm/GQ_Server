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
public class HotspotType extends Model {

    @Id
    private Long id;
    
    private String name;
    private String xmltype;
    private boolean show;
    private String image;
    private int radius;
    
    @ManyToMany
    private List<RuleType> possibleRuleTypes;
    
    @ManyToMany
    private List<Rule> defaultRules;
    
    @ManyToMany
    private List<AttributeType> attributeTypes;

    public HotspotType(String n, String x){

    	name = n;
    	xmltype = x;
    	radius = 30;

    }


    
    
    // SETTER
    
    public void addPossibleRuleType(RuleType rt){ possibleRuleTypes.add(rt); }
    
    public void setVisibility(boolean x){ show = x; }

	
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
    
    

    
    


// GETTER

public List<RuleType> getPossibleRuleTypes(){ return possibleRuleTypes; }
public List<AttributeType> getAttributeTypes(){ return attributeTypes; }
public boolean getVisibility(){ return show; }
    public String getName(){ return name; }
    
    

    public Long getId(){
        return id;
    }

   
    
    
    
    
    
    
    public Hotspot createMe(Float lon,Float lat,String n){
    	
    	Hotspot h = new Hotspot(this,lon,lat,n);
    
    	h.save();
    	return h;
    	
    }
    
    
    



    public static final Finder<Long, HotspotType> find = new Finder<Long, HotspotType>(
            Long.class, HotspotType.class);




}
