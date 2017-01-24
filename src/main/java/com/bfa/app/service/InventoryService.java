package com.bfa.app.service;

import java.util.List;

import com.bfa.app.service.dto.InventoryDTO;

/**
 * Service Interface for managing Inventory.
 */
public interface InventoryService {

    /**
     * Save a inventory.
     *
     * @param inventoryDTO the entity to save
     * @return the persisted entity
     */
    InventoryDTO save(InventoryDTO inventoryDTO);

    /**
     *  Get all the inventories.
     *  
     *  @return the list of entities
     */
    List<InventoryDTO> findAll();

    
    /**
     *  Get the "id" inventory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    InventoryDTO findOne(Long id);

    /**
     *  Delete the "id" inventory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
    
    
    
    InventoryDTO findByFlightNumberAndFlightDate(String flightNumber,String flightDate);
    
}
