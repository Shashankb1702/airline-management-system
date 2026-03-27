package com.airline.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.airline.dto.AirlineDTO;
import com.airline.exception.DuplicateResourceException;
import com.airline.exception.ResourceNotFoundException;
import com.airline.model.Airline;
import com.airline.repository.AirlineRepository;

@Service
@Transactional
public class AirlineService {

    @Autowired
    private AirlineRepository airlineRepository;

    public List<AirlineDTO> getAllAirlines() {
        return airlineRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AirlineDTO> getActiveAirlines() {
        return airlineRepository.findByIsActive(true).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AirlineDTO getAirlineById(Long id) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "id", id));
        return toDTO(airline);
    }

    public AirlineDTO getAirlineByIataCode(String iataCode) {
        Airline airline = airlineRepository.findByIataCode(iataCode.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "IATA code", iataCode));
        return toDTO(airline);
    }

    public AirlineDTO createAirline(AirlineDTO dto) {
        String code = dto.getIataCode().toUpperCase();
        if (airlineRepository.existsByIataCode(code)) {
            throw new DuplicateResourceException("Airline with IATA code '" + code + "' already exists");
        }
        Airline airline = toEntity(dto);
        airline.setIataCode(code);
        return toDTO(airlineRepository.save(airline));
    }

    public AirlineDTO updateAirline(Long id, AirlineDTO dto) {
        Airline existing = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "id", id));

        String newCode = dto.getIataCode().toUpperCase();
        if (!existing.getIataCode().equals(newCode) && airlineRepository.existsByIataCode(newCode)) {
            throw new DuplicateResourceException("Airline with IATA code '" + newCode + "' already exists");
        }

        existing.setName(dto.getName());
        existing.setIataCode(newCode);
        existing.setCountry(dto.getCountry());
        if (dto.getIsActive() != null) {
            existing.setIsActive(dto.getIsActive());
        }
        return toDTO(airlineRepository.save(existing));
    }

    public void deleteAirline(Long id) {
        if (!airlineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Airline", "id", id);
        }
        airlineRepository.deleteById(id);
    }

    public AirlineDTO toggleAirlineStatus(Long id) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "id", id));
        airline.setIsActive(!airline.getIsActive());
        return toDTO(airlineRepository.save(airline));
    }

    private AirlineDTO toDTO(Airline airline) {
        AirlineDTO dto = new AirlineDTO();
        dto.setId(airline.getId());
        dto.setName(airline.getName());
        dto.setIataCode(airline.getIataCode());
        dto.setCountry(airline.getCountry());
        dto.setIsActive(airline.getIsActive());
        return dto;
    }

    private Airline toEntity(AirlineDTO dto) {
        Airline airline = new Airline();
        airline.setName(dto.getName());
        airline.setIataCode(dto.getIataCode());
        airline.setCountry(dto.getCountry());
        airline.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        return airline;
    }
}
