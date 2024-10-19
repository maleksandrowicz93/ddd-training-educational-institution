package com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory;

import com.github.maleksandrowicz93.edu.domain.availability.AvailabilityReadModel;
import com.github.maleksandrowicz93.edu.domain.availability.GroupedAvailabilitySummary;
import com.github.maleksandrowicz93.edu.domain.availability.OwnerId;
import com.github.maleksandrowicz93.edu.domain.availability.ResourceId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.EducationalInstitutionId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class InventoryReadModel {

    AvailabilityReadModel availabilityReadModel;
    InventoryTypeCatalog inventoryTypeCatalog;

    public <T extends EducationalInstitutionId> Collection<T> findAllItemsByInventoryType(
            InventoryType inventoryType,
            Function<UUID, T> itemIdFactory
    ) {
        return inventoryTypeCatalog.findIdByInventoryType(inventoryType)
                                   .map(InventoryTypeId::value)
                                   .map(ResourceId::new)
                                   .map(availabilityReadModel::getParentSummary)
                                   .map(GroupedAvailabilitySummary::owners)
                                   .stream()
                                   .flatMap(Collection::stream)
                                   .map(OwnerId::value)
                                   .map(itemIdFactory)
                                   .collect(toSet());
    }

    public <T extends EducationalInstitutionId> Collection<T> findAllUnitsBy(
            UnitType unitType,
            Item item,
            Function<UUID, T> unitIdFactory
    ) {
        var unitIdByParentId = inventoryTypeCatalog.findAllIdsByUnitAndItemTypes(unitType, item.type())
                                                   .stream()
                                                   .collect(toMap(
                                                           entity -> new ResourceId(entity.id().value()),
                                                           InventoryTypeEntity::unitId
                                                   ));
        var ownerId = new OwnerId(item.id().value());
        return availabilityReadModel.filterParentsByResourcesBlockedBy(ownerId, unitIdByParentId.keySet())
                                    .stream()
                                    .map(unitIdByParentId::get)
                                    .map(EducationalInstitutionId::value)
                                    .map(unitIdFactory)
                                    .collect(toSet());
    }

    public <T extends EducationalInstitutionId> Optional<InventoryEntrySummary<T>> findSummaryByInventoryType(
            InventoryType inventoryType,
            Function<UUID, T> itemIdFactory
    ) {
        return inventoryTypeCatalog.findIdByInventoryType(inventoryType)
                                   .map(InventoryTypeId::value)
                                   .map(ResourceId::new)
                                   .map(availabilityReadModel::getParentSummary)
                                   .map(summary -> InventoryEntrySummary.from(
                                           inventoryType,
                                           summary,
                                           itemIdFactory
                                   ));
    }
}
