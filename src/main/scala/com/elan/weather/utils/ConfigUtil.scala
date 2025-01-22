package com.elan.weather.utils

import com.typesafe.config.{Config, ConfigFactory}
import scala.jdk.CollectionConverters._

object ConfigUtil {
  private val config: Config = ConfigFactory.load()

  def getApiEndpoint: String = config.getString("weather.api.endpoint")

  def getApiKey: String = config.getString("weather.api.key")

  def getCities: List[String] = config.getStringList("weather.cities").asScala.toList
}

