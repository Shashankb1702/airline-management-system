package com.airline.controller;

import com.airline.dto.AircraftDTO;
import com.airline.dto.ApiResponse;
import com.airline.model.Aircraft;
import com.airline.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/aircrafts")
@CrossOrigin(origins = "*")
public class AircraftController {

    @Autowired
    private AircraftService aircraftService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AircraftDTO>>> getAllAircrafts() {
        List<AircraftDTO> aircrafts = aircraftService.getAllAircrafts();
        return ResponseEntity.ok(ApiResponse.success("Aircrafts fetched successfully", aircrafts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AircraftDTO>> getAircraftById(@PathVariable Long id) {
        AircraftDTO aircraft = aircraftService.getAircraftById(id);
        return ResponseEntity.ok(ApiResponse.success("Aircraft fetched successfully", aircraft));
    }

    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<ApiResponse<List<AircraftDTO>>> getAircraftsByAirline(@PathVariable Long airlineId) {
        List<AircraftDTO> aircrafts = aircraftService.getAircraftsByAirline(airlineId);
        return ResponseEntity.ok(ApiResponse.success("Aircrafts fetched successfully", aircrafts));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<AircraftDTO>>> getAircraftsByStatus(
            @PathVariable Aircraft.AircraftStatus status) {
        List<AircraftDTO> aircrafts = aircraftService.getAircraftsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Aircrafts fetched successfully", aircrafts));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AircraftDTO>> createAircraft(@Valid @RequestBody AircraftDTO dto) {
        AircraftDTO created = aircraftService.createAircraft(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Aircraft created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AircraftDTO>> updateAircraft(
            @PathVariable Long id,
            @Valid @RequestBody AircraftDTO dto) {
        AircraftDTO updated = aircraftService.updateAircraft(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Aircraft updated successfully", updated));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<AircraftDTO>> updateAircraftStatus(
            @PathVariable Long id,
            @RequestParam Aircraft.AircraftStatus status) {
        AircraftDTO updated = aircraftService.updateAircraftStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Aircraft status updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAircraft(@PathVariable Long id) {
        aircraftService.deleteAircraft(id);
        return ResponseEntity.ok(ApiResponse.success("Aircraft deleted successfully", null));
    }
}
