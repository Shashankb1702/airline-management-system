package com.airline.service;

import com.airline.dto.PassengerDTO;
import com.airline.exception.DuplicateResourceException;
import com.airline.exception.ResourceNotFoundException;
import com.airline.model.Passenger;
import com.airline.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    public List<PassengerDTO> getAllPassengers() {
        return passengerRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PassengerDTO getPassengerById(Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", "id", id));
        return toDTO(passenger);
    }

    public PassengerDTO getPassengerByEmail(String email) {
        Passenger passenger = passengerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", "email", email));
        return toDTO(passenger);
    }

    public PassengerDTO getPassengerByPassport(String passportNumber) {
        Passenger passenger = passengerRepository.findByPassportNumber(passportNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", "passport number", passportNumber));
        return toDTO(passenger);
    }

    public PassengerDTO createPassenger(PassengerDTO dto) {
        if (passengerRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Passenger with email '" + dto.getEmail() + "' already exists");
        }
        if (passengerRepository.existsByPassportNumber(dto.getPassportNumber())) {
            throw new DuplicateResourceException(
                    "Passenger with passport number '" + dto.getPassportNumber() + "' already exists");
        }
        Passenger passenger = toEntity(dto);
        return toDTO(passengerRepository.save(passenger));
    }

    public PassengerDTO updatePassenger(Long id, PassengerDTO dto) {
        Passenger existing = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", "id", id));

        if (!existing.getEmail().equals(dto.getEmail())
                && passengerRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Passenger with email '" + dto.getEmail() + "' already exists");
        }
        if (!existing.getPassportNumber().equals(dto.getPassportNumber())
                && passengerRepository.existsByPassportNumber(dto.getPassportNumber())) {
            throw new DuplicateResourceException(
                    "Passenger with passport number '" + dto.getPassportNumber() + "' already exists");
        }

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setPassportNumber(dto.getPassportNumber());
        existing.setNationality(dto.getNationality());

        return toDTO(passengerRepository.save(existing));
    }

    public void deletePassenger(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Passenger", "id", id);
        }
        passengerRepository.deleteById(id);
    }

    private PassengerDTO toDTO(Passenger passenger) {
        PassengerDTO dto = new PassengerDTO();
        dto.setId(passenger.getId());
        dto.setFirstName(passenger.getFirstName());
        dto.setLastName(passenger.getLastName());
        dto.setEmail(passenger.getEmail());
        dto.setPhone(passenger.getPhone());
        dto.setPassportNumber(passenger.getPassportNumber());
        dto.setNationality(passenger.getNationality());
        return dto;
    }

    private Passenger toEntity(PassengerDTO dto) {
        Passenger passenger = new Passenger();
        passenger.setFirstName(dto.getFirstName());
        passenger.setLastName(dto.getLastName());
        passenger.setEmail(dto.getEmail());
        passenger.setPhone(dto.getPhone());
        passenger.setPassportNumber(dto.getPassportNumber());
        passenger.setNationality(dto.getNationality());
        return passenger;
    }
}
