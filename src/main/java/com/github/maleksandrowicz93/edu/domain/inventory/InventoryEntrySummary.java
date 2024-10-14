package com.github.maleksandrowicz93.edu.domain.inventory;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;

import java.util.Collection;

public record InventoryEntrySummary(
        InventoryTypeId inventoryTypeId,
        Capacity capacity,
        Collection<ItemInstanceId> items
) {

    static InventoryEntrySummary from(InventoryEntry inventoryEntry) {
        return new InventoryEntrySummary(
                inventoryEntry.inventoryTypeId(),
                inventoryEntry.capacity(),
                inventoryEntry.items()
        );
    }

    public boolean containsItem(ItemInstanceId itemInstanceId) {
        return items.contains(itemInstanceId);
    }
}
