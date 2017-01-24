package com.bfa.app.service.impl;

import com.bfa.app.service.InventoryService;
import com.bfa.app.domain.Inventory;
import com.bfa.app.repository.InventoryRepository;
import com.bfa.app.service.dto.InventoryDTO;
import com.bfa.app.service.mapper.InventoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Inventory.
 */
@Service
@Transactional
public class InventoryServiceImpl implements InventoryService{

    private final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);
    
    @Inject
    private InventoryRepository inventoryRepository;

    @Inject
    private InventoryMapper inventoryMapper;

    /**
     * Save a inventory.
     *
     * @param inventoryDTO the entity to save
     * @return the persisted entity
     */
    public InventoryDTO save(InventoryDTO inventoryDTO) {
        log.debug("Request to save Inventory : {}", inventoryDTO);
        Inventory inventory = inventoryMapper.inventoryDTOToInventory(inventoryDTO);
        inventory = inventoryRepository.save(inventory);
        InventoryDTO result = inventoryMapper.inventoryToInventoryDTO(inventory);
        return result;
    }

    /**
     *  Get all the inventories.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<InventoryDTO> findAll() {
        log.debug("Request to get all Inventories");
        List<InventoryDTO> result = inventoryRepository.findAll().stream()
            .map(inventoryMapper::inventoryToInventoryDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one inventory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public InventoryDTO findOne(Long id) {
        log.debug("Request to get Inventory : {}", id);
        Inventory inventory = inventoryRepository.findOne(id);
        InventoryDTO inventoryDTO = inventoryMapper.inventoryToInventoryDTO(inventory);
        return inventoryDTO;
    }

    /**
     *  Delete the  inventory by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Inventory : {}", id);
        inventoryRepository.delete(id);
    }

	@Override
	public InventoryDTO findByFlightNumberAndFlightDate(String flightNumber, String flightDate) {
        Inventory inventory = inventoryRepository.findByFlightNumberAndFlightDate(flightNumber, flightDate);
        InventoryDTO inventoryDTO = inventoryMapper.inventoryToInventoryDTO(inventory);
        return inventoryDTO;
	}
}
