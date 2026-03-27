package com.airline.dto;

import com.airline.model.Booking;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookingDTO {
    private Long id;
    private String bookingReference;

    @NotNull(message = "Flight ID is required")
    private Long flightId;
    private String flightNumber;
    private String originIataCode;
    private String destinationIataCode;
    private LocalDateTime departureTime;

    @NotNull(message = "Passenger ID is required")
    private Long passengerId;
    private String passengerName;
    private String passengerEmail;

    private String seatNumber;
    private BigDecimal totalAmount;

    private Booking.BookingStatus status = Booking.BookingStatus.CONFIRMED;
    private Booking.SeatClass seatClass = Booking.SeatClass.ECONOMY;
    private LocalDateTime bookingDate;
    private LocalDateTime checkinTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBookingReference() { return bookingReference; }
    public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }

    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getOriginIataCode() { return originIataCode; }
    public void setOriginIataCode(String originIataCode) { this.originIataCode = originIataCode; }

    public String getDestinationIataCode() { return destinationIataCode; }
    public void setDestinationIataCode(String destinationIataCode) { this.destinationIataCode = destinationIataCode; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public Long getPassengerId() { return passengerId; }
    public void setPassengerId(Long passengerId) { this.passengerId = passengerId; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public String getPassengerEmail() { return passengerEmail; }
    public void setPassengerEmail(String passengerEmail) { this.passengerEmail = passengerEmail; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public Booking.BookingStatus getStatus() { return status; }
    public void setStatus(Booking.BookingStatus status) { this.status = status; }

    public Booking.SeatClass getSeatClass() { return seatClass; }
    public void setSeatClass(Booking.SeatClass seatClass) { this.seatClass = seatClass; }

    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }

    public LocalDateTime getCheckinTime() { return checkinTime; }
    public void setCheckinTime(LocalDateTime checkinTime) { this.checkinTime = checkinTime; }
}
