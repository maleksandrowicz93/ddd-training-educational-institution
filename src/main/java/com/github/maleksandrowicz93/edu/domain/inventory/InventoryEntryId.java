package com.github.maleksandrowicz93.edu.domain.inventory;

import java.util.UUID;

record InventoryEntryId(
        UUID value
) {

    static InventoryEntryId newOne() {
        return new InventoryEntryId(UUID.randomUUID());
    }
}
