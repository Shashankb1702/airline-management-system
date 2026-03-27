package com.airline.repository;

import com.airline.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    Optional<Aircraft> findByRegistrationNumber(String registrationNumber);
    List<Aircraft> findByAirlineId(Long airlineId);
    List<Aircraft> findByStatus(Aircraft.AircraftStatus status);
    boolean existsByRegistrationNumber(String registrationNumber);
}
