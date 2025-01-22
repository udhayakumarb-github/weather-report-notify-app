import Dependencies._

ThisBuild / scalaVersion     := "3.3.4"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.elan"
ThisBuild / organizationName := "Elan Soft"

lazy val root = (project in file("."))
  .settings(
    name := "weather-report-notify-app",
    libraryDependencies += munit % Test
  )

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % "2.8.8",
  "com.typesafe.akka" %% "akka-stream" % "2.8.8",
  "org.apache.kafka" % "kafka_2.9.2" % "0.8.2.2",
  "com.typesafe.akka" %% "akka-stream-kafka" % "4.0.2",
  "com.typesafe.akka" %% "akka-actor" % "2.8.8",
  "com.typesafe.akka" %% "akka-serialization-jackson" % "2.8.8",
  "com.typesafe.akka" %% "akka-slf4j" % "2.8.8",
  "ch.qos.logback" % "logback-classic" % "1.5.16",
  "com.typesafe.play" %% "play-ahc-ws" % "2.9.6",
  "com.typesafe.play" %% "play-json" % "2.10.6",
  "com.typesafe.akka" %% "akka-http" % "10.5.3",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.3",
  "com.typesafe" % "config" % "1.4.3",
  "com.typesafe.slick" %% "slick" % "3.5.2",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.5.2",
  "org.postgresql" % "postgresql" % "42.7.5"
)
