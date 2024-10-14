package com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory;

import com.github.maleksandrowicz93.edu.common.infra.Entity;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.EducationalInstitutionId;
import com.github.maleksandrowicz93.edu.domain.inventory.InventoryTypeId;

record InventoryTypeEntity(
        InventoryTypeId id,
        EducationalInstitutionId unitId,
        UnitType unitType,
        ItemType itemType
) implements Entity<InventoryTypeId> {

    InventoryTypeEntity(
            EducationalInstitutionId unitId,
            UnitType unitType,
            ItemType itemType
    ) {
        this(InventoryTypeId.newOne(), unitId, unitType, itemType);
    }
}
