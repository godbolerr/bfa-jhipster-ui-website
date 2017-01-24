package com.bfa.app.service.impl;

import com.bfa.app.service.PassengerService;
import com.bfa.app.domain.Passenger;
import com.bfa.app.repository.PassengerRepository;
import com.bfa.app.service.dto.PassengerDTO;
import com.bfa.app.service.mapper.PassengerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Passenger.
 */
@Service
@Transactional
public class PassengerServiceImpl implements PassengerService{

    private final Logger log = LoggerFactory.getLogger(PassengerServiceImpl.class);
    
    @Inject
    private PassengerRepository passengerRepository;

    @Inject
    private PassengerMapper passengerMapper;

    /**
     * Save a passenger.
     *
     * @param passengerDTO the entity to save
     * @return the persisted entity
     */
    public PassengerDTO save(PassengerDTO passengerDTO) {
        log.debug("Request to save Passenger : {}", passengerDTO);
        Passenger passenger = passengerMapper.passengerDTOToPassenger(passengerDTO);
        passenger = passengerRepository.save(passenger);
        PassengerDTO result = passengerMapper.passengerToPassengerDTO(passenger);
        return result;
    }

    /**
     *  Get all the passengers.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PassengerDTO> findAll() {
        log.debug("Request to get all Passengers");
        List<PassengerDTO> result = passengerRepository.findAll().stream()
            .map(passengerMapper::passengerToPassengerDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one passenger by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PassengerDTO findOne(Long id) {
        log.debug("Request to get Passenger : {}", id);
        Passenger passenger = passengerRepository.findOne(id);
        PassengerDTO passengerDTO = passengerMapper.passengerToPassengerDTO(passenger);
        return passengerDTO;
    }

    /**
     *  Delete the  passenger by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Passenger : {}", id);
        passengerRepository.delete(id);
    }
}
