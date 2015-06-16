package models.GameParts;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



@Entity
public class AttributeType extends Model {

    @Id
    private Long id;
    
    private String name;
    private String xmltype;
    private boolean editable;
    private boolean show;
    private boolean canHaveAbstractValue;
    private String defaultvalue;
    
    
    @OneToOne
    private ObjectReference linkto;
    

    
    
    private boolean optional;
    private String filetype;
    private String editortype;
    private String mimetype;
    
    private Long sort;
   
    private boolean showinparent;
    
    @ManyToMany
    private List<SortedString> possibleValues;

    public AttributeType(String n, String xml, String ft){
     
    	name = n;
    	xmltype = xml;
    	
    	
    	/* CHECK IF FILETYPE IS DEFINED?
    	 * 
    	 */
    	
    	filetype = ft;
    	
    	editortype = ft;

    	editable = true;
    	show = true;
    	optional = false;
    	showinparent = false;
    	canHaveAbstractValue = true;
    		
    	defaultvalue = "";
    	mimetype = "none";
    	

    }


    
    /* SETTER
     * 
     */

    public void setShowInParent(boolean x){ showinparent = x; }
    public void setMimeType(String x){ mimetype = x; }
    public void setEditable(boolean x){ editable = x; }
    public void setVisibility(boolean x){ show = x; }
    public void setDefaultValue(String x){ defaultvalue = x; }
    public void addPossibleValue(String x){ SortedString nss = new SortedString(x); nss.save(); possibleValues.add(nss); }
    
    
    		/* DELETE POSSIBLE VALUE
    		 * 
    		 */
    
    
    
    
    
    
    /* GETTER
     * 
     */
    

    public Long getId(){
        return id;
    }

    public boolean isVisibleInParent(){ return showinparent; }
public String getMimeType(){ if(mimetype != null){ return mimetype; } else { return "null"; } }
    public String getXMLType(){ return xmltype; }
    public boolean getVisibility(){ return show; }
    public boolean isEditable(){ return editable; }
    public String getDefaultValue(){ return defaultvalue; }
    
    
    public List<String> getPossibleValues(){ 
    	List<String> possibleValuesAsString = new ArrayList<String>();
    	for(SortedString ss: possibleValues){ possibleValuesAsString.add(ss.getString()); }
    	
    	return possibleValuesAsString; }
    public boolean hasPossibleValues(){ if(possibleValues.isEmpty()){ return false; } else { return true; }  }
   

    
    
    public String getFileType(){ if(filetype.equals("seconds")){ return "int"; } else if(filetype.equals("stringseconds")){ return "String"; } {return filetype;} }
    
    public String getRealFileType(){ return filetype; }
    
    
    public Attribute createMe(){
    	
    	
    	Attribute a = new Attribute(this);
    	a.save();
    	
    	a.setValue(defaultvalue);
    	
    	

    	
    	
    	return a;
    	
    }
    
    
    
    


    public static final Finder<Long, AttributeType> find = new Finder<Long, AttributeType>(
            Long.class, AttributeType.class);

	public String getName() {
		return name;
	}



	public void setOptional(boolean b) {
		optional = b;
		
	}
	
	
	public void setLink(ObjectReference o){
		
		linkto = o;
		
	}

	
	
	
	public ObjectReference getLink(){
		
		
		return linkto;
		
	}
	
	
	public boolean hasLink(){
		
		
		if(linkto != null){
			
			return true;
			
		} else {
			
			return false;
		}
		
	}



}
