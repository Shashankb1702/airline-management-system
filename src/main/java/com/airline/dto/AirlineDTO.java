package com.airline.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AirlineDTO {
    private Long id;

    @NotBlank(message = "Airline name is required")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "IATA code is required")
    @Size(min = 2, max = 3)
    private String iataCode;

    @Size(max = 100)
    private String country;

    private Boolean isActive = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIataCode() { return iataCode; }
    public void setIataCode(String iataCode) { this.iataCode = iataCode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
