package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import play.db.ebean.Model;




@Entity
public class Device extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2487857565697504067L;


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
