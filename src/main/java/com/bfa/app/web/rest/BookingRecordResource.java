package com.bfa.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bfa.app.service.BookingRecordService;
import com.bfa.app.web.rest.util.HeaderUtil;
import com.bfa.app.web.rest.util.PaginationUtil;
import com.bfa.app.service.dto.BookingRecordDTO;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * REST controller for managing BookingRecord.
 */
@RestController
@RequestMapping("/api")
public class BookingRecordResource {

    private final Logger log = LoggerFactory.getLogger(BookingRecordResource.class);
        
    @Inject
    private BookingRecordService bookingRecordService;

    /**
     * POST  /booking-records : Create a new bookingRecord.
     *
     * @param bookingRecordDTO the bookingRecordDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bookingRecordDTO, or with status 400 (Bad Request) if the bookingRecord has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/booking-records")
    @Timed
    public ResponseEntity<BookingRecordDTO> createBookingRecord(@RequestBody BookingRecordDTO bookingRecordDTO) throws URISyntaxException {
        log.debug("REST request to save BookingRecord : {}", bookingRecordDTO);
        if (bookingRecordDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("bookingRecord", "idexists", "A new bookingRecord cannot already have an ID")).body(null);
        }
        BookingRecordDTO result = bookingRecordService.save(bookingRecordDTO);
        return ResponseEntity.created(new URI("/api/booking-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("bookingRecord", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /booking-records : Updates an existing bookingRecord.
     *
     * @param bookingRecordDTO the bookingRecordDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bookingRecordDTO,
     * or with status 400 (Bad Request) if the bookingRecordDTO is not valid,
     * or with status 500 (Internal Server Error) if the bookingRecordDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/booking-records")
    @Timed
    public ResponseEntity<BookingRecordDTO> updateBookingRecord(@RequestBody BookingRecordDTO bookingRecordDTO) throws URISyntaxException {
        log.debug("REST request to update BookingRecord : {}", bookingRecordDTO);
        if (bookingRecordDTO.getId() == null) {
            return createBookingRecord(bookingRecordDTO);
        }
        BookingRecordDTO result = bookingRecordService.save(bookingRecordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("bookingRecord", bookingRecordDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /booking-records : get all the bookingRecords.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bookingRecords in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/booking-records")
    @Timed
    public ResponseEntity<List<BookingRecordDTO>> getAllBookingRecords(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of BookingRecords");
        Page<BookingRecordDTO> page = bookingRecordService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/booking-records");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /booking-records/:id : get the "id" bookingRecord.
     *
     * @param id the id of the bookingRecordDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bookingRecordDTO, or with status 404 (Not Found)
     */
    @GetMapping("/booking-records/{id}")
    @Timed
    public ResponseEntity<BookingRecordDTO> getBookingRecord(@PathVariable Long id) {
        log.debug("REST request to get BookingRecord : {}", id);
        BookingRecordDTO bookingRecordDTO = bookingRecordService.findOne(id);
        
       
        
        return Optional.ofNullable(bookingRecordDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /booking-records/:id : delete the "id" bookingRecord.
     *
     * @param id the id of the bookingRecordDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/booking-records/{id}")
    @Timed
    public ResponseEntity<Void> deleteBookingRecord(@PathVariable Long id) {
        log.debug("REST request to delete BookingRecord : {}", id);
        bookingRecordService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bookingRecord", id.toString())).build();
    }

}
