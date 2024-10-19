package com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory;

import java.util.UUID;

public record InventoryTypeId(
        UUID value
) {

    static InventoryTypeId newOne() {
        return new InventoryTypeId(UUID.randomUUID());
    }
}
