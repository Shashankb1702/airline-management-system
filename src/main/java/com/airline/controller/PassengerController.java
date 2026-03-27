package com.airline.controller;

import com.airline.dto.ApiResponse;
import com.airline.dto.PassengerDTO;
import com.airline.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/passengers")
@CrossOrigin(origins = "*")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PassengerDTO>>> getAllPassengers() {
        List<PassengerDTO> passengers = passengerService.getAllPassengers();
        return ResponseEntity.ok(ApiResponse.success("Passengers fetched successfully", passengers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PassengerDTO>> getPassengerById(@PathVariable Long id) {
        PassengerDTO passenger = passengerService.getPassengerById(id);
        return ResponseEntity.ok(ApiResponse.success("Passenger fetched successfully", passenger));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<PassengerDTO>> getPassengerByEmail(@PathVariable String email) {
        PassengerDTO passenger = passengerService.getPassengerByEmail(email);
        return ResponseEntity.ok(ApiResponse.success("Passenger fetched successfully", passenger));
    }

    @GetMapping("/passport/{passportNumber}")
    public ResponseEntity<ApiResponse<PassengerDTO>> getPassengerByPassport(
            @PathVariable String passportNumber) {
        PassengerDTO passenger = passengerService.getPassengerByPassport(passportNumber);
        return ResponseEntity.ok(ApiResponse.success("Passenger fetched successfully", passenger));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PassengerDTO>> createPassenger(
            @Valid @RequestBody PassengerDTO dto) {
        PassengerDTO created = passengerService.createPassenger(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Passenger created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PassengerDTO>> updatePassenger(
            @PathVariable Long id,
            @Valid @RequestBody PassengerDTO dto) {
        PassengerDTO updated = passengerService.updatePassenger(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Passenger updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.ok(ApiResponse.success("Passenger deleted successfully", null));
    }
}
