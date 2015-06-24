package models;

import com.avaje.ebean.CallableSql;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.H2Platform;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import play.api.db.DBApi;
import play.api.db.evolutions.Evolution;
import play.api.db.evolutions.Evolutions;
import play.test.FakeApplication;
import play.test.Helpers;
import scala.collection.Seq;
import util.Global;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Base class for Model testing
 */
public class BaseModelTest {

    public static FakeApplication app;
    public static String createDdl = "";
    public static String dropDdl = "";
    public static DdlGenerator ddl;
    public static EbeanServer server;

    @BeforeClass
    public static void startApp() throws IOException {



        Map<String, String> settings = new HashMap<String, String>();
        settings.put("db.default.url", "jdbc:mysql://localhost/geotest?characterEncoding=UTF-8&useOldAliasMetadataBehavior=true");
        settings.put("db.default.user", "root");
        settings.put("db.default.password", "mysqlpw");



        app = Helpers.fakeApplication(settings);
        Helpers.start(app);





        int i = 1;
        while(app.getWrappedApplication().getFile("conf/evolutions/default/"+i+".sql").exists()){
        // Reading the evolution file
        String evolutionContent = FileUtils.readFileToString(app.getWrappedApplication().getFile("conf/evolutions/default/"+i+".sql"));
        // Splitting the String to get Create & Drop DDL
        String[] splittedEvolutionContent = evolutionContent.split("# --- !Ups");
        String[] upsDowns = splittedEvolutionContent[1].split("# --- !Downs");
        createDdl = createDdl+" "+upsDowns[0];
        dropDdl = upsDowns[1]+ dropDdl;





            i++;
        }

        dropDdl = "SET FOREIGN_KEY_CHECKS = 0; "+dropDdl+" DROP TABLE if exists play_evolutions;";



                    System.out.println("Testtest");




        CallableSql sql = Ebean.createCallableSql(dropDdl+createDdl);
        Ebean.execute(sql);



    }


    @Before
    public void resetDB(){










    }






    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }



}