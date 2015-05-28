name := """reactive-elasticsearch-play"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

jacoco.settings

libraryDependencies ++= Seq(
  "org.mockito" % "mockito-all" % "1.9.5",
  "org.easytesting" % "fest-assert-core" % "2.0M10",
  "com.google.inject" % "guice" % "3.0",
  "org.elasticsearch" % "elasticsearch" % "1.5.1",
  "net.lingala.zip4j" % "zip4j" % "1.3.2",
  "org.apache.tika" % "tika-core" % "1.7",
  "org.apache.tika" % "tika-parsers" % "1.7",
  "com.typesafe.play" %% "play-mailer" % "2.4.0")

initialize := {
  val _ = initialize.value
  if (sys.props("java.specification.version") != "1.8")
    sys.error("Java 8 is required for this project.")
}
