package com.bfa.app.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the BookingRecord entity.
 */
public class BookingRecordDTO implements Serializable {

    private Long id;

    private String flightNumber;

    private String origin;

    private String destination;

    private String flightDate;

    private LocalDate bookingDate;

    private String fare;

    private String status;
    
    private PassengerDTO pdto;

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
    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

    /**
	 * @return the pdto
	 */
	public PassengerDTO getPdto() {
		return pdto;
	}

	/**
	 * @param pdto the pdto to set
	 */
	public void setPdto(PassengerDTO pdto) {
		this.pdto = pdto;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BookingRecordDTO bookingRecordDTO = (BookingRecordDTO) o;

        if ( ! Objects.equals(id, bookingRecordDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BookingRecordDTO{" +
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
