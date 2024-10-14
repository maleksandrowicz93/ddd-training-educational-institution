package com.github.maleksandrowicz93.edu.domain.inventory;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class InventoryReadModel {

    Inventory inventory;

    public Collection<ItemInstanceId> findAllItemsByInventoryTypeId(InventoryTypeId inventoryTypeId) {
        return inventory.findByInventoryTypeId(inventoryTypeId)
                        .map(InventoryEntry::items)
                        .orElseGet(Set::of);
    }

    public Optional<InventoryEntrySummary> findByInventoryTypeId(InventoryTypeId inventoryTypeId) {
        return inventory.findByInventoryTypeId(inventoryTypeId)
                        .map(InventoryEntrySummary::from);
    }

    public Collection<InventoryEntrySummary> findAllByInventoryTypeIds(Collection<InventoryTypeId> inventoryTypeIds) {
        return inventory.findAllByInventoryTypeIds(inventoryTypeIds)
                        .stream()
                        .map(InventoryEntrySummary::from)
                        .collect(toSet());
    }
}
