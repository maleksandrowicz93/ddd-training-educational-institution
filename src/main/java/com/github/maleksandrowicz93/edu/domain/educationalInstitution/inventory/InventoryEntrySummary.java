package com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
import com.github.maleksandrowicz93.edu.domain.availability.GroupedAvailabilitySummary;
import com.github.maleksandrowicz93.edu.domain.availability.OwnerId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.EducationalInstitutionId;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Function;

import static java.util.stream.Collectors.toSet;

public record InventoryEntrySummary<T extends EducationalInstitutionId>(
        InventoryType inventoryType,
        Capacity capacity,
        Collection<T> items
) {

    public <ID extends EducationalInstitutionId> ID unitId(Function<UUID, ID> idFactory) {
        return idFactory.apply(inventoryType.unit().id().value());
    }

    static <T extends EducationalInstitutionId> InventoryEntrySummary<T> from(
            InventoryType inventoryType,
            GroupedAvailabilitySummary groupedAvailabilitySummary,
            Function<UUID, T> itemIdFactory
    ) {
        return new InventoryEntrySummary<>(
                inventoryType,
                Capacity.of(groupedAvailabilitySummary.unitsNumber()),
                toItems(groupedAvailabilitySummary, itemIdFactory)
        );
    }

    private static <T extends EducationalInstitutionId> Collection<T> toItems(
            GroupedAvailabilitySummary groupedAvailabilitySummary,
            Function<UUID, T> itemIdFactory
    ) {
        return groupedAvailabilitySummary.owners()
                                         .stream()
                                         .map(OwnerId::value)
                                         .map(itemIdFactory)
                                         .collect(toSet());
    }
}
