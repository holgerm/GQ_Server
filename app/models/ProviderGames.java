package models;

import play.db.ebean.Model;

import javax.persistence.*;


@Entity
@Table(name = "ProviderGames")
@IdClass(ProviderGamesId.class)
public class ProviderGames extends Model {


    @Id
    public long Id;



    @Column(name="visibility")
    private boolean visibility = false;

    @ManyToOne
    @PrimaryKeyJoinColumn(name="gameID", referencedColumnName="Id")
  /* if this JPA model doesn't create a table for the "PROJ_EMP" entity,
  *  please comment out the @PrimaryKeyJoinColumn, and use the ff:
  *  @JoinColumn(name = "employeeId", updatable = false, insertable = false)
  * or @JoinColumn(name = "employeeId", updatable = false, insertable = false, referencedColumnName = "id")
  */
    public Game game;
    @ManyToOne
    @PrimaryKeyJoinColumn(name="portalID", referencedColumnName="Id")
  /* the same goes here:
  *  if this JPA model doesn't create a table for the "PROJ_EMP" entity,
  *  please comment out the @PrimaryKeyJoinColumn, and use the ff:
  *  @JoinColumn(name = "projectId", updatable = false, insertable = false)
  * or @JoinColumn(name = "projectId", updatable = false, insertable = false, referencedColumnName = "id")
  */
    private ProviderPortal portal;



    public void setVisibility(boolean x){visibility = x; }
    public void setGame(Game g){ game = g; }
    public void setPortal(ProviderPortal p){ portal = p; }

    public Game getGame(){ return game; }
    public ProviderPortal getPortal(){ return portal; }
    public boolean getVisibility(){ return visibility; }

    public long getId(){ return Id; }






    /**
     *
     * Finder is a Play Framework Class that lets other classes find a specific object of this class,
     * in this case, searching for objects with a Long value is enabled.
     *
     */

	public static final Finder<Long, ProviderGames> find = new Finder<Long, ProviderGames>(
			Long.class, ProviderGames.class);


}
