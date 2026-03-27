package com.airline.controller;

import com.airline.dto.AirportDTO;
import com.airline.dto.ApiResponse;
import com.airline.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/airports")
@CrossOrigin(origins = "*")
public class AirportController {

    @Autowired
    private AirportService airportService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AirportDTO>>> getAllAirports() {
        List<AirportDTO> airports = airportService.getAllAirports();
        return ResponseEntity.ok(ApiResponse.success("Airports fetched successfully", airports));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<AirportDTO>>> getActiveAirports() {
        List<AirportDTO> airports = airportService.getActiveAirports();
        return ResponseEntity.ok(ApiResponse.success("Active airports fetched successfully", airports));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AirportDTO>> getAirportById(@PathVariable Long id) {
        AirportDTO airport = airportService.getAirportById(id);
        return ResponseEntity.ok(ApiResponse.success("Airport fetched successfully", airport));
    }

    @GetMapping("/iata/{code}")
    public ResponseEntity<ApiResponse<AirportDTO>> getAirportByIataCode(@PathVariable String code) {
        AirportDTO airport = airportService.getAirportByIataCode(code);
        return ResponseEntity.ok(ApiResponse.success("Airport fetched successfully", airport));
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<ApiResponse<List<AirportDTO>>> getAirportsByCountry(@PathVariable String country) {
        List<AirportDTO> airports = airportService.getAirportsByCountry(country);
        return ResponseEntity.ok(ApiResponse.success("Airports fetched successfully", airports));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AirportDTO>> createAirport(@Valid @RequestBody AirportDTO dto) {
        AirportDTO created = airportService.createAirport(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Airport created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AirportDTO>> updateAirport(
            @PathVariable Long id,
            @Valid @RequestBody AirportDTO dto) {
        AirportDTO updated = airportService.updateAirport(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Airport updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAirport(@PathVariable Long id) {
        airportService.deleteAirport(id);
        return ResponseEntity.ok(ApiResponse.success("Airport deleted successfully", null));
    }
}
