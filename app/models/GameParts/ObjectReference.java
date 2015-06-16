package models.GameParts;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import models.Game;



@Entity
public class ObjectReference extends Model {

    @Id
    private Long id;
    
   @OneToOne
    private Part part;
   @OneToOne
    private Rule rule;
   @OneToOne
   private Content content;

   @OneToOne
   private Game game;
   
  @OneToOne
  private Action action;
   
  @OneToOne
  private Attribute attribute;
   
  
  @OneToOne
  private Hotspot hotspot;
  
  private String stringvalue;
  private String special;
    private int sort;
    
    
    private String who;
 

    public ObjectReference(Part p){
    		part = p;
    		who = "Part";
    }
    public ObjectReference(Rule r){
		rule = r;
		who = "Rule";
    }
    public ObjectReference(Content c){
		content = c;
		who = "Content";
    }
    public ObjectReference(Game g){
		game = g;
		who = "Game";
    }
    public ObjectReference(Action ac){
		action = ac;
		who = "Action";
    }
    public ObjectReference(Attribute at){
		attribute = at;
		who = "Attribute";
    }
    public ObjectReference(Hotspot h){
		hotspot = h;
		who = "Hotspot";
    }
    public ObjectReference(String s){
		stringvalue = s;
		who = "String";
    }
    
   

    public void setSpecial(String s){
    	special = s;
    }
    
    public void setSort(int i){
    	
    	sort = i;
    }

    
   
    
    
    

    public Long getId(){
        return id;
    }

   
    public String getObjectType(){
    	
    	return who;
    }
    
    public Part getPart(){ return part; }
    public Rule getRule(){ return rule; }
    public Content getContent(){ return content; }
    public Game getGame(){ return game; }
    public Action getAction(){ return action; }
    public Attribute getAttribute(){ return attribute; }
    public String getString(){ return stringvalue; }
	public String getSpecial() {
		// TODO Auto-generated method stub
		return special;
	}
	public Long getObjectId() {

		Long re = null;
		
		if(who.equals("Part") && part != null){
			
			re = part.getId();
		}
		
		
		if(who.equals("Rule") && rule != null){
			re = rule.getId();
			
		}
		
		if(who.equals("Content") && content != null){
			re = content.getId();
			
		}
		
		if(who.equals("Game") && game != null){
			re = game.getId();
			
		}
		
		if(who.equals("Action") && action != null){
			re = action.getId();
			
		}
		
		
		if(who.equals("Attribute") && attribute != null){
		re = attribute.getId();
			
		}
		
		if(who.equals("Hotspot") && hotspot != null){
			re = hotspot.getId();
			
		}
	
		   
		
		
		
		
		
		
		
		
		
		return re;
	}
    
    
	
	
	
	
	
	
	
	
	
	public void setObjectValue(String x) {

		Long re = null;
		
		if(who.equals("Part") && part != null){
			
			re = part.getId();
		}
		
		
		if(who.equals("Rule") && rule != null){
			re = rule.getId();
			
		}
		
		if(who.equals("Content") && content != null){
			re = content.getId();
			
			content.setContent(x);
			content.update();
			
			
		}
		
		if(who.equals("Game") && game != null){
			re = game.getId();
			
		}
		
		if(who.equals("Action") && action != null){
			re = action.getId();
			
		}
		
		
		if(who.equals("Attribute") && attribute != null){
		re = attribute.getId();
		attribute.setValue(x);
		attribute.update();
			
		}
		
		if(who.equals("Hotspot") && hotspot != null){
			re = hotspot.getId();
			
		}
	
		   
		
		
		
	}
	
	
	






}
