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
public class ActionSet extends Model {

    @Id
    private Long id;
    
    
    @ManyToMany
    private List<Action> actions;
    
    
    
    public ActionSet(Action r){
    	
    	actions = new ArrayList<Action>();
    	actions.add(r);
    	
    	
    	
    }
    
    
    public ActionSet() {
		// TODO Auto-generated constructor stub
    	actions = new ArrayList<Action>();
	}


	public List<Action> getRules(){
    	
    	return actions;
    	
    }
    
   
    public void addAction(Action r){ actions.add(r); }    
    public Long getID(){ return id; }
    



    public static final Finder<Long, ActionSet> find = new Finder<Long, ActionSet>(
            Long.class, ActionSet.class);



	public Action get(int i) {
		// TODO Auto-generated method stub
		return actions.get(i);
	}


	public void remove(Action ar) {
		// TODO Auto-generated method stub
		actions.remove(ar);
	}


	public boolean isEmpty() {
	
		return actions.isEmpty();
	}







}
