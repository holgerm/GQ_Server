package models.GameParts;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;



@Entity
public class SortedString extends Model {

    @Id
    private Long id;
    
    private String me;
   

    public SortedString(String x){

    	me = x;
    }

    
    public String getString(){
    	return me;
    }
    
    public void setString(String x){ me = x; }
    

    public Long getId(){
        return id;
    }

   



    public static final Finder<Long, SortedString> find = new Finder<Long, SortedString>(
            Long.class, SortedString.class);




}
