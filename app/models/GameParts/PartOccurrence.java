package models.GameParts;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import java.util.HashSet;
import java.util.List;
import java.util.Set;



@Entity
public class PartOccurrence extends Model {

    @Id
    private Long id;
    
    @OneToOne
    private PartType part;
    private int min;
    private int max;
    private int sort;

    public PartOccurrence(PartType pt){
     
    	part = pt;


    }
    
   
    public void setMin(int x){ min = x; }
    public void setMax(int x){ max = x; }

    
    
    public int getMin(){ return min; }
    public int getMax(){ return max; }
    
    public PartType getPartType(){ return part; }

    
    
    
    
    
    
    
    

    public Long getId(){
        return id;
    }

   



    public static final Finder<Long, PartOccurrence> find = new Finder<Long, PartOccurrence>(
            Long.class, PartOccurrence.class);




}
