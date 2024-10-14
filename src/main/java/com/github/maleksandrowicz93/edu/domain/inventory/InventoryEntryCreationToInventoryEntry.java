package com.github.maleksandrowicz93.edu.domain.inventory;

import java.util.function.Function;

class InventoryEntryCreationToInventoryEntry implements Function<InventoryEntryCreation, InventoryEntry> {

    @Override
    public InventoryEntry apply(InventoryEntryCreation inventoryEntryCreation) {
        return new InventoryEntry(
                inventoryEntryCreation.inventoryTypeId(),
                inventoryEntryCreation.capacity()
        );
    }
}
