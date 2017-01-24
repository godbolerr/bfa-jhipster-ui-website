package com.bfa.app.service.impl;

import com.bfa.app.service.SearchInventoryService;
import com.bfa.app.domain.SearchInventory;
import com.bfa.app.repository.SearchInventoryRepository;
import com.bfa.app.service.dto.SearchInventoryDTO;
import com.bfa.app.service.mapper.SearchInventoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SearchInventory.
 */
@Service
@Transactional
public class SearchInventoryServiceImpl implements SearchInventoryService{

    private final Logger log = LoggerFactory.getLogger(SearchInventoryServiceImpl.class);
    
    @Inject
    private SearchInventoryRepository searchInventoryRepository;

    @Inject
    private SearchInventoryMapper searchInventoryMapper;

    /**
     * Save a searchInventory.
     *
     * @param searchInventoryDTO the entity to save
     * @return the persisted entity
     */
    public SearchInventoryDTO save(SearchInventoryDTO searchInventoryDTO) {
        log.debug("Request to save SearchInventory : {}", searchInventoryDTO);
        SearchInventory searchInventory = searchInventoryMapper.searchInventoryDTOToSearchInventory(searchInventoryDTO);
        searchInventory = searchInventoryRepository.save(searchInventory);
        SearchInventoryDTO result = searchInventoryMapper.searchInventoryToSearchInventoryDTO(searchInventory);
        return result;
    }

    /**
     *  Get all the searchInventories.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<SearchInventoryDTO> findAll() {
        log.debug("Request to get all SearchInventories");
        List<SearchInventoryDTO> result = searchInventoryRepository.findAll().stream()
            .map(searchInventoryMapper::searchInventoryToSearchInventoryDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one searchInventory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SearchInventoryDTO findOne(Long id) {
        log.debug("Request to get SearchInventory : {}", id);
        SearchInventory searchInventory = searchInventoryRepository.findOne(id);
        SearchInventoryDTO searchInventoryDTO = searchInventoryMapper.searchInventoryToSearchInventoryDTO(searchInventory);
        return searchInventoryDTO;
    }

    /**
     *  Delete the  searchInventory by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SearchInventory : {}", id);
        searchInventoryRepository.delete(id);
    }
}
