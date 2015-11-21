name := "docker-scala-app"

version := "1.0.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

//enablePlugins(JavaAppPackaging)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test
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