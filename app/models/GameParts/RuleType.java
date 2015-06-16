package models.GameParts;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import java.util.HashSet;
import java.util.List;
import java.util.Set;



@Entity
public class RuleType extends Model {

    @Id
    private Long id;
   
    private String name;
    private String xmltype;
    private boolean show;
    
    private String symbol;
    
    @ManyToMany
    private List<ActionType> possibleActionTypes;
    @ManyToMany
    private List<Condition> possibleConditions;
   
    @ManyToOne
    private Rule defaultImplementation;

    public RuleType(String n, String x){
    	name = n;
    	xmltype = x;
    	show = true;
    }


    
    // SETTER
    
    public void setVisibility(boolean x){ show = x; }
    public void addPossibleActionType(ActionType at){ possibleActionTypes.add(at); }
    public boolean addPossibleCondition(Condition c){ boolean help = false; if(c.isType()){ possibleConditions.add(c); help = true; } return help; }
    public void addDefaultImplementation(Rule r){ defaultImplementation = r; }
    
    
    // GETTER
    
    public String getName(){ return name; }
    public boolean getVisibility(){ return show; }
    public List<ActionType> getPossibleActionTypes(){ return possibleActionTypes; }
    public List<Condition> getPossibleConditions(){ return possibleConditions; }
    public Rule getDefaultImplementation(){ return defaultImplementation; }
    
    public String getTrigger(){ return xmltype; }
    
    
    
    
    

    public Long getId(){
        return id;
    }
    
    
    
    
    
    
    public Rule createMe(){
    	
    	Rule r = defaultImplementation.copyMe();
    	r.save();
    	return r;
    	
    }

   

	public String getSymbol() {
		return symbol;
	}



	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}



    public static final Finder<Long, RuleType> find = new Finder<Long, RuleType>(
            Long.class, RuleType.class);




}
