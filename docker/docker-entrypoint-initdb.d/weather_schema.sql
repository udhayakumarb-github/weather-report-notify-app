-- docker/docker-entrypoint-initdb.d/weather_schema.sql

-- Create the location table
CREATE TABLE IF NOT EXISTS location (
  id SERIAL PRIMARY KEY,
  city VARCHAR(255) NOT NULL,
  latitude DOUBLE PRECISION,
  longitude DOUBLE PRECISION
);

-- Create the weather_condition table
CREATE TABLE IF NOT EXISTS weather_condition (
  id SERIAL PRIMARY KEY,
  main VARCHAR(255),
  description VARCHAR(255),
  icon VARCHAR(50)
);

-- Create the weather_data table
CREATE TABLE IF NOT EXISTS weather_data (
  id SERIAL PRIMARY KEY,
  location_id INT REFERENCES location(id),
  weather_condition_id INT REFERENCES weather_condition(id),
  temp DOUBLE PRECISION,
  feels_like DOUBLE PRECISION,
  temp_min DOUBLE PRECISION,
  temp_max DOUBLE PRECISION,
  pressure INT,
  humidity INT,
  visibility INT,
  wind_speed DOUBLE PRECISION,
  wind_deg INT,
  clouds INT,
  dt INT,
  type INT,
  sys_id INT,
  sunrise INT,
  sunset INT,
  timezone INT,
  name VARCHAR(255),
  cod INT,
  received_at TIMESTAMP
);
