# A REST PLAY FRAMEWORK APPLICATION EXAMPLE


Play Framework project using : Docker, Slick and REST.

The goal of that project is to exercice onto Scala Play.

## Development

### How to dockerize our Play application

To dockerize our application, we will use the *sbt-native-packager* plugin from SBT

1 - Add the depen­den­cies to your **build.sbt** :

```scala
// setting a maintainer which is used for all packaging types
maintainer := "Your name"

// exposing the play ports
dockerExposedPorts in Docker := Seq(9000, 9443)
```

2 - add sbt plugins to your **/project/plugins.sbt** :

```scala
addSbtPlugin("com.typesafe.sbt" %% "sbt-native-packager" % "1.0.4")

addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.2.0")
```


### How to use Json parser of Play

Now, we want manipulate Json to a Json output. Play Framework contains a Json library.

In its part, we will work into the framework tree by adding a route and a controller.

#### First - we add route into route file

add the line into *conf/routes* : 

```
GET     /hello                      controllers.Json.hello
```

We are defining a new route called /hello which will return a json like that :

```json
{
	"message": "hello"
}
```

That route will be called by the GET HTTP method.

When the */hello* is called, the action *controllers.Json.hello* is called, now we will create the *hello* action from *Json* controller

#### Second - Create an action which returns Json

Create a new file *app/controllers/Json.scala* and add next lines : 

```scala
package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

class Json extends Controller {

  def hello = Action {
    Ok(Json.obj("message" -> "hello"))
  }

}
``` 

Now, On your browser, taping [http://localhost:9000/hello](), it displays :

```json
{
	"message": "hello"
}
```


> see more with the [Official documentation](https://www.playframework.com/documentation/2.4.x/ScalaJson) 

### Use Slick codegen

#### 1. Add dependencies

For a SBT project add the following lines to your build definition file (*build.sbt* or *project/Build.scala*)

```scala
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.1.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4"
)
```

Now we add the Slick's code generator. For SBT projects add following to build definition ( = build.sbt)

```scala
libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.1.0"
```
> If you want to use Mysql as Database Server, you must add Mysql connector dependency : 
> ``libraryDependencies ++= Seq("mysql" % "mysql-connector-java" % "5.1.37")``
> and 
> ``"org.scala-lang" % "scala-reflect" % scala_version``


#### 2. Integrated to SBT

Add these lines into your build.sbt:

```scala
// Autogenerate Slick models
slick <<= slickCodeGenTask

//Used Code generator during compilation
//sourceGenerators in Compile <+= slickCodeGenTask

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
  Seq(file(fname))
}
```

We created a SBT comand which create the file Tables.scala where there are all BDD models. The SBT command is :

```shell
sbt gen-tables
```


> Tables.scala will be created into the path : *target/scala-2.11/src_managed/app/models/*


## Compile

This file will be packaged with your application, when using `activator dist`.

### Publishing Docker image :

```
sbt docker:publishLocal
```

To check if the image is really created you should tape this command :

```
docker images
```

If it's ok, you should see one line with 


## Run the application

To run project via Docker:

```shell
docker run --name play-8080 -p 8080:9000 [your-app-name:[your-app-version]
```
With the previous command, we run the docker image, the options *-p*  to expose port **9000** outside Docker’s virtual network (that’s locally available as **8080**)

You can go to the application with your favorite browser by taping [http://localhost:8080]()