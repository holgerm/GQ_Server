package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.io.Serializable;


public class GameRightsId implements Serializable {

    private long gameId;

    private long userId;


    public int hashCode() {
        return (int)(gameId + userId);
    }

    public boolean equals(Object object) {
        if (object instanceof GameRightsId) {
            GameRightsId otherId = (GameRightsId) object;
            return (otherId.userId == this.userId) && (otherId.gameId == this.gameId);
        }
        return false;
    }

}