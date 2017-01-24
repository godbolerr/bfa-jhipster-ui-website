package com.bfa.app.service.mapper;

import com.bfa.app.domain.*;
import com.bfa.app.service.dto.InventoryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Inventory and its DTO InventoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InventoryMapper {

    InventoryDTO inventoryToInventoryDTO(Inventory inventory);

    List<InventoryDTO> inventoriesToInventoryDTOs(List<Inventory> inventories);

    Inventory inventoryDTOToInventory(InventoryDTO inventoryDTO);

    List<Inventory> inventoryDTOsToInventories(List<InventoryDTO> inventoryDTOs);
}
