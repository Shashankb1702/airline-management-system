package com.airline.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Flight number is required")
    @Size(max = 10)
    @Column(name = "flight_number", nullable = false, unique = true, length = 10)
    private String flightNumber;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_airport_id", nullable = false)
    private Airport originAirport;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_airport_id", nullable = false)
    private Airport destinationAirport;

    @NotNull(message = "Departure time is required")
    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @NotNull(message = "Base price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "base_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private FlightStatus status = FlightStatus.SCHEDULED;

    @JsonIgnore
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings = new ArrayList<>();

    public enum FlightStatus {
        SCHEDULED, DELAYED, CANCELLED, DEPARTED, ARRIVED
    }

    public Flight() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public Airline getAirline() { return airline; }
    public void setAirline(Airline airline) { this.airline = airline; }

    public Aircraft getAircraft() { return aircraft; }
    public void setAircraft(Aircraft aircraft) { this.aircraft = aircraft; }

    public Airport getOriginAirport() { return originAirport; }
    public void setOriginAirport(Airport originAirport) { this.originAirport = originAirport; }

    public Airport getDestinationAirport() { return destinationAirport; }
    public void setDestinationAirport(Airport destinationAirport) { this.destinationAirport = destinationAirport; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }

    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }

    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }

    public FlightStatus getStatus() { return status; }
    public void setStatus(FlightStatus status) { this.status = status; }

    public List<Booking> getBookings() { return bookings; }
    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }
}
