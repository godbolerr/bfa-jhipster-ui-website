package com.bfa.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SearchFlight.
 */
@Entity
@Table(name = "search_flight")
public class SearchFlight implements Serializable {

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private SearchFares sFlightFare;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private SearchInventory sFlightInv;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public SearchFlight flightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public SearchFlight origin(String origin) {
        this.origin = origin;
        return this;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public SearchFlight destination(String destination) {
        this.destination = destination;
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public SearchFlight flightDate(String flightDate) {
        this.flightDate = flightDate;
        return this;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public SearchFares getSFlightFare() {
        return sFlightFare;
    }

    public SearchFlight sFlightFare(SearchFares searchFares) {
        this.sFlightFare = searchFares;
        return this;
    }

    public void setSFlightFare(SearchFares searchFares) {
        this.sFlightFare = searchFares;
    }

    public SearchInventory getSFlightInv() {
        return sFlightInv;
    }

    public SearchFlight sFlightInv(SearchInventory searchInventory) {
        this.sFlightInv = searchInventory;
        return this;
    }

    public void setSFlightInv(SearchInventory searchInventory) {
        this.sFlightInv = searchInventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SearchFlight searchFlight = (SearchFlight) o;
        if (searchFlight.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, searchFlight.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SearchFlight{" +
            "id=" + id +
            ", flightNumber='" + flightNumber + "'" +
            ", origin='" + origin + "'" +
            ", destination='" + destination + "'" +
            ", flightDate='" + flightDate + "'" +
            '}';
    }
}
