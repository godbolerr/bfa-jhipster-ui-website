package com.bfa.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BookingRecord.
 */
@Entity
@Table(name = "booking_record")
public class BookingRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "origin")
    private String origin;

    @Column(name = "destination")
    private String destination;

    @Column(name = "flight_date")
    private String flightDate;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "fare")
    private String fare;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "bookingRecord",cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<Passenger> bookPsrs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public BookingRecord flightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public BookingRecord origin(String origin) {
        this.origin = origin;
        return this;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public BookingRecord destination(String destination) {
        this.destination = destination;
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public BookingRecord flightDate(String flightDate) {
        this.flightDate = flightDate;
        return this;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public BookingRecord bookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
        return this;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getFare() {
        return fare;
    }

    public BookingRecord fare(String fare) {
        this.fare = fare;
        return this;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getStatus() {
        return status;
    }

    public BookingRecord status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Passenger> getBookPsrs() {
        return bookPsrs;
    }

    public BookingRecord bookPsrs(Set<Passenger> passengers) {
        this.bookPsrs = passengers;
        return this;
    }

    public BookingRecord addBookPsr(Passenger passenger) {
        bookPsrs.add(passenger);
        passenger.setBookingRecord(this);
        return this;
    }

    public BookingRecord removeBookPsr(Passenger passenger) {
        bookPsrs.remove(passenger);
        passenger.setBookingRecord(null);
        return this;
    }

    public void setBookPsrs(Set<Passenger> passengers) {
        this.bookPsrs = passengers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookingRecord bookingRecord = (BookingRecord) o;
        if (bookingRecord.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bookingRecord.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BookingRecord{" +
            "id=" + id +
            ", flightNumber='" + flightNumber + "'" +
            ", origin='" + origin + "'" +
            ", destination='" + destination + "'" +
            ", flightDate='" + flightDate + "'" +
            ", bookingDate='" + bookingDate + "'" +
            ", fare='" + fare + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
