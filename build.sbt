name := "sudostream"

version := "1.0"

lazy val `sudostream` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(jdbc, anorm, cache, ws,
  "org.webjars" % "bootstrap" % "3.3.4",
  "javax.mail" % "mail" % "1.4.1",
  "com.typesafe.play" % "play-mailer_2.11" % "2.4.0",
  "org.mongodb" %% "casbah" % "2.8.1")

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")