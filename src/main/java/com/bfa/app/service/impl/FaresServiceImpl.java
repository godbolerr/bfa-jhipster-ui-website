package com.bfa.app.service.impl;

import com.bfa.app.service.FaresService;
import com.bfa.app.domain.Fares;
import com.bfa.app.repository.FaresRepository;
import com.bfa.app.service.dto.FaresDTO;
import com.bfa.app.service.mapper.FaresMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Fares.
 */
@Service
@Transactional
public class FaresServiceImpl implements FaresService{

    private final Logger log = LoggerFactory.getLogger(FaresServiceImpl.class);
    
    @Inject
    private FaresRepository faresRepository;

    @Inject
    private FaresMapper faresMapper;

    /**
     * Save a fares.
     *
     * @param faresDTO the entity to save
     * @return the persisted entity
     */
    public FaresDTO save(FaresDTO faresDTO) {
        log.debug("Request to save Fares : {}", faresDTO);
        Fares fares = faresMapper.faresDTOToFares(faresDTO);
        fares = faresRepository.save(fares);
        FaresDTO result = faresMapper.faresToFaresDTO(fares);
        return result;
    }

    /**
     *  Get all the fares.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<FaresDTO> findAll() {
        log.debug("Request to get all Fares");
        List<FaresDTO> result = faresRepository.findAll().stream()
            .map(faresMapper::faresToFaresDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one fares by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public FaresDTO findOne(Long id) {
        log.debug("Request to get Fares : {}", id);
        Fares fares = faresRepository.findOne(id);
        FaresDTO faresDTO = faresMapper.faresToFaresDTO(fares);
        return faresDTO;
    }

    /**
     *  Delete the  fares by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Fares : {}", id);
        faresRepository.delete(id);
    }
}
