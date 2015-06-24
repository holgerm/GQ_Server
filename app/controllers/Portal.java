package controllers;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.SubjectPresent;

import com.avaje.ebean.Ebean;
import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;

import flexjson.JSONSerializer;
import models.*;
import models.GameParts.*;
import net.sf.ehcache.ObjectExistsException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import play.api.libs.json.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import play.data.Form;
import play.data.format.Formats;
import play.data.format.Formats.NonEmpty;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.Session;
import providers.MyUsernamePasswordAuthProvider;
import providers.MyUsernamePasswordAuthUser;
import security.MyUserPortalRights;
import util.Global;

import java.io.File;

import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import views.html.index;
import views.html.signup;
import views.html.portal.my_portals;

import java.util.List;
import java.util.Set;

import javax.management.modelmbean.RequiredModelMBean;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import static play.data.Form.form;

public class Portal extends Controller {

	public static final String USER_ROLE = "user";
	public static final String ADMIN_ROLE = "admin";
	public static final String UNVERIFIED_ROLE = "unverified";

	/*
	 * RESULT PAGES
	 * 
	 * these are the computing parts of any page that is accessed in routes.
	 */

	public static Result publicGamesList(Long pid) {

		session("currentportal", pid.toString());

		List<ProviderGames> gr = Application.getLocalPortal()
				.getPublicGamesList();
		return ok(views.html.portal.public_games.render(gr));

	}

	public static Result publicGamesListOnCurrentPortal() {

		List<ProviderGames> gr = Application.getLocalPortal()
				.getPublicGamesList();
		return ok(views.html.portal.public_games.render(gr));

	}

	public static Result publicGamesMap(Long pid) {

		session("currentportal", pid.toString());

		List<ProviderGames> gr = Application.getLocalPortal()
				.getPublicGamesList();
		return ok(views.html.portal.publicgamesmap.render(gr));

	}

	// GAMES

	/*
	 * RESULTS
	 */

	@Restrict(@Group(Application.USER_ROLE))
	public static Result myGamesList(Long pid) {

		session("currentportal", pid.toString());

		ProviderPortal p = Application.getLocalPortal();

		User u = getLocalUser(session());

		if (Global.securityGuard.hasMinVerifiedRightsOnPortal(u) == false) {

			return badRequest(views.html.norightsonportal
					.render("Du benötigst mindestens User-Rechte, um diese Seite aufrufen zu können."));

		} else {

			return ok(views.html.portal.my_games.render(u.getGamesOnPortal(p)));

		}
	}

	public static Result myGamesListOnCurrentPortal() {
		ProviderPortal p = Application.getLocalPortal();

		String url = p.getTemplateServerURLDropSlash() + ""
				+ p.getPathTo(routes.Portal.myGamesList(p.getId()));
		return redirect(url);

	}

	public static Result logoutstepone() {
		ProviderPortal p = Application.getLocalPortal();
		String url = p.getTemplateServerURLDropSlash() + ""
				+ p.getPathTo(routes.Portal.logoutsteptwo(p.getId()));

		return redirect(url);

	}

	public static Result logoutsteptwo(Long pid) {

		return PlayAuthenticate.logout(session());

	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result myGamesList61() {
		Long pid = Long.valueOf(61);
		session("currentportal", pid.toString());

		ProviderPortal p = Application.getLocalPortal();

		User u = getLocalUser(session());

		if (Global.securityGuard.hasMinVerifiedRightsOnPortal(u) == false) {

			return badRequest(views.html.norightsonportal
					.render("Du benötigst mindestens User-Rechte, um diese Seite aufrufen zu können."));

		} else {

			return ok(views.html.portal.my_games.render(u.getGamesOnPortal(p)));

		}
	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result uploadGame(Long pid) {

		session("currentportal", pid.toString());

		if (Global.securityGuard
				.hasMinVerifiedRightsOnPortal(getLocalUser(session())) == false) {

			return badRequest(views.html.norightsonportal
					.render("Du benötigst mindestens User-Rechte, um diese Seite aufrufen zu können."));

		} else {

			return ok(views.html.portal.add_game.render(UPLOAD_FORM, ""));

		}
	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result addGameTypeFromGame(Long pid, Long gid) {

		session("currentportal", pid.toString());

		if (Global.securityGuard
				.hasMinVerifiedRightsOnPortal(getLocalUser(session())) == false) {

			return badRequest(views.html.norightsonportal
					.render("Du benötigst mindestens User-Rechte, um diese Seite aufrufen zu können."));

		} else {

			if (Game.find.where().eq("id", gid).findRowCount() != 1) {

				return badRequest(views.html.norights
						.render("Das Spiel existiert nicht"));

			} else {

				Game g = Game.find.byId(gid);

				return ok(views.html.portal.add_gametype.render(g,
						GAMETYPE_FORM, ""));
			}
		}
	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result editGame(Long pid, Long game) {

		session("currentportal", pid.toString());

		if (Game.find.where().eq("id", game).findRowCount() != 1) {

			return badRequest(views.html.norights
					.render("Das Spiel existiert nicht"));

		} else {

			Game mygame = Game.find.byId(game);

			if (Global.securityGuard.hasWriteRightsOnGame(
					getLocalUser(session()), mygame) == false) {

				return badRequest(views.html.norights
						.render("Du benötigst Schreib-Rechte, um dieses Spiel zu editieren."));

			} else {

				UploadedGame formobject = new UploadedGame();
				formobject.name = mygame.getName();
				formobject.publ = Global.defaultportal.getGame(mygame)
						.getVisibility();

				Form<UploadedGame> fillForm = form(UploadedGame.class);
				fillForm = fillForm.fill(formobject);

				return ok(views.html.portal.edit_game.render(fillForm, mygame,
						""));

			}
		}

	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result getConnectionTest() {

		return ok(views.html.connection.render());

	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result gameRightsonPortal(Long pid, Long gid) {

		session("currentportal", pid.toString());

		if (Game.find.where().eq("id", gid).findRowCount() != 1) {

			return badRequest(views.html.norights
					.render("Das Spiel existiert nicht"));

		} else {

			Game mygame = Game.find.byId(gid);
			ProviderPortal p = getLocalPortal();
			if (Global.securityGuard.hasWriteRightsOnGame(
					getLocalUser(session()), mygame) == false) {

				String xyz = "Du benötigst Schreib-Rechte für das Spiel um die Rechte anderer User zu editieren."
						+ mygame.getUser(getLocalUser(session())).getRights();

				return badRequest(views.html.norights.render(xyz));

			} else {

				Set<User> leer = new HashSet<User>();

				List<GameRights> toshow = new ArrayList<GameRights>();

				toshow.addAll(mygame.getRightsOnPortal(p));

				return ok(views.html.portal.game_rights.render(toshow, mygame,
						leer, USER_SEARCH_FORM));

			}
		}

	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result gameRightsOnCurrentPortal(Long gid) {

		if (Game.find.where().eq("id", gid).findRowCount() != 1) {

			return badRequest(views.html.norights
					.render("Das Spiel existiert nicht"));

		} else {

			Game mygame = Game.find.byId(gid);
			ProviderPortal p = getLocalPortal();
			if (Global.securityGuard.hasWriteRightsOnGame(
					getLocalUser(session()), mygame) == false) {

				String xyz = "Du benötigst Schreib-Rechte für das Spiel um die Rechte anderer User zu editieren."
						+ mygame.getUser(getLocalUser(session())).getRights();

				return badRequest(views.html.norights.render(xyz));

			} else {

				Set<User> leer = new HashSet<User>();

				List<GameRights> toshow = new ArrayList<GameRights>();

				toshow.addAll(mygame.getRightsOnPortal(p));

				return ok(views.html.portal.game_rights.render(toshow, mygame,
						leer, USER_SEARCH_FORM));

			}
		}

	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result gameRightsonPortalUserSearch(Long pid, Long gid) {

		session("currentportal", pid.toString());

		if (Game.find.where().eq("id", gid).findRowCount() != 1) {

			return badRequest(views.html.norights
					.render("Das Spiel existiert nicht"));

		} else {

			Game mygame = Game.find.byId(gid);
			ProviderPortal p = getLocalPortal();
			if (Global.securityGuard.hasWriteRightsOnGame(
					getLocalUser(session()), mygame) == false) {

				return badRequest(views.html.norights
						.render("Du benötigst Schreib-Rechte für das Spiel um die Rechte anderer User zu editieren."));

			} else {

				Set<User> leer = new HashSet<User>();

				final Form<UserSearch> filledForm = USER_SEARCH_FORM
						.bindFromRequest();
				UserSearch form = filledForm.get();

				if (!form.name.isEmpty()) {

					leer.addAll(p.searchForUserByName(form.name));

				}

				if (!form.email.isEmpty()) {

					leer.addAll(p.searchForUserByEmail(form.email));

				}

				List<GameRights> toshow = new ArrayList<GameRights>();

				toshow.addAll(mygame.getRightsOnPortal(p));
				return ok(views.html.portal.game_rights.render(toshow, mygame,
						leer, filledForm));

			}

		}

	}

	/*
	 * DO FUNCTIONS
	 * 
	 * These are used to compute the Post objects of Forms.
	 */

	@Restrict(@Group(Application.USER_ROLE))
	public static Result setGameVisibility(Long pid, Long gid, String value) {

		session("currentportal", pid.toString());

		ProviderPortal p = getLocalPortal();

		String help = "Error!";

		if (Game.find.where().eq("id", gid).findRowCount() != 1) {

			help = "Game " + gid + "not found";
		} else {

			Game c = Game.find.byId(gid);

			if (value.equals("true")) {

				p.getGame(c).setVisibility(true);

			} else {

				p.getGame(c).setVisibility(false);

			}
			p.getGame(c).update();

			help = "synced";

		}

		return ok(help);

	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result doDeleteGame(Long pid, Long game) {

		session("currentportal", pid.toString());

		if (Game.find.where().eq("id", game).findRowCount() != 1) {

			return badRequest(views.html.norights
					.render("Das Spiel existiert nicht"));

		} else {

			Game mygame = Game.find.byId(game);

			String gamaname = mygame.getName();

			if (Global.securityGuard.hasWriteRightsOnGame(
					getLocalUser(session()), mygame) == false) {

				return badRequest(views.html.norights
						.render("Du benötigst Schreib-Rechte, um dieses Spiel zu entfernen."));

			} else {

				boolean done = mygame.removeMe();
				try {
					mygame.delete();
				} catch (RuntimeException e) {

					System.out.println("Can't delete Game itself:");
					e.printStackTrace();

				}

				Global.updatePortals();

				if (done == true) {

					return redirect(routes.Portal.myGamesListOnCurrentPortal());

				} else {

					return badRequest(views.html.norights
							.render("Unbekannter Fehler beim Entfernen des Spiels."));

				}

			}
		}

	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result doUploadGame(Long pid) {
		session("currentportal", pid.toString());
		Game g;
		if (Global.securityGuard
				.hasMinVerifiedRightsOnPortal(getLocalUser(session())) == false) {

			return badRequest(views.html.norightsonportal
					.render("Du benötigst mindestens User-Rechte, um ein Spiel hochzuladen."));

		} else {

			User currentuser = getLocalUser(session());

			final Form<UploadedGame> filledForm = UPLOAD_FORM.bindFromRequest();

			if (filledForm.hasErrors()) {

				return badRequest(views.html.portal.add_game.render(filledForm,
						""));

			} else {

				MultipartFormData body = request().body().asMultipartFormData();
				FilePart resourceFile = body.getFile("zip");
				UploadedGame form = filledForm.get();
				Random generator = new Random(19580427);

				if (resourceFile == null) {

					if (form.gametype == 0) {

						return badRequest(views.html.portal.add_game
								.render(filledForm,
										"Du musst entweder einen Spieltyp auswählen oder eine Datei hochladen."));
					} else {

						// NEW GAME FROM GAMETYPE

						if (GameType.find.where().eq("id", form.gametype)
								.findRowCount() != 1) {

							return badRequest(views.html.portal.add_game
									.render(filledForm,
											"Der Spieltyp existiert nicht."));

						} else {

							System.out.println("Looking for GameType.");
							GameType gt = GameType.find.byId(form.gametype);
							System.out.println("GAMETYPE FOUND:"
									+ form.gametype);
							g = gt.createMe(form.name);
							System.out.println("Game created successfully.");

						}

					}

				} else {

					String fileName = resourceFile.getFilename();
					File file = resourceFile.getFile();

					int i = 0;
					boolean exists = true;
					while (exists == true) {

						i++;
						File f = new File("public/uploads/"
								+ Application.getLocalPortal().getName() + "/",
								i + "_" + fileName);
						if (!f.exists()) {
							exists = false;
						}

					}

					File tosave = new File("public/uploads/"
							+ Application.getLocalPortal().getName() + "/", i
							+ "_" + fileName);

					try {

						FileUtils.moveFile(file, tosave);

					} catch (IOException ioe) {
						return badRequest(views.html.portal.add_game
								.render(filledForm,
										"Fehler #0010 beim Datei-Upload. Versuchen Sie es später erneut oder kontaktieren Sie einen System-Administrator."));

					}

					g = new Game(form.name, tosave.getAbsolutePath());

					g.save();
				}

				System.out.println("Game created.");
				g.addOwner(currentuser);

				System.out.println("User added to Game.");
				// NEWSSTREAM-ITEMS

				// VISIBILITY

				String visi = "false";
				if (form.publ == true) {
					visi = "true";

				}
				System.out.println("Visibility");
				// USER WALL

				String text = getLocalPortal().getLanguageParameter(
						"hat_ein_neues_Spiel_erstellt")
						+ ": " + g.getName();

				String titel = "Neues Spiel";
				NewsstreamItem nsi = currentuser.createNewsstreamItem(titel,
						text, visi);
				nsi.save();
				currentuser.addNewsstream(nsi);

				// PROVIDER WALL COPY

				NewsstreamItem nsi2 = new NewsstreamItem(nsi);
				nsi2.save();
				getLocalPortal().addNewsstream(nsi2);

				// MAIN PORTAL COPY

				if (!(Global.defaultportal.getIdentifier().equals(Application
						.getLocalPortal().getIdentifier()))) {

					Global.defaultportal.addNewGame(g, form.publ);
					Global.defaultportal.save();

					NewsstreamItem nsi4 = new NewsstreamItem(nsi);
					nsi4.save();

					Global.defaultportal.addNewsstream(nsi4);

					Global.updatePortals();

				}
				System.out.println("ProviderPortal Wall");
				// GAME WALL

				text = "wurde neu erstellt.";
				titel = "Neu erstellt";
				NewsstreamItem nsi3 = g.createNewsstreamItem(titel, text, visi);
				nsi3.save();
				g.addNewsstream(nsi3);

				System.out.println("Game Wall");
				Application.getLocalPortal().addNewGame(g, form.publ);

				Application.getLocalPortal().save();

				System.out.println("Done");

				if (Application.getLocalPortal().getContentHtmlParameter(
						"general.games.directotoeditor") != null) {

					if (Application
							.getLocalPortal()
							.getContentHtmlParameter(
									"general.games.directotoeditor")
							.equals("true")) {
						return redirect(routes.Editor.getEditor(g.getId()));

					} else {

						return redirect(routes.Portal.myGamesList(pid));

					}

				} else {

					return redirect(routes.Portal.myGamesListOnCurrentPortal());

				}

			}

		}

	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result doAddGameTypeFromGame(Long pid, Long gid) {
		session("currentportal", pid.toString());
		if (Global.securityGuard
				.hasAdminRightsOnPortal(getLocalUser(session())) == false) {

			return badRequest(views.html.norightsonportal
					.render("Du benötigst Admin-Rechte, um einen Spieltyp anzulegen."));

		} else {

			if (Game.find.where().eq("id", gid).findRowCount() != 1) {

				return badRequest(views.html.norights
						.render("Das Spiel existiert nicht"));

			} else {

				Game g = Game.find.byId(gid);

				final Form<GameToGameType> filledForm = GAMETYPE_FORM
						.bindFromRequest();

				if (filledForm.hasErrors()) {

					return badRequest(views.html.portal.add_gametype.render(g,
							filledForm, ""));

				} else {

					if (ProviderPortal.find.where().eq("id", pid)
							.findRowCount() != 1) {
						return badRequest(views.html.portal.add_gametype
								.render(g, filledForm,
										"Entschulding! Diese Funktion ist noch in der Entwicklung."));

					} else {

						MultipartFormData body = request().body()
								.asMultipartFormData();
						GameToGameType form = filledForm.get();

						// NEW GAMETYPE FROM GAME

						GameType gt = new GameType(form.name);

						gt.save();

						if (form.onlyhotspot) {

							gt.easy_editor = true;
							gt.multiple_only_scene_type = true;

							SceneType s = new SceneType(form.scenename);
							s.save();
							String log = s.addDefaultsFromGame(g);
							s.update();
							gt.addPossibleSceneType(s);
							gt.update();

							gt.editor_only_scene_type = s;

							gt.update();

						} else {

							for (Part p : g.getParts()) {
								gt.addDefaultPart(p);
							}

							for (Hotspot h : g.getHotspots()) {

								gt.addDefaultHotspot(h);

							}

							for (AttributeType a : g.getType()
									.getAttributeTypes()) {

								AttributeType gt_att5 = new AttributeType(
										a.getName(), a.getXMLType(),
										a.getFileType());
								gt_att5.save();

								gt_att5.setDefaultValue(g.getAttributeValue(a));
								gt_att5.update();

								gt.setAttributeType(gt_att5);
								gt.update();

							}

							for (PartType pt : g.getType()
									.getPossiblePartTypes()) {

								gt.addPossiblePartType(pt);
								gt.update();

							}

							for (ActionType mi : g.getType()
									.getPossibleMenuItemActionTypes()) {

								gt.addPossibleMenuItemActionType(mi);
								gt.update();

							}

							for (HotspotType ht : g.getType()
									.getPossibleHotspotTypes()) {

								gt.addPossibleHotspotType(ht);
								gt.update();

							}

							for (SceneType st : g.getType()
									.getPossibleSceneTypes()) {

								gt.addPossibleSceneType(st);
								gt.update();

							}

							gt.update();
						}

						ProviderPortal myportal = ProviderPortal.find.byId(pid);

						myportal.addGameType(gt);

						myportal.update();

						return redirect(routes.Portal
								.myGamesListOnCurrentPortal());

					}
				}

			}
		}

	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result doEditGame(Long pid, Long game) {
		session("currentportal", pid.toString());

		if (Game.find.where().eq("id", game).findRowCount() != 1) {

			return badRequest(views.html.norights
					.render("Das Spiel existiert nicht"));

		} else {
			Game mygame = Game.find.byId(game);

			if (Global.securityGuard
					.hasMinVerifiedRightsOnPortal(getLocalUser(session())) == false) {

				return badRequest(views.html.norightsonportal
						.render("Du benötigst mindestens User-Rechte, um ein Spiel hochzuladen."));

			} else {

				User currentuser = getLocalUser(session());

				final Form<UploadedGame> filledForm = UPLOAD_FORM
						.bindFromRequest();

				if (filledForm.hasErrors()) {

					return badRequest(views.html.portal.edit_game.render(
							filledForm, mygame, ""));

				}

				MultipartFormData body = request().body().asMultipartFormData();

				UploadedGame form = filledForm.get();

				mygame.setName(form.name);

				for (ProviderGames pg : mygame.getPortals()) {

					pg.setVisibility(form.publ);
					pg.update();

				}

				FilePart resourceFile = body.getFile("zip");

				if (resourceFile != null) {

					Random generator = new Random(19580427);

					String fileName = resourceFile.getFilename();
					File file = resourceFile.getFile();

					int i = 0;
					boolean exists = true;
					while (exists == true) {

						i++;
						File f = new File("public/uploads/"
								+ Application.getLocalPortal().getName() + "/",
								i + "_" + fileName);
						if (!f.exists()) {
							exists = false;
						}

					}

					File tosave = new File("public/uploads/"
							+ Application.getLocalPortal().getName() + "/", i
							+ "_" + fileName);

					try {

						FileUtils.moveFile(file, tosave);

					} catch (IOException ioe) {

						return badRequest(views.html.portal.edit_game
								.render(filledForm,
										mygame,
										"Fehler #0011 beim Datei-Upload. Versuchen Sie es später erneut oder kontaktieren Sie einen System-Administrator."));

					}

					File todelete = new File(mygame.getFile());
					FileUtils.deleteQuietly(todelete);

					mygame.setFile(tosave.getAbsolutePath());

				}

				Global.updatePortals();
				mygame.update();

				return redirect(routes.Portal.myGamesListOnCurrentPortal());

			}
		}
	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result doCopyGame(Long pid, Long game) {
		session("currentportal", pid.toString());

		if (Game.find.where().eq("id", game).findRowCount() != 1) {

			return badRequest(views.html.norights
					.render("Das Spiel existiert nicht"));

		} else {
			Game mygame = Game.find.byId(game);

			if (Global.securityGuard
					.hasMinVerifiedRightsOnPortal(getLocalUser(session())) == false) {

				return badRequest(views.html.norightsonportal
						.render("Du benötigst mindestens User-Rechte, um ein Spiel zu kopieren."));

			} else {

				User currentuser = getLocalUser(session());

				Game g = mygame.copyMe(" Kopie");
				g.save();
				System.out.println("Game created.");
				g.addOwner(currentuser);

				System.out.println("User added to Game.");
				// NEWSSTREAM-ITEMS

				// VISIBILITY

				String visi = "false";
				boolean publ = false;
				// USER WALL

				String text = getLocalPortal().getLanguageParameter(
						"hat_ein_neues_Spiel_erstellt")
						+ ": " + g.getName();

				String titel = "Neues Spiel";
				NewsstreamItem nsi = currentuser.createNewsstreamItem(titel,
						text, visi);
				nsi.save();
				currentuser.addNewsstream(nsi);

				// PROVIDER WALL COPY

				NewsstreamItem nsi2 = new NewsstreamItem(nsi);
				nsi2.save();
				getLocalPortal().addNewsstream(nsi2);

				// MAIN PORTAL COPY

				if (!(Global.defaultportal.getIdentifier().equals(Application
						.getLocalPortal().getIdentifier()))) {

					Global.defaultportal.addNewGame(g, publ);
					Global.defaultportal.save();

					NewsstreamItem nsi4 = new NewsstreamItem(nsi);
					nsi4.save();

					Global.defaultportal.addNewsstream(nsi4);

					Global.updatePortals();

				}
				System.out.println("ProviderPortal Wall");
				// GAME WALL

				text = "wurde neu erstellt.";
				titel = "Neu erstellt";
				NewsstreamItem nsi3 = g.createNewsstreamItem(titel, text, visi);
				nsi3.save();
				g.addNewsstream(nsi3);

				System.out.println("Game Wall");
				Application.getLocalPortal().addNewGame(g, publ);

				Application.getLocalPortal().save();

				System.out.println("Done");

				return redirect(routes.Portal.myGamesListOnCurrentPortal());

			}
		}
	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result doEditUserRightsOnGame(Long pid, Long gid, Long uid,
			String rights) {

		session("currentportal", pid.toString());

		ProviderPortal p = getLocalPortal();

		if (Game.find.where().eq("id", gid).findRowCount() != 1) {

			return badRequest(views.html.norights
					.render("Das Spiel existiert nicht"));

		} else {
			Game mygame = Game.find.byId(gid);
			if (Global.securityGuard.hasWriteRightsOnGame(
					getLocalUser(session()), mygame) == false) {

				return badRequest(views.html.norights
						.render("Du benötigst Schreib-Rechte für das Spiel um die Rechte anderer User zu editieren."));

			} else {

				if (User.find.where().eq("id", uid).findRowCount() != 1) {

					return badRequest(views.html.norights
							.render("Der User existiert nicht"));

				} else {

					User usertoedit = User.find.byId(uid);

					if (mygame.existsUser(usertoedit)) {

						// EDIT

						mygame.editUser(usertoedit, rights);

						Global.updatePortals();

					} else {

						// NEU HINZUFÜGEN

						mygame.addUser(usertoedit, rights);
						Global.updatePortals();

					}

					mygame.save();

					Set<User> leer = new HashSet<User>();

					return redirect(routes.Portal
							.gameRightsOnCurrentPortal(gid));

				}
			}

		}
	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result doDeleteUserRightsOnGame(Long pid, Long gid, Long uid) {

		session("currentportal", pid.toString());

		ProviderPortal p = getLocalPortal();

		if (Game.find.where().eq("id", gid).findRowCount() != 1) {

			return badRequest(views.html.norights
					.render("Das Spiel existiert nicht"));

		} else {
			Game mygame = Game.find.byId(gid);
			if (Global.securityGuard.hasWriteRightsOnGame(
					getLocalUser(session()), mygame) == false) {

				return badRequest(views.html.norights
						.render("Du benötigst Schreib-Rechte für das Spiel um die Rechte anderer User zu editieren."));

			} else {

				if (User.find.where().eq("id", uid).findRowCount() != 1) {

					return badRequest(views.html.norights
							.render("Der User existiert nicht"));

				} else {

					User usertoedit = User.find.byId(uid);

					if (mygame.existsUser(usertoedit)) {

						// DELTE

						mygame.deleteUser(usertoedit);

						Global.updatePortals();

					}

					mygame.save();

					Set<User> leer = new HashSet<User>();

					return redirect(routes.Portal
							.gameRightsOnCurrentPortal(gid));

				}
			}

		}
	}

	// PORTALS

	/*
	 * RESULTS
	 */

	@Restrict(@Group(Application.USER_ROLE))
	public static Result myPortalsList(Long pid) {

		session("currentportal", pid.toString());

		ProviderPortal p = Application.getLocalPortal();

		User u = getLocalUser(session());

		if (Global.securityGuard.isDefaultPortal() == false) {

			return badRequest(views.html.norights
					.render("Diese Seite kann nur vom Geoquest Webservice aus aufgerufen werden."));

		} else {

			Set<ProviderUsers> pplist = new HashSet<ProviderUsers>();

			for (ProviderUsers pp : u.getPortals()) {
				if (!(pp.getPortal().getId().equals(Global.defaultportal
						.getId())))
					pplist.add(pp);

			}

			return ok(views.html.portal.my_portals.render(ProviderPortal.find
					.all()));

		}
	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result addPortal(Long pid) {

		session("currentportal", pid.toString());

		if (Global.securityGuard.isDefaultPortal() == false) {

			return badRequest(views.html.norights
					.render("Diese Seite kann nur vom Geoquest Webservice aus aufgerufen werden."));

		} else {

			return ok(views.html.portal.add_portal.render(PORTAL_FORM, ""));

		}
	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result doAddGameTypeToPortal(Long pid, Long portal, Long gtid) {

		session("currentportal", pid.toString());

		ProviderPortal p = getLocalPortal();

		if (ProviderPortal.find.where().eq("id", portal).findRowCount() == 1) {

			ProviderPortal myportal = ProviderPortal.find.byId(portal);

			String portalname = myportal.getName();

			if (Global.securityGuard.hasAdminRightsOnPortalX(
					getLocalUser(session()), myportal) == false) {

				return badRequest(views.html.norights
						.render("Du benötigst Admin-Rechte auf diesem Portal, um dieses Portal zu entfernen."));

			} else {

				if (GameType.find.where().eq("id", gtid).findRowCount() == 1) {

					myportal.addGameType(GameType.find.byId(gtid));

					myportal.update();

					return redirect(routes.Portal.gameTypesonPortal(myportal
							.getId(), Application.getLocalPortal().getId()));

				} else {
					return badRequest(views.html.norights
							.render("Der Spieltyp existiert nicht."));

				}

			}

		} else {

			return badRequest(views.html.norights
					.render("Das Portal existiert nicht."));

		}

	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result doDeleteGameTypeFromPortal(Long pid, Long portal,
			Long gtid) {

		session("currentportal", pid.toString());

		ProviderPortal p = getLocalPortal();

		if (ProviderPortal.find.where().eq("id", portal).findRowCount() == 1) {

			ProviderPortal myportal = ProviderPortal.find.byId(portal);

			String portalname = myportal.getName();

			if (Global.securityGuard.hasAdminRightsOnPortalX(
					getLocalUser(session()), myportal) == false) {

				return badRequest(views.html.norights
						.render("Du benötigst Admin-Rechte auf diesem Portal, um dieses Portal zu entfernen."));

			} else {

				if (GameType.find.where().eq("id", gtid).findRowCount() == 1) {

					myportal.removeGameType(GameType.find.byId(gtid));
					myportal.update();

					Global.updatePortals();

					return redirect(routes.Portal.gameTypesonPortal(myportal
							.getId(), Application.getLocalPortal().getId()));

				} else {
					return badRequest(views.html.norights
							.render("Der Spieltyp existiert nicht."));

				}

			}

		} else {

			return badRequest(views.html.norights
					.render("Das Portal existiert nicht."));

		}

	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result userRightsonPortal(Long pid, Long gid) {

		session("currentportal", pid.toString());

		if (ProviderPortal.find.where().eq("id", pid).findRowCount() != 1) {

			return badRequest(views.html.norights
					.render("Das Portal existiert nicht"));

		} else {
			ProviderPortal myportal = ProviderPortal.find.byId(gid);
			if (Global.securityGuard.hasAdminRightsOnPortalX(
					getLocalUser(session()), myportal) == false) {

				return badRequest(views.html.norights
						.render("Du benötigst Admin-Rechte auf diesem Portal, um die User-Rechte auf dem Portal zu editieren."));

			} else {

				return ok(views.html.portal.portal_rights.render(myportal));

			}
		}

	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result gameTypesonPortal(Long pid, Long gid) {

		session("currentportal", gid.toString());

		if (ProviderPortal.find.where().eq("id", pid).findRowCount() != 1) {

			return badRequest(views.html.norights
					.render("Das Portal existiert nicht"));

		} else {
			ProviderPortal myportal = ProviderPortal.find.byId(pid);
			if (Global.securityGuard.hasAdminRightsOnPortalX(
					getLocalUser(session()), myportal) == false) {

				return badRequest(views.html.norights
						.render("Du benötigst Admin-Rechte auf diesem Portal, um die User-Rechte auf dem Portal zu editieren."));

			} else {

				return ok(views.html.portal.portal_gametypes.render(myportal));

			}
		}

	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result userRightsonCurrentPortal(Long gid) {

		ProviderPortal myportal = Application.getLocalPortal();
		if (Global.securityGuard.hasAdminRightsOnPortalX(
				getLocalUser(session()), myportal) == false) {

			return badRequest(views.html.norights
					.render("Du benötigst Admin-Rechte auf diesem Portal, um die User-Rechte auf dem Portal zu editieren."));

		} else {

			return ok(views.html.portal.portal_rights.render(myportal));

		}

	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result editPortal(Long pid, Long portal) {

		session("currentportal", pid.toString());

		if (ProviderPortal.find.where().eq("id", portal).findRowCount() != 1) {

			return badRequest(views.html.norights
					.render("Das Portal existiert nicht"));

		} else {

			ProviderPortal myportal = ProviderPortal.find.byId(portal);

			if (Global.securityGuard.hasAdminRightsOnPortalX(
					getLocalUser(session()), myportal) == false) {

				return badRequest(views.html.norights
						.render("Du benötigst Admin-Rechte auf diesem Portal, um dieses Portal zu editieren."));

			} else {

				PostedPortal formobject = new PostedPortal();
				formobject.name = myportal.getName();
				formobject.htmlurl = myportal.getTemplateURL();
				formobject.formname = myportal.getTemplateForm();
				formobject.pwfield = myportal.getTemplateFormField();
				formobject.password = myportal.getTemplatePw();
				formobject.user = myportal.getTemplateUser();
				formobject.userfield = myportal.getTemplateUserField();
				formobject.afterloginurl = myportal.getTemplateAfterLoginURL();
				formobject.mappingurl = myportal.getTemplateMappingURL();
				formobject.submitbutton = myportal.getTemplateSubmitButton();
				formobject.autoverify = myportal.getAutoVerifyUsers();
				formobject.customserverurl = myportal.getTemplateServerURL();
				formobject.addcss = myportal.getAdditionalCSS();
				formobject.posturl = myportal.getTemplatePostURL();
				formobject.color = myportal.getDefaultcolor();
				formobject.color_2 = myportal.getComplementColor();
				formobject.color_3 = myportal.getGradientColor();
				formobject.color_4 = myportal.getNavbarColor();
				formobject.color_5 = myportal.getLinkColor();

				Form<PostedPortal> fillForm = form(PostedPortal.class);
				fillForm = fillForm.fill(formobject);

				return ok(views.html.portal.edit_portal.render(fillForm,
						myportal, ""));

			}
		}

	}

	/*
	 * DO FUNCTIONS
	 * 
	 * These are used to compute the Post objects of Forms.
	 */

	@Restrict(@Group(Application.USER_ROLE))
	public static Result doAddPortal(Long pid) {
		session("currentportal", pid.toString());

		if (Global.securityGuard.isDefaultPortal() == false) {

			return badRequest(views.html.norights
					.render("Diese Seite kann nur vom Geoquest Webservice aus aufgerufen werden."));

		} else {

			User currentuser = getLocalUser(session());
			final Form<PostedPortal> filledForm = PORTAL_FORM.bindFromRequest();

			if (filledForm.hasGlobalErrors()) {

				return badRequest(views.html.portal.add_portal.render(
						filledForm, "Global Error"));

			}

			if (filledForm.hasErrors()) {

				return badRequest(views.html.portal.add_portal.render(
						filledForm, ""));

			}

			PostedPortal form = filledForm.get();

			MultipartFormData body = request().body().asMultipartFormData();
			FilePart resourceFile = body.getFile("logo");

			Random generator = new Random(19580427);

			Long nid = Application.getLocalPortal().getId();

			String the_url = Global.SERVER_URL + "defaulttemplate";
			boolean set_url = false;
			if (!form.htmlurl.equals("")) {

				the_url = form.htmlurl;
				set_url = true;
			}

			// / HOCHLADEN

			String tosavename = "";

			if (resourceFile != null) {

				String fileName = resourceFile.getFilename();
				File file = resourceFile.getFile();

				int i = 0;
				boolean exists = true;
				while (exists == true) {

					i++;
					File f = new File("public/uploads/"
							+ Application.getLocalPortal().getName() + "/img/",
							i + "_" + fileName);
					if (!f.exists()) {
						exists = false;
					}

				}

				File tosave = new File("public/uploads/portallogos/", i + "_"
						+ fileName);

				try {

					FileUtils.moveFile(file, tosave);
					System.out.println("Done");
					tosavename = tosave.getAbsolutePath();

				} catch (IOException ioe) {
					return badRequest(views.html.portal.add_portal
							.render(filledForm,
									"Fehler #0012 beim Datei-Upload. Versuchen Sie es später erneut oder kontaktieren Sie einen System-Administrator."));

				}

			} else {

				// BAD REQUEST

			}

			String c1 = form.color;
			String c2 = form.color_2;
			String c3 = form.color_3;
			String c4 = form.color_4;
			String c5 = form.color_5;

			if (c1.equals("")) {
				c1 = "#ffccff";
			}
			if (c2.equals("")) {
				c2 = "#ffccff";
			}
			if (c3.equals("")) {
				c3 = "#ffccff";
			}
			if (c4.equals("")) {
				c4 = "#ffccff";
			}
			if (c5.equals("")) {
				c5 = "#ffccff";
			}

			ProviderPortal p = new ProviderPortal(form.name, the_url,
					form.formname, form.pwfield, form.userfield, form.password,
					form.user, form.afterloginurl, form.autoverify,
					form.submitbutton, form.mappingurl, form.customserverurl,
					form.posturl, form.addcss, c1, "default", c2, c3, c4, c5,
					tosavename);
			p.save();
			p.addNewAdmin(currentuser);
			p.save();
			nid = p.getId();

			// IF I GOT NO TEMPLATE PAGE, DO QEEVEEDESIGN DEFAULT1

			if (!set_url) {

				System.out.println("Trying to set default template");
				p.setTemplateURL(Global.SERVER_URL + nid
						+ "/qeeveetemplates/default/html");
				p.update();
				p.updateHtmlByTemplateNoPassword();

			}

			return redirect(Application.getLocalPortal()
					.getTemplateServerURLDropSlash() + "/" + nid + "/public");

		}
	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result doDeletePortal(Long pid, Long portal) {

		session("currentportal", pid.toString());

		if (ProviderPortal.find.where().eq("id", portal).findRowCount() == 1) {

			ProviderPortal myportal = ProviderPortal.find.byId(portal);

			String portalname = myportal.getName();

			if (Global.securityGuard.hasAdminRightsOnPortalX(
					getLocalUser(session()), myportal) == false) {

				return badRequest(views.html.norights
						.render("Du benötigst Admin-Rechte auf diesem Portal, um dieses Portal zu entfernen."));

			} else {

				boolean done = false;

				if (!(myportal.getId().equals(Global.defaultportal.getId()))) {

					done = myportal.removeMe();
					try {
						myportal.delete();
					} catch (RuntimeException e) {

						System.out.println("Can't delete Portal itself:");
						e.printStackTrace();

					}

					Global.updatePortals();

				}

				if (done == true) {

					return ok(views.html.portal.public_games.render(Application
							.getLocalPortal().getPublicGamesList()));

				} else {

					return badRequest(views.html.norights
							.render("Unbekannter Fehler beim Entfernen des Portals."));

				}

			}

		} else {

			return badRequest(views.html.norights
					.render("Das zu entfernende Portal existiert nicht."));

		}

	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result doEditPortal(Long pid, Long portal) {
		session("currentportal", pid.toString());

		if (ProviderPortal.find.where().eq("id", portal).findRowCount() == 1) {

			ProviderPortal myportal = ProviderPortal.find.byId(portal);

			if (Global.securityGuard.hasAdminRightsOnPortalX(
					getLocalUser(session()), myportal) == false) {

				return badRequest(views.html.norights
						.render("Du benötigst Admin-Rechte auf dem jeweiligen Portal, um es zu editieren."));

			} else {

				User currentuser = getLocalUser(session());

				final Form<PostedPortal> filledForm = PORTAL_FORM
						.bindFromRequest();

				if (filledForm.hasErrors()) {

					return badRequest(views.html.portal.edit_portal.render(
							filledForm, myportal, ""));

				}

				PostedPortal form = filledForm.get();

				MultipartFormData body = request().body().asMultipartFormData();
				FilePart resourceFile = body.getFile("logo");

				Random generator = new Random(19580427);

				String tosavename = "";

				if (resourceFile != null) {

					// HOCHLADEN

					String fileName = resourceFile.getFilename();
					File file = resourceFile.getFile();

					int i = 0;
					boolean exists = true;
					while (exists == true) {

						i++;
						File f = new File("public/uploads/portallogos/", i
								+ "_" + fileName);
						if (!f.exists()) {
							exists = false;
						}

					}

					File tosave = new File("public/uploads/portallogos/", i
							+ "_" + fileName);

					try {

						FileUtils.moveFile(file, tosave);
						tosavename = tosave.getAbsolutePath();

						myportal.setLogoimg(tosavename);

					} catch (IOException ioe) {
						return badRequest(views.html.portal.edit_portal
								.render(filledForm,
										myportal,
										"Fehler #0013 beim Datei-Upload. Versuchen Sie es später erneut oder kontaktieren Sie einen System-Administrator."));

					}

				}

				myportal.setName(form.name);

				myportal.setAutoVerifyUsers(form.autoverify);

				if (!form.htmlurl.equals("")) {
					myportal.setTemplateURL(form.htmlurl);

				} else {

					myportal.setTemplateURL(Global.SERVER_URL
							+ myportal.getIdentifier()
							+ "/qeeveetemplates/default/html");

				}

				if (!form.password.equals("")) {
					myportal.setTemplatePw(form.password);
				} else {

					System.out
							.println("ignored empty password in <<Edit Portal>>-form");

				}

				String c1 = form.color;
				String c2 = form.color_2;
				String c3 = form.color_3;
				String c4 = form.color_4;
				String c5 = form.color_5;

				if (c1.equals("")) {
					c1 = "#ffccff";
				}
				myportal.setDefaultcolor(c1);
				if (c2.equals("")) {
					c2 = "#ffccff";
				}
				myportal.setComplementColor(c2);
				if (c3.equals("")) {
					c3 = "#ffccff";
				}
				myportal.setGradientColor(c3);
				if (c4.equals("")) {
					c4 = "#ffccff";
				}
				myportal.setNavbarColor(c4);
				if (c5.equals("")) {
					c5 = "#ffccff";
				}
				myportal.setLinkColor(c5);

				myportal.setTemplateFormField(form.pwfield);
				myportal.setTemplateForm(form.formname);
				myportal.setTemplateAfterLoginURL(form.afterloginurl);
				myportal.setTemplateUser(form.user);
				myportal.setTemplateUserField(form.userfield);
				myportal.setTemplateSubmitButton(form.submitbutton);
				myportal.setTemplateMappingURL(form.mappingurl);
				myportal.setTemplateServerURL(form.customserverurl);
				myportal.setTemplatePostURL(form.posturl);
				myportal.setAdditionalCSS(form.addcss);
				System.out.println("##Portal-Data-Update complete.");
				myportal.update();
				myportal.computeMapping();
				System.out.println("##Portal-Mapping-Update complete.");
				myportal.updateHtmlByTemplateNoPassword();
				System.out.println("##Portal-Template-Update complete.");
				Global.updatePortals();
				System.out.println("##Portal-Update complete.");

				return redirect(routes.Portal.myPortalsList(pid));

			}

		} else {

			final Form<PostedPortal> filledForm = PORTAL_FORM.bindFromRequest();

			return badRequest(views.html.portal.edit_portal.render(filledForm,
					getLocalPortal(), "Das Portal existiert nicht"));

		}

	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result doEditUserRightsOnPortal(Long pid, Long peditid,
			Long uid, String rights) {

		session("currentportal", pid.toString());

		ProviderPortal p = getLocalPortal();

		if (ProviderPortal.find.where().eq("id", peditid).findRowCount() == 1) {

			ProviderPortal myportal = ProviderPortal.find.byId(peditid);
			if (Global.securityGuard.hasAdminRightsOnPortalX(
					getLocalUser(session()), myportal) == false) {

				return badRequest(views.html.norights
						.render("Du benötigst Admin-Rechte auf dem jeweiligen Portal, um es zu editieren."));

			} else {

				if (User.find.where().eq("id", uid).findRowCount() != 1) {

					return badRequest(views.html.norights
							.render("Der User existiert nicht"));

				} else {
					User usertoedit = User.find.byId(uid);

					myportal.editUser(usertoedit, rights);

					myportal.update();

					Global.updatePortals();

					return redirect(routes.Portal.userRightsonPortal(pid,
							peditid));

				}
			}

		} else {

			return redirect(routes.Portal.userRightsonCurrentPortal(peditid));

		}

	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result doDeleteUserRightsOnPortal(Long pid, Long peditid,
			Long uid) {

		session("currentportal", pid.toString());

		ProviderPortal p = getLocalPortal();

		if (ProviderPortal.find.where().eq("id", peditid).findRowCount() == 1) {

			ProviderPortal myportal = ProviderPortal.find.byId(peditid);
			if (Global.securityGuard.hasAdminRightsOnPortalX(
					getLocalUser(session()), myportal) == false) {

				return badRequest(views.html.norights
						.render("Du benötigst Admin-Rechte auf dem jeweiligen Portal, um es zu editieren."));

			} else {

				if (User.find.where().eq("id", uid).findRowCount() != 1) {

					return badRequest(views.html.norights
							.render("Der User existiert nicht"));

				} else {

					User usertoedit = User.find.byId(uid);

					myportal.deleteUser(usertoedit);

					myportal.update();

					Global.updatePortals();

					return redirect(routes.Portal
							.userRightsonCurrentPortal(peditid));

				}
			}

		} else {

			// NEEDS ERRORMESSAGE

			return badRequest(views.html.norights
					.render("Das Portal existiert nicht."));

		}

	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result doAddMeToPortal(Long pid) {

		session("currentportal", pid.toString());

		if (Global.securityGuard.hasAnyRightsOnPortal(Application
				.getLocalUser(session()))) {

			return badRequest(views.html.norights
					.render("Deine Registrierung auf diesem Portal muss erst von einem Administrator bestätigt werden."));

		} else {

			User u = Application.getLocalUser(session());

			if (ProviderPortal.find.where().eq("id", pid).findRowCount() != 1) {

				return badRequest(views.html.norights
						.render("Das Portal existiert nicht"));

			} else {

				ProviderPortal p = ProviderPortal.find.byId(pid);

				p.addNewUser(u);

				return redirect(routes.Portal.publicGamesListOnCurrentPortal());

			}

		}

	}

	public static Result migrateGame(Long pid, Long gid) {

		session("currentportal", pid.toString());

		ProviderPortal p = Application.getLocalPortal();

		// CHECK RIGHTS ON GAME
		if (Game.find.where().eq("id", gid).findRowCount() != 1) {

			return ok("Game not found");

		} else {

			Game g = Game.find.byId(gid);

			if (p.hasGameType(g.getType())) {

				return badRequest(views.html.norights
						.render("Es liegen keine Updates für diesen Spieltyp vor."));

			} else {

				for (GameType gt : p.getGameTypes()) {

					if (gt.getName().equals(g.getType().getName())) {

						Game newg = g.migrateTo(gt);
						newg.save();

						if (p.getGame(g) != null) {

							p.addNewGame(newg, p.getGame(g).getVisibility());

						} else {
							p.addNewGame(newg, false);

						}

						System.out
								.println("Done with Game Migration on Portal "
										+ p.getId());

						p.deleteGame(p.getGame(g));

						p.update();

						// Alle User Rechte auf neues Spiel linken

						List<GameRights> grl = new ArrayList<GameRights>();
						grl.addAll(g.getUsers());

						for (GameRights gr : grl) {

							// NEU HINZUFÜGEN

							newg.addUser(gr.getUser(), gr.getRights());
							newg.update();

						}

						// Alle Portale auf neues Spiel linken

						List<ProviderGames> pgl = new ArrayList<ProviderGames>();

						for (ProviderPortal app : ProviderPortal.find.all()) {

							if (app.getGame(g) != null) {

							}
						}
						pgl.addAll(g.getPortals());

						for (ProviderGames pg : pgl) {

							if (pg.getPortal().getId().equals(p.getId())) {

								// NEU HINZUFÜGEN
								pg.getPortal().addNewGame(newg,
										pg.getVisibility());
								pg.setVisibility(false);
								pg.update();

								pg.getPortal().deleteGame(pg);

								pg.getPortal().update();

							}
						}

						g.removeMe();

						try {
							g.setVersion(newg.getId());
							g.update();

							System.out.println("Old Game version linked.");

						} catch (RuntimeException e) {

							System.out.println("Can't delete old Game.");
							e.printStackTrace();

						}

						Global.updatePortals();

						return redirect(routes.Portal.gameRightsonPortal(pid,
								newg.getId()));

					}

				}

				return redirect(routes.Portal
						.gameRightsonPortal(pid, g.getId()));

			}

		}
	}

	// NEWSSTREAMS

	public static Result portalNewsstream(Long pid) {

		session("currentportal", pid.toString());

		ProviderPortal p = Application.getLocalPortal();

		Set<ProviderUsers> pplist = new HashSet<ProviderUsers>();

		List<NewsstreamItem> ilist;
		ilist = getLocalPortal().getNewsstream();

		return ok(views.html.portal.portalnewsstream.render(ilist));

	}

	public static Result portalNewsstreamOnCurrentPortal() {

		ProviderPortal p = Application.getLocalPortal();

		Set<ProviderUsers> pplist = new HashSet<ProviderUsers>();

		List<NewsstreamItem> ilist;
		ilist = getLocalPortal().getNewsstream();

		return ok(views.html.portal.portalnewsstream.render(ilist));

	}

	@Restrict(@Group(Application.USER_ROLE))
	public static Result doDeleteNewsItem(Long pid, Long nid) {

		session("currentportal", pid.toString());

		if (Global.securityGuard
				.hasMinVerifiedRightsOnPortal(getLocalUser(session())) == false) {

			return badRequest(views.html.norights
					.render("Deine Registrierung auf diesem Portal muss erst von einem Administrator bestätigt werden."));

		} else {

			Application.getLocalPortal().deleteNewsstream(nid);

			return redirect(routes.Portal.portalNewsstreamOnCurrentPortal());

		}

	}

	public static Result getGamesInfoListJSON(Long pid) {
		JSONSerializer postDetailsSerializer = new JSONSerializer()
				.include("id", "typeID", "name", "hotspots",
						"hotspots.longitude", "hotspots.latitude", "metadata",
						"metadata.key", "metadata.value", "lastUpdate")
				.exclude("*").prettyPrint(true);

		List<GameInfo> gameInfos = new ArrayList<GameInfo>();

		if (ProviderPortal.find.where().eq("id", pid).findRowCount() == 1) {

			ProviderPortal p = ProviderPortal.find.byId(pid);

			List<ProviderGames> objects = p.getPublicGamesList();

			for (ProviderGames pg : objects) {

				gameInfos.add(new GameInfo(pg.getGame()));

			}
		}
		return ok(postDetailsSerializer.serialize(gameInfos));
	}

	/*
	 * JSON RESULTS
	 */

	public static Result getPublicGamesJson(Long pid) {

		List<ProviderPortal> all = ProviderPortal.find.all();

		List<Long> allids = new ArrayList<Long>();

		for (ProviderPortal pp : all) {

			allids.add(pp.getId());

		}

		JSONSerializer postDetailsSerializer = new JSONSerializer()
				.include("id", "name", "zip", "lastUpdate", "hotspots",
						"hotspots.longitude", "hotspots.latitude",
						"parts.scene.hotspots.longitude",
						"parts.scene.hotspots.l", "parts.scene", "type.id")
				.exclude("*").prettyPrint(true);
		List<Game> obj = new ArrayList<Game>();

		if (allids.contains(pid)) {

			if (ProviderPortal.find.where().eq("id", pid).findRowCount() == 1) {

				ProviderPortal p = ProviderPortal.find.byId(pid);

				List<ProviderGames> objects = p.getPublicGamesList();

				for (ProviderGames pg : objects) {

					obj.add(pg.getGame());

				}
			}

		}

		return ok(postDetailsSerializer.serialize(obj));

	}

	public static Result getGameJson(Long gid) {

		if (Game.find.where().eq("id", gid).findRowCount() != 1) {

			return ok("[]");

		} else {

			JSONSerializer postDetailsSerializer = new JSONSerializer()
					.exclude("*.class").prettyPrint(true);

			Game g = Game.find.byId(gid);
			String jsonStr = "";
			try {
				jsonStr = postDetailsSerializer.deepSerialize(g);
			} catch (Exception e) {

				jsonStr = e.getMessage() + "\n";
				for (StackTraceElement ste : e.getStackTrace()) {
					jsonStr += ste.toString() + "\n";

				}

			}
			return ok(jsonStr);
		}

	}

	public static Result getPrivateGamesJson(Long pid) {

		ProviderPortal p = Application.getLocalPortal();

		User u = getLocalUser(session());

		if (Global.securityGuard.hasMinVerifiedRightsOnPortal(u) == false) {

			return ok("NORIGHTS");

		} else {

			JSONSerializer postDetailsSerializer = new JSONSerializer()
					.include("id", "name", "zip", "lastUpdate", "hotspots",
							"hotspots.longitude", "hotspots.latitude",
							"parts.scene.hotspots.longitude",
							"parts.scene.hotspots.l", "parts.scene", "type.id")
					.exclude("*").prettyPrint(true);

			List<Game> obj = new ArrayList<Game>();

			for (GameRights gr : u.getGamesOnPortal(p)) {

				obj.add(gr.getGame());

			}

			return ok(postDetailsSerializer.serialize(obj));

		}
	}

	public static Result getPortalsJson() {
		List<ProviderPortal> obj = Ebean.find(ProviderPortal.class)
				.select("id,name").findList();

		JSONSerializer postDetailsSerializer = new JSONSerializer()
				.include("id", "name").exclude("*").prettyPrint(true);

		return ok(postDetailsSerializer.serialize(obj));

	}

	/*
	 * DEFAULT DESIGNS
	 */

	public static Result getDesign(Long pid, String x, String y) {

		session("currentportal", pid.toString());

		if (x.equals("default")) {

			if (y.equals("css")) {

				response().setContentType("text/css");
				return ok(views.html.portaldesigns.default1_css.render());

			}

			if (y.equals("html")) {
				return ok(views.html.portaldesigns.default1_html.render());

			}

			if (y.equals("dtt")) {

				response().setContentType("text/css");
				return ok(views.html.portaldesigns.default1_dt.render());
			}

		}

		return ok(views.html.template.render());

	}

	/*
	 * HELPER FUNCTIONS
	 */

	/*
	 * getLocalPortal() looks for the global variable currentportal. If it is
	 * not set, it looks at the defaultportal variable, but in most cases
	 * currentportal should be set, because it gets set to defaultportal onStart
	 * of Global.
	 */

	public static Result updatePortalTemplate(Long pid) {
		session("currentportal", pid.toString());

		if (Global.securityGuard
				.hasMinVerifiedRightsOnPortal(getLocalUser(session())) == false) {

			return badRequest(views.html.norightsonportal
					.render("Aus Sicherheitsgründen können nur Benutzer dieses Portals die Template Seite updaten."));

		} else {

			Application.getLocalPortal().updateHtmlByTemplateNoPassword();
			Global.updatePortals();
			return ok(views.html.portal.public_games.render(Application
					.getLocalPortal().getPublicGamesList()));

		}

	}

	public static Result getGameFileSizeForPortal(Long portal, Long game) {
		if (Game.find.where().eq("id", game).findRowCount() != 1) {

			return badRequest(views.html.norights
					.render("Das Spiel existiert nicht"));

		} else {

			Game mygame = Game.find.byId(game);

			return ok(new File(mygame.getFile()).length() + "");
		}

	}

	public static Result getGameFileForPortal(Long portal, Long game) {

		if (Game.find.where().eq("id", game).findRowCount() != 1) {

			return badRequest(views.html.norights
					.render("Das Spiel existiert nicht"));

		} else {

			Game mygame = Game.find.byId(game);

			response().setContentType("application/x-download");
			response().setHeader("Content-disposition",
					"attachment; filename=game.zip");
			// response().setHeader("Content-Length" , new
			// File(mygame.getFile()).length()+"");

			System.out.println("Content-Length: "
					+ (new File(mygame.getFile()).length()));
			return ok(new File(mygame.getFile()));
		}
	}

	public static Result getGameFile(Long game) {

		if (Game.find.where().eq("id", game).findRowCount() != 1) {

			return badRequest(views.html.norights
					.render("Das Spiel existiert nicht"));

		} else {

			Game mygame = Game.find.byId(game);

			response().setContentType("application/x-download");
			response().setHeader("Content-disposition",
					"attachment; filename=game.zip");
			// response().setHeader("Content-Length" , new
			// File(mygame.getFile()).length()+"");
			System.out.println("Content-Length: "
					+ (new File(mygame.getFile()).length()));

			return ok(new File(mygame.getFile()));
		}
	}

	public static Result getGameFileSize(Long game) {

		if (Game.find.where().eq("id", game).findRowCount() != 1) {

			return badRequest(views.html.norights
					.render("Das Spiel existiert nicht"));

		} else {

			Game mygame = Game.find.byId(game);

			return ok(new File(mygame.getFile()).length() + "");
		}
	}

	public static Result getGQPlayer(String filename) {
		response().setContentType("image");
		byte[] i_file = null;

		try {
			i_file = IOUtils.toByteArray(new FileInputStream(new File(
					"public/gqplayer/" + filename)));
		} catch (FileNotFoundException e) {
			return ok("404: public/gqplayer/" + filename);
		} catch (IOException e) {
			return ok("404: public/gqplayer/" + filename);
		}

		return ok(i_file);
	}

	public static Result at(String filename) {
		response().setContentType("image");
		byte[] i_file = null;

		try {
			i_file = IOUtils.toByteArray(new FileInputStream(new File(
					"public/uploads/" + filename)));
		} catch (FileNotFoundException e) {
			return ok("404: public/uploads/" + filename);
		} catch (IOException e) {
			return ok("404: public/uploads/" + filename);
		}

		return ok(i_file);
	}

	public static User getLocalUser(final Session session) {
		final AuthUser currentAuthUser = PlayAuthenticate.getUser(session);
		final User localUser = User.findByAuthUserIdentity(currentAuthUser);
		return localUser;
	}

	public static ProviderPortal getLocalPortal() {

		return Application.getCurrentPortal(session());

	}

	/*
	 * FORM HANDLER
	 */

	public static class UploadedGame {

		@Required
		@MinLength(3)
		public String name;

		public long gametype;

		public boolean publ;

	}

	public static final Form<UploadedGame> UPLOAD_FORM = form(UploadedGame.class);

	public static class GameToGameType {

		@Required
		@MinLength(3)
		public String name;

		public String scenename;

		public boolean onlyhotspot;

	}

	public static final Form<GameToGameType> GAMETYPE_FORM = form(GameToGameType.class);

	public static class PostedPortal {

		@Required
		@MinLength(3)
		public String name;

		public String htmlurl;

		public String formname;

		public String pwfield;

		public String password;

		public String userfield;

		public String user;

		public String afterloginurl;

		public boolean autoverify;

		public String submitbutton;

		public String mappingurl;

		public String customserverurl;

		public String posturl;

		public String addcss;

		public String color;
		public String color_2;
		public String color_3;
		public String color_4;
		public String color_5;

	}

	public static final Form<PostedPortal> PORTAL_FORM = form(PostedPortal.class);

	public static class UserSearch {

		public String name;

		public String email;

	}

	public static final Form<UserSearch> USER_SEARCH_FORM = form(UserSearch.class);

}
