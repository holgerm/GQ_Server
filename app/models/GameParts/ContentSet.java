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
public class ContentSet extends Model {

    @Id
    private Long id;
    
    
    @ManyToMany
    private List<Content> contents;
    
    public ContentSet(){}
    
    
    public ContentSet(Content c){
    	
    	contents = new ArrayList<Content>();
    	contents.add(c);
    	
    	
    	
    }
    
    
    public List<Content> getContents(){
    	
    	return contents;
    	
    }
    
   
    public void addContent(Content c){ contents.add(c); }    
    public Long getID(){ return id; }
    



    public static final Finder<Long, ContentSet> find = new Finder<Long, ContentSet>(
            Long.class, ContentSet.class);



	public Content get(int i) {
		// TODO Auto-generated method stub
		return contents.get(i);
	}


	public void remove(Content ac) {
		// TODO Auto-generated method stub
		contents.remove(ac);
	}


	public boolean contains(Content c) {
		// TODO Auto-generated method stub
		return contents.contains(c);
	}





}
