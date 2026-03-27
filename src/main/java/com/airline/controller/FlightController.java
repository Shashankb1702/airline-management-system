package com.airline.controller;

import com.airline.dto.ApiResponse;
import com.airline.dto.FlightDTO;
import com.airline.dto.FlightSearchDTO;
import com.airline.model.Flight;
import com.airline.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin(origins = "*")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<FlightDTO>>> getAllFlights() {
        List<FlightDTO> flights = flightService.getAllFlights();
        return ResponseEntity.ok(ApiResponse.success("Flights fetched successfully", flights));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FlightDTO>> getFlightById(@PathVariable Long id) {
        FlightDTO flight = flightService.getFlightById(id);
        return ResponseEntity.ok(ApiResponse.success("Flight fetched successfully", flight));
    }

    @GetMapping("/number/{flightNumber}")
    public ResponseEntity<ApiResponse<FlightDTO>> getFlightByNumber(@PathVariable String flightNumber) {
        FlightDTO flight = flightService.getFlightByNumber(flightNumber);
        return ResponseEntity.ok(ApiResponse.success("Flight fetched successfully", flight));
    }

    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<ApiResponse<List<FlightDTO>>> getFlightsByAirline(@PathVariable Long airlineId) {
        List<FlightDTO> flights = flightService.getFlightsByAirline(airlineId);
        return ResponseEntity.ok(ApiResponse.success("Flights fetched successfully", flights));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<FlightDTO>>> getFlightsByStatus(
            @PathVariable Flight.FlightStatus status) {
        List<FlightDTO> flights = flightService.getFlightsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Flights fetched successfully", flights));
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<FlightDTO>>> searchFlights(
            @Valid @RequestBody FlightSearchDTO searchDTO) {
        List<FlightDTO> flights = flightService.searchAvailableFlights(searchDTO);
        return ResponseEntity.ok(ApiResponse.success("Available flights fetched successfully", flights));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FlightDTO>> createFlight(@Valid @RequestBody FlightDTO dto) {
        FlightDTO created = flightService.createFlight(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Flight created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FlightDTO>> updateFlight(
            @PathVariable Long id,
            @Valid @RequestBody FlightDTO dto) {
        FlightDTO updated = flightService.updateFlight(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Flight updated successfully", updated));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<FlightDTO>> updateFlightStatus(
            @PathVariable Long id,
            @RequestParam Flight.FlightStatus status) {
        FlightDTO updated = flightService.updateFlightStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Flight status updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok(ApiResponse.success("Flight deleted successfully", null));
    }
}
