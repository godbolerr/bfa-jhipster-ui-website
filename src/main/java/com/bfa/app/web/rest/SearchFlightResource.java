package com.bfa.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bfa.app.service.SearchFlightService;
import com.bfa.app.web.rest.util.HeaderUtil;
import com.bfa.app.service.dto.SearchFlightDTO;

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
 * REST controller for managing SearchFlight.
 */
@RestController
@RequestMapping("/api")
public class SearchFlightResource {

    private final Logger log = LoggerFactory.getLogger(SearchFlightResource.class);
        
    @Inject
    private SearchFlightService searchFlightService;

    /**
     * POST  /search-flights : Create a new searchFlight.
     *
     * @param searchFlightDTO the searchFlightDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new searchFlightDTO, or with status 400 (Bad Request) if the searchFlight has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/search-flights")
    @Timed
    public ResponseEntity<SearchFlightDTO> createSearchFlight(@RequestBody SearchFlightDTO searchFlightDTO) throws URISyntaxException {
        log.debug("REST request to save SearchFlight : {}", searchFlightDTO);
        if (searchFlightDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("searchFlight", "idexists", "A new searchFlight cannot already have an ID")).body(null);
        }
        SearchFlightDTO result = searchFlightService.save(searchFlightDTO);
        return ResponseEntity.created(new URI("/api/search-flights/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("searchFlight", result.getId().toString()))
            .body(result);
    }

    
    @PostMapping("/searchFlights")
    @Timed
    public List<SearchFlightDTO> searchFlights(@RequestBody SearchFlightDTO searchFlightDTO) throws URISyntaxException {
        log.debug("REST request SearchFlight : {}", searchFlightDTO);
        return searchFlightService.find(searchFlightDTO);
    }   
    
    
    /**
     * PUT  /search-flights : Updates an existing searchFlight.
     *
     * @param searchFlightDTO the searchFlightDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated searchFlightDTO,
     * or with status 400 (Bad Request) if the searchFlightDTO is not valid,
     * or with status 500 (Internal Server Error) if the searchFlightDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/search-flights")
    @Timed
    public ResponseEntity<SearchFlightDTO> updateSearchFlight(@RequestBody SearchFlightDTO searchFlightDTO) throws URISyntaxException {
        log.debug("REST request to update SearchFlight : {}", searchFlightDTO);
        if (searchFlightDTO.getId() == null) {
            return createSearchFlight(searchFlightDTO);
        }
        SearchFlightDTO result = searchFlightService.save(searchFlightDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("searchFlight", searchFlightDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /search-flights : get all the searchFlights.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of searchFlights in body
     */
    @GetMapping("/search-flights")
    @Timed
    public List<SearchFlightDTO> getAllSearchFlights() {
        log.debug("REST request to get all SearchFlights");
        return searchFlightService.findAll();
    }

    /**
     * GET  /search-flights/:id : get the "id" searchFlight.
     *
     * @param id the id of the searchFlightDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the searchFlightDTO, or with status 404 (Not Found)
     */
    @GetMapping("/search-flights/{id}")
    @Timed
    public ResponseEntity<SearchFlightDTO> getSearchFlight(@PathVariable Long id) {
        log.debug("REST request to get SearchFlight : {}", id);
        SearchFlightDTO searchFlightDTO = searchFlightService.findOne(id);
        return Optional.ofNullable(searchFlightDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /search-flights/:id : delete the "id" searchFlight.
     *
     * @param id the id of the searchFlightDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/search-flights/{id}")
    @Timed
    public ResponseEntity<Void> deleteSearchFlight(@PathVariable Long id) {
        log.debug("REST request to delete SearchFlight : {}", id);
        searchFlightService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("searchFlight", id.toString())).build();
    }

}
