
package models;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;

import controllers.Editor;
import flexjson.JSONSerializer;
import play.api.Play;
import play.db.ebean.Model;
import play.api.Play.*;
import play.db.ebean.Model.Finder;

import javax.imageio.ImageIO;
import javax.persistence.*;
import javax.xml.stream.events.StartDocument;

import models.GameParts.GameType;
import models.GameParts.MissionType;

import java.awt.Color;
import java.awt.Image;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.gargoylesoftware.htmlunit.*;

import controllers.Application;
import scala.Char;
import util.Global;

@Entity
@Table(name = "providerportal")
public class ProviderPortal extends Model {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final String USER_ROLE = "user";
    public static final String ADMIN_ROLE = "admin";
    public static final String UNVERIFIED_ROLE = "unverified";

    /**
     * Variables
     */

    @Id
    public Long id;
    public String name;
    @Lob
    public String TemplateURL;
    @Lob
    public String TemplateMappingURL;
    @Lob
    public String TemplatePostURL;
    public String TemplateUserField;
    public String TemplateUser;
    public String TemplateUserXPATH;
    public String TemplatePw;
    public String TemplatePwXPATH;
    public String TemplateForm;
    public String TemplateFormField;
    public String TemplateSubmitButton;

    @Lob
    public String TemplateAfterLoginURL;
    @Lob
    public String TemplateServerURL;

    @Lob
    public String AdditionalCSS;

    @Lob
    public String portalimprint;

    @Lob
    public String portalagbs;

    public Integer portalagbsversion;

    @Lob
    public String portalprivacyagreement;

    public Integer portalprivacyagreementversion;

    @Lob
    public String imprint;

    @Lob
    public String agbs;

    public Integer agbsversion;

    @Lob
    public String privacyagreement;

    public String getAppPrivacyAgreement() {
        return privacyagreement;
    }

    public Integer privacyagreementversion;

    public String designpreset;
    public String defaultcolor;

    public String color2;
    public String color3;
    public String color4;
    public String color5;
    public String color6;
    public String logoimg;

    public boolean autoVerifyUsers = true;

    @ManyToMany
    @OrderBy("datum desc")
    private List<NewsstreamItem> Newsstream;

    @ManyToMany
    private List<GameType> gameTypes;

    @ManyToMany
    private List<MissionType> missionTypes;

    @ManyToMany
    @OrderBy("zahl")
    private List<SortedHtml> Html;

    @Lob
    private String specialCss;

    @ManyToMany
    private Set<TemplateParameter> Mapping;

    @OneToMany(mappedBy = "portal")
    public List<ProviderUsers> userList;

    @OneToMany(mappedBy = "portal")
    public List<ProviderGames> GameList;

    /**
     * Constructors
     */

    public ProviderPortal(String startName) {
        name = startName;
        TemplateURL = Global.TEMPLATE_BASE_URL + "/defaulttemplate";

        TemplateMappingURL = "";
        TemplateForm = "";
        TemplateFormField = "";
        TemplatePw = "";
        TemplateUserField = "";
        TemplateUser = "";
        TemplateAfterLoginURL = "";
        TemplateServerURL = "";
        TemplateSubmitButton = "";
        TemplatePostURL = "";
        AdditionalCSS = "";
        defaultcolor = "#ffccff";
        autoVerifyUsers = true;

        agbsversion = 0;
        privacyagreementversion = 0;

        designpreset = "default";
        color2 = "#ffccff";
        color3 = "#ffccff";
        color4 = "#ffccff";
        color5 = "#ffccff";
        logoimg = "";

        updateHtmlByTemplateNoPassword();

    }

    public ProviderPortal(String startName, String url) {
        name = startName;
        TemplateURL = url;
        TemplateSubmitButton = "";
        TemplateServerURL = "";
        TemplateForm = "";
        TemplateFormField = "";
        TemplatePw = "";
        TemplateUserField = "";
        TemplateUser = "";
        TemplateAfterLoginURL = "";
        TemplateServerURL = "";
        TemplatePostURL = "";
        AdditionalCSS = "";
        defaultcolor = "#ffccff";
        autoVerifyUsers = true;
        TemplateMappingURL = "";

        agbsversion = 0;
        privacyagreementversion = 0;

        designpreset = "default";
        color2 = "#ffccff";
        color3 = "#ffccff";
        color4 = "#ffccff";
        color5 = "#ffccff";
        logoimg = "";

        boolean done = updateHtmlByTemplateNoPassword();
        if (done == false) {
            TemplateURL = Global.SERVER_URL + "defaulttemplate";
            updateHtmlByTemplateNoPassword();

        }

    }

    public ProviderPortal(String startName, String url, String tf, String tpwf, String tuf, String tpw, String tu,
                          String alurl, boolean av, String button, String murl, String csurl, String purl, String acss, String clr,
                          String dpr, String c2, String c3, String c4, String c5, String limg) {

        name = startName;
        TemplateURL = url;
        TemplateAfterLoginURL = alurl;
        TemplateForm = tf;
        TemplateFormField = tpwf;
        TemplatePw = tpw;
        TemplateUserField = tuf;
        TemplateUser = tu;
        autoVerifyUsers = av;
        TemplateSubmitButton = button;
        TemplateMappingURL = murl;
        TemplateServerURL = csurl;
        TemplatePostURL = purl;
        AdditionalCSS = acss;
        defaultcolor = clr;
        agbsversion = 0;
        privacyagreementversion = 0;
        designpreset = dpr;
        color2 = c2;
        color3 = c3;
        color4 = c4;
        color5 = c5;
        logoimg = limg;

        boolean done = updateHtmlByTemplateNoPassword();

        if (done == false) {
            TemplateURL = Global.TEMPLATE_BASE_URL + "/defaulttemplate";
            updateHtmlByTemplateNoPassword();

        }

    }

    /**
     * Functions
     */

    public boolean updateHtmlByTemplateNoPassword() {

        if (!TemplateMappingURL.equals("")) {
            computeMapping();
        }
        String url = TemplateURL;

        boolean correct_template = false;

        if (TemplateURL.equals("")) {

            url = Global.TEMPLATE_BASE_URL + "/defaulttemplate";

        }
        boolean done = true;
        int counter = 0;

        List<SortedHtml> Html2 = new ArrayList<SortedHtml>();

        String pagecontent = "";

        // System.out.println("########### Starte Template Update");

        WebClient webClient = new WebClient();
        try {

            WebRequest webRequest = new WebRequest(new URL(TemplateURL));
            webRequest.setCharset("utf-8");

            webClient.setThrowExceptionOnFailingStatusCode(false);
            webClient.setThrowExceptionOnScriptError(false);

            webClient.setPrintContentOnFailingStatusCode(false);
            webClient.setJavaScriptEnabled(false);
            webClient.setRedirectEnabled(true);
            webClient.setTimeout(20000);

            HtmlPage page = webClient.getPage(webRequest);

            boolean use_first_page = true;

            HtmlForm form = null;

            if (TemplatePw != null && !TemplatePw.equals("")) {

                use_first_page = false;

                // GET ALL INFORMATION

                if (!TemplateForm.equals("")) {

                    form = page.getFormByName(TemplateForm);
                } else {

                    // XPATH

                    try {

                        if (page != null) {
                            form = (HtmlForm) page.getByXPath("/html/body//form").get(0);
                        }

                    } catch (RuntimeException re) {
                        System.out.println("Xpath not found: form");
                    }

                }

                try {

                    HtmlSubmitInput button = null;

                    if (!TemplateSubmitButton.equals("")) {

                        button = form.getInputByName(TemplateSubmitButton);

                    } else {

                        // XPATH
                        try {
                            button = (HtmlSubmitInput) page.getByXPath("//input[@type='submit']").get(0);
                        } catch (RuntimeException re) {
                            System.out.println("Xpath not found: button");
                            use_first_page = true;
                        }

                    }

                    if (!TemplateUser.equals("")) {
                        String type = "";

                        if (!TemplateUserField.equals("")) {
                            try {
                                type = form.getInputByName(TemplateUserField).getTypeAttribute();
                            } catch (RuntimeException re) {
                                System.out.println("Given DOM-ID not found: username");
                            }
                        } else {
                            try {
                                final HtmlTextInput textField2 = (HtmlTextInput) page
                                        .getByXPath("//input[@type='text']").get(0);

                                textField2.setValueAttribute(TemplateUser);
                            } catch (RuntimeException re) {
                                System.out.println("Xpath not found: username");
                            }

                        }

                        // System.out.println("#### Template Formular User Daten gefunden: "+type);

                        if (type.equals("password")) {

                            try {
                                final HtmlPasswordInput textField2 = form.getInputByName(TemplateUserField);

                                textField2.setValueAttribute(TemplateUser);
                                // System.out.println("#### Template Formular User Daten eingesetzt");
                            } catch (RuntimeException re) {
                                System.out.println("Given DOM-ID not found: username");
                            }

                        } else if (type.equals("text")) {

                            try {
                                final HtmlTextInput textField2 = form.getInputByName(TemplateUserField);

                                textField2.setValueAttribute(TemplateUser);
                                // System.out.println("#### Template Formular User Daten eingesetzt");
                            } catch (RuntimeException re) {
                                System.out.println("Given DOM-ID not found: username");
                            }

                        }

                        // System.out.println("#### Template Formular User Daten eingesetzt");
                    }

                    if (!TemplatePw.equals("")) {

                        String type = "";

                        if (!TemplateFormField.equals("")) {

                            try {
                                type = form.getInputByName(TemplateFormField).getTypeAttribute();
                            } catch (RuntimeException re) {
                                System.out.println("Given DOM-ID not found: password");
                            }

                        } else {

                            // XPATH - GEHE DAVON AUS DAS ES EIN PASSWORTFELD IST

                            try {
                                final HtmlPasswordInput textField2 = (HtmlPasswordInput) page
                                        .getByXPath("//input[@type='password']").get(0);

                                textField2.setValueAttribute(TemplatePw);

                                System.out.println("### Using first Password Field by XPATH");
                            } catch (RuntimeException re) {
                                System.out.println("Xpath not found: password");
                            }

                        }

                        // System.out.println("#### Template Formular Password Daten gefunden:
                        // /"+type+"/");

                        if (type.equals("password")) {

                            try {
                                final HtmlPasswordInput textField2 = form.getInputByName(TemplateFormField);

                                textField2.setValueAttribute(TemplatePw);
                                // System.out.println("#### Template Formular Passwort Daten eingesetzt");
                            } catch (RuntimeException re) {
                                System.out.println("Given DOM-ID not found: password");
                            }

                        } else if (type.equals("text")) {

                            try {
                                final HtmlTextInput textField2 = form.getInputByName(TemplateFormField);

                                textField2.setValueAttribute(TemplatePw);
                                // System.out.println("#### Template Formular Passwort Daten eingesetzt");
                            } catch (RuntimeException re) {
                                System.out.println("Given DOM-ID not found: password");
                            }

                        }

                    }

                    if (use_first_page != true) {
                        // Now submit the form by clicking the button and get back the second page.
                        page = button.click();
                        pagecontent = page.getWebResponse().getContentAsString();
                    }

                } catch (ElementNotFoundException e) {

                    pagecontent = page.getWebResponse().getContentAsString();

                }

            }

            /// AUF AFTERLOGIN WECHSELN

            if (!TemplateAfterLoginURL.equals("")) {
                WebRequest webRequest2 = new WebRequest(new URL(TemplateAfterLoginURL));
                webRequest2.setCharset("utf-8");

                page = webClient.getPage(webRequest2);
            }

            pagecontent = page.getWebResponse().getContentAsString();

        } catch (IOException ioe) {
            pagecontent = "Problem accessing page...";
        }

        ///// MAPPING

        if (!Mapping.isEmpty()) {
            for (TemplateParameter atp : Mapping) {
                pagecontent = pagecontent.replaceAll(atp.getValue(), atp.getName());
            }
        }

        List<String> searchfor = new ArrayList<String>() {
            {

                /*
                 * Menü List Item
                 */
                add("%%_GEOQUEST_NAV_LI_%%");
                /*
                 * Start of Pagecontent
                 */
                add("%%_GEOQUEST_CONTENT_CODE_START_%%");
                /*
                 * get Portal Titel
                 */
                add("%%_GEOQUEST_PORTAL_NAME_%%");
                /*
                 * get Version Number
                 */
                add("%%_GEOQUEST_SERVER_VERSION_%%");
                /*
                 * get Portal Id
                 */
                add("%%_GEOQUEST_PORTAL_ID_%%");
                /*
                 * get User Login Info
                 */
                add("%%_GEOQUEST_USER_INFO_%%");
                /*
                 * get Page Title
                 */

                add("%%_GEOQUEST_CONTENT_TITLE_%%");
                /*
                 * End of Pagecontent
                 */
                add("%%_GEOQUEST_CONTENT_CODE_END_%%");

                /*
                 * Default Color
                 */
                add("%%_GEOQUEST_DEFAULT_COLOR_%%");
                /*
                 * Header
                 */
                add("<meta name='%%_GEOQUEST_HEADER_FUNCTIONS_%%'>");
                add("<meta name=\"%%_GEOQUEST_HEADER_FUNCTIONS_%%\"/>");
                /*
                 * Server URL
                 */
                add("%%_GEOQUEST_SERVER_URL_%%");
                /*
                 * Script Divs for loading from original source
                 */
                add("%%_GEOQUEST_SCRIPT_DIV_%%");
                /*
                 * URL to LOGO defined in Portal Settings
                 */
                add("%%_GEOQUEST_YOUR_LOGO_%%");

            }
        };

        List<Couple> couples = new ArrayList<Couple>();

        /*
         * ADD END OF CODE
         */
        couples.add(new Couple("%%_GEOQUEST_END_OF_HTML_FILE_%%", pagecontent.length() + 1));

        for (String lookingfor : searchfor) {

            if (pagecontent.contains(lookingfor) == true) {

                correct_template = true;

                int lastIndex = 0;

                int checkforloop = 10;

                while (lastIndex >= 0 && checkforloop > 0) {

                    lastIndex = pagecontent.indexOf(lookingfor, lastIndex + 1);

                    if (lastIndex != -1) {
                        // System.out.println("Gefunden: "+lookingfor+1);

                        couples.add(new Couple(lookingfor, lastIndex));

                        counter++;
                    }

                    if (checkforloop < 2) {

                        // System.out.println("Loop gefunden: "+lookingfor);

                    }

                    checkforloop--;

                }

            }

        }

        // System.out.println("########### Starte Hinzufügen zu Html-Liste");

        int sort = 0;
        String rest = pagecontent;

        while (!couples.isEmpty()) {

            int min = -1;

            Couple currentcouple = new Couple("", 0);
            for (Couple acouple : couples) {

                if (acouple.getZahl() < min | min < 0) {
                    currentcouple = acouple;
                    min = acouple.getZahl();

                    // System.out.println("Neues Minimum: "+acouple.getWort());

                }

            }

            // System.out.println("################ Starte Suche nach:
            // "+currentcouple.getWort());

            // System.out.println(rest);

            if (rest.contains(currentcouple.getWort())) {

                String[] split = rest.split(currentcouple.getWort());

                SortedHtml ns;
                String parameterlist = "";

                StringBuilder sb = new StringBuilder();

                String[] splits = split;

                if (split[1].charAt(0) == '{') {

                    // split at } = splits

                    splits = split[1].split("}");

                    // remove { from splits[0]

                    // rest = all splits from splits[1] on

                    for (int i = 1; i < splits.length; i++) {

                        if (i == splits.length - 1) {
                            sb.append(splits[i]);

                        } else {

                            sb.append(splits[i] + "}");

                        }

                        if (i + 1 < split.length) {
                            sb.append(currentcouple.getWort());
                        }

                    }

                    // rest + all following from split if split longer than 2

                    if (split.length > 2) {

                        // System.out.println("SPLITS LONGER THAN 2");

                        for (int i = 2; i < split.length; i++) {
                            sb.append(split[i]);

                            if (i + 1 < split.length) {
                                sb.append(currentcouple.getWort());
                            }

                        }
                    }

                    // add splits[0] as propertylist to Html

                    parameterlist = splits[0];

                    parameterlist = parameterlist.replace("{", "");

                    // System.out.println("PARAMETERLIST FOUND: "+parameterlist);

                } else {

                    for (int i = 1; i < split.length; i++) {
                        sb.append(split[i]);

                        if (i + 1 < split.length) {
                            sb.append(currentcouple.getWort());
                        }

                    }

                }

                rest = sb.toString();

                sort++;

                ns = new SortedHtml(split[0], sort, "");
                ns.save();
                Html2.add(ns);

                sort++;

                ns = new SortedHtml(currentcouple.getWort(), sort, parameterlist);
                ns.save();
                Html2.add(ns);

                // System.out.println("Adding Html: "+currentcouple.getWort());

            }

            if (currentcouple.getWort() == "%%_GEOQUEST_END_OF_HTML_FILE_%%") {

                sort++;

                // System.out.println("######### END OF HTML");

                SortedHtml ns = new SortedHtml(rest, sort, "");
                ns.save();
                Html2.add(ns);

            }

            couples.remove(currentcouple);

        }

        if (counter < 1) {
            done = false;
        }

        /*
         * Html Leeren
         */

        if (correct_template == true) {

            if (!Html.isEmpty()) {

                Set<SortedHtml> sh = new HashSet<SortedHtml>();
                sh.addAll(Html);
                for (SortedHtml onesh : sh) {

                    Html.remove(onesh);
                    this.update();

                    onesh.removeMe();
                    onesh.delete();

                }
            }

            Html = Html2;

        } else if (Html.isEmpty()) {

            if (TemplateURL.equals(Global.TEMPLATE_BASE_URL + "/defaulttemplate")) {

                SortedHtml ns = new SortedHtml("Template-Seite wurde nicht erkannt.", 1, "%errortemplate:=true%");
                ns.save();
                Html.add(ns);

            } else {

                if (ProviderPortal.find.findRowCount() == 0) {
                    SortedHtml ns = new SortedHtml("Initializing...", 1, "%errortemplate:=true%");
                    ns.save();
                    done = true;
                    Html.add(ns);
                } else {
                    TemplateURL = Global.TEMPLATE_BASE_URL + "/defaulttemplate";
                    updateHtmlByTemplateNoPassword();
                }
            }
        } else {
            System.out.println("Template-Update ignored..");
            // evtl. neue Template-Update Übersicht erstellen
        }

        this.save();
        return done;

    }

    public Set<User> searchForUserByName(String x) {

        Set<User> retuser = new HashSet<User>();

        for (ProviderUsers pu : userList) {

            String have = pu.getUser().getName().toLowerCase();
            String find = x.toLowerCase();

            if (have.contains(find) | have == find) {

                retuser.add(pu.getUser());

            }

        }

        return retuser;

    }

    public Set<User> searchForUserByEmail(String x) {

        Set<User> retuser = new HashSet<User>();

        for (ProviderUsers pu : userList) {

            String have = pu.getUser().getEmail().toLowerCase();
            String find = x.toLowerCase();

            if (have.contains(find) | have == find) {

                retuser.add(pu.getUser());

            }

        }

        return retuser;

    }

    public boolean removeMe() {

        Set<ProviderGames> pg = new HashSet<ProviderGames>();
        pg.addAll(GameList);
        Set<ProviderUsers> pu = new HashSet<ProviderUsers>();
        pu.addAll(userList);
        Set<NewsstreamItem> ni = new HashSet<NewsstreamItem>();
        ni.addAll(Newsstream);

        try {

            for (ProviderGames onepg : pg) {

                GameList.remove(onepg);
                update();

                onepg.getGame().removeMe();
                try {
                    onepg.getGame().delete();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }

        try {
            for (ProviderUsers onepu : pu) {

                onepu.getUser().deletePortal(onepu);

                onepu.getUser().update();

                userList.remove(onepu);
                update();
                try {
                    onepu.delete();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }

        try {
            for (NewsstreamItem oneni : ni) {

                Newsstream.remove(oneni);
                update();

                if (oneni.getPosterClass() == "user") {

                    if (User.find.where().eq("id", oneni.getPosterid()).findRowCount() == 1) {

                        User.find.byId(oneni.getPosterid()).deleteNewsstreamItem(oneni);
                        ;
                    }

                }

                if (oneni.getPosterClass() == "game") {

                    if (Game.find.where().eq("id", oneni.getPosterid()).findRowCount() == 1) {

                        Game.find.byId(oneni.getPosterid()).deleteNewsstreamItem(oneni);
                        ;
                    }

                }

                try {
                    oneni.delete();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }

        if (!Html.isEmpty()) {

            Set<SortedHtml> sh = new HashSet<SortedHtml>();
            sh.addAll(Html);
            try {
                for (SortedHtml onesh : sh) {

                    Html.remove(onesh);
                    update();
                    try {
                        onesh.removeMe();

                        onesh.delete();
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                }
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }
        }

        if (!Mapping.isEmpty()) {
            Set<TemplateParameter> x = new HashSet<TemplateParameter>();
            x.addAll(Mapping);

            try {
                for (TemplateParameter atp : x) {

                    if (Mapping.contains(atp)) {

                        Mapping.remove(atp);
                        update();
                        try {
                            atp.delete();
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public void computeMapping() {

        if (!Mapping.isEmpty()) {
            Set<TemplateParameter> x = new HashSet<TemplateParameter>();
            x.addAll(Mapping);

            for (TemplateParameter atp : x) {

                if (Mapping.contains(atp)) {

                    Mapping.remove(atp);
                    this.update();
                    atp.delete();

                }

            }
        }

        WebClient webClient = new WebClient();
        try {

            WebRequest webRequest = new WebRequest(new URL(TemplateMappingURL));
            webRequest.setCharset("utf-8");

            webClient.setThrowExceptionOnFailingStatusCode(false);
            webClient.setThrowExceptionOnScriptError(false);
            HtmlPage page = webClient.getPage(webRequest);
            String search = page.getWebResponse().getContentAsString();

            String[] split = search.split("%%%%MAPPING%%%%");

            for (String astring : split) {

                if (astring.contains("::=")) {

                    String[] newstrings = astring.split("::=");

                    String nametoadd = newstrings[0];

                    if (nametoadd.contains("!!!\"")) {

                        nametoadd = nametoadd.split("!!!\"")[1];

                        nametoadd = nametoadd.split("\"!!!")[0];

                    } else {

                        nametoadd = nametoadd.replaceAll(" ", "");
                        nametoadd = nametoadd.replaceAll("\n", "");

                    }

                    String valueadd = newstrings[1].split("%%%%MAPPING%%%%")[0];

                    if (valueadd.contains("!!!\"")) {

                        valueadd = valueadd.split("!!!\"")[1];

                        valueadd = valueadd.split("\"!!!")[0];

                    } else {

                        valueadd = valueadd.replaceAll(" ", "");
                        valueadd = valueadd.replaceAll("\n", "");

                    }

                    TemplateParameter padd = new TemplateParameter(nametoadd, valueadd);

                    padd.save();

                    Mapping.add(padd);
                }

            }

        } catch (IOException ioe) {
            System.out.println("Problem accessing page " + ioe.getMessage());
        }
    }

    /**
     * Getter & Setter
     */

    public Set<TemplateParameter> getContentHtmlParameters(String code) {

        SortedHtml contenthtml = new SortedHtml("", 0, "");
        Set<TemplateParameter> specific = new HashSet<TemplateParameter>();

        for (SortedHtml ahtml : Html) {

            if (ahtml.getWort().equals("%%_GEOQUEST_CONTENT_CODE_START_%%")) {

                contenthtml = ahtml;

            }

        }

        for (TemplateParameter aparamter : contenthtml.getParameters()) {

            if (aparamter.getName().startsWith(code + ".")) {

                String help = aparamter.getName().replaceFirst(code + ".", "");

                specific.add(new TemplateParameter(help, aparamter.getValue()));

            }

        }

        return specific;

    }

    public String getContentHtmlParameter(String code) {

        SortedHtml contenthtml = new SortedHtml("", 0, "");

        for (SortedHtml ahtml : Html) {

            if (ahtml.getWort().equals("%%_GEOQUEST_CONTENT_CODE_START_%%")) {

                contenthtml = ahtml;

            }

        }

        for (TemplateParameter aparamter : contenthtml.getParameters()) {

            if (code.equals(aparamter.getName())) {

                String sentence = aparamter.getValue();

                return sentence;

            }

        }

        return null;
    }

    public String getLanguageParameter(String code) {

        String x = getContentHtmlParameter("general.language." + code);

        if (x != null) {

            return x;

        } else {

            if (code.equals("Öffentliche Spiele")) {

                code = "Oeffentliche_Spiele";

            } else if (code.equals("Meine Spiele")) {
                code = "Meine_Spiele";

            } else if (code.equals("Neues Spiel erstellen")) {

                code = "Neues_Spiel_erstellen";

            } else if (code.equals("Du hast bisher keine Spiele auf diesem Portal.")) {
                code = "Du_hast_keine_Spiele";

            } else if (code.equals("hat ein neues Spiel erstellt")) {
                code = "hat_ein_neues_Spiel_erstellt";

            }

            x = getContentHtmlParameter("general.language." + code);

            if (x != null) {

                return x;

            }

        }

        return null;

    }

    public Set<TemplateParameter> getHtmlParameters(String code) {

        SortedHtml contenthtml = new SortedHtml("", 0, "");

        for (SortedHtml ahtml : Html) {

            if (ahtml.getWort().equals(code)) {

                contenthtml = ahtml;

            }

        }
        return contenthtml.getParameters();
    }

    public String getParameter(Long htmlid, String who) {

        String value = "%NOT_INCLUDED%";

        if (SortedHtml.find.where().eq("id", htmlid).findRowCount() == 1) {

            SortedHtml thehtml = SortedHtml.find.byId(htmlid);

            Set<TemplateParameter> tosearchin = thehtml.getParameters();

            if (!tosearchin.isEmpty()) {
                for (TemplateParameter aparameter : tosearchin) {

                    if (aparameter.getName().startsWith(who)) {

                        value = aparameter.getValue();

                    }

                }
            }
        }
        return value;

    }

    public Long getContentHtmlId() {

        Long contentid = 0L;

        for (SortedHtml ahtml : Html) {

            if (ahtml.getWort().equals("%%_GEOQUEST_CONTENT_CODE_START_%%")) {

                contentid = ahtml.getId();

            }

        }

        return contentid;

    }

    public List<SortedHtml> getHtml() {

        return Html;
    }

    public String getName() {

        return name;
    }

    public List<ProviderUsers> getUsers() {

        return userList;

    }

    public void addNewGame(Game g, boolean visibility) {

        ProviderGames pgames = new ProviderGames();
        pgames.setPortal(this);
        pgames.setGame(g);

        pgames.setVisibility(visibility);

        pgames.save();

        this.GameList.add(pgames);
        // Also add the association object to the employee.
        g.getPortals().add(pgames);

        this.save();
        g.save();

    }

    public void addNewsstream(NewsstreamItem nsi) {

        Newsstream.add(nsi);

        this.update();

        // POST ON WORDPRESS

        if (nsi.getVisibility().equals("true")) {

            if (!TemplatePostURL.equals("")) {

                try {

                    WebClient webClient = new WebClient();
                    WebRequest webRequest = new WebRequest(new URL(TemplatePostURL));
                    webRequest.setCharset("utf-8");

                    webClient.setThrowExceptionOnFailingStatusCode(false);
                    webClient.setThrowExceptionOnScriptError(false);

                    HtmlPage page = webClient.getPage(webRequest);

                    // Get the form that we are dealing with and within that form,
                    // find the submit button and the field that we want to change.
                    final HtmlForm form = page.getFormByName("geoquest_post_form");

                    final HtmlSubmitInput button = form.getInputByName("Submit");

                    final HtmlTextArea text1 = form.getTextAreaByName("title");

                    text1.setText(nsi.getTitle());

                    final HtmlTextArea text2 = form.getTextAreaByName("content");

                    String poststart = "";

                    // What if nobody exists? -> Not handled right now.

                    if (nsi.getPosterClass().equals("user")) {

                        if (User.find.where().eq("id", nsi.getPosterid()).findRowCount() == 1) {

                            poststart = User.find.byId(nsi.getPosterid()).getName();
                        }

                    } else if (nsi.getPosterClass().equals("game")) {

                        if (Game.find.where().eq("id", nsi.getPosterid()).findRowCount() == 1) {

                            poststart = Game.find.byId(nsi.getPosterid()).getName();
                        }

                    } else if (nsi.getPosterClass().equals("providerportal")) {

                        if (ProviderPortal.find.where().eq("id", nsi.getPosterid()).findRowCount() == 1) {

                            poststart = ProviderPortal.find.byId(nsi.getPosterid()).getName();
                        }

                    }

                    text2.setText(poststart + " " + nsi.getText());

                    final HtmlPasswordInput pwin = form.getInputByName("geopw");

                    pwin.setValueAttribute(TemplatePw);

                    button.click();

                } catch (IOException ioe) {
                    // System.out.println("Problem accessing page "+ ioe.getMessage());
                }

            }

        }

    }

    public void deleteNewsstream(Long id) {

        List<NewsstreamItem> help = new ArrayList<NewsstreamItem>();
        help.addAll(Newsstream);

        for (NewsstreamItem ansi : help) {

            if (ansi.getId().equals(id)) {

                Newsstream.remove(ansi);
                this.update();
                ansi.delete();

            }

        }

    }

    public NewsstreamItem createNewsstreamItem(String title, String content, String vis) {

        NewsstreamItem nsi = new NewsstreamItem(title, content, vis, "providerportal", getId());
        return nsi;

    }

    public void deleteGame(ProviderGames g) {
        if (GameList.contains(g) == true) {
            GameList.remove(g);
            exportPublicGamesJson();
        }
    }

    public void editUser(User u, String newright) {

        ProviderUsers agr = getUser(u);

        if (newright.equals("admin") | newright.equals("user") | newright.equals("unverified")) {
            agr.setRights(newright);
            // System.out.println("SETTING NEW USER RIGHTS");
        }

        agr.update();

    }

    public void deleteUser(User u) {

        // THINK ABOUT DELETING ALL PRIVATE GAMES THAT ONLY HAVE USER U AS GAMERIGHT

        if (userList.contains(getUser(u))) {
            ProviderUsers todelete = getUser(u);
            userList.remove(todelete);
            todelete.delete();
        }

    }

    public void deleteNewsstreamItem(NewsstreamItem ni) {

        if (Newsstream.contains(ni) == true) {

            Newsstream.remove(ni);

        }

    }

    public ProviderGames searchGame(Game g) {

        ProviderGames p = new ProviderGames();

        for (ProviderGames aprovidergame : GameList) {

            if (aprovidergame.getGame() == g) {

                p = aprovidergame;

            }

        }

        return p;

    }

    public ProviderGames getGame(Game g) {

        ProviderGames pg = null;

        for (ProviderGames aprovidergame : GameList) {

            if (aprovidergame.getGame().getId().equals(g.getId())) {

                pg = aprovidergame;

            }

        }

        return pg;

    }

    public List<ProviderGames> getGameList() {

        return GameList;

    }

    public boolean existsGame(Game g) {

        boolean exists = false;

        for (ProviderGames aprovidergame : GameList) {

            if (aprovidergame.getGame().getId().equals(g.getId())) {

                exists = true;

            }

        }

        return exists;

    }

    public void exportPublicGamesJson() {
        JSONSerializer postDetailsSerializer = new JSONSerializer()
                .include("id", "typeID", "name", "hotspots", "hotspots.longitude", "hotspots.latitude", "metadata",
                        "metadata.key", "metadata.value", "lastUpdate", "version", "featuredImagePath", "iconPath")
                .exclude("*").prettyPrint(true);

        List<GameInfo> gameInfos = new ArrayList<GameInfo>();
        List<ProviderGames> objects = getPublicGamesList();

        for (ProviderGames pg : objects) {
            gameInfos.add(new GameInfo(pg.getGame()));
        }

        // default is "[]" if no games public currently:
        String content = gameInfos.size() > 0 ? postDetailsSerializer.serialize(gameInfos) : "[]";

        File theDir = new File("public/portalfiles/" + id);
        if (!theDir.exists())
            theDir.mkdirs();

        File f = new File(theDir, "publicgames.json");
        String absoluteFilePath = f.getAbsolutePath();
        Path thePath = Paths.get(absoluteFilePath);
        try {
            Files.write(thePath, content.getBytes());
            System.out.println("ProviderPortal.exportPublicGamesJson done.");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    public String getUserEcho(User u) {

        String re = "Users: ";
        for (ProviderUsers aprovideruser : userList) {

            if (aprovideruser.getUser().getId() == u.getId()) {

                re = re + "" + aprovideruser.getUser().getId() + "(" + aprovideruser.getRights() + ")";
            }

        }

        return re;
    }

    public ProviderUsers getUser(User u) {

        ProviderUsers pu = new ProviderUsers();

        for (ProviderUsers aprovideruser : userList) {

            if (aprovideruser.getUser().getId().equals(u.getId())) {

                pu = aprovideruser;

            }

        }

        return pu;

    }

    public boolean existsUser(User u) {

        boolean exists = false;

        for (ProviderUsers aprovideruser : userList) {

            if (aprovideruser.getUser().getId().equals(u.getId())) {

                exists = true;

            }

        }

        return exists;

    }

    public void addNewUser(User u) {

        if (autoVerifyUsers == false) {

            addUser(u, UNVERIFIED_ROLE);
        } else {
            addUser(u, USER_ROLE);
        }
    }

    public void addUser(User u, String right) {

        boolean doit = true;

        for (ProviderUsers test : userList) {

            if (test.getUser().getId().equals(u.getId())) {

                doit = false;

            }

        }

        if (doit) {

            ProviderUsers pusers = new ProviderUsers();
            pusers.setPortal(this);
            pusers.setUser(u);

            pusers.setRights(right);

            pusers.save();

            userList.add(pusers);
            u.addPortal(pusers);

            this.update();
            u.update();
            pusers.update();

        }

    }

    public void addNewAdmin(User u) {

        addUser(u, ADMIN_ROLE);

    }

    public void setName(String n) {

        name = n;
    }

    public List<ProviderGames> getPublicGamesList() {

        List<ProviderGames> publGames = new ArrayList();

        Set<Long> containing = new HashSet<Long>();

        for (int j = GameList.size() - 1; j >= 0; j--) {

            ProviderGames onepg = getGame(GameList.get(j).getGame());

            if (onepg.getGame() != null) {
                if (onepg.getGame().hasFile()) {

                    if (!containing.contains(onepg.getGame().getId())) {

                        if (onepg.getPortal().getIdentifier().equals(getIdentifier())) {

                            if (onepg.getVisibility() == true) {

                                containing.add(onepg.getGame().getId());
                                publGames.add(onepg);

                            }
                        }

                    }
                }
            }

        }
        return publGames;
    }

    public String setTemplateURL(String url) {

        return TemplateURL = url;

    }

    public String getTemplateURL() {

        return TemplateURL;

    }

    public String getEndHtml() {

        SortedHtml winner = new SortedHtml("", 0, "");
        int max = 0;
        for (SortedHtml anhtml : Html) {

            if (anhtml.getZahl() > max) {

                max = anhtml.getZahl();
                winner = anhtml;
            }

        }

        return winner.getWort();

    }

    public String getStartHtml() {

        SortedHtml winner = new SortedHtml("", 0, "");
        int min = -1;
        for (SortedHtml anhtml : Html) {

            if (anhtml.getZahl() < min | min < 0) {

                min = anhtml.getZahl();
                winner = anhtml;
            }

        }

        return winner.getWort();

    }

    public String getIdentifier() {
        return Long.toString(id);
    }

    public Long getId() {

        return id;
    }

    public String getTemplatePw() {
        return TemplatePw;
    }

    public void setTemplatePw(String pw) {
        TemplatePw = pw;
    }

    public String getTemplateForm() {
        return TemplateForm;
    }

    public void setTemplateForm(String tf) {
        TemplateForm = tf;
    }

    public String getTemplateFormField() {
        return TemplateFormField;
    }

    public void setTemplateFormField(String tff) {

        TemplateFormField = tff;
    }

    public String getTemplateUserField() {
        return TemplateUserField;
    }

    public void setTemplateUserField(String x) {
        TemplateUserField = x;
    }

    public String getTemplateUser() {
        return TemplateUser;
    }

    public void setTemplateUser(String x) {
        TemplateUser = x;
    }

    public String getTemplateAfterLoginURL() {
        return TemplateAfterLoginURL;
    }

    public void setTemplateAfterLoginURL(String x) {
        TemplateAfterLoginURL = x;
    }

    public boolean getAutoVerifyUsers() {
        return autoVerifyUsers;
    }

    public void setAutoVerifyUsers(boolean x) {
        autoVerifyUsers = x;
    }

    public String getTemplateSubmitButton() {
        return TemplateSubmitButton;
    }

    public void setTemplateSubmitButton(String x) {
        TemplateSubmitButton = x;
    }

    public String getTemplateMappingURL() {

        return TemplateMappingURL;
    }

    public void setTemplateMappingURL(String x) {
        TemplateMappingURL = x;
    }

    public void setTemplateServerURL(String x) {

        TemplateServerURL = x;

    }

    public String getTemplateServerURL() {

        if (!(TemplateServerURL.equals(""))) {

            return TemplateServerURL;

        } else {

            return Global.SERVER_URL;
        }

    }

    public String getPortalURL() {

        if (!(TemplateServerURL.equals(""))) {

            return TemplateServerURL;

        } else {

            return Global.SERVER_URL + String.valueOf(id);
        }

    }

    public String getPathTo(play.api.mvc.Call x) {

        String z = x.url();

//		if (!(TemplateServerURL.equals(""))) {
//			if ((!TemplateServerURL.contains(Global.SERVER_URL))) {
//
//				System.out.println("Rearranging path string (TMPL_Server_URL: " + TemplateServerURL + " != Global_Server_URL: " + Global.SERVER_URL);
//				if (z.startsWith("/" + String.valueOf(id))) {
//
//					String y = String.valueOf(z.subSequence(String.valueOf(id).length() + 1, z.length()));
//					System.out.println("  getPathTo(" + x.toString() + ") -> " + y);
//					System.out.println("ProviserPortal.getPathTo(): z = " + z + " --> " + y);
//
//					return y;
//				}
//			}
//		}
//
//		System.out.println("ProviserPortal.getPathTo(): z = " + z);
        return z;

    }

    public String getTemplateServerURLDropSlash() {

        String url = getTemplateServerURL();

        if (url.charAt(url.length() - 1) == '/') {

            url = url.substring(0, url.length() - 1);

        }

        return url;
    }

    public List<NewsstreamItem> getNewsstream() {

        return Newsstream;

    }

    public void setTemplatePostURL(String u) {
        TemplatePostURL = u;
    }

    public String getTemplatePostURL() {
        return TemplatePostURL;
    }

    public void setAdditionalCSS(String css) {
        AdditionalCSS = css;
    }

    public String getAdditionalCSS() {
        return AdditionalCSS;
    }

    public void setDefaultcolor(String c) {

        defaultcolor = c;
    }

    public String getDefaultcolor() {
        return defaultcolor;
    }

    public String getComplementColor() {
        return color2;
    }

    public String getGradientColor() {
        return color3;
    }

    public String getNavbarColor() {
        return color4;
    }

    public String getLinkColor() {
        return color5;
    }

    public void setComplementColor(String c) {
        color2 = c;
    }

    public void setGradientColor(String c) {
        color3 = c;
    }

    public void setNavbarColor(String c) {
        color4 = c;
    }

    public void setLinkColor(String c) {
        color5 = c;
    }

    public void setLogoimg(String img) {

        logoimg = img;
    }

    public String getLogoURL() {

        String path = logoimg;
        String base = Play.current().path().getAbsolutePath() + "/public/";
        String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();

        return relative;

    }

    public boolean logoExists() {

        File f = new File(logoimg);
        if (f.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public List<GameType> getGameTypes() {
        return gameTypes;
    }

    public boolean gameTypesIsEmpty() {
        return gameTypes.isEmpty();
    }

    public void addGameType(GameType x) {
        gameTypes.add(x);
    }

    public void removeGameType(GameType x) {

        if (gameTypes.contains(x)) {

            gameTypes.remove(x);

        }

    }

    public boolean hasGameType(String x) {
        boolean help = false;
        for (GameType gt : gameTypes) {

            if (gt.getName().equals(x)) {
                help = true;
            }

        }
        return help;
    }

    public boolean hasGameType(GameType ts) {
        boolean help = false;
        for (GameType gt : gameTypes) {

            if (gt.getId().equals(ts.getId())) {
                help = true;
            }

        }
        return help;
    }

    public Integer getLogoHeight(Integer i) {

        try {

            // Get the image
            Image image = ImageIO.read(new File(logoimg));

            return i + image.getHeight(null);

        } catch (IOException e) {
            return 0 + i;
        }

    }

    /**
     * Finder is a Play Framework Class that lets other classes find a specific
     * object of this class, in this case, searching for objects with a Long value
     * is enabled.
     */

    public static final Finder<Long, ProviderPortal> find = new Finder<Long, ProviderPortal>(Long.class,
            ProviderPortal.class);

    public GameType getGameType(String x) {

        for (GameType gt : gameTypes) {

            if (gt.getName().equals(x)) {
                return gt;
            }

        }
        return null;
    }

}
