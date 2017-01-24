package com.bfa.app.service;

import com.bfa.app.service.dto.CheckInRecordDTO;
import java.util.List;

/**
 * Service Interface for managing CheckInRecord.
 */
public interface CheckInRecordService {

    /**
     * Save a checkInRecord.
     *
     * @param checkInRecordDTO the entity to save
     * @return the persisted entity
     */
    CheckInRecordDTO save(CheckInRecordDTO checkInRecordDTO);

    /**
     *  Get all the checkInRecords.
     *  
     *  @return the list of entities
     */
    List<CheckInRecordDTO> findAll();

    /**
     *  Get the "id" checkInRecord.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CheckInRecordDTO findOne(Long id);

    /**
     *  Delete the "id" checkInRecord.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
