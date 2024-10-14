package com.github.maleksandrowicz93.edu.domain.inventory;

import com.github.maleksandrowicz93.edu.common.infra.InMemoryAbstractRepo;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

class InMemoryInventory extends InMemoryAbstractRepo<InventoryEntryId, InventoryEntry> implements Inventory {

    private static final Function<InventoryTypeId, Predicate<InventoryEntry>> INVENTORY_TYPE_ID_PREDICATE_FACTORY =
            inventoryTypeId -> inventoryEntry -> inventoryTypeId.equals(inventoryEntry.inventoryTypeId());
    private static final Function<Collection<InventoryTypeId>, Predicate<InventoryEntry>> INVENTORY_TYPE_IDS_PREDICATE_FACTORY =
            inventoryTypeIds -> inventoryEntry -> inventoryTypeIds.contains(inventoryEntry.inventoryTypeId());

    @Override
    public Optional<InventoryEntry> findByInventoryTypeId(InventoryTypeId inventoryTypeId) {
        return findBy(INVENTORY_TYPE_ID_PREDICATE_FACTORY.apply(inventoryTypeId));
    }

    @Override
    public Collection<InventoryEntry> findAllByInventoryTypeIds(Collection<InventoryTypeId> inventoryTypeIds) {
        return findAllBy(INVENTORY_TYPE_IDS_PREDICATE_FACTORY.apply(inventoryTypeIds));
    }

    @Override
    public void deleteByInventoryTypeId(InventoryTypeId inventoryTypeId) {
        deleteBy(INVENTORY_TYPE_ID_PREDICATE_FACTORY.apply(inventoryTypeId));
    }
}
