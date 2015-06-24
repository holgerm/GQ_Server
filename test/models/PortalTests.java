package models;


import controllers.Application;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;
import util.Global;

import java.util.logging.Level;
import java.util.logging.Logger;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
public class PortalTests extends BaseModelTest {



    @Test
    public void junitworks() {


        Integer i = 1+1;
        Integer l = 2;
        assertThat(i).isEqualTo(l);


    }


    @Test
    public void startupworks() {


        Global.SERVER_URL="http://localhost:3333/";
        Global.SERVER_URL_2 = "http://localhost:3333";


        running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");


                System.out.println(browser.$("h1").first().text());


                assertThat(browser.$("h1").first().getText()).contains("Geoquest Webservice");
            }
        });



    }


    @Test
    public void testserverurlworks() {



        Global.SERVER_URL="http://localhost:3333/";
        Global.SERVER_URL_2 = "http://localhost:3333";

        running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {

                browser.goTo("http://localhost:3333");


                assertThat(browser.$("script").getAttributes("src")).contains("http://localhost:3333/assets/js/bootstrap.min.js");


            }
        });



    }


    /////////////////////////////////////////////////////////////////////////
    //Pages:
    /////////////////////////////////////////////////////////////////////////


    //// Registering User

    //// Logging in

    //// Showing my Portals

    //// Adding a Portal

    //// Adding a Game

    //// Showing my Games


    /////////////////////////////////////////////////////////////////////////
    //Template Contents:
    /////////////////////////////////////////////////////////////////////////


    ////%%_GEOQUEST_PORTAL_NAME_%%

                @Test
                public void test_portal_contains_portal_title() {


                    Global.SERVER_URL="http://localhost:3333/";
                    Global.SERVER_URL_2 = "http://localhost:3333";

                    running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
                        public void invoke(TestBrowser browser) {

                            WebDriver drive = browser.getDriver();

                            browser.goTo("http://localhost:3333");




                            ProviderPortal p = new ProviderPortal("Test-Portal","http://localhost:3333/testingtemplate");

                            p.save();

                            browser.goTo("http://localhost:3333/"+p.getId().toString());



                            assertThat(drive.getPageSource()).contains("Geo_Portal_Titel:Test-Portal");

                            p.removeMe();
                            p.delete();


                        }
                    });



                }


    ////%%_GEOQUEST_CONTENT_TITLE_%%

                @Test
                public void test_portal_contains_content_title() {


                    Global.SERVER_URL="http://localhost:3333/";
                    Global.SERVER_URL_2 = "http://localhost:3333";

                    running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
                        public void invoke(TestBrowser browser) {

                            WebDriver drive = browser.getDriver();

                            browser.goTo("http://localhost:3333");




                            ProviderPortal p = new ProviderPortal("Test-Portal","http://localhost:3333/testingtemplate");

                            p.save();

                            browser.goTo("http://localhost:3333/"+p.getId().toString()+"/public");



                            assertThat(drive.getPageSource()).contains("Geo_Content_Titel:Öffentliche Spiele");

                            p.removeMe();
                            p.delete();


                        }
                    });



                }


    ////%%_GEOQUEST_NAV_LI_%%

                @Test
                public void test_portal_contains_navigation() {


                    Global.SERVER_URL="http://localhost:3333/";
                    Global.SERVER_URL_2 = "http://localhost:3333";

                    running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
                        public void invoke(TestBrowser browser) {

                            WebDriver drive = browser.getDriver();

                            browser.goTo("http://localhost:3333");




                            ProviderPortal p = new ProviderPortal("Test-Portal","http://localhost:3333/testingtemplate");

                            p.save();

                            browser.goTo("http://localhost:3333/"+p.getId().toString()+"/public");



                            assertThat(drive.getPageSource()).contains("<a href=\"http://localhost:3333/"+p.getId()+"/newsstream\">");
                            assertThat(drive.getPageSource()).contains("<a href=\"http://localhost:3333/"+p.getId()+"/public\">");
                            assertThat(drive.getPageSource()).contains("<a href=\"http://localhost:3333/"+p.getId()+"/signup\">");
                            assertThat(drive.getPageSource()).contains("<a href=\"http://localhost:3333/"+p.getId()+"/login\">");

                            p.removeMe();
                            p.delete();


                        }
                    });



                }


    ////%%_GEOQUEST_SERVER_VERSION_%%


                @Test
                public void test_portal_contains_server_version() {


                    Global.SERVER_URL="http://localhost:3333/";
                    Global.SERVER_URL_2 = "http://localhost:3333";

                    running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
                        public void invoke(TestBrowser browser) {

                            WebDriver drive = browser.getDriver();

                            browser.goTo("http://localhost:3333");




                            ProviderPortal p = new ProviderPortal("Test-Portal","http://localhost:3333/testingtemplate");

                            p.save();

                            browser.goTo("http://localhost:3333/"+p.getId().toString()+"/public");



                            assertThat(drive.getPageSource()).contains(Global.GEOQUEST_VERSION);

                            p.removeMe();
                            p.delete();


                        }
                    });



                }



    ////%%_GEOQUEST_DEFAULT_COLOR_%%

                @Test
                public void test_portal_contains_default_color() {


                    Global.SERVER_URL="http://localhost:3333/";
                    Global.SERVER_URL_2 = "http://localhost:3333";

                    running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
                        public void invoke(TestBrowser browser) {

                            WebDriver drive = browser.getDriver();

                            browser.goTo("http://localhost:3333");




                            ProviderPortal p = new ProviderPortal("Test-Portal","http://localhost:3333/testingtemplate");

                            p.setDefaultcolor("#12CC12");
                            p.save();

                            browser.goTo("http://localhost:3333/"+p.getId().toString()+"/public");



                            assertThat(drive.getPageSource()).contains("#12CC12");

                            p.removeMe();
                            p.delete();


                        }
                    });



                }



    ////<meta name='%%_GEOQUEST_HEADER_FUNCTIONS_%%'>

                @Test
                public void test_portal_contains_header_functions() {


                    Global.SERVER_URL="http://localhost:3333/";
                    Global.SERVER_URL_2 = "http://localhost:3333";

                    running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
                        public void invoke(TestBrowser browser) {

                            WebDriver drive = browser.getDriver();

                            browser.goTo("http://localhost:3333");




                            ProviderPortal p = new ProviderPortal("Test-Portal","http://localhost:3333/testingtemplate");

                            p.setAdditionalCSS("TESTING HEADER FUNCTION ADDITIONAL CSS");
                            p.save();

                            browser.goTo("http://localhost:3333/"+p.getId().toString()+"/public");



                            assertThat(drive.getPageSource()).contains("TESTING HEADER FUNCTION ADDITIONAL CSS");

                            p.removeMe();
                            p.delete();


                        }
                    });



                }



    ////  All Html Elements

                @Test
                public void test_portal_contains_all_html_elements() {


                    Global.SERVER_URL="http://localhost:3333/";
                    Global.SERVER_URL_2 = "http://localhost:3333";

                    running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
                        public void invoke(TestBrowser browser) {

                            WebDriver drive = browser.getDriver();

                            browser.goTo("http://localhost:3333");




                                    ProviderPortal p = new ProviderPortal("Test-Portal","http://localhost:3333/testingtemplate");

                                    p.save();

                                    browser.goTo("http://localhost:3333/"+p.getId().toString());



                            assertThat(drive.getPageSource()).contains("<geoquesttest id=\"1\">");
                            assertThat(drive.getPageSource()).contains("<geoquesttest id=\"2\">");
                            assertThat(drive.getPageSource()).contains("<geoquesttest id=\"3\">");
                            assertThat(drive.getPageSource()).contains("<geoquesttest id=\"4\">");
                            assertThat(drive.getPageSource()).contains("<geoquesttest id=\"5\">");
                            assertThat(drive.getPageSource()).contains("<geoquesttest id=\"6\">");
                            assertThat(drive.getPageSource()).contains("<geoquesttest id=\"7\">");
                            assertThat(drive.getPageSource()).contains("<geoquesttest id=\"8\">");
                            assertThat(drive.getPageSource()).contains("<geoquesttest id=\"9\">");
                            assertThat(drive.getPageSource()).contains("<geoquesttest id=\"10\">");
                            assertThat(drive.getPageSource()).contains("<geoquesttest id=\"11");

                            p.removeMe();
                            p.delete();


                        }
                    });



                }




    /////////////////////////////////////////////////////////////////////////
    //Template Parameters:
    /////////////////////////////////////////////////////////////////////////


    ////li.attributes

                @Test
                public void test_portal_contains_li_attributes() {


                    Global.SERVER_URL="http://localhost:3333/";
                    Global.SERVER_URL_2 = "http://localhost:3333";

                    running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
                        public void invoke(TestBrowser browser) {

                            WebDriver drive = browser.getDriver();

                            browser.goTo("http://localhost:3333");




                            ProviderPortal p = new ProviderPortal("Test-Portal","http://localhost:3333/testingtemplate");


                            p.save();

                            browser.goTo("http://localhost:3333/"+p.getId().toString()+"/public");



                            assertThat(drive.getPageSource()).containsIgnoringCase("TESTING_LI_ATTRIBUTES");

                            p.removeMe();
                            p.delete();


                        }
                    });



                }



    ////general.button.attributes

                @Test
                public void test_portal_contains_general_button_attributes() {


                    Global.SERVER_URL="http://localhost:3333/";
                    Global.SERVER_URL_2 = "http://localhost:3333";

                    running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
                        public void invoke(TestBrowser browser) {

                            WebDriver drive = browser.getDriver();

                            browser.goTo("http://localhost:3333");




                            ProviderPortal p = new ProviderPortal("Test-Portal","http://localhost:3333/testingtemplate");


                            p.save();

                            browser.goTo("http://localhost:3333/"+p.getId().toString()+"/login");



                            assertThat(drive.getPageSource()).containsIgnoringCase("TESTING_GENERAL_BUTTON");

                            p.removeMe();
                            p.delete();


                        }
                    });



                }


    ////general.speciallinks



    ////general.navtabs





    /////////////////////////////////////////////////////////////////////////
    // Template Mapping:
    /////////////////////////////////////////////////////////////////////////


    ////replacing normal code


                @Test
                public void test_portal_replaces_regular_mapping_code() {


                    Global.SERVER_URL="http://localhost:3333/";
                    Global.SERVER_URL_2 = "http://localhost:3333";

                    running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
                        public void invoke(TestBrowser browser) {

                            WebDriver drive = browser.getDriver();

                            browser.goTo("http://localhost:3333");




                             // Name

                           String startName = "Test-Portal";

                            //TemplateURL
                            String url = "http://localhost:3333/testingtemplate";
                            //TemplateAfterLoginURL
                            String alurl = "";
                            //TemplateForm
                            String tf = "";
                            //TemplateFormField
                            String tpwf = "";
                            //TemplatePw
                            String tpw = "";
                            //TemplateUserField
                            String tuf = "";
                            //TemplateUser
                            String tu = "";
                            //autoVerifyUsers
                            boolean av = true;
                            //TemplateSubmitButton
                            String button = "";
                            //TemplateMappingURL
                            String murl = "http://localhost:3333/testmapping";
                            //TemplateServerURL
                            String csurl = "";
                            //TemplatePostURL
                            String purl = "";
                            //AdditionalCSS
                            String acss = "";
                            //defaultcolor
                            String clr = "";

                            
                            // Default Design Options
                            String dpr ="";
                            String c2 = "";
                            String c3 = "";
                            String c4 = "";
                            String c5 = "";
                            String limg = "";
                            
                            
                            
                            
                            
                            

                            ProviderPortal p = new ProviderPortal(startName,url,tf,tpwf,tuf,tpw,tu,alurl,av,button,murl,csurl,purl,acss,clr,dpr,c2, c3,c4,c5,limg);

                                p.save();

                            browser.goTo("http://localhost:3333/"+p.getId().toString()+"/public");



                            assertThat(drive.getPageSource()).contains("TESTING_MAPPING_REPLACE_1");

                            p.removeMe();
                            p.delete();


                    }



                });








}


    ////replacing normal code


                @Test
                public void test_portal_replaces_complex_mapping_code() {


                    Global.SERVER_URL="http://localhost:3333/";
                    Global.SERVER_URL_2 = "http://localhost:3333";

                    running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
                        public void invoke(TestBrowser browser) {

                            WebDriver drive = browser.getDriver();

                            browser.goTo("http://localhost:3333");




                            // Name

                            String startName = "Test-Portal";

                            //TemplateURL
                            String url = "http://localhost:3333/testingtemplate";
                            //TemplateAfterLoginURL
                            String alurl = "";
                            //TemplateForm
                            String tf = "";
                            //TemplateFormField
                            String tpwf = "";
                            //TemplatePw
                            String tpw = "";
                            //TemplateUserField
                            String tuf = "";
                            //TemplateUser
                            String tu = "";
                            //autoVerifyUsers
                            boolean av = true;
                            //TemplateSubmitButton
                            String button = "";
                            //TemplateMappingURL
                            String murl = "http://localhost:3333/testmapping";
                            //TemplateServerURL
                            String csurl = "";
                            //TemplatePostURL
                            String purl = "";
                            //AdditionalCSS
                            String acss = "";
                            //defaultcolor
                            String clr = "";
                            
                            // Default Design Options
                            String dpr ="";
                            String c2 = "";
                            String c3 = "";
                            String c4 = "";
                            String c5 = "";
                            String limg = "";





                            ProviderPortal p = new ProviderPortal(startName,url,tf,tpwf,tuf,tpw,tu,alurl,av,button,murl,csurl,purl,acss,clr,dpr,c2, c3,c4,c5,limg);

                                p.save();

                                browser.goTo("http://localhost:3333/"+p.getId().toString()+"/public");



                                assertThat(drive.getPageSource()).contains("TESTING_MAPPING_REPLACE_2");

                                p.removeMe();
                                p.delete();


                        }



                    });








                }




// end
}
