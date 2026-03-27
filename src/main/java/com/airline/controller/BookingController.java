package com.airline.controller;

import com.airline.dto.ApiResponse;
import com.airline.dto.BookingDTO;
import com.airline.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookingDTO>>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(ApiResponse.success("Bookings fetched successfully", bookings));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingDTO>> getBookingById(@PathVariable Long id) {
        BookingDTO booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(ApiResponse.success("Booking fetched successfully", booking));
    }

    @GetMapping("/reference/{reference}")
    public ResponseEntity<ApiResponse<BookingDTO>> getBookingByReference(@PathVariable String reference) {
        BookingDTO booking = bookingService.getBookingByReference(reference);
        return ResponseEntity.ok(ApiResponse.success("Booking fetched successfully", booking));
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<ApiResponse<List<BookingDTO>>> getBookingsByPassenger(
            @PathVariable Long passengerId) {
        List<BookingDTO> bookings = bookingService.getBookingsByPassenger(passengerId);
        return ResponseEntity.ok(ApiResponse.success("Bookings fetched successfully", bookings));
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<ApiResponse<List<BookingDTO>>> getBookingsByFlight(
            @PathVariable Long flightId) {
        List<BookingDTO> bookings = bookingService.getBookingsByFlight(flightId);
        return ResponseEntity.ok(ApiResponse.success("Bookings fetched successfully", bookings));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookingDTO>> createBooking(@Valid @RequestBody BookingDTO dto) {
        BookingDTO created = bookingService.createBooking(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Booking created successfully", created));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<BookingDTO>> cancelBooking(@PathVariable Long id) {
        BookingDTO cancelled = bookingService.cancelBooking(id);
        return ResponseEntity.ok(ApiResponse.success("Booking cancelled successfully", cancelled));
    }

    @PatchMapping("/{id}/checkin")
    public ResponseEntity<ApiResponse<BookingDTO>> checkIn(@PathVariable Long id) {
        BookingDTO checkedIn = bookingService.checkIn(id);
        return ResponseEntity.ok(ApiResponse.success("Passenger checked in successfully", checkedIn));
    }

    @PatchMapping("/{id}/board")
    public ResponseEntity<ApiResponse<BookingDTO>> boardPassenger(@PathVariable Long id) {
        BookingDTO boarded = bookingService.boardPassenger(id);
        return ResponseEntity.ok(ApiResponse.success("Passenger boarded successfully", boarded));
    }
}
