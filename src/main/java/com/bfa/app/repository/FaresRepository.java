package com.bfa.app.repository;

import com.bfa.app.domain.Fares;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Fares entity.
 */
@SuppressWarnings("unused")
public interface FaresRepository extends JpaRepository<Fares,Long> {

}
