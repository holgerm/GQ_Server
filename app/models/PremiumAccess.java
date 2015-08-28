package models;

import play.data.format.Formats;
import play.db.ebean.Model;
import models.User;
import models.Game;

import javax.persistence.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;


@Entity
public class PremiumAccess extends Model {


    @Id
    public long Id;



    private String rights;

    private String bill;
    

    private Date validuntil;
    
    
    
    public PremiumAccess(String right,Date until){
    	
    	rights = right;
    	validuntil = until;
    	
    }
    
    public Date getValidUntil(){
    	
    	return validuntil;
    	
    }
    
    
    public Boolean isValid(){
    	
    	Date now = new Date();
    	
    	if(validuntil.after(now)){
    		
    		return true;
    	} else {
    		
    		return false;
    		
    	}
    	
    }
    
    
    public void addMonths(int m){
    	
    	
    	
    	
		 Calendar cal = Calendar.getInstance();
	        cal.setTime(validuntil);
	       // int m = months+0;
	        cal.add(Calendar.MONTH, m); //minus number would decrement the days
	       validuntil= cal.getTime();
		
    	
    }
    
    




    /**
     *
     * Finder is a Play Framework Class that lets other classes find a specific object of this class,
     * in this case, searching for objects with a Long value is enabled.
     *
     */

	public static final Finder<Long, PremiumAccess> find = new Finder<Long, PremiumAccess>(
			Long.class, PremiumAccess.class);



	public String getName() {
		// TODO Auto-generated method stub
		return rights;
	}


}
