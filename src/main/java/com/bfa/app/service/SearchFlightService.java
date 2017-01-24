package com.bfa.app.service;

import com.bfa.app.service.dto.SearchFlightDTO;
import java.util.List;

/**
 * Service Interface for managing SearchFlight.
 */
public interface SearchFlightService {
	
	
	List<SearchFlightDTO> init(List<SearchFlightDTO> flights);

    /**
     * Save a searchFlight.
     *
     * @param searchFlightDTO the entity to save
     * @return the persisted entity
     */
    SearchFlightDTO save(SearchFlightDTO searchFlightDTO);

    /**
     *  Get all the searchFlights.
     *  
     *  @return the list of entities
     */
    List<SearchFlightDTO> findAll();
    
    
    /**
     * Find flights based on criterion as populated in DTO
     * @param dto
     * @return
     */
    List<SearchFlightDTO> find(SearchFlightDTO dto);

    /**
     *  Get the "id" searchFlight.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SearchFlightDTO findOne(Long id);

    /**
     *  Delete the "id" searchFlight.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
