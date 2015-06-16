package models;

import java.io.Serializable;


public class ProviderGamesId implements Serializable {

    private long gameId;

    private long portalId;


    public int hashCode() {
        return (int)(gameId + portalId);
    }

    public boolean equals(Object object) {
        if (object instanceof ProviderGamesId) {
            ProviderGamesId otherId = (ProviderGamesId) object;
            return (otherId.portalId == this.portalId) && (otherId.gameId == this.gameId);
        }
        return false;
    }

}