package com.airline.dto;

import com.airline.model.Flight;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FlightDTO {
    private Long id;

    @NotBlank(message = "Flight number is required")
    @Size(max = 10)
    private String flightNumber;

    @NotNull(message = "Airline ID is required")
    private Long airlineId;
    private String airlineName;

    @NotNull(message = "Aircraft ID is required")
    private Long aircraftId;
    private String aircraftModel;

    @NotNull(message = "Origin airport ID is required")
    private Long originAirportId;
    private String originAirportName;
    private String originIataCode;

    @NotNull(message = "Destination airport ID is required")
    private Long destinationAirportId;
    private String destinationAirportName;
    private String destinationIataCode;

    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;

    @NotNull(message = "Base price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal basePrice;

    private Integer availableSeats;
    private Flight.FlightStatus status = Flight.FlightStatus.SCHEDULED;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public Long getAirlineId() { return airlineId; }
    public void setAirlineId(Long airlineId) { this.airlineId = airlineId; }

    public String getAirlineName() { return airlineName; }
    public void setAirlineName(String airlineName) { this.airlineName = airlineName; }

    public Long getAircraftId() { return aircraftId; }
    public void setAircraftId(Long aircraftId) { this.aircraftId = aircraftId; }

    public String getAircraftModel() { return aircraftModel; }
    public void setAircraftModel(String aircraftModel) { this.aircraftModel = aircraftModel; }

    public Long getOriginAirportId() { return originAirportId; }
    public void setOriginAirportId(Long originAirportId) { this.originAirportId = originAirportId; }

    public String getOriginAirportName() { return originAirportName; }
    public void setOriginAirportName(String originAirportName) { this.originAirportName = originAirportName; }

    public String getOriginIataCode() { return originIataCode; }
    public void setOriginIataCode(String originIataCode) { this.originIataCode = originIataCode; }

    public Long getDestinationAirportId() { return destinationAirportId; }
    public void setDestinationAirportId(Long destinationAirportId) { this.destinationAirportId = destinationAirportId; }

    public String getDestinationAirportName() { return destinationAirportName; }
    public void setDestinationAirportName(String destinationAirportName) { this.destinationAirportName = destinationAirportName; }

    public String getDestinationIataCode() { return destinationIataCode; }
    public void setDestinationIataCode(String destinationIataCode) { this.destinationIataCode = destinationIataCode; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }

    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }

    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }

    public Flight.FlightStatus getStatus() { return status; }
    public void setStatus(Flight.FlightStatus status) { this.status = status; }
}
