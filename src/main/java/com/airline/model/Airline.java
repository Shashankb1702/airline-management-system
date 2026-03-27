package com.airline.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airlines")
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Airline name is required")
    @Size(max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "IATA code is required")
    @Size(min = 2, max = 3)
    @Column(name = "iata_code", nullable = false, unique = true, length = 3)
    private String iataCode;

    @Size(max = 100)
    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @JsonIgnore
    @OneToMany(mappedBy = "airline", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Aircraft> aircrafts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "airline", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Flight> flights = new ArrayList<>();

    public Airline() {}

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

    public List<Aircraft> getAircrafts() { return aircrafts; }
    public void setAircrafts(List<Aircraft> aircrafts) { this.aircrafts = aircrafts; }

    public List<Flight> getFlights() { return flights; }
    public void setFlights(List<Flight> flights) { this.flights = flights; }
}
