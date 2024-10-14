package com.github.maleksandrowicz93.edu.domain.inventory;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
import com.github.maleksandrowicz93.edu.common.infra.Entity;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Getter(PACKAGE)
@Accessors(fluent = true)
@FieldDefaults(level = PRIVATE)
class InventoryEntry implements Entity<InventoryEntryId> {

    @Getter
    final InventoryEntryId id;
    final InventoryTypeId inventoryTypeId;
    Capacity capacity;
    final Collection<ItemInstanceId> items = new HashSet<>();

    InventoryEntry(InventoryTypeId inventoryTypeId, Capacity capacity) {
        id = InventoryEntryId.newOne();
        this.inventoryTypeId = inventoryTypeId;
        this.capacity = capacity;
    }

    boolean addItem(ItemInstanceId itemInstanceId) {
        if (items.contains(itemInstanceId) || items.size() >= capacity.value()) {
            return false;
        }
        return items.add(itemInstanceId);
    }

    boolean removeItem(ItemInstanceId itemInstanceId) {
        return items.remove(itemInstanceId);
    }

    boolean changeCapacity(Capacity capacity) {
        if (capacity.value() >= items.size()) {
            this.capacity = capacity;
            return true;
        }
        return false;
    }

    Collection<ItemInstanceId> items() {
        return Set.copyOf(items);
    }
}
