package com.airline.service;

import com.airline.dto.AirportDTO;
import com.airline.exception.DuplicateResourceException;
import com.airline.exception.ResourceNotFoundException;
import com.airline.model.Airport;
import com.airline.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AirportService {

    @Autowired
    private AirportRepository airportRepository;

    public List<AirportDTO> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AirportDTO> getActiveAirports() {
        return airportRepository.findByIsActive(true).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AirportDTO getAirportById(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", id));
        return toDTO(airport);
    }

    public AirportDTO getAirportByIataCode(String iataCode) {
        Airport airport = airportRepository.findByIataCode(iataCode.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "IATA code", iataCode));
        return toDTO(airport);
    }

    public List<AirportDTO> getAirportsByCountry(String country) {
        return airportRepository.findByCountry(country).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AirportDTO createAirport(AirportDTO dto) {
        String code = dto.getIataCode().toUpperCase();
        if (airportRepository.existsByIataCode(code)) {
            throw new DuplicateResourceException("Airport with IATA code '" + code + "' already exists");
        }
        Airport airport = toEntity(dto);
        airport.setIataCode(code);
        return toDTO(airportRepository.save(airport));
    }

    public AirportDTO updateAirport(Long id, AirportDTO dto) {
        Airport existing = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport", "id", id));

        String newCode = dto.getIataCode().toUpperCase();
        if (!existing.getIataCode().equals(newCode) && airportRepository.existsByIataCode(newCode)) {
            throw new DuplicateResourceException("Airport with IATA code '" + newCode + "' already exists");
        }

        existing.setName(dto.getName());
        existing.setIataCode(newCode);
        existing.setCity(dto.getCity());
        existing.setCountry(dto.getCountry());
        existing.setTimezone(dto.getTimezone());
        if (dto.getIsActive() != null) {
            existing.setIsActive(dto.getIsActive());
        }
        return toDTO(airportRepository.save(existing));
    }

    public void deleteAirport(Long id) {
        if (!airportRepository.existsById(id)) {
            throw new ResourceNotFoundException("Airport", "id", id);
        }
        airportRepository.deleteById(id);
    }

    private AirportDTO toDTO(Airport airport) {
        AirportDTO dto = new AirportDTO();
        dto.setId(airport.getId());
        dto.setName(airport.getName());
        dto.setIataCode(airport.getIataCode());
        dto.setCity(airport.getCity());
        dto.setCountry(airport.getCountry());
        dto.setTimezone(airport.getTimezone());
        dto.setIsActive(airport.getIsActive());
        return dto;
    }

    private Airport toEntity(AirportDTO dto) {
        Airport airport = new Airport();
        airport.setName(dto.getName());
        airport.setIataCode(dto.getIataCode());
        airport.setCity(dto.getCity());
        airport.setCountry(dto.getCountry());
        airport.setTimezone(dto.getTimezone());
        airport.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        return airport;
    }
}
