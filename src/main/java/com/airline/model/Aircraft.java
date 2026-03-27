package com.airline.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "aircrafts")
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Aircraft model is required")
    @Size(max = 100)
    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @NotBlank(message = "Registration number is required")
    @Size(max = 20)
    @Column(name = "registration_number", nullable = false, unique = true, length = 20)
    private String registrationNumber;

    @NotNull(message = "Total seats is required")
    @Min(value = 1)
    @Column(name = "total_seats", nullable = false)
    private Integer totalSeats;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AircraftStatus status = AircraftStatus.ACTIVE;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;

    @JsonIgnore
    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Flight> flights = new ArrayList<>();

    public enum AircraftStatus {
        ACTIVE, MAINTENANCE, RETIRED
    }

    public Aircraft() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public Integer getTotalSeats() { return totalSeats; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }

    public AircraftStatus getStatus() { return status; }
    public void setStatus(AircraftStatus status) { this.status = status; }

    public Airline getAirline() { return airline; }
    public void setAirline(Airline airline) { this.airline = airline; }

    public List<Flight> getFlights() { return flights; }
    public void setFlights(List<Flight> flights) { this.flights = flights; }
}
