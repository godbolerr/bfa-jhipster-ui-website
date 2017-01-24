package com.bfa.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bfa.app.service.SearchFaresService;
import com.bfa.app.web.rest.util.HeaderUtil;
import com.bfa.app.service.dto.SearchFaresDTO;

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
 * REST controller for managing SearchFares.
 */
@RestController
@RequestMapping("/api")
public class SearchFaresResource {

    private final Logger log = LoggerFactory.getLogger(SearchFaresResource.class);
        
    @Inject
    private SearchFaresService searchFaresService;

    /**
     * POST  /search-fares : Create a new searchFares.
     *
     * @param searchFaresDTO the searchFaresDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new searchFaresDTO, or with status 400 (Bad Request) if the searchFares has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/search-fares")
    @Timed
    public ResponseEntity<SearchFaresDTO> createSearchFares(@RequestBody SearchFaresDTO searchFaresDTO) throws URISyntaxException {
        log.debug("REST request to save SearchFares : {}", searchFaresDTO);
        if (searchFaresDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("searchFares", "idexists", "A new searchFares cannot already have an ID")).body(null);
        }
        SearchFaresDTO result = searchFaresService.save(searchFaresDTO);
        return ResponseEntity.created(new URI("/api/search-fares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("searchFares", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /search-fares : Updates an existing searchFares.
     *
     * @param searchFaresDTO the searchFaresDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated searchFaresDTO,
     * or with status 400 (Bad Request) if the searchFaresDTO is not valid,
     * or with status 500 (Internal Server Error) if the searchFaresDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/search-fares")
    @Timed
    public ResponseEntity<SearchFaresDTO> updateSearchFares(@RequestBody SearchFaresDTO searchFaresDTO) throws URISyntaxException {
        log.debug("REST request to update SearchFares : {}", searchFaresDTO);
        if (searchFaresDTO.getId() == null) {
            return createSearchFares(searchFaresDTO);
        }
        SearchFaresDTO result = searchFaresService.save(searchFaresDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("searchFares", searchFaresDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /search-fares : get all the searchFares.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of searchFares in body
     */
    @GetMapping("/search-fares")
    @Timed
    public List<SearchFaresDTO> getAllSearchFares() {
        log.debug("REST request to get all SearchFares");
        return searchFaresService.findAll();
    }

    /**
     * GET  /search-fares/:id : get the "id" searchFares.
     *
     * @param id the id of the searchFaresDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the searchFaresDTO, or with status 404 (Not Found)
     */
    @GetMapping("/search-fares/{id}")
    @Timed
    public ResponseEntity<SearchFaresDTO> getSearchFares(@PathVariable Long id) {
        log.debug("REST request to get SearchFares : {}", id);
        SearchFaresDTO searchFaresDTO = searchFaresService.findOne(id);
        return Optional.ofNullable(searchFaresDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /search-fares/:id : delete the "id" searchFares.
     *
     * @param id the id of the searchFaresDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/search-fares/{id}")
    @Timed
    public ResponseEntity<Void> deleteSearchFares(@PathVariable Long id) {
        log.debug("REST request to delete SearchFares : {}", id);
        searchFaresService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("searchFares", id.toString())).build();
    }

}
