package com.airline.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "airports")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Airport name is required")
    @Size(max = 150)
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @NotBlank(message = "IATA code is required")
    @Size(min = 3, max = 3)
    @Column(name = "iata_code", nullable = false, unique = true, length = 3)
    private String iataCode;

    @NotBlank(message = "City is required")
    @Size(max = 100)
    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @NotBlank(message = "Country is required")
    @Size(max = 100)
    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @Column(name = "timezone", length = 50)
    private String timezone;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    public Airport() {}

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
