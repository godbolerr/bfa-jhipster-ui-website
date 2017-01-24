package com.bfa.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CheckInRecord.
 */
@Entity
@Table(name = "check_in_record")
public class CheckInRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "check_in_time")
    private LocalDate checkInTime;

    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "flight_date")
    private String flightDate;

    @Column(name = "booking_id")
    private Integer bookingId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public CheckInRecord lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public CheckInRecord firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public CheckInRecord seatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public LocalDate getCheckInTime() {
        return checkInTime;
    }

    public CheckInRecord checkInTime(LocalDate checkInTime) {
        this.checkInTime = checkInTime;
        return this;
    }

    public void setCheckInTime(LocalDate checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public CheckInRecord flightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public CheckInRecord flightDate(String flightDate) {
        this.flightDate = flightDate;
        return this;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public CheckInRecord bookingId(Integer bookingId) {
        this.bookingId = bookingId;
        return this;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CheckInRecord checkInRecord = (CheckInRecord) o;
        if (checkInRecord.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, checkInRecord.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CheckInRecord{" +
            "id=" + id +
            ", lastName='" + lastName + "'" +
            ", firstName='" + firstName + "'" +
            ", seatNumber='" + seatNumber + "'" +
            ", checkInTime='" + checkInTime + "'" +
            ", flightNumber='" + flightNumber + "'" +
            ", flightDate='" + flightDate + "'" +
            ", bookingId='" + bookingId + "'" +
            '}';
    }
}
