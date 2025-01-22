package com.elan.weather.api

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import play.api.libs.json.Json
import com.elan.weather.models.{JsonFormats, WeatherData}
import com.elan.weather.utils.ConfigUtil
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContextExecutor, Future}

class WeatherApiFetcher(city: String)(implicit system: ActorSystem[_]) {
  implicit val ec: ExecutionContextExecutor = system.executionContext
  private val logger = LoggerFactory.getLogger(this.getClass)

  val endpoint: String = ConfigUtil.getApiEndpoint
  val apiKey: String = ConfigUtil.getApiKey

  val url: String = s"$endpoint?q=$city&appid=$apiKey"

  def fetchWeatherData(): Future[WeatherData] = {
    logger.info(s"Fetching weather data from URL: $url")
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = url))

    responseFuture.flatMap { response =>
      logger.info(s"Received response with status: ${response.status}")
      Unmarshal(response.entity).to[String].map { jsonString =>
        import JsonFormats._
        val weatherData = Json.parse(jsonString).as[WeatherData]
        logger.info(s"Parsed weather data: $weatherData")
        weatherData
      }
    }
  }
}
