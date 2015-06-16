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

    @ManyToMany
    private List<AttributeType> attributeTypes;
    

    public ActionType(String n, String xml){
    	
    	name = n;
    	xmltype = xml;
    	
    	show = true;

    }
    
    
    // SETTER
    
    public void setVisibility(boolean x){
    	
    	show = x;
    }
    
    public void setSymbol(String x){ symbol = x; }
    
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
    
    public String getSymbol(){ return symbol; }
    public List<AttributeType> getAttributeTypes(){ return attributeTypes; }

    
    public boolean getVisibility(){
    
    	return show;
    
    }

    public String getName(){ return name; }
    
    
    
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
