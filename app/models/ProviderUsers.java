package models;

import play.db.ebean.Model;

import javax.persistence.*;


@Entity
@Table(name = "ProviderUsers")
@IdClass(ProviderUsersId.class)
public class ProviderUsers extends Model {


    @Id
    public long Id;



    @Column(name="rights")
    private String rights = "user";

    @ManyToOne
    @PrimaryKeyJoinColumn(name="userID", referencedColumnName="Id")
  /* if this JPA model doesn't create a table for the "PROJ_EMP" entity,
  *  please comment out the @PrimaryKeyJoinColumn, and use the ff:
  *  @JoinColumn(name = "employeeId", updatable = false, insertable = false)
  * or @JoinColumn(name = "employeeId", updatable = false, insertable = false, referencedColumnName = "id")
  */
    public User user;
    @ManyToOne
    @PrimaryKeyJoinColumn(name="portalID", referencedColumnName="Id")
  /* the same goes here:
  *  if this JPA model doesn't create a table for the "PROJ_EMP" entity,
  *  please comment out the @PrimaryKeyJoinColumn, and use the ff:
  *  @JoinColumn(name = "projectId", updatable = false, insertable = false)
  * or @JoinColumn(name = "projectId", updatable = false, insertable = false, referencedColumnName = "id")
  */
    private ProviderPortal portal;



    public void setRights(String x){rights = x; }
    public void setUser(User u){ user = u; }
    public void setPortal(ProviderPortal p){ portal = p; }

    public User getUser(){ return user; }
    public ProviderPortal getPortal(){ return portal; }
    public String getRights(){ return rights; }

    public long getId(){ return Id; }






    /**
     *
     * Finder is a Play Framework Class that lets other classes find a specific object of this class,
     * in this case, searching for objects with a Long value is enabled.
     *
     */

	public static final Finder<Long, ProviderUsers> find = new Finder<Long, ProviderUsers>(
			Long.class, ProviderUsers.class);


}
