package com.bfa.app.repository;

import com.bfa.app.domain.Passenger;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Passenger entity.
 */
@SuppressWarnings("unused")
public interface PassengerRepository extends JpaRepository<Passenger,Long> {

}
