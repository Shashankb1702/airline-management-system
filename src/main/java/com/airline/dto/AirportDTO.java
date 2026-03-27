package com.airline.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AirportDTO {
    private Long id;

    @NotBlank(message = "Airport name is required")
    @Size(max = 150)
    private String name;

    @NotBlank(message = "IATA code is required")
    @Size(min = 3, max = 3)
    private String iataCode;

    @NotBlank(message = "City is required")
    @Size(max = 100)
    private String city;

    @NotBlank(message = "Country is required")
    @Size(max = 100)
    private String country;

    @Size(max = 50)
    private String timezone;

    private Boolean isActive = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIataCode() { return iataCode; }
    public void setIataCode(String iataCode) { this.iataCode = iataCode; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
