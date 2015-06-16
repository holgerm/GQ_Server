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
public class RuleSet extends Model {

    @Id
    private Long id;
    
    
    @ManyToMany
    private List<Rule> rules;
    
    
    
    public RuleSet(Rule r){
    	
    	rules = new ArrayList<Rule>();
    	rules.add(r);
    	
    	
    	
    }
    
    
    public RuleSet() {
		// TODO Auto-generated constructor stub
    	rules = new ArrayList<Rule>();
	}


	public List<Rule> getRules(){
    	
    	return rules;
    	
    }
    
   
    public void addRule(Rule r){ rules.add(r); }    
    public Long getID(){ return id; }
    



    public static final Finder<Long, RuleSet> find = new Finder<Long, RuleSet>(
            Long.class, RuleSet.class);



	public Rule get(int i) {
		// TODO Auto-generated method stub
		return rules.get(i);
	}


	public void remove(Rule ar) {
		// TODO Auto-generated method stub
		rules.remove(ar);
	}


	public boolean isEmpty() {
	
		return rules.isEmpty();
	}







}
