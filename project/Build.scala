import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "play-authenticate-usage"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(

      "be.objectify"  %%  "deadbolt-java"     % "2.1-SNAPSHOT",
      // Comment this for local development of the Play Authentication core
      "com.feth"      %%  "play-authenticate" % "0.3.4-SNAPSHOT",
      "mysql" % "mysql-connector-java" % "5.1.18",
      "org.apache.directory.studio" % "org.apache.commons.io" % "2.4",
      "net.sourceforge.htmlunit" % "htmlunit" % "2.10",
      "postgresql" % "postgresql" % "9.1-901.jdbc4",
      "org.json" % "json" % "20140107",
      "net.sf.flexjson" % "flexjson" % "3.1",
          "com.google.guava" % "guava" % "14.0-rc1",
    "com.jolbox" % "bonecp" % "0.8.0.RELEASE",
    "org.w3c" % "dom" % "2.3.0-jaxb-1.0.6",

        javaCore,
      javaJdbc,
      javaEbean
    )

  //val c3p0 = RootProject(uri("git://github.com/hadashi/play2-c3p0-plugin.git"))


  //  Uncomment this for local development of the Play Authenticate core:
     /*
    val playAuthenticate = play.Project(
      "play-authenticate", "1.0-SNAPSHOT", Seq(javaCore), path = file("modules/play-authenticate")
    ).settings(
      libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.1",
      libraryDependencies += "com.feth" %% "play-easymail" % "0.2-SNAPSHOT",
      libraryDependencies += "org.mindrot" % "jbcrypt" % "0.3m",
      libraryDependencies += "commons-lang" % "commons-lang" % "2.6",

      resolvers += Resolver.url("play-easymail (release)", url("http://joscha.github.com/play-easymail/repo/releases/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("play-easymail (snapshot)", url("http://joscha.github.com/play-easymail/repo/snapshots/"))(Resolver.ivyStylePatterns)
    )
     */

    val main = play.Project(appName, appVersion, appDependencies).settings(

      resolvers += Resolver.url("Objectify Play Repository (release)", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("Objectify Play Repository (snapshot)", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns),

      resolvers += Resolver.url("play-easymail (release)", url("http://joscha.github.com/play-easymail/repo/releases/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("play-easymail (snapshot)", url("http://joscha.github.com/play-easymail/repo/snapshots/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("Sonatype OSS Snapshots",url("https://oss.sonatype.org/content/repositories/snapshots"))(Resolver.ivyStylePatterns),

      resolvers += Resolver.url("play-authenticate (release)", url("http://joscha.github.com/play-authenticate/repo/releases/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("play-authenticate (snapshot)", url("http://joscha.github.com/play-authenticate/repo/snapshots/"))(Resolver.ivyStylePatterns)
    )
     //.dependsOn(c3p0)
//  Uncomment this for local development of the Play Authenticate core:
//    .dependsOn(playAuthenticate).aggregate(playAuthenticate)




}
