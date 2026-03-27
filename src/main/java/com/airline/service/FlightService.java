package com.airline.service;

import com.airline.dto.FlightDTO;
import com.airline.dto.FlightSearchDTO;
import com.airline.exception.BusinessException;
import com.airline.exception.DuplicateResourceException;
import com.airline.exception.ResourceNotFoundException;
import com.airline.model.Aircraft;
import com.airline.model.Airline;
import com.airline.model.Airport;
import com.airline.model.Flight;
import com.airline.repository.AircraftRepository;
import com.airline.repository.AirlineRepository;
import com.airline.repository.AirportRepository;
import com.airline.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AirportRepository airportRepository;

    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public FlightDTO getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", id));
        return toDTO(flight);
    }

    public FlightDTO getFlightByNumber(String flightNumber) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "flight number", flightNumber));
        return toDTO(flight);
    }

    public List<FlightDTO> getFlightsByAirline(Long airlineId) {
        if (!airlineRepository.existsById(airlineId)) {
            throw new ResourceNotFoundException("Airline", "id", airlineId);
        }
        return flightRepository.findByAirlineId(airlineId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<FlightDTO> getFlightsByStatus(Flight.FlightStatus status) {
        return flightRepository.findByStatus(status).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<FlightDTO> searchAvailableFlights(FlightSearchDTO searchDTO) {
        LocalDateTime startOfDay = searchDTO.getTravelDate().atStartOfDay();
        LocalDateTime endOfDay = searchDTO.getTravelDate().atTime(LocalTime.MAX);

        return flightRepository.findAvailableFlights(
                searchDTO.getOriginAirportId(),
                searchDTO.getDestinationAirportId(),
                startOfDay,
                endOfDay
        ).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public FlightDTO createFlight(FlightDTO dto) {
        String flightNum = dto.getFlightNumber().toUpperCase();
        if (flightRepository.existsByFlightNumber(flightNum)) {
            throw new DuplicateResourceException("Flight with number '" + flightNum + "' already exists");
        }

        validateFlightTimes(dto);

        Airline airline = airlineRepository.findById(dto.getAirlineId())
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "id", dto.getAirlineId()));

        Aircraft aircraft = aircraftRepository.findById(dto.getAircraftId())
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft", "id", dto.getAircraftId()));

        if (aircraft.getStatus() != Aircraft.AircraftStatus.ACTIVE) {
            throw new BusinessException("Aircraft is not active and cannot be assigned to a flight");
        }

        Airport origin = airportRepository.findById(dto.getOriginAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", dto.getOriginAirportId()));

        Airport destination = airportRepository.findById(dto.getDestinationAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", dto.getDestinationAirportId()));

        if (origin.getId().equals(destination.getId())) {
            throw new BusinessException("Origin and destination airports cannot be the same");
        }

        Flight flight = toEntity(dto, airline, aircraft, origin, destination);
        flight.setFlightNumber(flightNum);
        flight.setAvailableSeats(aircraft.getTotalSeats());

        return toDTO(flightRepository.save(flight));
    }

    public FlightDTO updateFlight(Long id, FlightDTO dto) {
        Flight existing = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", id));

        String newFlightNum = dto.getFlightNumber().toUpperCase();
        if (!existing.getFlightNumber().equals(newFlightNum)
                && flightRepository.existsByFlightNumber(newFlightNum)) {
            throw new DuplicateResourceException("Flight with number '" + newFlightNum + "' already exists");
        }

        validateFlightTimes(dto);

        Airline airline = airlineRepository.findById(dto.getAirlineId())
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "id", dto.getAirlineId()));

        Aircraft aircraft = aircraftRepository.findById(dto.getAircraftId())
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft", "id", dto.getAircraftId()));

        Airport origin = airportRepository.findById(dto.getOriginAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", dto.getOriginAirportId()));

        Airport destination = airportRepository.findById(dto.getDestinationAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", dto.getDestinationAirportId()));

        if (origin.getId().equals(destination.getId())) {
            throw new BusinessException("Origin and destination airports cannot be the same");
        }

        existing.setFlightNumber(newFlightNum);
        existing.setAirline(airline);
        existing.setAircraft(aircraft);
        existing.setOriginAirport(origin);
        existing.setDestinationAirport(destination);
        existing.setDepartureTime(dto.getDepartureTime());
        existing.setArrivalTime(dto.getArrivalTime());
        existing.setBasePrice(dto.getBasePrice());
        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }

        return toDTO(flightRepository.save(existing));
    }

    public FlightDTO updateFlightStatus(Long id, Flight.FlightStatus status) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", id));
        flight.setStatus(status);
        return toDTO(flightRepository.save(flight));
    }

    public void deleteFlight(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new ResourceNotFoundException("Flight", "id", id);
        }
        flightRepository.deleteById(id);
    }

    private void validateFlightTimes(FlightDTO dto) {
        if (dto.getDepartureTime() != null && dto.getArrivalTime() != null) {
            if (!dto.getArrivalTime().isAfter(dto.getDepartureTime())) {
                throw new BusinessException("Arrival time must be after departure time");
            }
        }
    }

    private FlightDTO toDTO(Flight flight) {
        FlightDTO dto = new FlightDTO();
        dto.setId(flight.getId());
        dto.setFlightNumber(flight.getFlightNumber());
        dto.setAirlineId(flight.getAirline().getId());
        dto.setAirlineName(flight.getAirline().getName());
        dto.setAircraftId(flight.getAircraft().getId());
        dto.setAircraftModel(flight.getAircraft().getModel());
        dto.setOriginAirportId(flight.getOriginAirport().getId());
        dto.setOriginAirportName(flight.getOriginAirport().getName());
        dto.setOriginIataCode(flight.getOriginAirport().getIataCode());
        dto.setDestinationAirportId(flight.getDestinationAirport().getId());
        dto.setDestinationAirportName(flight.getDestinationAirport().getName());
        dto.setDestinationIataCode(flight.getDestinationAirport().getIataCode());
        dto.setDepartureTime(flight.getDepartureTime());
        dto.setArrivalTime(flight.getArrivalTime());
        dto.setBasePrice(flight.getBasePrice());
        dto.setAvailableSeats(flight.getAvailableSeats());
        dto.setStatus(flight.getStatus());
        return dto;
    }

    private Flight toEntity(FlightDTO dto, Airline airline, Aircraft aircraft,
                            Airport origin, Airport destination) {
        Flight flight = new Flight();
        flight.setFlightNumber(dto.getFlightNumber());
        flight.setAirline(airline);
        flight.setAircraft(aircraft);
        flight.setOriginAirport(origin);
        flight.setDestinationAirport(destination);
        flight.setDepartureTime(dto.getDepartureTime());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setBasePrice(dto.getBasePrice());
        flight.setStatus(dto.getStatus() != null ? dto.getStatus() : Flight.FlightStatus.SCHEDULED);
        return flight;
    }
}
