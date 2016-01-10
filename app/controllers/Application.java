package controllers;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import play.Logger;
import util.Global;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.H2Platform;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;

import models.*;
import play.Routes;
import play.data.Form;
import play.mvc.*;
import play.mvc.Http.Response;
import play.mvc.Http.Session;
import play.i18n.Lang;
import providers.MyUsernamePasswordAuthProvider;
import providers.MyUsernamePasswordAuthProvider.MyLogin;
import providers.MyUsernamePasswordAuthProvider.MySignup;
import views.html.*;
import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider;
import com.feth.play.module.pa.user.AuthUser;




public class Application extends Controller {

	public static final String FLASH_MESSAGE_KEY = "message";
	public static final String FLASH_ERROR_KEY = "error";
	public static final String USER_ROLE = "user";
	public static final String ADMIN_ROLE = "admin";
	public static final String UNVERIFIED_ROLE = "unverified";

	
	
	
	
	
	public static Result index() {

		session("currentportal", Global.defaultportal.getId().toString());

		return redirect(routes.Portal.myGamesList(61L));
	}

	public static Result portalindex(Long pid) {

		return redirect(routes.Portal.myGamesList(pid));
	}

	public static Result getDefaultTemplate() {

		return ok(views.html.defaulttemplate.render());
	}

	public static Result getTestingTemplate() {

		return ok(views.html.testingtemplate.render());
	}

	public static Result getTestMapping() {

		return ok(views.html.testingmapping.render());
	}

	public static Result getUserRoleByPortal(Long pid) {

		return ok(views.html.string_getuserrole.render());

	}
	
	
	public static Result getDatatables() {

		return ok(views.html.dt_geoquest.render());

	}
	
	public static Result getDatatablesJquery() {

		return ok(views.html.dt_Jquery.render());

	}

	public static String getUserRoleOfCurrentPortal() {

		String i = "";
		if (getLocalPortal().existsUser(getLocalUser(session()))) {
			i = getLocalPortal().getUser(getLocalUser(session())).getRights();
		}

		return i;

	}
	
	
	

	public static Result createDevice(String name, String deviceid) {

		
		
		

if(	Device.find.where().eq("deviceid", deviceid).findRowCount() != 1){
		
	Device d = new Device(name, deviceid);
	d.save();
String s =	d.generateCode();
	d.update();
	return ok(s);
		
		
	} else {
		
		Device d = Device.find.where().eq("deviceid",deviceid).findUnique();
		
		
		return ok(d.code);
	}

	}
	
	
	
	
	public static Result addDeviceByCode(Long uid, String code){
		
		
		
		
		if (User.find.where().eq("id", uid).findRowCount() == 1) {

			User u2 =User.find.byId(uid);
		
		
	if(	Device.find.where().eq("code", code).findRowCount() != 1){
		
		
		return ok("Code not found");
		
	} else {
		
		Device d = Device.find.where().eq("code",code).findUnique();
		
	
		
		if(!u2.paireddevices.contains(d)){
		u2.paireddevices.add(d);
		System.out.println(u2.paireddevices.size());
		u2.update();
	
		
		return ok(d.name);
		} else {
			
			return ok("Device is already in list");

			
		}
}	
		
		} else {
			return ok("User not found");

			
			
		}
		
	}
	
	
	
	public static Result pushQuestToDevice(Long did, Long gid){
		
		
		if(	Device.find.where().eq("id", did).findRowCount() != 1){
			
			
			return ok("DEVICE NOT FOUND");
			
		} else {
			
			Device d = Device.find.where().eq("id",did).findUnique();
			
			
			
			if (Game.find.where().eq("id", gid).findRowCount() != 1) {

				return badRequest(views.html.norights
						.render("Das Spiel existiert nicht"));

			} else {
				

				if (Global.securityGuard.hasWriteRightsOnGame(
						Application.getLocalUser(session()), Game.find.byId(gid)) == false) {

					return badRequest(views.html.norights
							.render("Du benötigst Schreib-Rechte an diesem Spiel."));

				} else {
				

				
				Game g = Game.find.byId(gid);
				String s = g.createTestXML();
				d.quest = s;
				d.questpush = gid;
				d.update();
				return ok("ok");
				
				
				}
			}
			
		
			
			
	}
		
		
		
	}
	
	
	
public static Result getQuestPushes(String deviceid){
		
		
if(	Device.find.where().eq("deviceid", deviceid).findRowCount() != 1){
		
		
		return ok("BAD REQUEST");
		
	} else {
		
		Device d = Device.find.where().eq("deviceid",deviceid).findUnique();
		
	
		if(d.questpush == null){
			return ok("");

		} else 
		if(d.questpush== 0L){
		
		return ok("");
		} else {
			
			
			
		
			
			
			Long l = d.questpush;
			
			
			
			return ok(""+l);
		

			
		}
}	
		
		
		
	}
	
	


public boolean listAttributeContainsKey(String list, String key){
	
	
	
	

	String[] split = list.split(", ");
	
	for(String s : split){
		
		if(s.equals(key)){
			
			return true;
			
		}
		
		
	}
	
	
	return false;
	
}





public String removeKeyInList(String list, String key){
	
	
		
		String newList = "";
		String[] split = list.split(", ");
		
		int i = 0;
		for(String s : split){
			i++;
			if(!s.equals(key)){
				newList+= s;
			}
			
			if(i < split.length){
				newList+=", ";
			}
			
		}
		return newList;
		
	
	
	
}
public String addKeyInList(String list, String key){

	if(!listAttributeContainsKey(list,key)){

	
if(list.length() > 0){
	list += ", ";
}
list += key;
	}
return list;

}


	
	
	

	public static Result portalfourofour(Long pid, String path) {

		return ok(views.html.notfound.render("/" + path));

	}

	public static Result getQeeveeHtml() {

		return ok(views.html.template.render());

	}

	public static Result getGeoQuestHtml() {

		return ok(views.html.template_p.render());

	}

	public static Result getDataTableCSS() {

		return ok(views.html.datatables.render()).as("text/css");

	}

	/*
	 * HELPER FUNCTIONS
	 * 
	 * These are for example used to help routing to the page with the wished
	 * content and the global variables set to the right value.
	 */

	/*
	 * getLocalPortal() looks for the global variable currentportal. If it is
	 * not set, it looks at the defaultportal variable, but in most cases
	 * currentportal should be set, because it gets set to defaultportal onStart
	 * of Global.
	 */

	public static ProviderPortal getLocalPortal() {

		return getCurrentPortal(session());

	}

	public static boolean onDefaultProviderPortal() {

		return Global.securityGuard.isDefaultPortal();

	}

	public static ProviderPortal getPortalById(Long pid) {

		if (ProviderPortal.find.where().eq("id", pid).findRowCount() != 1) {
			return Global.defaultportal;

		} else {

			ProviderPortal p = ProviderPortal.find.byId(pid);

			return p;

		}

	}

	/*
	 * TOOLS
	 */

	public static String getInverseColor(String in) {

		String inColor = in;

		String rawInColor = inColor.substring(1, inColor.length());
		int rgb = Integer.parseInt(rawInColor, 16);

		Color inn = new Color(rgb);

		Color inverse = new Color(255 - inn.getRed(), 255 - inn.getGreen(),
				255 - inn.getBlue());

		String hex = "#" + Integer.toHexString(inverse.getRGB()).substring(2);
		return hex;
	}

	public static String getBrighterColor(String in) {

		String inColor = in;

		String rawInColor = inColor.substring(1, inColor.length());
		int rgb = Integer.parseInt(rawInColor, 16);

		Color inn = new Color(rgb);

		Color brighter = inn.brighter();
		Color brighter2 = brighter.brighter();

		String hex = "#" + Integer.toHexString(brighter2.getRGB()).substring(2);
		return hex;

	}

	public static String getDarkerColor(String in) {

		String inColor = in;

		String rawInColor = inColor.substring(1, inColor.length());
		int rgb = Integer.parseInt(rawInColor, 16);

		Color inn = new Color(rgb);

		Color darker = inn.darker();

		String hex = "#" + Integer.toHexString(darker.getRGB()).substring(2);
		return hex;

	}

	public static String getFontColor(String fontColor) {

		String rawFontColor = fontColor.substring(1, fontColor.length());
		int rgb = Integer.parseInt(rawFontColor, 16);

		Color c = new Color(rgb);

		float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(),
				null);

		float brightness = hsb[2];

		if (brightness < 0.5) {
			return "#FFFFFF";
		} else {

			return "#000000";
		}

	}

	/*
	 * setPidRedirect and setPidRdirectWithAppendix are used to set the global
	 * variable currentportal. If any page in routes is opened with a /:id/ in
	 * front of the page adress, this function gets called. It will then set the
	 * currentportal to the ProviderPortal with the id = pid and redirect the
	 * user to the page that is set in linkto. If the page needs another
	 * variable the function withAppendix can be called and the needed String
	 * from linkadd will be appended.
	 */

	/*
	 * Play Authenticate Code
	 */

	public static User getLocalUser(final Session session) {
		final AuthUser currentAuthUser = PlayAuthenticate.getUser(session);
		final User localUser = User.findByAuthUserIdentity(currentAuthUser);
		return localUser;
	}
	
	
	
	public static User getLocalUser() {

		return getLocalUser(session());
	}
	
	
	
	
	


	public static ProviderPortal getCurrentPortal(final Session session) {

		String pid = session("currentportal");

		if (pid != null) {

			Long pidlong = Long.parseLong(pid, 10);

			if (ProviderPortal.find.where().eq("id", pidlong).findRowCount() == 1) {

				return ProviderPortal.find.byId(pidlong);

			} else {

				return Global.defaultportal;

			}
		} else {

			return ProviderPortal.find.byId(61L);

		}

	}
	
	
	


	@Restrict(@Group(Application.USER_ROLE))
	public static Result profile(Long pid) {
		session("currentportal", pid.toString());
		final User localUser = getLocalUser(session());
		return ok(profile.render(localUser));
	}

	public static Result login(Long pid) {
		session("currentportal", pid.toString());

		if (pid == 1L && Global.SECURED_MODE) {

			return redirect("https://quest-mill.com/geoquest/private.php");
		} else if (pid == 61L) {

			return ok(views.html.portal.publicportal_login.render());

		} else {

			return ok(login.render(MyUsernamePasswordAuthProvider.LOGIN_FORM));

		}
	}
	
	
	
	
	public static Result setLanguage(String language) {
	
		
		
		session("geoquest_language", language);
		Controller.changeLang(language);
		return ok(language);
	}
	
	
	
	
	 public static String getLanguageCode(){
		
		
		

    	
    	String language = session("geoquest_language");

		if (language == null) {
			
			
			if(getLocalPortal().getContentHtmlParameter("general.defaultlanguage") != null){
				
				language = getLocalPortal().getContentHtmlParameter("general.defaultlanguage");
				
			} else {
				
				//Lang lang = Lang.preferred();
				language = "de";
				Lang lang = Lang.preferred(request().acceptLanguages());
				language = lang.code();
				
			}
		}
		
		
		return language;
		
	}
	
	public static String getLanguage(String code){
		
		
		
		String translation = code;
    	
    	String language = session("geoquest_language");

		if (language == null) {
			
			
			if(getLocalPortal().getContentHtmlParameter("general.defaultlanguage") != null){
				
				language = getLocalPortal().getContentHtmlParameter("general.defaultlanguage");
				
			} else {
				
				//Lang lang = Lang.preferred();
				language = "de";
				Lang lang = Lang.preferred(request().acceptLanguages());
				language = lang.code();
				
			}
		}
    	
		
		if(!language.equals("de")){
		    			
			
			
			if(language.equals("en")){
				
				if(Global.en_Translation.containsKey(code)){
					
					translation = Global.en_Translation.get(code);
					
				}
				
				
			}
			
			
			code = language+"_"+code;


		}
		

		
		if(getLocalPortal().getLanguageParameter(code) != null){
			
			translation = getLocalPortal().getLanguageParameter(code);
			
		}
		
		
		
		
		return translation;
		
		
		
		
	}
	
	
	
	
	


	public static Result loginToPortalFromCache() {
		return ok(login.render(MyUsernamePasswordAuthProvider.LOGIN_FORM));
	}

	public static Result login2(Long pid, Long pid2) {
		session("currentportal", pid.toString());
		return ok(login.render(MyUsernamePasswordAuthProvider.LOGIN_FORM));
	}

	public static Result doLogin(Long pid) {
		session("currentportal", pid.toString());
		com.feth.play.module.pa.controllers.Authenticate.noCache(response());
		final Form<MyLogin> filledForm = MyUsernamePasswordAuthProvider.LOGIN_FORM
				.bindFromRequest();
		if (filledForm.hasErrors()) {
			// User did not fill everything properly
			return badRequest(login.render(filledForm));
		} else {
			// Everything was filled
			return UsernamePasswordAuthProvider.handleLogin(ctx());
		}
	}

	public static Result signup(Long pid) {
		session("currentportal", pid.toString());

		if (pid == 1L) {

			return redirect("https://quest-mill.com/geoquest/private.php");
		} else if (pid == 61L) {

			return ok(views.html.portal.publicportal_signup.render());

		} else {
			return ok(signup.render(MyUsernamePasswordAuthProvider.SIGNUP_FORM));

		}
	}

	public static Result jsRoutes() {
		return ok(
				Routes.javascriptRouter("jsRoutes",
						controllers.routes.javascript.Signup.forgotPassword()))
				.as("text/javascript");
	}

	public static Result doSignup(Long pid) {
		session("currentportal", pid.toString());

		com.feth.play.module.pa.controllers.Authenticate.noCache(response());

		final Form<MySignup> filledForm = MyUsernamePasswordAuthProvider.SIGNUP_FORM
				.bindFromRequest();

		if (filledForm.hasErrors()) {
			// User did not fill everything properly
			return badRequest(signup.render(filledForm));
		} else {
			// Everything was filled
			// do something with your part of the form before handling the user
			// signup

			return UsernamePasswordAuthProvider.handleSignup(ctx());
			
		}

	}

	public void comment(String s) {

	}

	public static String formatTimestamp(final long t) {
		return new SimpleDateFormat("yyyy-dd-MM HH:mm:ss").format(new Date(t));
	}

	public static Result dologout(Long pid) {

		session("currentportal", pid.toString());

		return redirect(Global.SERVER_URL_2 + "/dologout");

	}

	public static Result doauthenticate(Long pid, String provider) {

		session("currentportal", Global.defaultportal.getId().toString());

		return redirect(Application.getLocalPortal()
				.getTemplateServerURLDropSlash() + "/authenticate/" + provider);

	}

}