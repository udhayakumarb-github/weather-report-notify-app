package com.elan.weather

import akka.actor.typed.ActorSystem
import com.elan.weather.consumers.WeatherConsumer
import org.slf4j.LoggerFactory

object Main extends App {
  private val logger = LoggerFactory.getLogger(this.getClass)

  logger.info("Starting Weather Fetcher System...")
  val system = ActorSystem(WeatherFetcher(), "weather-fetcher-system")

  logger.info("Starting Weather Consumer...")
  WeatherConsumer.consumeWeatherData()(system)
}

