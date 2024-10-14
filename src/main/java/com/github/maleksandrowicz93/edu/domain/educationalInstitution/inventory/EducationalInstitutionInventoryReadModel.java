package com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.EducationalInstitutionId;
import com.github.maleksandrowicz93.edu.domain.inventory.InventoryEntrySummary;
import com.github.maleksandrowicz93.edu.domain.inventory.InventoryReadModel;
import com.github.maleksandrowicz93.edu.domain.inventory.ItemInstanceId;
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
public class EducationalInstitutionInventoryReadModel {

    InventoryReadModel inventoryReadModel;
    InventoryTypeCatalog inventoryTypeCatalog;

    public <T extends EducationalInstitutionId> Collection<T> findAllItemsByInventoryType(
            InventoryType inventoryType,
            Function<UUID, T> itemIdFactory
    ) {
        return inventoryTypeCatalog.findIdByInventoryType(inventoryType)
                                   .map(inventoryReadModel::findAllItemsByInventoryTypeId)
                                   .stream()
                                   .flatMap(Collection::stream)
                                   .map(ItemInstanceId::value)
                                   .map(itemIdFactory)
                                   .collect(toSet());
    }

    public <T extends EducationalInstitutionId> Collection<T> findAllUnitsBy(
            UnitType unitType,
            Item item,
            Function<UUID, T> unitIdFactory
    ) {
        var unitIdByInventoryTypeId = inventoryTypeCatalog.findAllIdsByUnitAndItemTypes(unitType, item.type())
                                                          .stream()
                                                          .collect(toMap(
                                                                  InventoryTypeEntity::id,
                                                                  InventoryTypeEntity::unitId
                                                          ));
        var itemInstanceId = new ItemInstanceId(item.id().value());
        return inventoryReadModel.findAllByInventoryTypeIds(unitIdByInventoryTypeId.keySet())
                                 .stream()
                                 .filter(inventoryEntrySummary -> inventoryEntrySummary.containsItem(itemInstanceId))
                                 .map(InventoryEntrySummary::inventoryTypeId)
                                 .map(unitIdByInventoryTypeId::get)
                                 .map(EducationalInstitutionId::value)
                                 .map(unitIdFactory)
                                 .collect(toSet());
    }

    public <T extends EducationalInstitutionId> Optional<EducationalInstitutionInventoryEntrySummary<T>> findSummaryByInventoryType(
            InventoryType inventoryType,
            Function<UUID, T> itemIdFactory
    ) {
        return inventoryTypeCatalog.findIdByInventoryType(inventoryType)
                                   .flatMap(inventoryReadModel::findByInventoryTypeId)
                                   .map(summary -> EducationalInstitutionInventoryEntrySummary.from(
                                           inventoryType,
                                           summary,
                                           itemIdFactory
                                   ));
    }
}
