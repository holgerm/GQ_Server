package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import models.User;
import models.Game;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "GameRights")
@IdClass(GameRightsId.class)
public class GameRights extends Model {


    @Id
    public long Id;



    @Column(name="rights")
    private String rights = "admin";

    @ManyToOne
    @PrimaryKeyJoinColumn(name="gameID", referencedColumnName="Id")
  /* if this JPA model doesn't create a table for the "PROJ_EMP" entity,
  *  please comment out the @PrimaryKeyJoinColumn, and use the ff:
  *  @JoinColumn(name = "employeeId", updatable = false, insertable = false)
  * or @JoinColumn(name = "employeeId", updatable = false, insertable = false, referencedColumnName = "id")
  */
    public Game game;
    @ManyToOne
    @PrimaryKeyJoinColumn(name="userID", referencedColumnName="Id")
  /* the same goes here:
  *  if this JPA model doesn't create a table for the "PROJ_EMP" entity,
  *  please comment out the @PrimaryKeyJoinColumn, and use the ff:
  *  @JoinColumn(name = "projectId", updatable = false, insertable = false)
  * or @JoinColumn(name = "projectId", updatable = false, insertable = false, referencedColumnName = "id")
  */
    private User user;



    public void setRights(String x){rights = x; }
    public void setGame(Game g){ game = g; }
    public void setUser(User u){ user = u; }

    public Game getGame(){ return game; }
    public User getUser(){ return user; }
    public String getRights(){ return rights; }







    /**
     *
     * Finder is a Play Framework Class that lets other classes find a specific object of this class,
     * in this case, searching for objects with a Long value is enabled.
     *
     */

	public static final Finder<Long, GameRights> find = new Finder<Long, GameRights>(
			Long.class, GameRights.class);


}
