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
public class ActionType extends Model {

    @Id
    private Long id;
    
    private String name;
    private String xmltype;
    private boolean show;
    

    private String symbol;
    
    
    private String category;

    @ManyToMany
    private List<AttributeType> attributeTypes;
    
    
    
    
    private String premiumcodes;

    public ActionType(String n, String xml){
    	
    	name = n;
    	xmltype = xml;
    	
    	show = true;

    }
    
    
    // SETTER
    
    public void setVisibility(boolean x){
    	
    	show = x;
    }
    
    
    public String getCategory(){
    	
    	
    	
    	
    	if(category == null){
    		
    		return "";
    	} else {
    	
    return category;	
    	}
    
    }
    
    
    public void setCategory(String s){
category = s;
}
    
    
    
   public String getPremiumRequirement(){
	   
	   return premiumcodes;
	   
   }
   
   
   public void setPremiumRequirement(String s){
	   
	   premiumcodes = s;
	   
   }
    
   
   public void addPremiumRequirement(String s){
	   
	premiumcodes = premiumcodes + ","+s;   
   
   }
   
   
    public void setSymbol(String x){ symbol = x; }
    
    public void setAttributeType(AttributeType t){
    	
    	 try{
             List<AttributeType> copyOfAttributes =  new ArrayList<AttributeType>(attributeTypes.size());
             for(AttributeType item: attributeTypes) copyOfAttributes.add(item);

             for(AttributeType aatr: copyOfAttributes){
                 if(aatr.getXMLType() == t.getXMLType()){
                    attributeTypes.remove(aatr);
                 }
             }
             attributeTypes.add(t);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    // GETTER
    
    public String getSymbol(){ return symbol; }
    public List<AttributeType> getAttributeTypes(){ return attributeTypes; }

    
    public boolean getVisibility(){
    
    	return show;
    
    }

    public String getName(){ return name; }
    
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
    
    
    public Action createMe(String n){
    	
    	Action a= new Action(n,this);
    	a.save();
    	return a;
    }
    
    
    

    public Long getId(){
        return id;
    }

   



    public static final Finder<Long, ActionType> find = new Finder<Long, ActionType>(
            Long.class, ActionType.class);


	public String getXMLType() {
		// TODO Auto-generated method stub
		return xmltype;
	}




}
