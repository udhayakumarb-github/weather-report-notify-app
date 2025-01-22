package com.elan.weather.consumers

import akka.actor.typed.ActorSystem
import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.stream.scaladsl.Sink
import com.elan.weather.entities.{LocationEntity, WeatherConditionEntity, WeatherDataEntity}
import com.elan.weather.models.{JsonFormats, WeatherData}
import com.elan.weather.repositories.{LocationRepository, WeatherConditionRepository, WeatherDataRepository}
import com.elan.weather.utils.ConfigUtil
import com.typesafe.config.Config
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory
import play.api.libs.json.Json
import slick.jdbc.PostgresProfile.api.*

import java.sql.Timestamp
import java.time.Instant
import scala.concurrent.ExecutionContext

object WeatherConsumer {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def consumeWeatherData()(implicit system: ActorSystem[_]): Unit = {
    import JsonFormats.*
    implicit val ec: ExecutionContext = system.executionContext

    val config: Config = system.settings.config.getConfig("akka.kafka.consumer")
    val consumerSettings = ConsumerSettings(config, new StringDeserializer, new StringDeserializer)
      .withBootstrapServers("localhost:9092")
      .withGroupId("weather-consumer-group")
      .withProperty("auto.offset.reset", "earliest")

    val db = ConfigUtil.getDatabaseConfig
    val locationRepository = new LocationRepository(db)
    val weatherConditionRepository = new WeatherConditionRepository(db)
    val weatherDataRepository = new WeatherDataRepository(db)

    Consumer
      .plainSource(consumerSettings, Subscriptions.topics("weather-data"))
      .mapAsync(1) { msg =>
        val weatherData = Json.parse(msg.value()).as[WeatherData]
        logger.info(s"Consumed weather data: $weatherData")

        val currentTimestamp = new Timestamp(Instant.now.toEpochMilli)

        for {
          locationId <- locationRepository.insert(LocationEntity(None, weatherData.name, weatherData.coord.lat, weatherData.coord.lon))
          weatherConditionId <- weatherConditionRepository.insert(WeatherConditionEntity(None, weatherData.weather.head.main, weatherData.weather.head.description, weatherData.weather.head.icon))
          _ <- weatherDataRepository.insert(WeatherDataEntity(None, locationId, weatherConditionId, weatherData.main.temp, weatherData.main.feels_like, weatherData.main.temp_min, weatherData.main.temp_max, weatherData.main.pressure, weatherData.main.humidity, weatherData.visibility, weatherData.wind.speed, weatherData.wind.deg, weatherData.clouds.all, weatherData.dt, weatherData.sys.`type`, weatherData.sys.id, weatherData.sys.sunrise, weatherData.sys.sunset, weatherData.timezone, weatherData.name, weatherData.cod, currentTimestamp))
        } yield ()
      }
      .runWith(Sink.ignore)
  }
}
