package com.bfa.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bfa.app.service.FaresService;
import com.bfa.app.web.rest.util.HeaderUtil;
import com.bfa.app.service.dto.FaresDTO;

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
 * REST controller for managing Fares.
 */
@RestController
@RequestMapping("/api")
public class FaresResource {

    private final Logger log = LoggerFactory.getLogger(FaresResource.class);
        
    @Inject
    private FaresService faresService;

    /**
     * POST  /fares : Create a new fares.
     *
     * @param faresDTO the faresDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new faresDTO, or with status 400 (Bad Request) if the fares has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fares")
    @Timed
    public ResponseEntity<FaresDTO> createFares(@RequestBody FaresDTO faresDTO) throws URISyntaxException {
        log.debug("REST request to save Fares : {}", faresDTO);
        if (faresDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("fares", "idexists", "A new fares cannot already have an ID")).body(null);
        }
        FaresDTO result = faresService.save(faresDTO);
        return ResponseEntity.created(new URI("/api/fares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("fares", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fares : Updates an existing fares.
     *
     * @param faresDTO the faresDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated faresDTO,
     * or with status 400 (Bad Request) if the faresDTO is not valid,
     * or with status 500 (Internal Server Error) if the faresDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fares")
    @Timed
    public ResponseEntity<FaresDTO> updateFares(@RequestBody FaresDTO faresDTO) throws URISyntaxException {
        log.debug("REST request to update Fares : {}", faresDTO);
        if (faresDTO.getId() == null) {
            return createFares(faresDTO);
        }
        FaresDTO result = faresService.save(faresDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("fares", faresDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fares : get all the fares.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fares in body
     */
    @GetMapping("/fares")
    @Timed
    public List<FaresDTO> getAllFares() {
        log.debug("REST request to get all Fares");
        return faresService.findAll();
    }

    /**
     * GET  /fares/:id : get the "id" fares.
     *
     * @param id the id of the faresDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the faresDTO, or with status 404 (Not Found)
     */
    @GetMapping("/fares/{id}")
    @Timed
    public ResponseEntity<FaresDTO> getFares(@PathVariable Long id) {
        log.debug("REST request to get Fares : {}", id);
        FaresDTO faresDTO = faresService.findOne(id);
        return Optional.ofNullable(faresDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fares/:id : delete the "id" fares.
     *
     * @param id the id of the faresDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fares/{id}")
    @Timed
    public ResponseEntity<Void> deleteFares(@PathVariable Long id) {
        log.debug("REST request to delete Fares : {}", id);
        faresService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fares", id.toString())).build();
    }

}
