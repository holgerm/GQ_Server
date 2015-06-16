package models;

import java.io.Serializable;


public class ProviderUsersId implements Serializable {

    private long userId;

    private long portalId;


    public int hashCode() {
        return (int)(userId + portalId);
    }

    public boolean equals(Object object) {
        if (object instanceof ProviderUsersId) {
            ProviderUsersId otherId = (ProviderUsersId) object;
            return (otherId.portalId == this.portalId) && (otherId.userId == this.userId);
        }
        return false;
    }

}