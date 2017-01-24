package com.bfa.app.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Fares entity.
 */
public class FaresDTO implements Serializable {

    private Long id;

    private String flightNumber;

    private String flightDate;

    private String fare;

    private String currency;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FaresDTO faresDTO = (FaresDTO) o;

        if ( ! Objects.equals(id, faresDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FaresDTO{" +
            "id=" + id +
            ", flightNumber='" + flightNumber + "'" +
            ", flightDate='" + flightDate + "'" +
            ", fare='" + fare + "'" +
            ", currency='" + currency + "'" +
            '}';
    }
}
