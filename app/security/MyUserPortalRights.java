package security;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;

import controllers.Application;
import models.GameRights;
import models.ProviderPortal;
import models.ProviderGames;
import models.User;
import models.Game;
import models.ProviderUsers;

import java.util.Set;

import play.mvc.Http;
import util.Global;


public class MyUserPortalRights {


    public static final String USER_ROLE = "user";
    public static final String ADMIN_ROLE = "admin";
    public static final String UNVERIFIED_ROLE = "unverified";



    /*
     * Single Combinations
     */

    public boolean hasAdminRightsOnPortal(User u) {

        ProviderPortal p = Application.getLocalPortal();
        try {
            Long idd = u.getId();
        } catch (java.lang.Throwable t) {
            return false;
        }

        if (p.existsUser(u) == true) {

            if (p.getUser(u).getRights().equals("admin")) {

                return true;
            } else {

                return false;

            }

        } else {

            return false;
        }


    }

    public boolean hasAdminRightsOnPortalX(User u, ProviderPortal p) {
        try {
            Long idd = u.getId();
        } catch (java.lang.Throwable t) {
            return false;
        }


        if (p.existsUser(u) == true) {

            if (p.getUser(u).getRights().equals("admin")) {

                return true;
            } else {

                return false;

            }

        } else {

            return false;
        }


    }


    public boolean hasMinVerifiedRightsOnPortal(User u) {
        ProviderPortal p = Application.getLocalPortal();

        try {
            Long idd = u.getId();
        } catch (java.lang.Throwable t) {
            return false;
        }

        if (p.existsUser(u) == true) {

            if (p.getUser(u).getRights().equals("user")) {

                return true;

            } else if (p.getUser(u).getRights().equals("admin")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    public boolean hasAnyRightsOnPortal(User u) {

        ProviderPortal p = Application.getLocalPortal();

        try {
            Long idd = u.getId();
        } catch (java.lang.Throwable t) {
            return false;
        }

        return p.existsUser(u);
    }


    public boolean hasWriteRightsOnGame(User u, Game g) {


        try {
            Long idd = u.getId();
        } catch (java.lang.Throwable t) {
            return false;
        }

        ProviderPortal p = Application.getLocalPortal();

        String help1 = "false";
        if (p.getContentHtmlParameter("general.games.adminshaveallrights") != null) {

            help1 = p.getContentHtmlParameter("general.games.adminshaveallrights");
        }
        if (help1.equals("true") && Global.securityGuard.hasAdminRightsOnPortalX(u, p)) {

            return true;


        }


        if (g.existsUser(u) == true) {

            if (g.getUser(u).getRights().equals("write")) {


                return true;
            } else {

                return false;

            }

        } else {

            return false;
        }

    }

    public boolean hasMinReadRightsOnGame(User u, Game g) {

        try {
            Long idd = u.getId();
        } catch (java.lang.Throwable t) {
            return false;
        }

        if (g.existsUser(u) == true) {

            if (g.getUser(u).getRights().equals("write")) {

                return true;
            } else if (g.getUser(u).getRights().equals("read")) {

                return true;

            } else {

                return false;

            }

        } else {

            return false;
        }


    }

    public boolean isDefaultPortal() {

        if (Application.getLocalPortal().getIdentifier().equals(Global.defaultportal.getIdentifier())) {

            return true;

        } else {
            return false;
        }

    }


    /*
     * AbstractCombinations
     */

    public boolean hasWriteAccessRightsToGameOnPortal(User u, Game g) {

        try {
            Long idd = u.getId();
        } catch (java.lang.Throwable t) {
            System.out.println(t.getClass().getName()); //this'll tell you what class has been thrown

            return false;


        }


        if (hasMinVerifiedRightsOnPortal(u) == true) {

            if (hasWriteRightsOnGame(u, g) == true) {

                return true;

            } else {

                return false;

            }
        } else {

            return false;
        }


    }

    public boolean hasReadAccessRightsToGameOnPortal(User u, Game g) {

        try {
            Long idd = u.getId();
        } catch (java.lang.Throwable t) {
            System.out.println(t.getClass().getName()); //this'll tell you what class has been thrown

            return false;


        }


        if (hasMinVerifiedRightsOnPortal(u) == true) {

            if (hasMinReadRightsOnGame(u, g) == true) {

                return true;

            } else {

                return false;

            }
        } else {

            return false;
        }


    }


    public static User getLocalUser(final Http.Session session) {
        final AuthUser currentAuthUser = PlayAuthenticate.getUser(session);
        final User localUser = User.findByAuthUserIdentity(currentAuthUser);
        return localUser;
    }


}
