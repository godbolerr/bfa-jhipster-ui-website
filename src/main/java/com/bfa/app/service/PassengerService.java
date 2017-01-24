package com.bfa.app.service;

import com.bfa.app.service.dto.PassengerDTO;
import java.util.List;

/**
 * Service Interface for managing Passenger.
 */
public interface PassengerService {

    /**
     * Save a passenger.
     *
     * @param passengerDTO the entity to save
     * @return the persisted entity
     */
    PassengerDTO save(PassengerDTO passengerDTO);

    /**
     *  Get all the passengers.
     *  
     *  @return the list of entities
     */
    List<PassengerDTO> findAll();

    /**
     *  Get the "id" passenger.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PassengerDTO findOne(Long id);

    /**
     *  Delete the "id" passenger.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
