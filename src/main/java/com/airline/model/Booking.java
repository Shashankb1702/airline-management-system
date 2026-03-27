package com.airline.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Booking reference is required")
    @Column(name = "booking_reference", nullable = false, unique = true, length = 10)
    private String bookingReference;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @Column(name = "seat_number", length = 5)
    private String seatNumber;

    @NotNull(message = "Total amount is required")
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private BookingStatus status = BookingStatus.CONFIRMED;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_class", nullable = false, length = 20)
    private SeatClass seatClass = SeatClass.ECONOMY;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate = LocalDateTime.now();

    @Column(name = "checkin_time")
    private LocalDateTime checkinTime;

    public enum BookingStatus {
        CONFIRMED, CANCELLED, CHECKED_IN, BOARDED
    }

    public enum SeatClass {
        ECONOMY, BUSINESS, FIRST_CLASS
    }

    public Booking() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBookingReference() { return bookingReference; }
    public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }

    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }

    public Passenger getPassenger() { return passenger; }
    public void setPassenger(Passenger passenger) { this.passenger = passenger; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    public SeatClass getSeatClass() { return seatClass; }
    public void setSeatClass(SeatClass seatClass) { this.seatClass = seatClass; }

    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }

    public LocalDateTime getCheckinTime() { return checkinTime; }
    public void setCheckinTime(LocalDateTime checkinTime) { this.checkinTime = checkinTime; }
}
