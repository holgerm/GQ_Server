package models;

import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;




@Entity
public class Device extends Model {

	@Id
    private Long id;


    @Lob
    public String name;
    @Lob
    public String deviceid;

    public Long questpush;
  
    public String quest;
    
    public String code;
  

    public Device(String n, String d){
     
    	name = n;
    	deviceid =d;

    }
    
    public Long getId(){
    	
    	
    	return id;
    	
    }


  public String generateCode(){
	  
	  
	  Long s1 = id*13+1010;
	  Long s2 = id*2+4123;

	  code = s1+"-"+s2;
	  
	  
	  return code;
  }
  
  
  
  public static final Finder<String, Device> find = new Finder<String, Device>(
          String.class, Device.class);
 
}
