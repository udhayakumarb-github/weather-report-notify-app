package com.elan.weather.consumers

import akka.actor.typed.ActorSystem
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.kafka.scaladsl.Consumer
import akka.stream.scaladsl.Sink
import com.typesafe.config.Config
import org.apache.kafka.common.serialization.StringDeserializer
import play.api.libs.json.Json
import com.elan.weather.models.{WeatherData, JsonFormats}
import org.slf4j.LoggerFactory

object WeatherConsumer {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def consumeWeatherData()(implicit system: ActorSystem[_]): Unit = {
    import JsonFormats._

    val config: Config = system.settings.config.getConfig("akka.kafka.consumer")
    val consumerSettings = ConsumerSettings(config, new StringDeserializer, new StringDeserializer)
      .withBootstrapServers("localhost:9092")
      .withGroupId("weather-consumer-group")
      .withProperty("auto.offset.reset", "earliest")

    Consumer
      .plainSource(consumerSettings, Subscriptions.topics("weather-data"))
      .map { msg =>
        val weatherData = Json.parse(msg.value()).as[WeatherData]
        logger.info(s"Consumed weather data: $weatherData")
        weatherData
      }
      .runWith(Sink.ignore)
  }
}
