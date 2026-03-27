package com.airline.controller;

import com.airline.dto.AirlineDTO;
import com.airline.dto.ApiResponse;
import com.airline.service.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@CrossOrigin(origins = "*")
public class AirlineController {

    @Autowired
    private AirlineService airlineService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AirlineDTO>>> getAllAirlines() {
        List<AirlineDTO> airlines = airlineService.getAllAirlines();
        return ResponseEntity.ok(ApiResponse.success("Airlines fetched successfully", airlines));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<AirlineDTO>>> getActiveAirlines() {
        List<AirlineDTO> airlines = airlineService.getActiveAirlines();
        return ResponseEntity.ok(ApiResponse.success("Active airlines fetched successfully", airlines));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AirlineDTO>> getAirlineById(@PathVariable Long id) {
        AirlineDTO airline = airlineService.getAirlineById(id);
        return ResponseEntity.ok(ApiResponse.success("Airline fetched successfully", airline));
    }

    @GetMapping("/iata/{code}")
    public ResponseEntity<ApiResponse<AirlineDTO>> getAirlineByIataCode(@PathVariable String code) {
        AirlineDTO airline = airlineService.getAirlineByIataCode(code);
        return ResponseEntity.ok(ApiResponse.success("Airline fetched successfully", airline));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AirlineDTO>> createAirline(@Valid @RequestBody AirlineDTO dto) {
        AirlineDTO created = airlineService.createAirline(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Airline created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AirlineDTO>> updateAirline(
            @PathVariable Long id,
            @Valid @RequestBody AirlineDTO dto) {
        AirlineDTO updated = airlineService.updateAirline(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Airline updated successfully", updated));
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<ApiResponse<AirlineDTO>> toggleAirlineStatus(@PathVariable Long id) {
        AirlineDTO updated = airlineService.toggleAirlineStatus(id);
        return ResponseEntity.ok(ApiResponse.success("Airline status updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAirline(@PathVariable Long id) {
        airlineService.deleteAirline(id);
        return ResponseEntity.ok(ApiResponse.success("Airline deleted successfully", null));
    }
}
