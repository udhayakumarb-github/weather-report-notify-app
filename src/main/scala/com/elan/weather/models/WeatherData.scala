package com.elan.weather.models

import java.sql.Timestamp

// Coordinates
case class Coord(lon: Double, lat: Double)

// Weather Details
case class Weather(id: Int, main: String, description: String, icon: String)

// Main Weather Data
case class Main(temp: Double, feels_like: Double, temp_min: Double, temp_max: Double, pressure: Int, humidity: Int, sea_level: Option[Int], grnd_level: Option[Int])

// Wind Data
case class Wind(speed: Double, deg: Int)

// Clouds Data
case class Clouds(all: Int)

// System Data
case class Sys(`type`: Int, id: Int, country: String, sunrise: Int, sunset: Int)

// Complete Weather Data
case class WeatherData(
                        coord: Coord,
                        weather: List[Weather],
                        base: String,
                        main: Main,
                        visibility: Int,
                        wind: Wind,
                        clouds: Clouds,
                        dt: Int,
                        sys: Sys,
                        timezone: Int,
                        id: Int,
                        name: String,
                        cod: Int
                      )
