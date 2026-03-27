package com.airline.config;

import com.airline.model.*;
import com.airline.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public void run(String... args) {
        if (airlineRepository.count() > 0) {
            logger.info("Database already seeded. Skipping initialization.");
            return;
        }

        logger.info("Seeding sample data...");

        // --- Airlines ---
        Airline indigo = new Airline();
        indigo.setName("IndiGo");
        indigo.setIataCode("6E");
        indigo.setCountry("India");
        indigo.setIsActive(true);
        airlineRepository.save(indigo);

        Airline airIndia = new Airline();
        airIndia.setName("Air India");
        airIndia.setIataCode("AI");
        airIndia.setCountry("India");
        airIndia.setIsActive(true);
        airlineRepository.save(airIndia);

        // --- Airports ---
        Airport del = new Airport();
        del.setName("Indira Gandhi International Airport");
        del.setIataCode("DEL");
        del.setCity("New Delhi");
        del.setCountry("India");
        del.setTimezone("Asia/Kolkata");
        del.setIsActive(true);
        airportRepository.save(del);

        Airport bom = new Airport();
        bom.setName("Chhatrapati Shivaji Maharaj International Airport");
        bom.setIataCode("BOM");
        bom.setCity("Mumbai");
        bom.setCountry("India");
        bom.setTimezone("Asia/Kolkata");
        bom.setIsActive(true);
        airportRepository.save(bom);

        Airport blr = new Airport();
        blr.setName("Kempegowda International Airport");
        blr.setIataCode("BLR");
        blr.setCity("Bengaluru");
        blr.setCountry("India");
        blr.setTimezone("Asia/Kolkata");
        blr.setIsActive(true);
        airportRepository.save(blr);

        Airport hyd = new Airport();
        hyd.setName("Rajiv Gandhi International Airport");
        hyd.setIataCode("HYD");
        hyd.setCity("Hyderabad");
        hyd.setCountry("India");
        hyd.setTimezone("Asia/Kolkata");
        hyd.setIsActive(true);
        airportRepository.save(hyd);

        // --- Aircrafts ---
        Aircraft a320 = new Aircraft();
        a320.setModel("Airbus A320");
        a320.setRegistrationNumber("VT-IGO");
        a320.setTotalSeats(180);
        a320.setStatus(Aircraft.AircraftStatus.ACTIVE);
        a320.setAirline(indigo);
        aircraftRepository.save(a320);

        Aircraft b737 = new Aircraft();
        b737.setModel("Boeing 737-800");
        b737.setRegistrationNumber("VT-AIB");
        b737.setTotalSeats(162);
        b737.setStatus(Aircraft.AircraftStatus.ACTIVE);
        b737.setAirline(airIndia);
        aircraftRepository.save(b737);

        // --- Flights ---
        Flight f1 = new Flight();
        f1.setFlightNumber("6E201");
        f1.setAirline(indigo);
        f1.setAircraft(a320);
        f1.setOriginAirport(del);
        f1.setDestinationAirport(bom);
        f1.setDepartureTime(LocalDateTime.now().plusDays(1).withHour(6).withMinute(0).withSecond(0).withNano(0));
        f1.setArrivalTime(LocalDateTime.now().plusDays(1).withHour(8).withMinute(10).withSecond(0).withNano(0));
        f1.setBasePrice(new BigDecimal("3500.00"));
        f1.setAvailableSeats(180);
        f1.setStatus(Flight.FlightStatus.SCHEDULED);
        flightRepository.save(f1);

        Flight f2 = new Flight();
        f2.setFlightNumber("AI501");
        f2.setAirline(airIndia);
        f2.setAircraft(b737);
        f2.setOriginAirport(bom);
        f2.setDestinationAirport(blr);
        f2.setDepartureTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(30).withSecond(0).withNano(0));
        f2.setArrivalTime(LocalDateTime.now().plusDays(1).withHour(12).withMinute(0).withSecond(0).withNano(0));
        f2.setBasePrice(new BigDecimal("4200.00"));
        f2.setAvailableSeats(162);
        f2.setStatus(Flight.FlightStatus.SCHEDULED);
        flightRepository.save(f2);

        Flight f3 = new Flight();
        f3.setFlightNumber("6E350");
        f3.setAirline(indigo);
        f3.setAircraft(a320);
        f3.setOriginAirport(blr);
        f3.setDestinationAirport(hyd);
        f3.setDepartureTime(LocalDateTime.now().plusDays(2).withHour(14).withMinute(0).withSecond(0).withNano(0));
        f3.setArrivalTime(LocalDateTime.now().plusDays(2).withHour(15).withMinute(10).withSecond(0).withNano(0));
        f3.setBasePrice(new BigDecimal("2800.00"));
        f3.setAvailableSeats(180);
        f3.setStatus(Flight.FlightStatus.SCHEDULED);
        flightRepository.save(f3);

        // --- Passengers ---
        Passenger p1 = new Passenger();
        p1.setFirstName("Rahul");
        p1.setLastName("Sharma");
        p1.setEmail("rahul.sharma@example.com");
        p1.setPhone("+919876543210");
        p1.setPassportNumber("P1234567");
        p1.setNationality("Indian");
        passengerRepository.save(p1);

        Passenger p2 = new Passenger();
        p2.setFirstName("Priya");
        p2.setLastName("Mehta");
        p2.setEmail("priya.mehta@example.com");
        p2.setPhone("+919876500001");
        p2.setPassportNumber("P7654321");
        p2.setNationality("Indian");
        passengerRepository.save(p2);

        logger.info("Sample data seeded successfully.");
    }
}
