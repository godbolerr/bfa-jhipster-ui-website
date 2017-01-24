package com.bfa.app.service;

import com.bfa.app.service.dto.SearchFaresDTO;
import java.util.List;

/**
 * Service Interface for managing SearchFares.
 */
public interface SearchFaresService {

    /**
     * Save a searchFares.
     *
     * @param searchFaresDTO the entity to save
     * @return the persisted entity
     */
    SearchFaresDTO save(SearchFaresDTO searchFaresDTO);

    /**
     *  Get all the searchFares.
     *  
     *  @return the list of entities
     */
    List<SearchFaresDTO> findAll();

    /**
     *  Get the "id" searchFares.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SearchFaresDTO findOne(Long id);

    /**
     *  Delete the "id" searchFares.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
