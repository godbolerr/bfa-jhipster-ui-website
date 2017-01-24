package com.bfa.app.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the SearchFlight entity.
 */
public class SearchFlightDTO implements Serializable {

    private Long id;

    private String flightNumber;

    private String origin;

    private String destination;

    private String flightDate;

    private Long fare;
    
    private Long inventory;
    
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
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }


    /**
	 * @return the fare
	 */
	public Long getFare() {
		return fare;
	}

	/**
	 * @param fare the fare to set
	 */
	public void setFare(Long fare) {
		this.fare = fare;
	}

	/**
	 * @return the inventory
	 */
	public Long getInventory() {
		return inventory;
	}

	/**
	 * @param inventory the inventory to set
	 */
	public void setInventory(Long inventory) {
		this.inventory = inventory;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SearchFlightDTO searchFlightDTO = (SearchFlightDTO) o;

        if ( ! Objects.equals(id, searchFlightDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SearchFlightDTO{" +
            "id=" + id +
            ", flightNumber='" + flightNumber + "'" +
            ", origin='" + origin + "'" +
            ", destination='" + destination + "'" +
            ", flightDate='" + flightDate + "'" +
            '}';
    }
}
