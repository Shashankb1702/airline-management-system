-- ============================================================
--  Airline Management System - MySQL 5.1.42 Compatible Schema
--  Run this script to create the database before starting the app
-- ============================================================

CREATE DATABASE IF NOT EXISTS airline_db
    CHARACTER SET utf8
    COLLATE utf8_general_ci;

USE airline_db;

-- Airlines
CREATE TABLE IF NOT EXISTS airlines (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    iata_code   VARCHAR(3) NOT NULL,
    country     VARCHAR(100),
    is_active   TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    UNIQUE KEY uk_airlines_iata_code (iata_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Airports
CREATE TABLE IF NOT EXISTS airports (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    name        VARCHAR(150) NOT NULL,
    iata_code   VARCHAR(3) NOT NULL,
    city        VARCHAR(100) NOT NULL,
    country     VARCHAR(100) NOT NULL,
    timezone    VARCHAR(50),
    is_active   TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    UNIQUE KEY uk_airports_iata_code (iata_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Aircrafts
CREATE TABLE IF NOT EXISTS aircrafts (
    id                  BIGINT NOT NULL AUTO_INCREMENT,
    model               VARCHAR(100) NOT NULL,
    registration_number VARCHAR(20) NOT NULL,
    total_seats         INT NOT NULL,
    status              VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    airline_id          BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_aircrafts_reg_number (registration_number),
    CONSTRAINT fk_aircrafts_airline FOREIGN KEY (airline_id) REFERENCES airlines (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Flights
CREATE TABLE IF NOT EXISTS flights (
    id                      BIGINT NOT NULL AUTO_INCREMENT,
    flight_number           VARCHAR(10) NOT NULL,
    airline_id              BIGINT NOT NULL,
    aircraft_id             BIGINT NOT NULL,
    origin_airport_id       BIGINT NOT NULL,
    destination_airport_id  BIGINT NOT NULL,
    departure_time          DATETIME NOT NULL,
    arrival_time            DATETIME NOT NULL,
    base_price              DECIMAL(10,2) NOT NULL,
    available_seats         INT NOT NULL,
    status                  VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',
    PRIMARY KEY (id),
    UNIQUE KEY uk_flights_flight_number (flight_number),
    CONSTRAINT fk_flights_airline        FOREIGN KEY (airline_id)             REFERENCES airlines (id),
    CONSTRAINT fk_flights_aircraft       FOREIGN KEY (aircraft_id)            REFERENCES aircrafts (id),
    CONSTRAINT fk_flights_origin         FOREIGN KEY (origin_airport_id)      REFERENCES airports (id),
    CONSTRAINT fk_flights_destination    FOREIGN KEY (destination_airport_id) REFERENCES airports (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Passengers
CREATE TABLE IF NOT EXISTS passengers (
    id                BIGINT NOT NULL AUTO_INCREMENT,
    first_name        VARCHAR(50) NOT NULL,
    last_name         VARCHAR(50) NOT NULL,
    email             VARCHAR(100) NOT NULL,
    phone             VARCHAR(20),
    passport_number   VARCHAR(20) NOT NULL,
    nationality       VARCHAR(50),
    PRIMARY KEY (id),
    UNIQUE KEY uk_passengers_email          (email),
    UNIQUE KEY uk_passengers_passport       (passport_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Bookings
CREATE TABLE IF NOT EXISTS bookings (
    id                BIGINT NOT NULL AUTO_INCREMENT,
    booking_reference VARCHAR(10) NOT NULL,
    flight_id         BIGINT NOT NULL,
    passenger_id      BIGINT NOT NULL,
    seat_number       VARCHAR(5),
    total_amount      DECIMAL(10,2) NOT NULL,
    status            VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED',
    seat_class        VARCHAR(20) NOT NULL DEFAULT 'ECONOMY',
    booking_date      DATETIME NOT NULL,
    checkin_time      DATETIME,
    PRIMARY KEY (id),
    UNIQUE KEY uk_bookings_reference (booking_reference),
    CONSTRAINT fk_bookings_flight     FOREIGN KEY (flight_id)    REFERENCES flights (id),
    CONSTRAINT fk_bookings_passenger  FOREIGN KEY (passenger_id) REFERENCES passengers (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
