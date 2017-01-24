package com.bfa.app.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Passenger entity.
 */
public class PassengerDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String gender;


    private Long bookingRecordId;
    
    private Long psrBookId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getBookingRecordId() {
        return bookingRecordId;
    }

    public void setBookingRecordId(Long bookingRecordId) {
        this.bookingRecordId = bookingRecordId;
    }

    public Long getPsrBookId() {
        return psrBookId;
    }

    public void setPsrBookId(Long bookingRecordId) {
        this.psrBookId = bookingRecordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PassengerDTO passengerDTO = (PassengerDTO) o;

        if ( ! Objects.equals(id, passengerDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PassengerDTO{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", gender='" + gender + "'" +
            '}';
    }
}
