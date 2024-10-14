package com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory;

public record InventoryType(
        Unit unit,
        ItemType itemType
) {

    static InventoryType from(InventoryTypeEntity inventoryTypeEntity) {
        return new InventoryType(
                new Unit(inventoryTypeEntity.unitId(), inventoryTypeEntity.unitType()),
                inventoryTypeEntity.itemType()
        );
    }
}
