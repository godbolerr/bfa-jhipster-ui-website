package com.bfa.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bfa.app.service.SearchInventoryService;
import com.bfa.app.web.rest.util.HeaderUtil;
import com.bfa.app.service.dto.SearchInventoryDTO;

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
 * REST controller for managing SearchInventory.
 */
@RestController
@RequestMapping("/api")
public class SearchInventoryResource {

    private final Logger log = LoggerFactory.getLogger(SearchInventoryResource.class);
        
    @Inject
    private SearchInventoryService searchInventoryService;

    /**
     * POST  /search-inventories : Create a new searchInventory.
     *
     * @param searchInventoryDTO the searchInventoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new searchInventoryDTO, or with status 400 (Bad Request) if the searchInventory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/search-inventories")
    @Timed
    public ResponseEntity<SearchInventoryDTO> createSearchInventory(@RequestBody SearchInventoryDTO searchInventoryDTO) throws URISyntaxException {
        log.debug("REST request to save SearchInventory : {}", searchInventoryDTO);
        if (searchInventoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("searchInventory", "idexists", "A new searchInventory cannot already have an ID")).body(null);
        }
        SearchInventoryDTO result = searchInventoryService.save(searchInventoryDTO);
        return ResponseEntity.created(new URI("/api/search-inventories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("searchInventory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /search-inventories : Updates an existing searchInventory.
     *
     * @param searchInventoryDTO the searchInventoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated searchInventoryDTO,
     * or with status 400 (Bad Request) if the searchInventoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the searchInventoryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/search-inventories")
    @Timed
    public ResponseEntity<SearchInventoryDTO> updateSearchInventory(@RequestBody SearchInventoryDTO searchInventoryDTO) throws URISyntaxException {
        log.debug("REST request to update SearchInventory : {}", searchInventoryDTO);
        if (searchInventoryDTO.getId() == null) {
            return createSearchInventory(searchInventoryDTO);
        }
        SearchInventoryDTO result = searchInventoryService.save(searchInventoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("searchInventory", searchInventoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /search-inventories : get all the searchInventories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of searchInventories in body
     */
    @GetMapping("/search-inventories")
    @Timed
    public List<SearchInventoryDTO> getAllSearchInventories() {
        log.debug("REST request to get all SearchInventories");
        return searchInventoryService.findAll();
    }

    /**
     * GET  /search-inventories/:id : get the "id" searchInventory.
     *
     * @param id the id of the searchInventoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the searchInventoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/search-inventories/{id}")
    @Timed
    public ResponseEntity<SearchInventoryDTO> getSearchInventory(@PathVariable Long id) {
        log.debug("REST request to get SearchInventory : {}", id);
        SearchInventoryDTO searchInventoryDTO = searchInventoryService.findOne(id);
        return Optional.ofNullable(searchInventoryDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /search-inventories/:id : delete the "id" searchInventory.
     *
     * @param id the id of the searchInventoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/search-inventories/{id}")
    @Timed
    public ResponseEntity<Void> deleteSearchInventory(@PathVariable Long id) {
        log.debug("REST request to delete SearchInventory : {}", id);
        searchInventoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("searchInventory", id.toString())).build();
    }

}
