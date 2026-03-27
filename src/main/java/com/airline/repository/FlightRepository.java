package com.airline.repository;

import com.airline.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByFlightNumber(String flightNumber);

    List<Flight> findByStatus(Flight.FlightStatus status);

    List<Flight> findByAirlineId(Long airlineId);

    @Query("SELECT f FROM Flight f WHERE f.originAirport.id = :originId " +
           "AND f.destinationAirport.id = :destinationId " +
           "AND f.departureTime >= :fromDate AND f.departureTime <= :toDate " +
           "AND f.status = 'SCHEDULED'")
    List<Flight> findAvailableFlights(
            @Param("originId") Long originId,
            @Param("destinationId") Long destinationId,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );

    @Query("SELECT f FROM Flight f WHERE f.departureTime >= :startTime AND f.departureTime <= :endTime")
    List<Flight> findFlightsByDateRange(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    boolean existsByFlightNumber(String flightNumber);
}
