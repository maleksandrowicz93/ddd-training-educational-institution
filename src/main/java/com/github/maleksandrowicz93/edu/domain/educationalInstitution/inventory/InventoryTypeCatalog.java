package com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory;

import com.github.maleksandrowicz93.edu.domain.inventory.InventoryTypeId;

import java.util.Collection;
import java.util.Optional;

interface InventoryTypeCatalog {

    void save(InventoryTypeEntity inventoryTypeEntry);

    void deleteById(InventoryTypeId inventoryTypeId);

    Optional<InventoryTypeId> findIdByInventoryType(InventoryType inventoryType);

    Collection<InventoryTypeEntity> findAllIdsByUnitAndItemTypes(UnitType unitType, ItemType itemType);
}
