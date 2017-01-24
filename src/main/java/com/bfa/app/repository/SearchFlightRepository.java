package com.bfa.app.repository;

import com.bfa.app.domain.SearchFlight;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SearchFlight entity.
 */
@SuppressWarnings("unused")
public interface SearchFlightRepository extends JpaRepository<SearchFlight,Long> {

	List<SearchFlight> findByOriginAndDestinationAndFlightDate(String origin,String dest,String flightDate);
}
