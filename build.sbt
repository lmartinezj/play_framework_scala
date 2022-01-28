import sbtcrossproject.{crossProject, CrossType}

lazy val server = (project in file("server")).settings(commonSettings).settings(
	name := "Play-Videos-Server",
  scalaJSProjects := Seq(client),
  Assets / pipelineStages := Seq(scalaJSPipeline),
  pipelineStages := Seq(digest, gzip),
  // triggers scalaJSPipeline when using compile or continuous compilation
  Compile / compile := ((Compile / compile) dependsOn scalaJSPipeline).value,
  libraryDependencies ++= Seq(
    "com.vmunier" %% "scalajs-scripts" % "1.2.0",
    guice,
    specs2 % Test,
    "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
    "com.typesafe.play" %% "twirl-compiler" % "1.5.1",
    "org.scalatest" %% "scalatest" % "3.2.9" % Test

  ),
  // Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
  EclipseKeys.preTasks := Seq(Compile / compile)
).enablePlugins(PlayScala).
  dependsOn(sharedJvm)

lazy val client = (project in file("client")).settings(commonSettings).settings(
	name := "Play-Videos-Client",
  scalaJSUseMainModuleInitializer := true,
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.7",
		"org.querki" %%% "jquery-facade" % "1.2",
		"com.typesafe.play" %%% "play-json" % "2.8.1"
  )
).enablePlugins(ScalaJSPlugin, ScalaJSWeb).
  dependsOn(sharedJs)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(
		name := "Play-Videos-Shared",
		commonSettings,
		libraryDependencies ++= Seq(
			"com.typesafe.play" %%% "play-json" % "2.8.1"
		))
lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val commonSettings = Seq(
  scalaVersion := "2.12.13",
  organization := "edu.trinity"
)

// loads the server project at sbt startup
onLoad in Global := (onLoad in Global).value andThen {s: State => "project server" :: s}
server / libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.9" % Test
)
