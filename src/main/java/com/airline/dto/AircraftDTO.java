package com.airline.dto;

import com.airline.model.Aircraft;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AircraftDTO {
    private Long id;

    @NotBlank(message = "Model is required")
    @Size(max = 100)
    private String model;

    @NotBlank(message = "Registration number is required")
    @Size(max = 20)
    private String registrationNumber;

    @NotNull(message = "Total seats is required")
    @Min(value = 1)
    private Integer totalSeats;

    private Aircraft.AircraftStatus status = Aircraft.AircraftStatus.ACTIVE;

    @NotNull(message = "Airline ID is required")
    private Long airlineId;

    private String airlineName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public Integer getTotalSeats() { return totalSeats; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }

    public Aircraft.AircraftStatus getStatus() { return status; }
    public void setStatus(Aircraft.AircraftStatus status) { this.status = status; }

    public Long getAirlineId() { return airlineId; }
    public void setAirlineId(Long airlineId) { this.airlineId = airlineId; }

    public String getAirlineName() { return airlineName; }
    public void setAirlineName(String airlineName) { this.airlineName = airlineName; }
}
