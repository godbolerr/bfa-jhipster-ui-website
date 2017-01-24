package com.bfa.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Fares.
 */
@Entity
@Table(name = "fares")
public class Fares implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "flight_date")
    private String flightDate;

    @Column(name = "fare")
    private String fare;

    @Column(name = "currency")
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

    public Fares flightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public Fares flightDate(String flightDate) {
        this.flightDate = flightDate;
        return this;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public String getFare() {
        return fare;
    }

    public Fares fare(String fare) {
        this.fare = fare;
        return this;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getCurrency() {
        return currency;
    }

    public Fares currency(String currency) {
        this.currency = currency;
        return this;
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
        Fares fares = (Fares) o;
        if (fares.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, fares.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Fares{" +
            "id=" + id +
            ", flightNumber='" + flightNumber + "'" +
            ", flightDate='" + flightDate + "'" +
            ", fare='" + fare + "'" +
            ", currency='" + currency + "'" +
            '}';
    }
}
