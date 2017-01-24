package com.bfa.app.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the SearchFares entity.
 */
public class SearchFaresDTO implements Serializable {

    private Long id;

    private String fare;

    private String currency;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        SearchFaresDTO searchFaresDTO = (SearchFaresDTO) o;

        if ( ! Objects.equals(id, searchFaresDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SearchFaresDTO{" +
            "id=" + id +
            ", fare='" + fare + "'" +
            ", currency='" + currency + "'" +
            '}';
    }
}
