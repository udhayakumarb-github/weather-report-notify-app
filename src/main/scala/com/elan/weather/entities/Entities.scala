package com.elan.weather.entities

import java.sql.Timestamp

case class LocationEntity(id: Option[Int], city: String, latitude: Double, longitude: Double)

case class WeatherConditionEntity(id: Option[Int], main: String, description: String, icon: String)

case class WeatherDataEntity(
                              id: Option[Int],
                              location: Int,
                              weatherCondition: Int,
                              temp: Double,
                              feelsLike: Double,
                              tempMin: Double,
                              tempMax: Double,
                              pressure: Int,
                              humidity: Int,
                              visibility: Int,
                              windSpeed: Double,
                              windDeg: Int,
                              clouds: Int,
                              dt: Int,
                              `type`: Int,
                              sysId: Int,
                              sunrise: Int,
                              sunset: Int,
                              timezone: Int,
                              name: String,
                              cod: Int,
                              receivedAt: Timestamp
                            )

