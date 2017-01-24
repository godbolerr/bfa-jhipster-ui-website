package com.bfa.app.service;

import com.bfa.app.service.dto.BookingRecordDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing BookingRecord.
 */
public interface BookingRecordService {

    /**
     * Save a bookingRecord.
     *
     * @param bookingRecordDTO the entity to save
     * @return the persisted entity
     */
    BookingRecordDTO save(BookingRecordDTO bookingRecordDTO);

    /**
     *  Get all the bookingRecords.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<BookingRecordDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" bookingRecord.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BookingRecordDTO findOne(Long id);

    /**
     *  Delete the "id" bookingRecord.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
