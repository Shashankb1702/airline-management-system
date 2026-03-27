package com.airline.service;

import com.airline.dto.BookingDTO;
import com.airline.exception.BusinessException;
import com.airline.exception.ResourceNotFoundException;
import com.airline.model.Booking;
import com.airline.model.Flight;
import com.airline.model.Passenger;
import com.airline.repository.BookingRepository;
import com.airline.repository.FlightRepository;
import com.airline.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));
        return toDTO(booking);
    }

    public BookingDTO getBookingByReference(String reference) {
        Booking booking = bookingRepository.findByBookingReference(reference)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "reference", reference));
        return toDTO(booking);
    }

    public List<BookingDTO> getBookingsByPassenger(Long passengerId) {
        if (!passengerRepository.existsById(passengerId)) {
            throw new ResourceNotFoundException("Passenger", "id", passengerId);
        }
        return bookingRepository.findByPassengerId(passengerId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<BookingDTO> getBookingsByFlight(Long flightId) {
        if (!flightRepository.existsById(flightId)) {
            throw new ResourceNotFoundException("Flight", "id", flightId);
        }
        return bookingRepository.findByFlightId(flightId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public BookingDTO createBooking(BookingDTO dto) {
        Flight flight = flightRepository.findById(dto.getFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", dto.getFlightId()));

        if (flight.getStatus() == Flight.FlightStatus.CANCELLED) {
            throw new BusinessException("Cannot book a cancelled flight");
        }
        if (flight.getStatus() == Flight.FlightStatus.DEPARTED
                || flight.getStatus() == Flight.FlightStatus.ARRIVED) {
            throw new BusinessException("Cannot book a flight that has already departed or arrived");
        }
        if (flight.getAvailableSeats() <= 0) {
            throw new BusinessException("No available seats on this flight");
        }

        Passenger passenger = passengerRepository.findById(dto.getPassengerId())
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", "id", dto.getPassengerId()));

        if (bookingRepository.existsByFlightIdAndPassengerId(flight.getId(), passenger.getId())) {
            throw new BusinessException("Passenger already has a booking on this flight");
        }

        BigDecimal totalAmount = calculatePrice(flight.getBasePrice(),
                dto.getSeatClass() != null ? dto.getSeatClass() : Booking.SeatClass.ECONOMY);

        Booking booking = new Booking();
        booking.setBookingReference(generateBookingReference());
        booking.setFlight(flight);
        booking.setPassenger(passenger);
        booking.setSeatNumber(dto.getSeatNumber());
        booking.setTotalAmount(totalAmount);
        booking.setStatus(Booking.BookingStatus.CONFIRMED);
        booking.setSeatClass(dto.getSeatClass() != null ? dto.getSeatClass() : Booking.SeatClass.ECONOMY);
        booking.setBookingDate(LocalDateTime.now());

        // Decrement available seats
        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        return toDTO(bookingRepository.save(booking));
    }

    public BookingDTO cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));

        if (booking.getStatus() == Booking.BookingStatus.CANCELLED) {
            throw new BusinessException("Booking is already cancelled");
        }
        if (booking.getStatus() == Booking.BookingStatus.BOARDED) {
            throw new BusinessException("Cannot cancel a booking that has already boarded");
        }

        booking.setStatus(Booking.BookingStatus.CANCELLED);

        // Increment available seats back
        Flight flight = booking.getFlight();
        flight.setAvailableSeats(flight.getAvailableSeats() + 1);
        flightRepository.save(flight);

        return toDTO(bookingRepository.save(booking));
    }

    public BookingDTO checkIn(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));

        if (booking.getStatus() != Booking.BookingStatus.CONFIRMED) {
            throw new BusinessException("Only confirmed bookings can be checked in");
        }

        Flight flight = booking.getFlight();
        if (flight.getStatus() == Flight.FlightStatus.CANCELLED) {
            throw new BusinessException("Cannot check in for a cancelled flight");
        }

        booking.setStatus(Booking.BookingStatus.CHECKED_IN);
        booking.setCheckinTime(LocalDateTime.now());

        return toDTO(bookingRepository.save(booking));
    }

    public BookingDTO boardPassenger(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));

        if (booking.getStatus() != Booking.BookingStatus.CHECKED_IN) {
            throw new BusinessException("Passenger must be checked in before boarding");
        }

        booking.setStatus(Booking.BookingStatus.BOARDED);
        return toDTO(bookingRepository.save(booking));
    }

    private BigDecimal calculatePrice(BigDecimal basePrice, Booking.SeatClass seatClass) {
        switch (seatClass) {
            case BUSINESS:
                return basePrice.multiply(new BigDecimal("2.0"));
            case FIRST_CLASS:
                return basePrice.multiply(new BigDecimal("3.5"));
            default:
                return basePrice;
        }
    }

    private String generateBookingReference() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder ref = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            ref.append(chars.charAt(random.nextInt(chars.length())));
        }
        String reference = ref.toString();
        // Ensure uniqueness
        if (bookingRepository.existsByBookingReference(reference)) {
            return generateBookingReference();
        }
        return reference;
    }

    private BookingDTO toDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setBookingReference(booking.getBookingReference());
        dto.setFlightId(booking.getFlight().getId());
        dto.setFlightNumber(booking.getFlight().getFlightNumber());
        dto.setOriginIataCode(booking.getFlight().getOriginAirport().getIataCode());
        dto.setDestinationIataCode(booking.getFlight().getDestinationAirport().getIataCode());
        dto.setDepartureTime(booking.getFlight().getDepartureTime());
        dto.setPassengerId(booking.getPassenger().getId());
        dto.setPassengerName(booking.getPassenger().getFullName());
        dto.setPassengerEmail(booking.getPassenger().getEmail());
        dto.setSeatNumber(booking.getSeatNumber());
        dto.setTotalAmount(booking.getTotalAmount());
        dto.setStatus(booking.getStatus());
        dto.setSeatClass(booking.getSeatClass());
        dto.setBookingDate(booking.getBookingDate());
        dto.setCheckinTime(booking.getCheckinTime());
        return dto;
    }
}
