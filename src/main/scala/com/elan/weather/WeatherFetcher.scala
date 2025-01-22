package com.elan.weather

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior}
import com.elan.weather.api.WeatherApiFetcher
import com.elan.weather.models.{JsonFormats, WeatherData}
import com.elan.weather.producers.WeatherProducer
import com.elan.weather.utils.ConfigUtil
import org.slf4j.LoggerFactory
import play.api.libs.json.Json

import scala.concurrent.duration.*
import scala.util.{Failure, Success}

object WeatherFetcher {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def apply(): Behavior[Command] = Behaviors.setup { context =>
    implicit val system: ActorSystem[Nothing] = context.system
    implicit val ec = system.executionContext

    import JsonFormats._

    Behaviors.withTimers { timers =>
      timers.startTimerWithFixedDelay(FetchWeather, 10.seconds)
      logger.info("Scheduled weather data fetching every 1 minutes")
      val cities: List[String] = ConfigUtil.getCities
      Behaviors.receiveMessage {
        case FetchWeather =>
          cities.foreach { city =>
            logger.info(s"Fetching weather data for city: $city")
            val weatherApiFetcher = new WeatherApiFetcher(city)
            weatherApiFetcher.fetchWeatherData().onComplete {
              case Success(weatherData) =>
                logger.info(s"Successfully fetched weather data for $city: ${Json.toJson(weatherData)}")
                WeatherProducer.produceWeatherData(Json.toJson(weatherData).toString)
              case Failure(exception) =>
                logger.error(s"Failed to fetch weather data for $city", exception)
            }
          }
          Behaviors.same
      }
    }
  }
  
  sealed trait Command
  case object FetchWeather extends Command
}
