package com.bfa.app.service;

import com.bfa.app.service.dto.SearchInventoryDTO;
import java.util.List;

/**
 * Service Interface for managing SearchInventory.
 */
public interface SearchInventoryService {

    /**
     * Save a searchInventory.
     *
     * @param searchInventoryDTO the entity to save
     * @return the persisted entity
     */
    SearchInventoryDTO save(SearchInventoryDTO searchInventoryDTO);

    /**
     *  Get all the searchInventories.
     *  
     *  @return the list of entities
     */
    List<SearchInventoryDTO> findAll();

    /**
     *  Get the "id" searchInventory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SearchInventoryDTO findOne(Long id);

    /**
     *  Delete the "id" searchInventory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
