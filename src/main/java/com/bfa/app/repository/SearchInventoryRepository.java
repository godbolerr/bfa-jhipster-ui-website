package com.bfa.app.repository;

import com.bfa.app.domain.SearchInventory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SearchInventory entity.
 */
@SuppressWarnings("unused")
public interface SearchInventoryRepository extends JpaRepository<SearchInventory,Long> {

}
