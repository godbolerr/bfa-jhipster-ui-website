package com.bfa.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Inventory.
 */
@Entity
@Table(name = "inventory")
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "flight_date")
    private String flightDate;

    @Column(name = "available")
    private Integer available;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Inventory flightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public Inventory flightDate(String flightDate) {
        this.flightDate = flightDate;
        return this;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public Integer getAvailable() {
        return available;
    }

    public Inventory available(Integer available) {
        this.available = available;
        return this;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Inventory inventory = (Inventory) o;
        if (inventory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, inventory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Inventory{" +
            "id=" + id +
            ", flightNumber='" + flightNumber + "'" +
            ", flightDate='" + flightDate + "'" +
            ", available='" + available + "'" +
            '}';
    }
}
