package com.bfa.app.service;

import com.bfa.app.service.dto.FaresDTO;
import java.util.List;

/**
 * Service Interface for managing Fares.
 */
public interface FaresService {

    /**
     * Save a fares.
     *
     * @param faresDTO the entity to save
     * @return the persisted entity
     */
    FaresDTO save(FaresDTO faresDTO);

    /**
     *  Get all the fares.
     *  
     *  @return the list of entities
     */
    List<FaresDTO> findAll();

    /**
     *  Get the "id" fares.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FaresDTO findOne(Long id);

    /**
     *  Delete the "id" fares.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
