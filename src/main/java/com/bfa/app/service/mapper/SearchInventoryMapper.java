package com.bfa.app.service.mapper;

import com.bfa.app.domain.*;
import com.bfa.app.service.dto.SearchInventoryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SearchInventory and its DTO SearchInventoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SearchInventoryMapper {

    SearchInventoryDTO searchInventoryToSearchInventoryDTO(SearchInventory searchInventory);

    List<SearchInventoryDTO> searchInventoriesToSearchInventoryDTOs(List<SearchInventory> searchInventories);

    SearchInventory searchInventoryDTOToSearchInventory(SearchInventoryDTO searchInventoryDTO);

    List<SearchInventory> searchInventoryDTOsToSearchInventories(List<SearchInventoryDTO> searchInventoryDTOs);
}
