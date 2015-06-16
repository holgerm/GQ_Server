package models;

import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.validation.Email;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthUser;
import com.feth.play.module.pa.user.AuthUser;
import com.feth.play.module.pa.user.AuthUserIdentity;
import com.feth.play.module.pa.user.EmailIdentity;
import com.feth.play.module.pa.user.NameIdentity;
import com.feth.play.module.pa.user.FirstLastNameIdentity;

import controllers.Application;
import models.TokenAction.Type;
import models.GameParts.ObjectReference;
import play.data.format.Formats;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import util.Global;

import javax.persistence.*;

import java.util.*;

/**
 * Initial version based on work by Steve Chaloner (steve@objectify.be) for
 * Deadbolt2
 */
@Entity
@Table(name = "users")
public class User extends Model implements Subject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Id
	public Long id;

	@Email
	// if you make this unique, keep in mind that users *must* merge/link their
	// accounts then on signup with additional providers
	// @Column(unique = true)
	public String email;

	public String name;
	
	public String firstName;
	
	public String lastName;

    @ManyToMany
    @OrderBy("datum")
    private List<NewsstreamItem> Newsstream;



    @OneToMany(mappedBy="user")
    private Set<GameRights> games;

    @OneToMany(mappedBy="user")
    private Set<ProviderUsers> portals;



	@Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date lastLogin;

	public boolean active;

	public boolean emailValidated;

	@ManyToMany
	public List<SecurityRole> roles;
	
	
	
	@ManyToMany
	public List<ObjectReference> clipboard;

	@OneToMany(cascade = CascadeType.ALL)
	public List<LinkedAccount> linkedAccounts;

	@ManyToMany
	public List<UserPermission> permissions;



    public String getEmail(){

        return email;
    }

    public String getName(){

        return name;
    }
    
    
    
    public void deleteNewsstreamItem(NewsstreamItem ni){
    	
    	if(Newsstream.contains(ni)== true){

            Newsstream.remove(ni);

        }
    	
    }


    public void deleteGame(GameRights g){

        if(games.contains(g)== true){

            games.remove(g);

        }


    }

    public void deletePortal(ProviderUsers pu){

        if(portals.contains(pu)== true){

            portals.remove(pu);

        }


    }


    public Set<GameRights> getGames(){
        return games;

    }



    public Set<GameRights> getGamesOnPortal(ProviderPortal p){

        Set<GameRights> onPortal = new HashSet<GameRights>();


        for(GameRights agameright: games){


            if(p.existsGame(agameright.getGame()) == true){
                onPortal.add(agameright);
            } else {
            	
            	//System.out.println("I have a GameRight that is not on this portal:"+agameright.getGame().getId());
            	
            }


        }

        for(ProviderGames aprovidergame: p.getPublicGamesList()){

            boolean contained = false;

            for(GameRights acontainedgr: onPortal){
                if(acontainedgr.getGame().getId().equals(aprovidergame.getGame().getId())){
                    contained = true;
                }

            }

           
        }


        return onPortal;

    }




    public Set<GameRights> getGamesForPortal(ProviderPortal p){

        Set<GameRights> filtered = new HashSet();

        for(GameRights agameright :games){

           for(ProviderGames aprovidergame : Application.getLocalPortal().getGameList()){

               if(agameright.getGame().equals(aprovidergame.getGame())){

                   filtered.add(agameright);

               }

           }

        }

        return filtered;

    }

    public Long getId(){

        return id;
    }

    public Set<ProviderUsers> getPortals(){
        return portals;

    }

    
    public void addPortal(ProviderUsers pu){
    	
    	boolean doit = true;

    	for(ProviderUsers test:portals){
    		
    		if(test.getPortal().getId().equals(pu.getPortal().getId())){
    			
    			doit = false;
    			
    			
    		}
    		
    		
    	}
    	
    	
    	
    	if(doit){
    		
    		portals.add(pu);
    		
    	}
    	
    	
    }
    
    public ProviderUsers getPortal(ProviderPortal p){


        ProviderUsers pu = new ProviderUsers();

        for(ProviderUsers aprovideruser : portals ){

            if(aprovideruser.getPortal().getId() == p.getId()){

                pu = aprovideruser;

            }

        }
        return pu;


    }


    public NewsstreamItem createNewsstreamItem(String title, String content,String vis){


        NewsstreamItem nsi = new NewsstreamItem(title,content,vis,"user",getId());
       return nsi;

    }


    public void addNewsstream(NewsstreamItem nsi){


        Newsstream.add(nsi);

        this.update();




    }






       /*
        * Play Authenticate Code
        */



    @Override
    public String getIdentifier()
    {
        return Long.toString(id);
    }

    @Override
    public List<? extends Role> getRoles() {
        return roles;
    }

    @Override
    public List<? extends Permission> getPermissions() {
        return permissions;
    }

	public static boolean existsByAuthUserIdentity(
			final AuthUserIdentity identity) {
		final ExpressionList<User> exp;
		if (identity instanceof UsernamePasswordAuthUser) {
			exp = getUsernamePasswordAuthUserFind((UsernamePasswordAuthUser) identity);
		} else {
			exp = getAuthUserFind(identity);
		}
		return exp.findRowCount() > 0;
	}

	private static ExpressionList<User> getAuthUserFind(
			final AuthUserIdentity identity) {
		return find.where().eq("active", true)
				.eq("linkedAccounts.providerUserId", identity.getId())
				.eq("linkedAccounts.providerKey", identity.getProvider());
	}

	public static User findByAuthUserIdentity(final AuthUserIdentity identity) {
		if (identity == null) {
			return null;
		}
		if (identity instanceof UsernamePasswordAuthUser) {
			return findByUsernamePasswordIdentity((UsernamePasswordAuthUser) identity);
		} else {
			return getAuthUserFind(identity).findUnique();
		}
	}

	public static User findByUsernamePasswordIdentity(
			final UsernamePasswordAuthUser identity) {
		return getUsernamePasswordAuthUserFind(identity).findUnique();
	}

	private static ExpressionList<User> getUsernamePasswordAuthUserFind(
			final UsernamePasswordAuthUser identity) {
		return getEmailUserFind(identity.getEmail()).eq(
				"linkedAccounts.providerKey", identity.getProvider());
	}

	public void merge(final User otherUser) {
		for (final LinkedAccount acc : otherUser.linkedAccounts) {
			this.linkedAccounts.add(LinkedAccount.create(acc));
		}
		// do all other merging stuff here - like resources, etc.

		// deactivate the merged user that got added to this one
		otherUser.active = false;
		Ebean.save(Arrays.asList(new User[] { otherUser, this }));
	}

	public static User create(final AuthUser authUser) {
		final User user = new User();
		user.roles = Collections.singletonList(SecurityRole
				.findByRoleName(controllers.Application.USER_ROLE));
		// user.permissions = new ArrayList<UserPermission>();
		// user.permissions.add(UserPermission.findByValue("printers.edit"));
		user.active = true;
		user.lastLogin = new Date();
		user.linkedAccounts = Collections.singletonList(LinkedAccount
				.create(authUser));

		if (authUser instanceof EmailIdentity) {
			final EmailIdentity identity = (EmailIdentity) authUser;
			// Remember, even when getting them from FB & Co., emails should be
			// verified within the application as a security breach there might
			// break your security as well!
			user.email = identity.getEmail();
			user.emailValidated = false;
		}

		if (authUser instanceof NameIdentity) {
			final NameIdentity identity = (NameIdentity) authUser;
			final String name = identity.getName();
			if (name != null) {
				user.name = name;
			}
		}
		
		if (authUser instanceof FirstLastNameIdentity) {
		  final FirstLastNameIdentity identity = (FirstLastNameIdentity) authUser;
		  final String firstName = identity.getFirstName();
		  final String lastName = identity.getLastName();
		  if (firstName != null) {
		    user.firstName = firstName;
		  }
		  if (lastName != null) {
		    user.lastName = lastName;
		  }
		}

		user.save();
		user.saveManyToManyAssociations("roles");
		// user.saveManyToManyAssociations("permissions");
		return user;
	}

	public static void merge(final AuthUser oldUser, final AuthUser newUser) {
		User.findByAuthUserIdentity(oldUser).merge(
				User.findByAuthUserIdentity(newUser));
	}

	public Set<String> getProviders() {
		final Set<String> providerKeys = new HashSet<String>(
				linkedAccounts.size());
		for (final LinkedAccount acc : linkedAccounts) {
			providerKeys.add(acc.providerKey);
		}
		return providerKeys;
	}

	public static void addLinkedAccount(final AuthUser oldUser,
			final AuthUser newUser) {
		final User u = User.findByAuthUserIdentity(oldUser);
		u.linkedAccounts.add(LinkedAccount.create(newUser));
		u.save();
	}

	public static void setLastLoginDate(final AuthUser knownUser) {
		final User u = User.findByAuthUserIdentity(knownUser);
		
		if(u != null){
		u.setLastLoginDateDate(new Date());
		
		u.update();
		}
	}
	
	
	public void setLastLoginDateDate(Date x){
		
		lastLogin = x;
		
	}

	public static User findByEmail(final String email) {
		return getEmailUserFind(email).findUnique();
	}

	private static ExpressionList<User> getEmailUserFind(final String email) {
		return find.where().eq("active", true).eq("email", email);
	}

	public LinkedAccount getAccountByProvider(final String providerKey) {
		return LinkedAccount.findByProviderKey(this, providerKey);
	}

	public static void verify(final User unverified) {
		// You might want to wrap this into a transaction
		unverified.emailValidated = true;
		unverified.save();
		TokenAction.deleteByUser(unverified, Type.EMAIL_VERIFICATION);
	}

	public void changePassword(final UsernamePasswordAuthUser authUser,
			final boolean create) {
		LinkedAccount a = this.getAccountByProvider(authUser.getProvider());
		if (a == null) {
			if (create) {
				a = LinkedAccount.create(authUser);
				a.user = this;
			} else {
				throw new RuntimeException(
						"Account not enabled for password usage");
			}
		}
		a.providerUserId = authUser.getHashedPassword();
		a.save();
	}

	public void resetPassword(final UsernamePasswordAuthUser authUser,
			final boolean create) {
		// You might want to wrap this into a transaction
		this.changePassword(authUser, create);
		TokenAction.deleteByUser(this, Type.PASSWORD_RESET);
	}








    /**
     *
     * Finder is a Play Framework Class that lets other classes find a specific object of this class,
     * in this case, searching for objects with a Long value is enabled.
     *
     */


    public static final Finder<Long, User> find = new Finder<Long, User>(
            Long.class, User.class);



}
