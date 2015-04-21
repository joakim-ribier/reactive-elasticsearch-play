name := """reactive-elasticsearch-play"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

libraryDependencies ++= Seq(
  "net.lingala.zip4j" % "zip4j" % "1.3.2",
  "org.testng" % "testng" % "6.8.8",
  "com.google.inject" % "guice" % "3.0",
  "org.elasticsearch" % "elasticsearch" % "1.4.1",
  "org.elasticsearch" % "elasticsearch-mapper-attachments" % "2.4.1")

initialize := {
  val _ = initialize.value
  if (sys.props("java.specification.version") != "1.8")
    sys.error("Java 8 is required for this project.")
}