package com.elan.weather.models

import play.api.libs.json._

// JSON Formats
object JsonFormats {
  implicit val coordFormat: OFormat[Coord] = Json.format[Coord]
  implicit val weatherFormat: OFormat[Weather] = Json.format[Weather]
  implicit val mainFormat: OFormat[Main] = Json.format[Main]
  implicit val windFormat: OFormat[Wind] = Json.format[Wind]
  implicit val cloudsFormat: OFormat[Clouds] = Json.format[Clouds]
  implicit val sysFormat: OFormat[Sys] = Json.format[Sys]
  implicit val weatherDataFormat: OFormat[WeatherData] = Json.format[WeatherData]
}
