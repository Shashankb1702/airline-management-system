package com.airline.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class FlightSearchDTO {

    @NotNull(message = "Origin airport ID is required")
    private Long originAirportId;

    @NotNull(message = "Destination airport ID is required")
    private Long destinationAirportId;

    @NotNull(message = "Travel date is required")
    private LocalDate travelDate;

    public Long getOriginAirportId() { return originAirportId; }
    public void setOriginAirportId(Long originAirportId) { this.originAirportId = originAirportId; }

    public Long getDestinationAirportId() { return destinationAirportId; }
    public void setDestinationAirportId(Long destinationAirportId) { this.destinationAirportId = destinationAirportId; }

    public LocalDate getTravelDate() { return travelDate; }
    public void setTravelDate(LocalDate travelDate) { this.travelDate = travelDate; }
}
