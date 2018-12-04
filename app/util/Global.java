package util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import models.ProviderPortal;
import models.SecurityRole;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.PlayAuthenticate.Resolver;
import com.feth.play.module.pa.exceptions.AccessDeniedException;
import com.feth.play.module.pa.exceptions.AuthException;

import controllers.routes;
import controllers.Portal;
import gametypes.*;
import play.Application;
import play.GlobalSettings;
import play.mvc.Call;
import scala.concurrent.duration.Duration;
import security.MyUserPortalRights;
import play.libs.Akka;
import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import akka.actor.UntypedActorFactory;
import akka.actor.UntypedActor;
import akka.actor.Props;
import akka.actor.ActorRefFactory;
import akka.util.*;

public class Global extends GlobalSettings {

	public static final String GEOQUEST_VERSION = "0.1.20";
	
	public static boolean DEBUGGING = true;
	public static void Log(String message) {
		if (DEBUGGING) {
			System.out.println(message);
		}
	}

	private static String SERVER_URL_2_FALLBACK = "https://quest-mill.intertech.de"; 
	public static String SERVER_URL_2;
	public static String SERVER_URL;
	
	public static String TEMPLATE_BASE_URL = "http://qeevee.org:9091";
	
	public static boolean SECURED_MODE = true;

	static {
		SERVER_URL_2 = System.getenv("GQ_EDITOR_BASE_URL");
		if (SERVER_URL_2 == null)
			SERVER_URL_2 = SERVER_URL_2_FALLBACK;

		SERVER_URL = SERVER_URL_2 + "/";
		
		if (System.getenv("GQ_EDITOR_MODE_INSECURE") != null) {
			SECURED_MODE = false;
		}
	}
	
	public static final String REGEXP_NUM = "(\\s*)[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)(\\s*)";
	
	public static Map<String,String> en_Translation;
	public static Map<String,String> fr_Translation;
	public static Map<String,String> es_Translation;


	public static ProviderPortal defaultportal;
	public static MyUserPortalRights securityGuard;

	public static void updatePortals() {

		ProviderPortal d = ProviderPortal.find.byId(defaultportal.getId());

		defaultportal = d;

	}

	public void onStart(Application app) {

		securityGuard = new MyUserPortalRights();

		if (ProviderPortal.find.findRowCount() != 0) {

			if (defaultportal == null) {

				if (ProviderPortal.find.findRowCount() != 0) {

					defaultportal = ProviderPortal.find.byId(1L);
				} else {

					defaultportal = new ProviderPortal("Geoquest Webservice",
							SERVER_URL + "/qeeveetemplates/qeeve");
					defaultportal.save();
					defaultportal.update();

				}
			}

			// // CHECK IF GAMETYPES EXIST

			if (!defaultportal.hasGameType("beliebiges Spiel")) {

				GeoQuestDefaultsFactory factory = new GeoQuestDefaultsFactory();

				defaultportal.addGameType(factory.addGameToDatabase());
				defaultportal.update();
				System.out.println("GameType erstellt: beliebiges Spiel");

			}
			
			
			// LANGUAGE MAP INIT
			
			en_Translation =  new HashMap();
			fr_Translation =  new HashMap();
			es_Translation =  new HashMap();
			
			TranslationFactory trfactory = new TranslationFactory();
			
			trfactory.buildTranslationMaps();
			

		


			// // AKKA ACTORS

			ActorRef templateActor = Akka.system().actorOf(
					(new Props().withCreator(new UntypedActorFactory() {
						public UntypedActor create() {
							return new UntypedActor() {
								public void onReceive(Object message) {
									if (message.equals("log")) {
										// Do something
										// controllers.Application.log();

										System.out
												.println("Template Update...");
										List<ProviderPortal> allportals = ProviderPortal.find
												.all();

										for (ProviderPortal aportal : allportals) {

											System.out.println("Updating "
													+ aportal.getName());

											aportal.updateHtmlByTemplateNoPassword();

										}

										System.out.println("...done");

									} else {
										unhandled(message);
									}
								}
							};
						}
					})));

			Akka.system()
					.scheduler()
					.schedule(Duration.create(0, TimeUnit.SECONDS),
							Duration.create(60, TimeUnit.MINUTES),
							templateActor, "log", Akka.system().dispatcher());

			// / PLAY AUTHENTICATE CODE

			PlayAuthenticate.setResolver(new Resolver() {

				@Override
				public Call login() {
					// Your login page
					return routes.Application.login(Portal.getLocalPortal()
							.getId());
				}

				@Override
				public Call afterAuth() {
					// The user will be redirected to this page after
					// authentication
					// if no original URL was saved
					return routes.Portal.myGamesList(Portal.getLocalPortal()
							.getId());
				}

				@Override
				public Call afterLogout() {
					return routes.Portal.publicGamesList(Portal
							.getLocalPortal().getId());
				}

				@Override
				public Call auth(final String provider) {
					// You can provide your own authentication implementation,
					// however the default should be sufficient for most cases
					return com.feth.play.module.pa.controllers.routes.Authenticate
							.authenticate(provider);
				}

				@Override
				public Call askMerge() {
					return routes.Account.askMerge(Portal.getLocalPortal()
							.getId());
				}

				@Override
				public Call askLink() {
					return routes.Account.askLink(Portal.getLocalPortal()
							.getId());
				}

				@Override
				public Call onException(final AuthException e) {
					if (e instanceof AccessDeniedException) {
						return routes.Signup.oAuthDenied(Portal
								.getLocalPortal().getId(),
								((AccessDeniedException) e).getProviderKey());
					}

					// more custom problem handling here...
					return super.onException(e);
				}
			});

			initialData();

		}

	}

	private void initialData() {
		if (SecurityRole.find.findRowCount() == 0) {
			for (final String roleName : Arrays
					.asList(controllers.Application.USER_ROLE)) {
				final SecurityRole role = new SecurityRole();
				role.roleName = roleName;
				role.save();
			}
		}

	}
}
