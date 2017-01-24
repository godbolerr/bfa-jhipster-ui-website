package com.bfa.app.service.mapper;

import com.bfa.app.domain.*;
import com.bfa.app.service.dto.SearchFaresDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SearchFares and its DTO SearchFaresDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SearchFaresMapper {

    SearchFaresDTO searchFaresToSearchFaresDTO(SearchFares searchFares);

    List<SearchFaresDTO> searchFaresToSearchFaresDTOs(List<SearchFares> searchFares);

    SearchFares searchFaresDTOToSearchFares(SearchFaresDTO searchFaresDTO);

    List<SearchFares> searchFaresDTOsToSearchFares(List<SearchFaresDTO> searchFaresDTOs);
}
