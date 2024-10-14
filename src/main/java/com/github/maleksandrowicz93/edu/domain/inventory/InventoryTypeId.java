package com.github.maleksandrowicz93.edu.domain.inventory;

import java.util.UUID;

public record InventoryTypeId(
        UUID value
) {

    public static InventoryTypeId newOne() {
        return new InventoryTypeId(UUID.randomUUID());
    }
}
