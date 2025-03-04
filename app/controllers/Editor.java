package controllers;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import models.Device;
import models.Game;
import models.ProviderPortal;
import models.ProviderUsers;
import models.GameParts.Action;
import models.GameParts.ActionType;
import models.GameParts.Attribute;
import models.GameParts.AttributeType;
import models.GameParts.Condition;
import models.GameParts.Content;
import models.GameParts.ContentType;
import models.GameParts.GameType;
import models.GameParts.Hotspot;
import models.GameParts.HotspotType;
import models.GameParts.MenuItem;
import models.GameParts.Mission;
import models.GameParts.MissionType;
import models.GameParts.Part;
import models.GameParts.Rule;
import models.GameParts.RuleType;
import models.GameParts.Scene;
import models.GameParts.SceneType;

import models.help.GameCopyContext;
import org.apache.commons.io.FileUtils;

import play.mvc.BodyParser;
import play.mvc.BodyParser.Xml;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import providers.MyUsernamePasswordAuthProvider;
import util.Global;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import util.Txt;

public class Editor extends Controller {

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getEditor(Long gid) {

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render(Txt.TheQuest() + " existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an " + Txt.thisQuest_Dative() + "."));

            } else {
                Game game = Game.find.byId(gid);
                return ok(views.html.editor.editormain.render(game));
            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getEditorRedirect(Long gid, Long pid) {

        return redirect(Global.SERVER_URL_2 + "/editor/" + gid);

    }

    /*
     * FRAGMENTS FOR EDITOR-RELOAD
     */

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getPartInSideMenuForEditor(Long gid, Long pid) {

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Part.find.where().eq("id", pid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

                } else {

                    return ok(
                            views.html.editor.editor_apartinsidemenu.render(Game.find.byId(gid), Part.find.byId(pid)));

                }
            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getMissionListInSideMenuForEditor(Long gid) {

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                return ok(views.html.editor.editor_sidemenu_missionlist.render(Game.find.byId(gid)));

            }

        }
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getMenuItemInSideMenuForEditor(Long gid, Long pid) {

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (MenuItem.find.where().eq("id", pid).findRowCount() != 1) {

                return badRequest(views.html.norights.render("Das Menu-Item existiert nicht"));

            } else {

                if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                        Game.find.byId(gid)) == false) {

                    return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

                } else {

                    return ok(views.html.editor.editor_amenuiteminsidemenu.render(Game.find.byId(gid),
                            MenuItem.find.byId(pid)));

                }

            }

        }
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getHotspotInSideMenuForEditor(Long gid, Long pid) {

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Hotspot.find.where().eq("id", pid).findRowCount() != 1) {

                return badRequest(views.html.norights.render("Der Hotspot existiert nicht"));

            } else {

                if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                        Game.find.byId(gid)) == false) {

                    return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

                } else {

                    return ok(views.html.editor.editor_ahotspotinsidemenu.render(Game.find.byId(gid),
                            Hotspot.find.byId(pid)));

                }

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getHotspotRulesForEditor(Long gid, Long pid) {

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Hotspot.find.where().eq("id", pid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Der Hotspot existiert nicht"));

                } else {

                    return ok(views.html.editor.editor_rulesinhotspot.render(Game.find.byId(gid),
                            Hotspot.find.byId(pid)));

                }

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getMissionRulesForEditor(Long gid, Long pid) {

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Mission.find.where().eq("id", pid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Mission existiert nicht"));

                } else {
                    return ok(views.html.editor.editor_rulesinmission.render(Game.find.byId(gid),
                            Mission.find.byId(pid)));
                }
            }
        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getContentRulesForEditor(Long gid, Long pid) {
        if (Game.find.where().eq("id", gid).findRowCount() != 1) {
            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));
        } else {
            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {
                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));
            } else {
                if (Content.find.where().eq("id", pid).findRowCount() != 1) {
                    return badRequest(views.html.norights.render("Dieser Content existiert nicht"));
                } else {
                    Game game = Game.find.byId(gid);
                    Content content = Content.find.byId(pid);
                    return ok(views.html.editor.editor_rulesincontent.render(game, content));
                }
            }
        }
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getMarkerAddScriptForEditor(Long gid, Long pid, boolean last) {

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Hotspot.find.where().eq("id", pid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Der Hotspot existiert nicht"));

                } else {

                    return ok(views.html.editor.leaflet_addmarker.render(Game.find.byId(gid), Hotspot.find.byId(pid),
                            last));

                }
            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getPartInfoForEditor(Long gid, Long pid) {

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {
                if (Part.find.where().eq("id", pid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

                } else {

                    return ok(views.html.editor.editor_allmissioninfo.render(Game.find.byId(gid),
                            Part.find.byId(pid).getMission()));

                }
            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getMissionInfoForEditor(Long gid, Long pid) {

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Mission.find.where().eq("id", pid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

                } else {

                    return ok(views.html.editor.editor_allmissioninfo.render(Game.find.byId(gid),
                            Mission.find.byId(pid)));

                }
            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getSceneInfoForEditor(Long gid, Long pid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Part.find.where().eq("id", pid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Szene existiert nicht"));

                } else {

                    return ok(views.html.editor.editor_allsceneinfo.render(Game.find.byId(gid),
                            Part.find.byId(pid).getScene()));

                }
            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getHotspotPopupContent(Long gid, Long hid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Hotspot.find.where().eq("id", hid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Der Hotspot existiert nicht"));

                } else {

                    return ok(
                            views.html.editor.editor_ahotspotpopup.render(Game.find.byId(gid), Hotspot.find.byId(hid)));

                }
            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getIdFromPart(Long gid, Long pid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Part.find.where().eq("id", pid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

                } else {

                    Part x = Part.find.byId(pid);
                    if (x.isScene()) {

                        return ok(String.valueOf(x.getScene().getId()));

                    } else {

                        return ok(String.valueOf(x.getMission().getId()));

                    }

                }
            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getIdFromSceneHotspot(Long gid, Long pid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Scene.find.where().eq("id", pid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

                } else {

                    Scene x = Scene.find.byId(pid);
                    if (!x.getHotspots().isEmpty()) {

                        return ok(String.valueOf(x.getHotspots().get(0).getId()));

                    } else {

                        return ok("NO");

                    }

                }
            }

        }
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getNewContentInContentForEditor(Long gid, Long cid, Long mid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Mission.find.where().eq("id", mid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Mission existiert nicht"));

                } else {

                    if (Content.find.where().eq("id", cid).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Der Inhalt existiert nicht"));

                    } else {

                        return ok(views.html.editor.editor_contentincontent.render(Game.find.byId(gid),
                                Mission.find.byId(mid), Content.find.byId(cid)));

                    }

                }
            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getRuleInMissionForEditor(Long gid, Long rtype, Long mid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Mission.find.where().eq("id", mid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Mission existiert nicht"));

                } else {

                    if (RuleType.find.where().eq("id", rtype).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Der Inhalt existiert nicht"));

                    } else {

                        return ok(views.html.editor.editor_rulesinmission.render(Game.find.byId(gid),
                                Mission.find.byId(mid)));

                    }

                }
            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getMissionSelectorForEditor(Long gid, String v) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                return ok(views.html.editor.editor_missionselector.render(Game.find.byId(gid), v));

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getHotspotSelectorForEditor(Long gid, String selectedValue) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                return ok(views.html.editor.editor_hotspotselector.render(Game.find.byId(gid), selectedValue));

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getContentListItemForEditor(Long gid, Long cid, Long mid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Mission.find.where().eq("id", mid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

                } else {

                    if (Content.find.where().eq("id", cid).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

                    } else {

                        return ok(views.html.editor.editor_acontentinmissionedit.render(Game.find.byId(gid),
                                Content.find.byId(cid), Mission.find.byId(mid)));

                    }

                }

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getContentInContentListForEditor(Long gid, Long cid, Long scid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Content.find.where().eq("id", scid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Der Sub-Content existiert nicht"));

                } else {

                    if (Content.find.where().eq("id", cid).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

                    } else {

                        return ok(views.html.editor.editor_acontentincontent.render(Game.find.byId(gid),
                                Content.find.byId(scid), Content.find.byId(cid)));

                    }

                }
            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getAllContentInMissionForEditor(Long gid, Long mid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Mission.find.where().eq("id", mid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Mission existiert nicht"));

                } else {

                    return ok(views.html.editor.editor_contentlistinmission.render(Game.find.byId(gid),
                            Mission.find.byId(mid)));

                }

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getContentInContentForEditor(Long gid, Long cid, Long mid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Mission.find.where().eq("id", mid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

                } else {

                    if (Content.find.where().eq("id", cid).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

                    } else {

                        return ok(views.html.editor.editor_contentincontent.render(Game.find.byId(gid),
                                Mission.find.byId(mid), Content.find.byId(cid)));

                    }

                }

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getContentInfoForEditor(Long gid, Long cid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Content.find.where().eq("id", cid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

                } else {

                    return ok(views.html.editor.editor_contentedit.render(Game.find.byId(gid), Content.find.byId(cid)));

                }

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getDevicesForEditor() {

        return ok(views.html.editor.editor_devices.render());

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getMissionActionInfoForEditor(Long gid, Long cid, String rtype, Integer z, Long mid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Action.find.where().eq("id", cid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Action existiert nicht"));

                } else {

                    if (Mission.find.where().eq("id", mid).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Die Mission existiert nicht"));

                    } else {

                        return ok(views.html.editor.editor_actioninmissionedit.render(Game.find.byId(gid),
                                Action.find.byId(cid), rtype, z, Mission.find.byId(mid)));

                    }
                }
            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getActionInfoForEditor(Long gid, Long cid, String rtype, Integer z) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Action.find.where().eq("id", cid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Action existiert nicht"));

                } else {

                    return ok(views.html.editor.editor_actionedit.render(Game.find.byId(gid), Action.find.byId(cid),
                            rtype, z));

                }

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getActionInRuleForEditor(Long gid, Long cid, Long rid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Action.find.where().eq("id", cid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Action existiert nicht"));

                } else {

                    if (Rule.find.where().eq("id", rid).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Die Regel existiert nicht"));

                    } else {

                        return ok(views.html.editor.editor_aactionintrigger.render(Game.find.byId(gid),
                                Action.find.byId(cid), Rule.find.byId(rid)));

                    }
                }

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getActionInActionAttributeForEditor(Long gid, Long aid, Long parent, Long atrid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Action.find.where().eq("id", aid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Action existiert nicht"));

                } else {

                    if (Action.find.where().eq("id", parent).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Die Parent-Action existiert nicht"));

                    } else {

                        if (AttributeType.find.where().eq("id", atrid).findRowCount() != 1) {

                            return badRequest(views.html.norights.render("Der AttributTyp existiert nicht"));

                        } else {

                            Action b = Action.find.byId(aid);

                            Action d = Action.find.byId(parent);
                            AttributeType a = AttributeType.find.byId(atrid);
                            if (d.getAttribute(a) == null) {

                                return badRequest(views.html.norights.render("Das Attribut existiert nicht"));

                            } else {

                                Attribute c = d.getAttribute(a);
                                return ok(
                                        views.html.editor.editor_aactioninattribute.render(Game.find.byId(gid), b, c));

                            }
                        }
                    }

                }

            }
        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getMissionRuleForEditor(Long gid, Long rid, Long rtype, Long mid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {
            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));
        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {
            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));
        } else {
            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {
                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));
            } else {
                if (Rule.find.where().eq("id", rid).findRowCount() != 1) {
                    return badRequest(views.html.norights.render("Die Regel existiert nicht"));
                } else {
                    if (RuleType.find.where().eq("id", rtype).findRowCount() != 1) {
                        return badRequest(views.html.norights.render("Die Regel existiert nicht"));
                    } else {
                        if (Mission.find.where().eq("id", mid).findRowCount() != 1) {
                            return badRequest(views.html.norights.render("Die Mission existiert nicht"));
                        } else {
                            return ok(views.html.editor.editor_aruleinmission.render(Game.find.byId(gid),
                                    Rule.find.byId(rid), RuleType.find.byId(rtype), Mission.find.byId(mid)));
                        }
                    }
                }
            }
        }
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getContentRuleForEditor(Long gid, Long rid, Long rtype, Long contentId) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {
            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));
        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {
            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));
        } else {
            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {
                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));
            } else {
                if (Rule.find.where().eq("id", rid).findRowCount() != 1) {
                    return badRequest(views.html.norights.render("Die Regel existiert nicht"));
                } else {
                    if (RuleType.find.where().eq("id", rtype).findRowCount() != 1) {
                        return badRequest(views.html.norights.render("Die Regel existiert nicht"));
                    } else {
                        if (Content.find.where().eq("id", contentId).findRowCount() != 1) {
                            return badRequest(views.html.norights.render("Die Mission existiert nicht"));
                        } else {
                            return ok(views.html.editor.editor_aruleincontent.render(Game.find.byId(gid),
                                    Rule.find.byId(rid), RuleType.find.byId(rtype), Content.find.byId(contentId)));
                        }
                    }
                }
            }
        }
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getRuleForEditor(Long gid, Long rid, Long rtype) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Rule.find.where().eq("id", rid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Regel existiert nicht"));

                } else {

                    if (RuleType.find.where().eq("id", rtype).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Die Regel existiert nicht"));

                    } else {

                        return ok(views.html.editor.editor_arule.render(Game.find.byId(gid), Rule.find.byId(rid),
                                RuleType.find.byId(rtype)));

                    }
                }

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getActionsForRuleForEditor(Long gid, Long rid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Rule.find.where().eq("id", rid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Regel existiert nicht"));

                } else {

                    return ok(
                            views.html.editor.editor_actionsintrigger.render(Game.find.byId(gid), Rule.find.byId(rid)));

                }

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getMapScript(Long gid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {
                return ok(views.html.editor.leafletMapfunction.render(Game.find.byId(gid)));

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getMapSearchScript(Long gid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {
                return ok(views.html.editor.geosearch.render(Game.find.byId(gid)));
            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result addMissionInSzene(Long gid, Long sid, Long mtype, String name) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Scene.find.where().eq("id", sid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Szene existiert nicht"));

                } else {

                    Scene x = Scene.find.byId(sid);

                    if (MissionType.find.where().eq("id", mtype).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Der Missionstyp existiert nicht"));

                    } else {

                        MissionType y = MissionType.find.byId(mtype);

                        Mission nm = y.createMe(name);
                        nm.save();

                        Part z = new Part(nm);
                        z.save();

                        x.addPart(z);
                        x.update();

                        return ok(String.valueOf(z.getId()));
                    }

                }

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result addMissionInGame(Long gid, Long mtype, String name) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (MissionType.find.where().eq("id", mtype).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Der Missionstyp existiert nicht"));

                } else {

                    MissionType y = MissionType.find.byId(mtype);
                    try {

                        name = URLDecoder.decode(name, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        // Will it ever be thrown?
                    }

                    if (name.equals("Hier Namen eingeben")) {
                        int i = g.getMissions().size() + 1;
                        name = "Neue Seite (" + i + ")";
                    }

                    Mission nm = y.createMe(name);
                    nm.save();

                    Part z = new Part(nm);
                    z.save();

                    g.addPart(z);
                    g.update();

                    return ok(String.valueOf(z.getId()));
                }

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result addMenuItemInGame(Long gid, String title) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                MenuItem z = new MenuItem(title);

                z.save();

                g.addMenuItem(z);
                g.update();

                return ok(String.valueOf(z.getId()));

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result duplicateMissionInGame(Long gid, Long mid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        GameCopyContext copyContext = new GameCopyContext();

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (Mission.find.where().eq("id", mid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Mission existiert nicht"));

                } else {

                    Mission y = Mission.find.byId(mid);

                    Mission nm = y.copyMe("Copy of " + y.getName(), copyContext, false);
                    nm.save();

                    Part z = new Part(nm);
                    z.save();

                    g.addPart(z);
                    g.update();

                    return ok(String.valueOf(z.getId()));
                }

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result duplicateHotspotInGame(Long gid, Long mid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (Hotspot.find.where().eq("id", mid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Der hotspot existiert nicht"));

                } else {
                    Hotspot y = Hotspot.find.byId(mid);

                    Hotspot nm = y.copyMe(new GameCopyContext());
                    nm.save();

                    g.addHotspot(nm);
                    g.update();
                    return ok(String.valueOf(nm.getId()));
                }

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result duplicateSubRuleInRule(Long gid, Long rid, Long srid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        } else {

            if (Rule.find.where().eq("id", rid).findRowCount() != 1) {

                return badRequest(views.html.norights.render("Die Hauptregel existiert nicht"));

            } else {

                Rule r = Rule.find.byId(rid);

                if (Rule.find.where().eq("id", srid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Unterregel existiert nicht"));

                } else {

                    Rule sr = Rule.find.byId(srid);

                    Rule nr = sr.copyMe(new GameCopyContext());
                    nr.save();

                    r.addSubRule(nr);
                    r.update();

                }

                return redirect(routes.Editor.getEditor(gid));

            }
        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result duplicateSceneInGame(Long gid, Long sid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        GameCopyContext copyContext = new GameCopyContext();

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (Scene.find.where().eq("id", sid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Mission existiert nicht"));

                } else {

                    Scene y = Scene.find.byId(sid);

                    Scene nm = y.copyMe("Copy of " + y.getName(), copyContext);
                    nm.save();

                    Part z = new Part(nm);
                    z.save();

                    g.addPart(z);
                    g.update();

                }

                return redirect(routes.Editor.getEditor(gid));
            }
        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result addHotspotInGame(Long gid, Long htype, String lon, String lat, String n) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (HotspotType.find.where().eq("id", htype).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Der Missionstyp existiert nicht"));

                } else {

                    HotspotType y = HotspotType.find.byId(htype);

                    Hotspot nm = y.createMe(Float.valueOf(lon), Float.valueOf(lat), n);
                    nm.save();

                    g.addHotspot(nm);
                    g.update();

                    return ok(String.valueOf(nm.getId()));

                }
            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result addSceneInGame(Long gid, Long stype, String name) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (SceneType.find.where().eq("id", stype).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Der Szenentyp existiert nicht"));

                } else {

                    if (name.equals("Hier Namen eingeben")) {

                        int i = g.getMissions().size() + 1;
                        name = "Neuer Ordner (" + i + ")";

                    }

                    SceneType y = SceneType.find.byId(stype);

                    Scene nm = y.createMe(name, g);

                    Part z = new Part(nm);
                    z.save();

                    g.addPart(z);
                    g.update();

                    return ok(String.valueOf(z.getId()));

                }

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result addNewSceneTypeInGameTypeFromGame(Long gid, Long gtid, String name) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (GameType.find.where().eq("id", gtid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Der Gametyp existiert nicht"));
                } else {
                    GameType gt = GameType.find.byId(gtid);
                    SceneType s = new SceneType(name);
                    s.save();
                    s.addDefaultsFromGame(g);
                    s.update();
                    gt.addPossibleSceneType(s);
                    gt.update();

                    return ok(views.html.gteditor.gteditor_newscene.render(s, gt, ""));
                }
            }
        }
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result deleteSceneTypeInGameType(Long sid, Long gtid) {
        if (SceneType.find.where().eq("id", sid).findRowCount() != 1) {
            return badRequest(views.html.norights.render("Der SceneType existiert nicht"));
        } else {
            SceneType s = SceneType.find.byId(sid);

            if (GameType.find.where().eq("id", gtid).findRowCount() != 1) {
                return badRequest(views.html.norights.render("Der Gametyp existiert nicht"));
            } else {
                GameType gt = GameType.find.byId(gtid);
                gt.deletePossibleSceneType(s);
                gt.update();
                return ok("Deleted");
            }
        }
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result addSceneInGameWithPoints(Long gid, Long stype, String name, String lon, String lat) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (SceneType.find.where().eq("id", stype).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Der Szenentyp existiert nicht"));

                } else {

                    if (name.equals("Hier Namen eingeben")) {

                        int i = g.getMissions().size() + 1;
                        name = "Neuer Ordner (" + i + ")";

                    }

                    SceneType y = SceneType.find.byId(stype);

                    Scene nm = y.createMe(name, Float.valueOf(lon), Float.valueOf(lat), g);

                    Part z = new Part(nm);
                    z.save();

                    g.addPart(z);
                    g.update();

                    return ok(String.valueOf(z.getId()));
                }

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result addContentInMission(Long gid, Long mission, Long ctype, String name) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (ContentType.find.where().eq("id", ctype).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Der Inhaltstyp existiert nicht"));

                } else {

                    if (Mission.find.where().eq("id", mission).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Die Mission existiert nicht"));

                    } else {

                        Mission mi = Mission.find.byId(mission);

                        ContentType y = ContentType.find.byId(ctype);

                        Content nm = y.createMe(name);

                        mi.addContent(nm);
                        mi.update();

                        return ok(String.valueOf(nm.getId()));

                    }
                }

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result addActionInRule(Long gid, Long rid, Long atype) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (ActionType.find.where().eq("id", atype).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Der Action-Typ existiert nicht"));

                } else {

                    if (Rule.find.where().eq("id", rid).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Die Rule existiert nicht"));

                    } else {
                        Rule r = Rule.find.byId(rid);

                        ActionType y = ActionType.find.byId(atype);

                        Action a = new Action(y.getName(), y);
                        a.save();

                        r.addAction(a);
                        r.update();
                        return ok(String.valueOf(a.getId()));

                    }

                }

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result moveActionInRule(Long gid, Long rid, Long aid, String direction) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (Action.find.where().eq("id", aid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Der Action existiert nicht"));

                } else {

                    if (Rule.find.where().eq("id", rid).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Die Rule existiert nicht"));

                    } else {

                        Rule r = Rule.find.byId(rid);

                        Action a = Action.find.byId(aid);

                        if (direction.equals("left")) {
                            r.action_left(a);
                        } else if (direction.equals("right")) {
                            r.action_right(a);
                        }

                        return ok("synced");

                    }

                }

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result addActionInActionAttribute(Long gid, Long aid, Long atrid, Long atype) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (ActionType.find.where().eq("id", atype).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Der Action-Typ existiert nicht"));

                } else {

                    if (Action.find.where().eq("id", aid).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Die Action existiert nicht"));

                    } else {

                        if (AttributeType.find.where().eq("id", atrid).findRowCount() != 1) {

                            return badRequest(views.html.norights.render("Der Attributtyp existiert nicht"));

                        } else {
                            AttributeType atr = AttributeType.find.byId(atrid);

                            ActionType y = ActionType.find.byId(atype);

                            Action a = new Action(y.getName(), y);
                            a.save();

                            Action b = Action.find.byId(aid);

                            if (b.getAttribute(atr) == null) {

                                Attribute c = new Attribute(atr);
                                c.save();
                                c.addAction(a);
                                c.update();

                                b.setAttribute(c);
                                b.update();

                            } else {

                                Attribute c = b.getAttribute(atr);
                                c.addAction(a);
                                c.update();

                            }
                            return ok(String.valueOf(a.getId()));

                        }

                    }

                }

            }
        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result addConditionInRule(Long gid, Long rid, String text) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (Rule.find.where().eq("id", rid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Rule existiert nicht"));

                } else {

                    Rule r = Rule.find.byId(rid);

                    Condition x = new Condition(true, text);
                    x.save();
                    boolean done = false;

                    r.addCondition(x);
                    r.update();

                }

            }

        }

        return redirect(routes.Editor.getEditor(gid));

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result editConditionInRule(Long gid, Long rid, String text) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        String help = "Error";

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);
                if (Rule.find.where().eq("id", rid).findRowCount() != 1) {

                    System.out.println("Die Rule existiert nicht");

                } else {

                    Rule r = Rule.find.byId(rid);

                    for (Condition c : r.getConditions()) {

                        System.out.println(String.valueOf(c.isFull()));
                        if (c.isFull()) {
                            c.setFull(text);

                            System.out.println(text);
                            c.update();
                            help = "synced";
                        } else {

                            Condition x = new Condition(true, text);
                            x.save();

                            r.addCondition(x);
                            r.update();

                        }

                    }

                }

            }

        }

        return ok(help);

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result deleteActionFromRule(Long gid, Long rid, Long aid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (Action.find.where().eq("id", aid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Aktion existiert nicht"));

                } else {

                    if (Rule.find.where().eq("id", rid).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Die Rule existiert nicht"));

                    } else {

                        Rule r = Rule.find.byId(rid);

                        Action a = Action.find.byId(aid);

                        if (r.isSimpleRule()) {

                            r.deleteAction(a);

                            r.update();

                        } else {

                            if (r.hasSubRules()) {

                                for (Rule ar : r.getSubRules()) {

                                    ar.deleteAction(a);
                                    ar.update();

                                }

                            }

                        }

                    }
                }

                return redirect(routes.Editor.getEditor(gid));

            }

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result deleteActionFromAttribute(Long gid, Long atrid, Long aid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (Action.find.where().eq("id", aid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Aktion existiert nicht"));

                } else {

                    if (Attribute.find.where().eq("id", atrid).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Das Attribut existiert nicht"));

                    } else {

                        Attribute at = Attribute.find.byId(atrid);

                        Action a = Action.find.byId(aid);

                        if (at.getActions() != null) {

                            at.removeAction(a);
                            at.update();

                        }
                    }

                }

            }
        }

        return ok("synced");

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result deleteActionFromMenuItem(Long gid, Long atrid, Long aid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (Action.find.where().eq("id", aid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Aktion existiert nicht"));

                } else {

                    if (MenuItem.find.where().eq("id", atrid).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Das Menu-Item existiert nicht"));

                    } else {

                        MenuItem mi = MenuItem.find.byId(atrid);

                        Action a = Action.find.byId(aid);

                        if (mi.getOnSelect() != null) {
                            if (mi.getOnSelect().getActions() != null) {

                                mi.getOnSelect().deleteAction(a);
                                mi.getOnSelect().update();

                            }

                        }
                    }

                }

            }
        }

        return ok("synced");

    }

    /**
     * @param gid
     * @param mission
     * @param rtype
     * @return just the id of the newly added rule object (db id)
     */
    @Restrict(@Group(Application.USER_ROLE))
    public static Result addRuleInMission(Long gid, Long mission, Long rtype) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {
            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));
        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {
            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));
        } else {
            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {
                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));
            } else {
                Game g = Game.find.byId(gid);

                if (RuleType.find.where().eq("id", rtype).findRowCount() != 1) {
                    return badRequest(views.html.norights.render("Der Rule-Typ existiert nicht"));
                } else {
                    if (Mission.find.where().eq("id", mission).findRowCount() != 1) {
                        return badRequest(views.html.norights.render("Die Mission existiert nicht"));
                    } else {
                        Mission mi = Mission.find.byId(mission);
                        RuleType y = RuleType.find.byId(rtype);
                        Condition c = new Condition(y.getTrigger());
                        c.save();
                        Rule nm = new Rule();
                        nm.addCondition(c);
                        nm.save();

                        Rule z = new Rule();
                        z.save();
                        nm.addSubRule(z);
                        nm.update();

                        mi.addRule(nm);
                        mi.update();

                        return ok(String.valueOf(z.getId()));
                    }
                }
            }
        }
    }

    /**
     * @param gid
     * @param content
     * @param rtype
     * @return just the id of the newly added rule object to the given content (db id)
     */
    @Restrict(@Group(Application.USER_ROLE))
    public static Result addRuleInContent(Long gid, Long content, Long rtype) {
        System.out.println("addRuleInContent(" + gid + ", " + content + ", " + rtype);
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {
            System.out.println("addRuleInContent: Schreibrechte fehlen");

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));
        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {
            System.out.println("addRuleInContent: Spiel existiert nicht");
            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));
        } else {
            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {
                System.out.println("addRuleInContent: Schreibrechte fehlen 2");
                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));
            } else {
                Game g = Game.find.byId(gid);

                if (RuleType.find.where().eq("id", rtype).findRowCount() != 1) {
                    System.out.println("addRuleInContent: Rule-Typ existiert nicht");
                    return badRequest(views.html.norights.render("Der Rule-Typ existiert nicht"));
                } else {
                    if (Content.find.where().eq("id", content).findRowCount() != 1) {
                        System.out.println("addRuleInContent: Content existiert nicht");
                        return badRequest(views.html.norights.render("Der Content existiert nicht"));
                    } else {
                        Content ct = Content.find.byId(content);
                        RuleType y = RuleType.find.byId(rtype);
                        Condition c = new Condition(y.getTrigger());
                        c.save();
                        Rule nm = new Rule();
                        nm.addCondition(c);
                        nm.save();

                        Rule z = new Rule();
                        z.save();
                        nm.addSubRule(z);
                        nm.update();

                        ct.addRule(nm);
                        ct.update();

                        return ok(String.valueOf(z.getId()));
                    }
                }
            }
        }
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result addSubRuleInRule(Long gid, Long rule) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (Rule.find.where().eq("id", rule).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Rule existiert nicht"));

                } else {

                    Rule y = Rule.find.byId(rule);

                    Rule z = new Rule();
                    z.save();
                    y.addSubRule(z);
                    y.update();

                }

                return redirect(routes.Editor.getEditor(gid));

            }
        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result addSubActionInAction(Long gid, Long action, Long atype) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (Action.find.where().eq("id", action).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Action existiert nicht"));

                } else {

                    if (ActionType.find.where().eq("id", atype).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Die Action existiert nicht"));

                    } else {

                        Action y = Action.find.byId(action);
                        ActionType at = ActionType.find.byId(atype);

                        Action z = new Action(at.getName(), at);
                        z.save();
                        y.addSubAction(z);
                        y.update();

                    }

                }

                return redirect(routes.Editor.getEditor(gid));

            }
        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result deleteSubRuleFromRule(Long gid, Long rid, Long srid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return ok("Das Spiel existiert nicht");

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (Rule.find.where().eq("id", rid).findRowCount() != 1) {

                    return ok("Die Rule existiert nicht");

                } else {

                    if (Rule.find.where().eq("id", srid).findRowCount() != 1) {

                        return ok("Die Sub-Rule existiert nicht");

                    } else {

                        Rule y = Rule.find.byId(rid);

                        Rule z = Rule.find.byId(srid);

                        try {

                            y.deleteSubRule(z);

                            y.update();
                        } catch (RuntimeException e) {
                            return ok("Fehler #4784 beim Löschen der Regel.");
                        }

                        try {
                            z.removeMe();
                            z.delete();
                        } catch (RuntimeException e) {
                            System.out.println(
                                    "Couldn't delete Subrule-Object. Will remain in database with id " + z.getId());
                        }

                    }

                    return ok("synced");

                }

            }
        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result addRuleInHotspot(Long gid, Long hotspot, Long rtype) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (RuleType.find.where().eq("id", rtype).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Der Rule-Typ existiert nicht"));

                } else {

                    if (Hotspot.find.where().eq("id", hotspot).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Die Mission existiert nicht"));

                    } else {

                        Hotspot hs = Hotspot.find.byId(hotspot);

                        RuleType y = RuleType.find.byId(rtype);

                        Condition c = new Condition(y.getTrigger());
                        c.save();
                        Rule nm = new Rule();
                        nm.addCondition(c);
                        nm.save();

                        Rule z = new Rule();
                        z.save();
                        nm.addSubRule(z);
                        nm.update();

                        hs.addRule(nm);
                        hs.update();

                        return ok(String.valueOf(z.getId()));

                    }
                }
            }
        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result addContentInContent(Long gid, Long cid, Long ctype, String name) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (ContentType.find.where().eq("id", ctype).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Der Inhaltstyp existiert nicht"));

                } else {

                    if (Content.find.where().eq("id", cid).findRowCount() != 1) {

                        return badRequest(views.html.norights.render("Der Inhalt existiert nicht"));

                    } else {

                        Content co = Content.find.byId(cid);

                        ContentType y = ContentType.find.byId(ctype);

                        Content nm = y.createMe(name);

                        co.addSubContent(nm);
                        co.update();

                        return ok(String.valueOf(nm.getId()));

                    }
                }
            }
        }

    }

    // / EDITOR SETTERS: NAMES

    @Restrict(@Group(Application.USER_ROLE))
    public static Result setSceneName(Long gid, Long sid, String name) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        } else {

            String help = "Error!";

            if (Scene.find.where().eq("id", sid).findRowCount() != 1) {

                help = "Scene " + sid + "not found";
            } else {

                Scene c = Scene.find.byId(sid);

                c.setName(name);
                c.update();

                help = "synced";

            }

            return ok(help);

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result setGameName(Long gid, String name) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        String help = "Error!";

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            help = "Game " + gid + "not found";
        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game c = Game.find.byId(gid);
                c.setName(name);
                c.update();

                help = "synced";

            }

        }

        return ok(help);

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result setHotspotName(Long gid, Long cid, String name) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        } else {

            String help = "Error!";

            if (Hotspot.find.where().eq("id", cid).findRowCount() != 1) {

                help = "Hotspot " + cid + "not found";
            } else {

                Hotspot c = Hotspot.find.byId(cid);

                c.setName(name);
                c.update();

                help = "synced";

            }

            return ok(help);

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result setMenuItemName(Long gid, Long cid, String name) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        } else {
            String help = "Error!";

            if (MenuItem.find.where().eq("id", cid).findRowCount() != 1) {

                help = "MenuItem " + cid + "not found";
            } else {

                MenuItem c = MenuItem.find.byId(cid);

                c.setTitle(name);
                c.update();

                help = "synced";

            }

            return ok(help);

        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result setContentName(Long gid, Long cid, String name) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        } else {
            String help = "Error!";

            if (Content.find.where().eq("id", cid).findRowCount() != 1) {

                help = "Content " + cid + "not found";
            } else {

                Content c = Content.find.byId(cid);

                c.setName(name);
                c.update();

                help = "synced";

            }

            return ok(help);
        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result setContentValue(Long gid, Long cid, String name) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        } else {
            String help = "Error!";

            if (Content.find.where().eq("id", cid).findRowCount() != 1) {

                help = "Content " + cid + "not found";
            } else {

                Content c = Content.find.byId(cid);

                c.setContent(name);
                c.update();

                help = "synced";

            }

            return ok(help);
        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result setHotspotCoords(Long gid, Long hid, String lon, String lat) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        } else {
            String help = "Error!";

            if (Hotspot.find.where().eq("id", hid).findRowCount() != 1) {

                help = "Hotspot " + hid + " nicht gefunden.";

            } else {

                Hotspot h = Hotspot.find.byId(hid);

                h.setLongitude(Float.valueOf(lon));
                h.setLatitude(Float.valueOf(lat));
                h.update();

                System.out.println(Float.valueOf(lon) + " " + Float.valueOf(lat));
                help = "synced";
            }

            return ok(help);
        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result setMissionName(Long gid, Long mid, String name) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        } else {
            String help = "Error!";

            if (Mission.find.where().eq("id", mid).findRowCount() != 1) {

                help = "Mission " + mid + " nicht gefunden.";

            } else {

                Mission m = Mission.find.byId(mid);

                try {

                    name = URLDecoder.decode(name, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // Will it ever be thrown?
                }

                m.setName(name);
                m.update();

                help = "synced";
            }

            return ok(help);
        }
    }

    // / EDITOR SETTERS: UPLOADS

    @Restrict(@Group(Application.USER_ROLE))
    public static Result uploadAttributeFile(Long gid, String type, Long hid, Long cid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        } else {
            String help = "Error!";

            if (AttributeType.find.where().eq("id", cid).findRowCount() != 1) {

                help = "AttributeType " + cid + "not found";
            } else {

                AttributeType c = AttributeType.find.byId(cid);

                // // SAVE FILE
                try {
                    System.out.println(request().body().asText());
                    MultipartFormData body = request().body().asMultipartFormData();

                    FilePart resourceFile = null;
                    List<FilePart> files = body.getFiles();

                    if (!files.isEmpty()) {

                        resourceFile = files.get(0);

                    } else {

                        return ok("No file uploaded");
                    }

                    if (resourceFile != null) {

                        String fileName = resourceFile.getFilename();
                        String destfilename = fileName.replaceAll(" ", "_");
                        destfilename = destfilename.toLowerCase();
                        destfilename = destfilename.replaceAll("ä", "ae");
                        destfilename = destfilename.replaceAll("ö", "oe");
                        destfilename = destfilename.replaceAll("ü", "ue");
                        destfilename = destfilename.replaceAll("ß", "ss");

                        File file = resourceFile.getFile();

                        int i = 0;
                        boolean exists = true;
                        while (exists == true) {

                            i++;
                            File f = new File(
                                    "public/uploads/" + Application.getLocalPortal().getId() + "/editor/" + gid + "/",
                                    i + "_" + destfilename);
                            if (!f.exists()) {
                                exists = false;
                            }

                        }

                        File tosave = new File(
                                "public/uploads/" + Application.getLocalPortal().getId() + "/editor/" + gid + "/",
                                i + "_" + destfilename);

                        try {

                            FileUtils.moveFile(file, tosave);

                            String dest = Global.SERVER_URL_2 + "/uploadedassets/"
                                    + Application.getLocalPortal().getId() + "/editor/" + gid + "/" + i + "_"
                                    + destfilename;

                            if (type.equals("hotspot")) {

                                if (Hotspot.find.where().eq("id", hid).findRowCount() != 1) {

                                    help = "Hotspot " + hid + "not found";
                                } else {

                                    Hotspot b = Hotspot.find.byId(hid);

                                    Attribute atts = new Attribute(c);
                                    atts.setValue(dest);
                                    atts.save();
                                    b.setAttribute(atts);

                                    b.update();

                                    help = "" + atts.getId();

                                }

                            } else if (type.equals("mission")) {

                                if (Mission.find.where().eq("id", hid).findRowCount() != 1) {

                                    help = "Mission " + hid + "not found";
                                } else {

                                    Mission b = Mission.find.byId(hid);

                                    Attribute atts = new Attribute(c);
                                    atts.setValue(dest);
                                    atts.save();
                                    b.setAttribute(atts);

                                    b.update();

                                    help = "" + atts.getId();

                                }

                            } else if (type.equals("content")) {

                                if (Content.find.where().eq("id", hid).findRowCount() != 1) {

                                    help = "Content " + hid + "not found";
                                } else {

                                    Content b = Content.find.byId(hid);

                                    Attribute atts = new Attribute(c);
                                    atts.setValue(dest);
                                    atts.save();
                                    b.setAttribute(atts);

                                    b.update();

                                    help = "" + atts.getId();

                                }

                            } else if (type.equals("action")) {

                                if (Action.find.where().eq("id", hid).findRowCount() != 1) {

                                    help = "Action " + hid + "not found";
                                } else {

                                    Action b = Action.find.byId(hid);

                                    Attribute atts = new Attribute(c);
                                    atts.setValue(dest);
                                    atts.save();
                                    b.setAttribute(atts);

                                    b.update();

                                    help = "" + atts.getId();

                                }

                            } else if (type.equals("scene")) {

                                if (Scene.find.where().eq("id", hid).findRowCount() != 1) {

                                    help = "Scene " + hid + "not found";
                                } else {

                                    Scene b = Scene.find.byId(hid);

                                    if (b.getAttribute(c) != null) {

                                        Attribute a = b.getAttribute(c);
                                        a.setValue(dest);
                                        a.update();

                                        if (a.hasLink()) {

                                            a.getLink().setObjectValue(dest);

                                        }

                                    } else {

                                        Attribute atts = new Attribute(c);
                                        atts.setValue(dest);
                                        atts.save();
                                        b.setAttribute(atts);

                                        b.update();

                                        help = "" + atts.getId();

                                    }

                                }

                            }

                        } catch (IOException ioe) {
                            return ok("Error!");
                        }

                    }
                } catch (RuntimeException e) {
                    System.out.println("Problem uploading file.");
                    e.printStackTrace();

                }

            }

            return ok(help);
        }
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result getFilePreview(Long gid, Long attr) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Attribute.find.where().eq("id", attr).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Action existiert nicht"));

                } else {

                    Attribute a = Attribute.find.byId(attr);

                    return ok(views.html.editor.editor_afilepreview.render(Game.find.byId(gid), attr, a.getValue()));

                }
            }
        }

    }

    // / EDITOR SETTERS: ATTRIBUTES

    @Restrict(@Group(Application.USER_ROLE))
    public static Result setGameAttribute(Long gid, Long atype, String value) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        String help = "Error!";

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return ok("Game not found");

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (AttributeType.find.where().eq("id", atype).findRowCount() != 1) {

                    return ok("Attribute not found");

                } else {

                    AttributeType at = AttributeType.find.byId(atype);

                    Attribute a = at.createMe();
                    a.save();
                    a.setValue(value);
                    a.update();

                    g.setAttribute(a);
                    g.update();

                    if (g.getAttributeValue(at).equals(value)) {
                        help = "synced";
                    }

                }
            }
        }

        return ok(help);

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result setContentAttribute(Long gid, Long cid, Long atype, String value) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        String help = "Error!";

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return ok("Game not found");

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Content.find.where().eq("id", cid).findRowCount() != 1) {

                    return ok("Content not found");

                } else {

                    Content c = Content.find.byId(cid);

                    if (AttributeType.find.where().eq("id", atype).findRowCount() != 1) {

                        return ok("Attribute not found");

                    } else {

                        AttributeType at = AttributeType.find.byId(atype);

                        Attribute a = at.createMe();
                        a.save();
                        a.setValue(value);
                        a.update();

                        c.setAttribute(a);
                        c.update();

                        if (c.getAttributeValue(at).equals(value)) {
                            help = "synced";
                        }

                    }
                }
            }
        }

        return ok(help);

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result setMissionAttribute(Long gid, Long mid, Long atype, String value) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        String help = "Error!";

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return ok("Game not found");

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Mission.find.where().eq("id", mid).findRowCount() != 1) {

                    return ok("Content not found");

                } else {

                    Mission c = Mission.find.byId(mid);

                    if (AttributeType.find.where().eq("id", atype).findRowCount() != 1) {

                        return ok("Attribute not found");

                    } else {

                        AttributeType at = AttributeType.find.byId(atype);

                        Attribute a = at.createMe();
                        a.save();
                        a.setValue(value);
                        a.update();

                        c.setAttribute(a);
                        c.update();

                        if (c.getAttributeValue(at).equals(value)) {
                            help = "synced";
                        }

                    }
                }
            }
        }

        return ok(help);

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result setHotspotAttribute(Long gid, Long mid, Long atype, String value) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        String help = "Error!";

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return ok("Game not found");

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Hotspot.find.where().eq("id", mid).findRowCount() != 1) {

                    return ok("Content not found");

                } else {

                    Hotspot c = Hotspot.find.byId(mid);

                    if (AttributeType.find.where().eq("id", atype).findRowCount() != 1) {

                        return ok("Attribute not found");

                    } else {

                        AttributeType at = AttributeType.find.byId(atype);

                        Attribute a = at.createMe();
                        a.save();
                        a.setValue(value);
                        a.update();

                        c.setAttribute(a);
                        c.update();

                        if (c.getAttributeValue(at).equals(value)) {
                            help = "synced";
                        }

                    }
                }
            }
        }

        return ok(help);

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result setActionAttribute(Long gid, Long mid, Long atype, String value) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        String help = "Error!";

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return ok("Game not found");

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {
                if (Action.find.where().eq("id", mid).findRowCount() != 1) {

                    return ok("Content not found");

                } else {

                    Action c = Action.find.byId(mid);

                    if (AttributeType.find.where().eq("id", atype).findRowCount() != 1) {

                        return ok("Attribute not found");

                    } else {

                        AttributeType at = AttributeType.find.byId(atype);

                        Attribute a = at.createMe();
                        a.save();
                        a.setValue(value);
                        a.update();

                        c.setAttribute(a);
                        c.update();

                        if (c.getAttributeValue(at).equals(value)) {
                            help = "synced";
                        }

                    }
                }
            }
        }

        return ok(help);

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result setSceneAttribute(Long gid, Long mid, Long atype, String value) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        String help = "Error!";

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return ok("Game not found");

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Scene.find.where().eq("id", mid).findRowCount() != 1) {

                    return ok("Scene not found");

                } else {

                    Scene c = Scene.find.byId(mid);

                    if (AttributeType.find.where().eq("id", atype).findRowCount() != 1) {

                        return ok("Attribute not found");

                    } else {

                        AttributeType at = AttributeType.find.byId(atype);

                        if (c.getAttribute(at) != null) {

                            if (at.getFileType().equals("QuoteStringTextArea")) {

                                value = value.replaceAll("\n", "<br>");

                            }

                            Attribute a = c.getAttribute(at);
                            a.setValue(value);
                            a.update();

                            if (a.hasLink()) {
                                if (a.getLink().getObjectType().equals("Attribute")) {
                                    System.out.println("Attribute id: " + a.getId() + ", name: " + a.getName() +
                                            ", has link to " + a.getLink().getAttribute().getId());
                                } else {
                                    System.out.println("Attribute id: " + a.getId() + ", name: " + a.getName());
                                }
                                a.getLink().setObjectValue(value);

                            } else {
                                System.out.println(a.getName() + " has no link");
                            }

                        } else {

                            Attribute a = at.createMe();
                            a.save();
                            a.setValue(value);
                            a.update();

                            c.setAttribute(a);
                            c.update();

                        }

                        if (at.getFileType().equals("QuoteString") || at.getFileType().equals("QuoteStringTextArea")) {

                            help = "synced";

                        } else {
                            if (c.getAttributeValue(at).equals(value)) {
                                help = "synced";
                            }

                        }

                    }
                }
            }
        }

        return ok(help);

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result setMenuItemAttribute(Long gid, Long mid, String a, String value) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        String help = "Error!";

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return ok("Game not found");

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (MenuItem.find.where().eq("id", mid).findRowCount() != 1) {

                    return ok("MenuItem not found");

                } else {

                    MenuItem c = MenuItem.find.byId(mid);

                    if (a.equals("show")) {

                        c.setShow(value);
                        help = "synced";

                    } else if (a.equals("activity")) {

                        if (value.equals("true")) {
                            c.setActivity(true);
                            help = "synced";
                        } else {
                            c.setActivity(false);
                            help = "synced";
                        }

                    } else if (a.equals("priority")) {

                        c.setPriority(Integer.valueOf(value));
                        help = "synced";

                    } else if (a.equals("icon")) {

                        c.setIcon(value);
                        help = "synced";

                    } else if (a.equals("showText")) {

                        if (value.equals("true")) {
                            c.setShowText(true);
                            help = "synced";
                        } else {
                            c.setShowText(false);
                            help = "synced";
                        }

                    }

                    c.update();

                }
            }
        }

        return ok(help);

    }

    // / EDITOR: XML

    @Restrict(@Group(Application.USER_ROLE))
    public static Result createXML(Long gid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));
        }


        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));
        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));
            }
        }

        // Everything ok, we can start to do the work:

        ProviderPortal p = Application.getLocalPortal();

        boolean doit = true;
        String help1 = "false";
        if (p.getContentHtmlParameter("general.games.adminshavetopublish") != null) {

            help1 = p.getContentHtmlParameter("general.games.adminshavetopublish");
        }
        if (help1.equals("true")) {
            if (Global.securityGuard.hasAdminRightsOnPortal(Application.getLocalUser(session())) == false) {
                doit = false;
            }
        }

        Game c = Game.find.byId(gid);

        if (doit) {
            if (p.getGame(c) != null) {
                p.getGame(c).setVisibility(true);
                p.getGame(c).update();
                p.update();
            }

            Game g = Game.find.byId(gid);
            g.userlastupdated = Application.getLocalUser().getId();
            g.lastUpdate = new Date();
            g.createXML();

            // a game has been published, hence we update the public games list file:
            p.exportPublicGamesJson();
        } else {

            String help = "false";
            if (p.getContentHtmlParameter("general.games.adminsgetnotified") != null) {

                help = p.getContentHtmlParameter("general.games.adminsgetnotified");
            }
            if (help.equals("true")) {

                for (ProviderUsers au : p.getUsers()) {

                    if (au.getRights().equals("admin")) {
                        System.out.println("trying to send an email to admin: " + au.getUser().getName());
                        final MyUsernamePasswordAuthProvider provider = MyUsernamePasswordAuthProvider
                                .getProvider();
                        String linkToUserRightsTable = Global.SERVER_URL + p.id + "/portal/rights/"
                                + p.id;
                        String text = "Auf dem Geoquest Portal '" + p.name
                                + "' wurde eine neue Quest mit dem Namen'" + c.getName() + "' und der ID "
                                + c.getId()
                                + " erstellt. Solltest nur du die Berechtigung haben, diese Quest zu veröffentlichen, kümmere dich bitte darum: "
                                + linkToUserRightsTable;

                        String html = "Auf dem Geoquest Portal '" + p.name
                                + "' wurde eine neue Quest mit dem Namen'" + c.getName() + "' und der ID "
                                + c.getId()
                                + " erstellt.<br/><br/> Solltest <b>nur du</b> die Berechtigung haben, diese Quest zu veröffentlichen, kümmere dich bitte darum: "
                                + "<a href=\"" + linkToUserRightsTable + "\">" + linkToUserRightsTable + "</a>";
                        provider.sendEmailToUser(au.getUser(), "Neue Quest: " + c.getName(), text, html);
                    }
                }
            }
        }

        return ok("synced");
    }

    @BodyParser.Of(Xml.class)
    public static Result createXMLForWeb(Long gid, Long mid) {

        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                if (Mission.find.where().eq("id", mid).findRowCount() != 1) {

                    return badRequest(views.html.norights.render("Die Mission existiert nicht"));

                } else {

                    Game g = Game.find.byId(gid);
                    Mission m = Mission.find.byId(mid);
                    String result = g.createXMLForWeb(m);

                    return ok(result);
                }

            }
        }

    }

    @BodyParser.Of(Xml.class)
    public static Result getXMLForClient(Long gid) {
        if (Game.find.where().eq("id", gid).findRowCount() != 1) {
            return badRequest(views.html.norights.render("Das Spiel existiert nicht"));
        } else {
            Game g = Game.find.byId(gid);
            if (g.zip == null) {
                return ok("<error>No Pages defined</error>");
            }

            File gameXMLFile = new File(g.zip);
            if (!gameXMLFile.exists() || !gameXMLFile.canRead()) {
                return ok("<error>No Pages defined</error>");
            }

            if (gameXMLFile.getName().endsWith(".zip")) {
                g.createXML();
                gameXMLFile = new File(g.zip);
            }

            if ("game.xml".equals(gameXMLFile.getName())) {
                // Mission m = g.getFirstMission();
                // String result = g.createXMLForWeb(m); // return file instead

                response().setContentType("text/xml");
                response().setHeader("Content-disposition", "attachment; filename=game.xml");
                response().setHeader("Content-Length", gameXMLFile.length() + "");

                return ok(gameXMLFile);
            } else {
                return ok("<error>No Pages defined</error>");
            }
        }
    }

    @BodyParser.Of(Xml.class)
    public static Result getTestXMLForClient(String deviceid) {

        if (Device.find.where().eq("deviceid", deviceid).findRowCount() != 1) {

            return ok("<error>Invalid device id</error>");

        } else {

            Device d = Device.find.where().eq("deviceid", deviceid).findUnique();

            if (d.quest == "") {

                return ok("");
            } else {

                File gameXMLFile = new File(d.quest);

                if (!gameXMLFile.exists() || !gameXMLFile.canRead()) {
                    return ok("<error>No Pages defined</error>");
                }

                if ("game.xml".equals(gameXMLFile.getName())) {
                    // Mission m = g.getFirstMission();
                    // String result = g.createXMLForWeb(m); // return file
                    // instead

                    response().setContentType("text/xml");
                    response().setHeader("Content-disposition", "attachment; filename=game.xml");
                    response().setHeader("Content-Length", gameXMLFile.length() + "");

                    d.questpush = 0L;
                    d.update();

                    return ok(gameXMLFile);

                } else {
                    return ok("<error>No Pages defined</error>");
                }

            }
        }
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result movePartInGame(Long gid, Long pid, String direction) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        // CHECK RIGHTS ON GAME
        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return badRequest("Game not found");

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (Part.find.where().eq("id", pid).findRowCount() != 1) {

                    return badRequest("Part not found");

                } else {

                    Part p = Part.find.byId(pid);

                    if (direction.equals("up")) {
                        System.out.println("moving up");
                        g.part_up(p);
                    } else if (direction.equals("down")) {
                        System.out.println("moving down");
                        g.part_down(p);
                    }

                    return ok(views.html.editor.editor_sidemenu_missionlist.render(Game.find.byId(gid)));

                }
            }
        }

    }

    // / EDITOR: DELETERS

    @Restrict(@Group(Application.USER_ROLE))
    public static Result deletePartFromGame(Long gid, Long pid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        // CHECK RIGHTS ON GAME
        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return ok("Game not found");

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (Part.find.where().eq("id", pid).findRowCount() != 1) {

                    return ok("Part not found");

                } else {

                    Part p = Part.find.byId(pid);

                    g.removePart(p);

                    g.update();

                    return ok("synced");

                }
            }
        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result deleteHotspotFromGame(Long gid, Long pid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        // CHECK RIGHTS ON GAME
        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return ok("Game not found");

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (Hotspot.find.where().eq("id", pid).findRowCount() != 1) {

                    return ok("Hotspot not found");

                } else {

                    Hotspot p = Hotspot.find.byId(pid);

                    g.removeHotspot(p);

                    g.update();

                    return ok("synced");

                }
            }
        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result deleteMenuItemFromGame(Long gid, Long pid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        // CHECK RIGHTS ON GAME
        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return ok("Game not found");

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (MenuItem.find.where().eq("id", pid).findRowCount() != 1) {

                    return ok("Menu Item not found");

                } else {

                    MenuItem p = MenuItem.find.byId(pid);

                    g.removeMenuItem(p);

                    g.update();

                    return ok("synced");

                }
            }
        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result deleteContentFromMission(Long gid, Long mid, Long cid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        // CHECK RIGHTS ON GAME
        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return ok("Game not found");

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (Mission.find.where().eq("id", mid).findRowCount() != 1) {

                    return ok("Mission not found");

                } else {

                    Mission m = Mission.find.byId(mid);

                    if (Content.find.where().eq("id", cid).findRowCount() != 1) {

                        return ok("Content not found");

                    } else {

                        Content c = Content.find.byId(cid);

                        m.removeContent(c);
                        m.update();

                        return ok("synced");

                    }
                }
            }
        }

    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result setPositionInMission(Long gid, Long mid, Integer top, Integer left) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        // CHECK RIGHTS ON GAME
        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return ok("Game not found");

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (Mission.find.where().eq("id", mid).findRowCount() != 1) {

                    return ok("Mission not found");

                } else {

                    Mission m = Mission.find.byId(mid);

                    m.setTop(top);
                    m.setLeft(left);
                    m.update();

                    return ok("synced");

                }
            }
        }
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result deleteContentFromContent(Long gid, Long ccid, Long cid) {
        if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                Game.find.byId(gid)) == false) {

            return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

        }

        // CHECK RIGHTS ON GAME
        if (Game.find.where().eq("id", gid).findRowCount() != 1) {

            return ok("Game not found");

        } else {

            if (Global.securityGuard.hasWriteRightsOnGame(Application.getLocalUser(session()),
                    Game.find.byId(gid)) == false) {

                return badRequest(views.html.norights.render("Du benötigst Schreib-Rechte an diesem Spiel."));

            } else {

                Game g = Game.find.byId(gid);

                if (Content.find.where().eq("id", ccid).findRowCount() != 1) {

                    return ok("Content-Folder not found");

                } else {

                    Content cc = Content.find.byId(ccid);

                    if (Content.find.where().eq("id", cid).findRowCount() != 1) {

                        return ok("Content not found");

                    } else {

                        Content c = Content.find.byId(cid);

                        cc.removeSubContent(c);
                        cc.update();
                        c.removeMe();
                        c.delete();

                        return ok("synced");

                    }
                }
            }
        }

    }

}