name := "docker-scala-app"

version := "1.0.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

//enablePlugins(JavaAppPackaging)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "com.typesafe.slick" %% "slick" % "3.1.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-codegen" % "3.1.0",
  "mysql" % "mysql-connector-java" % "5.1.37",
  "com.typesafe.play" %% "play-slick" % "1.1.1"/*,
  "com.typesafe.play" %% "play-slick-evolutions" % "1.1.1"*/
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

maintainer in Docker := "Damien Suhard <ptitdam2001@gmail.com>"
packageSummary in Docker := "A small docker application with play scala"
packageDescription := "Docker [micro|nano] Service"

// Only add this if you want to rename your docker image name
packageName in Docker := "docker-scala-app"

dockerExposedPorts in Docker := Seq(9000, 9443)

// Autogenerate Slick models
slick <<= slickCodeGenTask

//sourceGenerators in Compile <+= {
//  slickCodeGenTask
//}

lazy val slick = TaskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
  val outputDir = (dir / "app").getPath
  val username = "root"
  val password = "damien"
  val url = "jdbc:mysql://localhost/slick"
  val jdbcDriver = "com.mysql.jdbc.Driver"
  val slickDriver = "slick.driver.MySQLDriver"
  val pkg = "models"

  toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg, username, password), s.log))
  val fname = outputDir + "/" + "models" + "/Tables.scala"

  //remove
  //val fileOutput = new File(fname)
  //if (fileOutput.exists()) fileOutput.delete()

  Seq(file(fname))
}
