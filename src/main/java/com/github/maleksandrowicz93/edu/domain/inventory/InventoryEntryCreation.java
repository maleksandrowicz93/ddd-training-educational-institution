package com.github.maleksandrowicz93.edu.domain.inventory;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;

public record InventoryEntryCreation(
        InventoryTypeId inventoryTypeId,
        Capacity capacity
) {
}
