package com.elan.weather.repositories

import slick.jdbc.PostgresProfile.api._
import com.elan.weather.entities.{LocationEntity, WeatherConditionEntity, WeatherDataEntity}
import com.elan.weather.tables.{LocationTable, WeatherConditionTable, WeatherDataTable}
import scala.concurrent.{ExecutionContext, Future}

class LocationRepository(db: Database)(implicit ec: ExecutionContext) {
  private val locations = TableQuery[LocationTable]

  def insert(location: LocationEntity): Future[Int] = db.run {
    (locations returning locations.map(_.id)) += location
  }
}

class WeatherConditionRepository(db: Database)(implicit ec: ExecutionContext) {
  private val weatherConditions = TableQuery[WeatherConditionTable]

  def insert(weatherCondition: WeatherConditionEntity): Future[Int] = db.run {
    (weatherConditions returning weatherConditions.map(_.id)) += weatherCondition
  }
}

class WeatherDataRepository(db: Database)(implicit ec: ExecutionContext) {
  private val weatherData = TableQuery[WeatherDataTable]
  private val locations = TableQuery[LocationTable] // Ensure these are defined within the scope
  private val weatherConditions = TableQuery[WeatherConditionTable] // Ensure these are defined within the scope

  def insert(weatherDataEntry: WeatherDataEntity): Future[Int] = db.run {
    (weatherData returning weatherData.map(_.id)) += weatherDataEntry
  }

  def findAll: Future[Seq[(WeatherDataEntity, LocationEntity, WeatherConditionEntity)]] = {
    val query = for {
      ((weatherData, location), weatherCondition) <- weatherData join locations on (_.locationId === _.id) join weatherConditions on (_._1.weatherConditionId === _.id)
    } yield (weatherData, location, weatherCondition)

    db.run(query.result)
  }
}
