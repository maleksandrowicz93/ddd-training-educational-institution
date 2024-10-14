package com.github.maleksandrowicz93.edu.domain.inventory;

import java.util.Collection;
import java.util.Optional;

interface Inventory {

    Optional<InventoryEntry> findByInventoryTypeId(InventoryTypeId inventoryTypeId);

    Collection<InventoryEntry> findAllByInventoryTypeIds(Collection<InventoryTypeId> inventoryTypeIds);

    void save(InventoryEntry inventoryEntry);

    void saveAll(Collection<InventoryEntry> inventoryEntries);

    void deleteByInventoryTypeId(InventoryTypeId inventoryTypeId);
}
