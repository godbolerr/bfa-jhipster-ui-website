package com.bfa.app.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Inventory entity.
 */
public class InventoryDTO implements Serializable {

    private Long id;

    private String flightNumber;

    private String flightDate;

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

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }
    public Integer getAvailable() {
        return available;
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

        InventoryDTO inventoryDTO = (InventoryDTO) o;

        if ( ! Objects.equals(id, inventoryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InventoryDTO{" +
            "id=" + id +
            ", flightNumber='" + flightNumber + "'" +
            ", flightDate='" + flightDate + "'" +
            ", available='" + available + "'" +
            '}';
    }
}
