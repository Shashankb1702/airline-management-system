package com.airline.service;

import com.airline.dto.AircraftDTO;
import com.airline.exception.DuplicateResourceException;
import com.airline.exception.ResourceNotFoundException;
import com.airline.model.Aircraft;
import com.airline.model.Airline;
import com.airline.repository.AircraftRepository;
import com.airline.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AircraftService {

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    public List<AircraftDTO> getAllAircrafts() {
        return aircraftRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AircraftDTO getAircraftById(Long id) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft", "id", id));
        return toDTO(aircraft);
    }

    public List<AircraftDTO> getAircraftsByAirline(Long airlineId) {
        if (!airlineRepository.existsById(airlineId)) {
            throw new ResourceNotFoundException("Airline", "id", airlineId);
        }
        return aircraftRepository.findByAirlineId(airlineId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AircraftDTO> getAircraftsByStatus(Aircraft.AircraftStatus status) {
        return aircraftRepository.findByStatus(status).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AircraftDTO createAircraft(AircraftDTO dto) {
        if (aircraftRepository.existsByRegistrationNumber(dto.getRegistrationNumber())) {
            throw new DuplicateResourceException(
                    "Aircraft with registration number '" + dto.getRegistrationNumber() + "' already exists");
        }
        Airline airline = airlineRepository.findById(dto.getAirlineId())
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "id", dto.getAirlineId()));

        Aircraft aircraft = toEntity(dto, airline);
        return toDTO(aircraftRepository.save(aircraft));
    }

    public AircraftDTO updateAircraft(Long id, AircraftDTO dto) {
        Aircraft existing = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft", "id", id));

        if (!existing.getRegistrationNumber().equals(dto.getRegistrationNumber())
                && aircraftRepository.existsByRegistrationNumber(dto.getRegistrationNumber())) {
            throw new DuplicateResourceException(
                    "Aircraft with registration number '" + dto.getRegistrationNumber() + "' already exists");
        }

        Airline airline = airlineRepository.findById(dto.getAirlineId())
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "id", dto.getAirlineId()));

        existing.setModel(dto.getModel());
        existing.setRegistrationNumber(dto.getRegistrationNumber());
        existing.setTotalSeats(dto.getTotalSeats());
        existing.setStatus(dto.getStatus() != null ? dto.getStatus() : Aircraft.AircraftStatus.ACTIVE);
        existing.setAirline(airline);

        return toDTO(aircraftRepository.save(existing));
    }

    public AircraftDTO updateAircraftStatus(Long id, Aircraft.AircraftStatus status) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft", "id", id));
        aircraft.setStatus(status);
        return toDTO(aircraftRepository.save(aircraft));
    }

    public void deleteAircraft(Long id) {
        if (!aircraftRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aircraft", "id", id);
        }
        aircraftRepository.deleteById(id);
    }

    private AircraftDTO toDTO(Aircraft aircraft) {
        AircraftDTO dto = new AircraftDTO();
        dto.setId(aircraft.getId());
        dto.setModel(aircraft.getModel());
        dto.setRegistrationNumber(aircraft.getRegistrationNumber());
        dto.setTotalSeats(aircraft.getTotalSeats());
        dto.setStatus(aircraft.getStatus());
        dto.setAirlineId(aircraft.getAirline().getId());
        dto.setAirlineName(aircraft.getAirline().getName());
        return dto;
    }

    private Aircraft toEntity(AircraftDTO dto, Airline airline) {
        Aircraft aircraft = new Aircraft();
        aircraft.setModel(dto.getModel());
        aircraft.setRegistrationNumber(dto.getRegistrationNumber());
        aircraft.setTotalSeats(dto.getTotalSeats());
        aircraft.setStatus(dto.getStatus() != null ? dto.getStatus() : Aircraft.AircraftStatus.ACTIVE);
        aircraft.setAirline(airline);
        return aircraft;
    }
}
