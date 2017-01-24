package com.bfa.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SearchFares.
 */
@Entity
@Table(name = "search_fares")
public class SearchFares implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    public String getFare() {
        return fare;
    }

    public SearchFares fare(String fare) {
        this.fare = fare;
        return this;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getCurrency() {
        return currency;
    }

    public SearchFares currency(String currency) {
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
        SearchFares searchFares = (SearchFares) o;
        if (searchFares.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, searchFares.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SearchFares{" +
            "id=" + id +
            ", fare='" + fare + "'" +
            ", currency='" + currency + "'" +
            '}';
    }
}
