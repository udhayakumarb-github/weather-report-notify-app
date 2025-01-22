package com.elan.weather.utils

import com.typesafe.config.{Config, ConfigFactory}
import scala.jdk.CollectionConverters._
import slick.jdbc.PostgresProfile.api._

object ConfigUtil {
  private val config: Config = ConfigFactory.load()

  def getApiEndpoint: String = config.getString("weather.api.endpoint")

  def getApiKey: String = config.getString("weather.api.key")

  def getCities: List[String] = config.getStringList("weather.cities").asScala.toList

  def getDatabaseConfig: Database = {
    val dbConfig = config.getConfig("slick.dbs.default.db")
    Database.forConfig("", dbConfig)
  }
}

