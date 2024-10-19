package com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory;

import com.github.maleksandrowicz93.edu.common.infra.InMemoryAbstractRepo;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

class InMemoryInventoryTypeCatalog
        extends InMemoryAbstractRepo<InventoryTypeId, InventoryTypeEntity>
        implements InventoryTypeCatalog {

    @Override
    public Optional<InventoryTypeId> findIdByInventoryType(InventoryType inventoryType) {
        return findBy(entry -> inventoryType.equals(InventoryType.from(entry)))
                .map(InventoryTypeEntity::id);
    }

    @Override
    public Collection<InventoryTypeEntity> findAllIdsByUnitAndItemTypes(
            UnitType unitType,
            ItemType itemType
    ) {
        Predicate<InventoryTypeEntity> unitTypePredicate = entry -> unitType == entry.unitType();
        Predicate<InventoryTypeEntity> itemTypePredicate = entry -> itemType == entry.itemType();
        return findAllBy(unitTypePredicate.and(itemTypePredicate));
    }
}
