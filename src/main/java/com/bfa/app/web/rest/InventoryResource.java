package com.bfa.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bfa.app.service.InventoryService;
import com.bfa.app.web.rest.util.HeaderUtil;
import com.bfa.app.service.dto.InventoryDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Inventory.
 */
@RestController
@RequestMapping("/api")
public class InventoryResource {

    private final Logger log = LoggerFactory.getLogger(InventoryResource.class);
        
    @Inject
    private InventoryService inventoryService;

    /**
     * POST  /inventories : Create a new inventory.
     *
     * @param inventoryDTO the inventoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inventoryDTO, or with status 400 (Bad Request) if the inventory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inventories")
    @Timed
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO inventoryDTO) throws URISyntaxException {
        log.debug("REST request to save Inventory : {}", inventoryDTO);
        if (inventoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("inventory", "idexists", "A new inventory cannot already have an ID")).body(null);
        }
        InventoryDTO result = inventoryService.save(inventoryDTO);
        return ResponseEntity.created(new URI("/api/inventories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("inventory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inventories : Updates an existing inventory.
     *
     * @param inventoryDTO the inventoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inventoryDTO,
     * or with status 400 (Bad Request) if the inventoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the inventoryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inventories")
    @Timed
    public ResponseEntity<InventoryDTO> updateInventory(@RequestBody InventoryDTO inventoryDTO) throws URISyntaxException {
        log.debug("REST request to update Inventory : {}", inventoryDTO);
        if (inventoryDTO.getId() == null) {
            return createInventory(inventoryDTO);
        }
        InventoryDTO result = inventoryService.save(inventoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("inventory", inventoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inventories : get all the inventories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of inventories in body
     */
    @GetMapping("/inventories")
    @Timed
    public List<InventoryDTO> getAllInventories() {
        log.debug("REST request to get all Inventories");
        return inventoryService.findAll();
    }

    /**
     * GET  /inventories/:id : get the "id" inventory.
     *
     * @param id the id of the inventoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inventoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/inventories/{id}")
    @Timed
    public ResponseEntity<InventoryDTO> getInventory(@PathVariable Long id) {
        log.debug("REST request to get Inventory : {}", id);
        InventoryDTO inventoryDTO = inventoryService.findOne(id);
        return Optional.ofNullable(inventoryDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /inventories/:id : delete the "id" inventory.
     *
     * @param id the id of the inventoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inventories/{id}")
    @Timed
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        log.debug("REST request to delete Inventory : {}", id);
        inventoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("inventory", id.toString())).build();
    }

}
