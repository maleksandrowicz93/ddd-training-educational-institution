package com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.EducationalInstitutionId;
import com.github.maleksandrowicz93.edu.domain.inventory.InventoryEntrySummary;
import com.github.maleksandrowicz93.edu.domain.inventory.ItemInstanceId;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Function;

import static java.util.stream.Collectors.toSet;

public record EducationalInstitutionInventoryEntrySummary<T extends EducationalInstitutionId>(
        InventoryType inventoryType,
        Capacity capacity,
        Collection<T> items
) {

    static <T extends EducationalInstitutionId> EducationalInstitutionInventoryEntrySummary<T> from(
            InventoryType inventoryType,
            InventoryEntrySummary inventoryEntrySummary,
            Function<UUID, T> itemIdFactory
    ) {
        return new EducationalInstitutionInventoryEntrySummary<>(
                inventoryType,
                inventoryEntrySummary.capacity(),
                toItems(inventoryEntrySummary, itemIdFactory)
        );
    }

    private static <T extends EducationalInstitutionId> Collection<T> toItems(
            InventoryEntrySummary inventoryEntrySummary,
            Function<UUID, T> itemIdFactory
    ) {
        return inventoryEntrySummary.items()
                                    .stream()
                                    .map(ItemInstanceId::value)
                                    .map(itemIdFactory)
                                    .collect(toSet());
    }
}
