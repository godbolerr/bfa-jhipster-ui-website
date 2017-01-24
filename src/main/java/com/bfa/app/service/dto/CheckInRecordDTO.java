package com.bfa.app.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the CheckInRecord entity.
 */
public class CheckInRecordDTO implements Serializable {

    private Long id;

    private String lastName;

    private String firstName;

    private String seatNumber;

    private LocalDate checkInTime;

    private String flightNumber;

    private String flightDate;

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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
    public LocalDate getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDate checkInTime) {
        this.checkInTime = checkInTime;
    }
    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }
    public Integer getBookingId() {
        return bookingId;
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

        CheckInRecordDTO checkInRecordDTO = (CheckInRecordDTO) o;

        if ( ! Objects.equals(id, checkInRecordDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CheckInRecordDTO{" +
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
