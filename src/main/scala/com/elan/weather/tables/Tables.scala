package com.elan.weather.tables

import slick.jdbc.PostgresProfile.api.*
import com.elan.weather.entities.{LocationEntity, WeatherConditionEntity, WeatherDataEntity}

import java.sql.Timestamp

class LocationTable(tag: Tag) extends Table[LocationEntity](tag, "location") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def city = column[String]("city")
  def latitude = column[Double]("latitude")
  def longitude = column[Double]("longitude")
  def * = (id.?, city, latitude, longitude) <> ((LocationEntity.apply _).tupled, LocationEntity.unapply)
}

class WeatherConditionTable(tag: Tag) extends Table[WeatherConditionEntity](tag, "weather_condition") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def main = column[String]("main")
  def description = column[String]("description")
  def icon = column[String]("icon")
  def * = (id.?, main, description, icon) <> ((WeatherConditionEntity.apply _).tupled, WeatherConditionEntity.unapply)
}

class WeatherDataTable(tag: Tag) extends Table[WeatherDataEntity](tag, "weather_data") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def locationId = column[Int]("location_id")
  def weatherConditionId = column[Int]("weather_condition_id")
  def temp = column[Double]("temp")
  def feelsLike = column[Double]("feels_like")
  def tempMin = column[Double]("temp_min")
  def tempMax = column[Double]("temp_max")
  def pressure = column[Int]("pressure")
  def humidity = column[Int]("humidity")
  def visibility = column[Int]("visibility")
  def windSpeed = column[Double]("wind_speed")
  def windDeg = column[Int]("wind_deg")
  def clouds = column[Int]("clouds")
  def dt = column[Int]("dt")
  def `type` = column[Int]("type")
  def sysId = column[Int]("sys_id")
  def sunrise = column[Int]("sunrise")
  def sunset = column[Int]("sunset")
  def timezone = column[Int]("timezone")
  def name = column[String]("name")
  def cod = column[Int]("cod")
  def receivedAt = column[Timestamp]("received_at")

  def locationFk = foreignKey("location_fk", locationId, TableQuery[LocationTable])(_.id)
  def weatherConditionFk = foreignKey("weather_condition_fk", weatherConditionId, TableQuery[WeatherConditionTable])(_.id)

  def * = (
    id.?, locationId, weatherConditionId, temp, feelsLike, tempMin, tempMax, pressure, humidity,
    visibility, windSpeed, windDeg, clouds, dt, `type`, sysId, sunrise, sunset, timezone, name, cod, receivedAt
  ) <> ((WeatherDataEntity.apply _).tupled, WeatherDataEntity.unapply)
}
