package com.bfa.app.service.impl;

import com.bfa.app.service.SearchFaresService;
import com.bfa.app.domain.SearchFares;
import com.bfa.app.repository.SearchFaresRepository;
import com.bfa.app.service.dto.SearchFaresDTO;
import com.bfa.app.service.mapper.SearchFaresMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SearchFares.
 */
@Service
@Transactional
public class SearchFaresServiceImpl implements SearchFaresService{

    private final Logger log = LoggerFactory.getLogger(SearchFaresServiceImpl.class);
    
    @Inject
    private SearchFaresRepository searchFaresRepository;

    @Inject
    private SearchFaresMapper searchFaresMapper;

    /**
     * Save a searchFares.
     *
     * @param searchFaresDTO the entity to save
     * @return the persisted entity
     */
    public SearchFaresDTO save(SearchFaresDTO searchFaresDTO) {
        log.debug("Request to save SearchFares : {}", searchFaresDTO);
        SearchFares searchFares = searchFaresMapper.searchFaresDTOToSearchFares(searchFaresDTO);
        searchFares = searchFaresRepository.save(searchFares);
        SearchFaresDTO result = searchFaresMapper.searchFaresToSearchFaresDTO(searchFares);
        return result;
    }

    /**
     *  Get all the searchFares.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<SearchFaresDTO> findAll() {
        log.debug("Request to get all SearchFares");
        List<SearchFaresDTO> result = searchFaresRepository.findAll().stream()
            .map(searchFaresMapper::searchFaresToSearchFaresDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one searchFares by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SearchFaresDTO findOne(Long id) {
        log.debug("Request to get SearchFares : {}", id);
        SearchFares searchFares = searchFaresRepository.findOne(id);
        SearchFaresDTO searchFaresDTO = searchFaresMapper.searchFaresToSearchFaresDTO(searchFares);
        return searchFaresDTO;
    }

    /**
     *  Delete the  searchFares by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SearchFares : {}", id);
        searchFaresRepository.delete(id);
    }
}
