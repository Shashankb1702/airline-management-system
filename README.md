# ✈️ Airline Management System
Spring Boot REST API — fully compatible with **MySQL 5.1.42**

---

## Tech Stack
| Layer | Technology |
|---|---|
| Framework | Spring Boot 2.7.18 |
| Language | Java 11 |
| Database | MySQL 5.1.42 |
| ORM | Spring Data JPA / Hibernate |
| Dialect | `MySQL5InnoDBDialect` |
| Driver | `mysql-connector-java 5.1.49` |
| Validation | Hibernate Validator (JSR-380) |
| Build | Maven |

---

## Project Structure
```
src/main/java/com/airline/
├── AirlineManagementApplication.java
├── config/
│   ├── DataInitializer.java      ← seeds sample data on first run
│   └── JacksonConfig.java        ← LocalDateTime serialization
├── controller/
│   ├── AirlineController.java
│   ├── AircraftController.java
│   ├── AirportController.java
│   ├── FlightController.java
│   ├── PassengerController.java
│   └── BookingController.java
├── dto/
│   ├── AirlineDTO.java
│   ├── AircraftDTO.java
│   ├── AirportDTO.java
│   ├── FlightDTO.java
│   ├── FlightSearchDTO.java
│   ├── PassengerDTO.java
│   ├── BookingDTO.java
│   └── ApiResponse.java
├── exception/
│   ├── BusinessException.java
│   ├── DuplicateResourceException.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── model/
│   ├── Airline.java
│   ├── Aircraft.java
│   ├── Airport.java
│   ├── Flight.java
│   ├── Passenger.java
│   └── Booking.java
├── repository/
│   ├── AirlineRepository.java
│   ├── AircraftRepository.java
│   ├── AirportRepository.java
│   ├── FlightRepository.java
│   ├── PassengerRepository.java
│   └── BookingRepository.java
└── service/
    ├── AirlineService.java
    ├── AircraftService.java
    ├── AirportService.java
    ├── FlightService.java
    ├── PassengerService.java
    └── BookingService.java
```

---

## Step 1 — Create the Database

Run the provided SQL script against your MySQL 5.1.42 server:

```bash
mysql -u root -p < src/main/resources/schema.sql
```

Or manually in MySQL:
```sql
CREATE DATABASE IF NOT EXISTS airline_db CHARACTER SET utf8 COLLATE utf8_general_ci;
```

---

## Step 2 — Configure Database Credentials

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/airline_db?useSSL=false&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&zeroDateTimeBehavior=convertToNull
spring.datasource.username=root
spring.datasource.password=your_password_here
```

---

## Step 3 — Build & Run

```bash
# Build
mvn clean package -DskipTests

# Run
java -jar target/airline-management-system-1.0.0.jar

# Or with Maven
mvn spring-boot:run
```

The app starts at: **http://localhost:8080**

Sample data (airlines, airports, aircrafts, flights, passengers) is seeded automatically on first run.

---

## REST API Reference

### Airlines — `/api/airlines`
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/airlines` | List all airlines |
| GET | `/api/airlines/active` | List active airlines |
| GET | `/api/airlines/{id}` | Get airline by ID |
| GET | `/api/airlines/iata/{code}` | Get by IATA code |
| POST | `/api/airlines` | Create airline |
| PUT | `/api/airlines/{id}` | Update airline |
| PATCH | `/api/airlines/{id}/toggle-status` | Toggle active/inactive |
| DELETE | `/api/airlines/{id}` | Delete airline |

### Airports — `/api/airports`
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/airports` | List all airports |
| GET | `/api/airports/active` | List active airports |
| GET | `/api/airports/{id}` | Get airport by ID |
| GET | `/api/airports/iata/{code}` | Get by IATA code |
| GET | `/api/airports/country/{country}` | Get by country |
| POST | `/api/airports` | Create airport |
| PUT | `/api/airports/{id}` | Update airport |
| DELETE | `/api/airports/{id}` | Delete airport |

### Aircrafts — `/api/aircrafts`
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/aircrafts` | List all aircrafts |
| GET | `/api/aircrafts/{id}` | Get aircraft by ID |
| GET | `/api/aircrafts/airline/{airlineId}` | By airline |
| GET | `/api/aircrafts/status/{status}` | By status (ACTIVE/MAINTENANCE/RETIRED) |
| POST | `/api/aircrafts` | Create aircraft |
| PUT | `/api/aircrafts/{id}` | Update aircraft |
| PATCH | `/api/aircrafts/{id}/status?status=MAINTENANCE` | Update status |
| DELETE | `/api/aircrafts/{id}` | Delete aircraft |

### Flights — `/api/flights`
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/flights` | List all flights |
| GET | `/api/flights/{id}` | Get flight by ID |
| GET | `/api/flights/number/{flightNumber}` | Get by flight number |
| GET | `/api/flights/airline/{airlineId}` | By airline |
| GET | `/api/flights/status/{status}` | By status |
| POST | `/api/flights/search` | Search available flights |
| POST | `/api/flights` | Create flight |
| PUT | `/api/flights/{id}` | Update flight |
| PATCH | `/api/flights/{id}/status?status=DELAYED` | Update status |
| DELETE | `/api/flights/{id}` | Delete flight |

### Passengers — `/api/passengers`
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/passengers` | List all passengers |
| GET | `/api/passengers/{id}` | Get by ID |
| GET | `/api/passengers/email/{email}` | Get by email |
| GET | `/api/passengers/passport/{number}` | Get by passport |
| POST | `/api/passengers` | Create passenger |
| PUT | `/api/passengers/{id}` | Update passenger |
| DELETE | `/api/passengers/{id}` | Delete passenger |

### Bookings — `/api/bookings`
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/bookings` | List all bookings |
| GET | `/api/bookings/{id}` | Get booking by ID |
| GET | `/api/bookings/reference/{ref}` | Get by booking reference |
| GET | `/api/bookings/passenger/{id}` | By passenger |
| GET | `/api/bookings/flight/{id}` | By flight |
| POST | `/api/bookings` | Create booking |
| PATCH | `/api/bookings/{id}/cancel` | Cancel booking |
| PATCH | `/api/bookings/{id}/checkin` | Check in passenger |
| PATCH | `/api/bookings/{id}/board` | Board passenger |

---

## Sample API Requests

### Create an Airline
```http
POST /api/airlines
Content-Type: application/json

{
  "name": "SpiceJet",
  "iataCode": "SG",
  "country": "India"
}
```

### Search Available Flights
```http
POST /api/flights/search
Content-Type: application/json

{
  "originAirportId": 1,
  "destinationAirportId": 2,
  "travelDate": "2026-03-20"
}
```

### Create a Booking
```http
POST /api/bookings
Content-Type: application/json

{
  "flightId": 1,
  "passengerId": 1,
  "seatNumber": "12A",
  "seatClass": "ECONOMY"
}
```

### Cancel a Booking
```http
PATCH /api/bookings/1/cancel
```

---

## Enumerations
| Enum | Values |
|---|---|
| `AircraftStatus` | `ACTIVE`, `MAINTENANCE`, `RETIRED` |
| `FlightStatus` | `SCHEDULED`, `DELAYED`, `CANCELLED`, `DEPARTED`, `ARRIVED` |
| `BookingStatus` | `CONFIRMED`, `CANCELLED`, `CHECKED_IN`, `BOARDED` |
| `SeatClass` | `ECONOMY`, `BUSINESS`, `FIRST_CLASS` |

---

## Pricing Rules
| Class | Multiplier |
|---|---|
| Economy | 1× base price |
| Business | 2× base price |
| First Class | 3.5× base price |

---

## MySQL 5.1.42 Compatibility Notes
- Driver: `mysql-connector-java 5.1.49` (legacy JDBC driver, not Connector/J 8)
- Dialect: `org.hibernate.dialect.MySQL5InnoDBDialect`
- JDBC URL includes: `useSSL=false`, `zeroDateTimeBehavior=convertToNull`, `autoReconnect=true`
- All tables use `ENGINE=InnoDB` with `utf8` charset
- No JSON columns, no `DATETIME(6)` — all compatible with MySQL 5.1.x
