package com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory;

import java.util.function.Function;

enum InventoryTypeEntityFactory implements Function<InventoryType, InventoryTypeEntity> {

    INSTANCE;

    @Override
    public InventoryTypeEntity apply(InventoryType inventoryType) {
        return new InventoryTypeEntity(
                inventoryType.unit().id(),
                inventoryType.unit().type(),
                inventoryType.itemType()
        );
    }
}
