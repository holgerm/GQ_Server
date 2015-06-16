package models.GameParts;

import play.db.ebean.Model;

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

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



@Entity
public class ContentTypeSet extends Model {

    @Id
    private Long id;
    
    
    @ManyToMany
    private List<ContentType> contents;
    
    public ContentTypeSet(){ 
    	
    	contents = new ArrayList<ContentType>();
    	
    }
    
    
    public ContentTypeSet(ContentType c){
    	
    	contents = new ArrayList<ContentType>();
    	contents.add(c);
    	
    	
    	
    }
    
    
    public List<ContentType> getContents(){
    	
    	return contents;
    	
    }
    
   
    public void addContent(ContentType c){ contents.add(c); }    
    public Long getID(){ return id; }
    



    public static final Finder<Long, ContentTypeSet> find = new Finder<Long, ContentTypeSet>(
            Long.class, ContentTypeSet.class);



	public ContentType get(int i) {
		// TODO Auto-generated method stub
		return contents.get(i);
	}


	public void remove(Content ac) {
		// TODO Auto-generated method stub
		contents.remove(ac);
	}





}
