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
public class ContentTypeOccurrence extends Model {

    @Id
    private Long id;
    
    @OneToOne
    private ContentType content;
    private int min;
    private int max;
    private int sort;

    public ContentTypeOccurrence(ContentType ct){
     
    	content = ct;


    }
    
   
    public void setMin(int x){ min = x; }
    public void setMax(int x){ max = x; }

    
    
    public int getMin(){ return min; }
    public int getMax(){ return max; }
    
    public ContentType getContentType(){ return content; }

    
    
    
    
    
    
    
    

    public Long getId(){
        return id;
    }

   



    public static final Finder<Long, ContentTypeOccurrence> find = new Finder<Long, ContentTypeOccurrence>(
            Long.class, ContentTypeOccurrence.class);




}
