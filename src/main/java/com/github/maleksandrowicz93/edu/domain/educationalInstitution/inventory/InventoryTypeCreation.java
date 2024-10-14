package com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;

public record InventoryTypeCreation(
        InventoryType inventoryType,
        Capacity capacity
) {
}
