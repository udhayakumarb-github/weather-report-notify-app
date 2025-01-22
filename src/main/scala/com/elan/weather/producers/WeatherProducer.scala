package com.elan.weather.producers

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import akka.actor.typed.ActorSystem
import play.api.libs.json.Json
import com.elan.weather.models.{JsonFormats, WeatherData}
import org.slf4j.LoggerFactory

object WeatherProducer {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def produceWeatherData(weatherData: String)(implicit system: ActorSystem[_]): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    val record = new ProducerRecord[String, String]("weather-data", "weather", weatherData)

    logger.info(s"Sending weather data to Kafka topic 'weather-data': ${Json.toJson(weatherData)}")
    producer.send(record, (metadata, exception) => {
      if (exception != null) {
        logger.error("Failed to send weather data to Kafka", exception)
      } else {
        logger.info(s"Successfully sent weather data to Kafka: ${metadata.topic()} [${metadata.partition()}] @ offset ${metadata.offset()}")
      }
    })
    producer.close()
  }
}
