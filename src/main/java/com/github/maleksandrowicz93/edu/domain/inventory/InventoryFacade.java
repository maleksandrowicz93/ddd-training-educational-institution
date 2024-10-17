package com.github.maleksandrowicz93.edu.domain.inventory;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
import com.github.maleksandrowicz93.edu.common.infra.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class InventoryFacade {

    Inventory inventory;

    public void createInventoryEntry(InventoryEntryCreation inventoryEntryCreation) {
        log.info("Creating inventory entry {}...", inventoryEntryCreation);
        var inventoryEntry = new InventoryEntry(
                inventoryEntryCreation.inventoryTypeId(),
                inventoryEntryCreation.capacity()
        );
        inventory.save(inventoryEntry);
    }

    public void deleteInventoryEntryByInventoryTypeId(InventoryTypeId inventoryTypeId) {
        log.info("Deleting inventory entry of type {}...", inventoryTypeId);
        inventory.deleteByInventoryTypeId(inventoryTypeId);
    }

    @Transactional
    public boolean addItemToInventoryOfType(InventoryTypeId inventoryTypeId, ItemInstanceId itemInstanceId) {
        log.info("Adding item {} to inventory of type {}...", itemInstanceId, inventoryTypeId);
        var itemAddedResult = new AtomicBoolean(false);
        inventory.findByInventoryTypeId(inventoryTypeId)
                 .ifPresentOrElse(
                         inventoryEntry -> {
                             var itemAdded = inventoryEntry.addItem(itemInstanceId);
                             itemAddedResult.set(itemAdded);
                             if (itemAdded) {
                                 inventory.save(inventoryEntry);
                             } else {
                                 log.info(
                                         "Item {} was not added to inventory of type {}",
                                         itemInstanceId, inventoryTypeId
                                 );
                             }
                         },
                         () -> log.info(
                                 "Cannot add item {} to inventory of type {} because no such inventory found",
                                 itemInstanceId, inventoryTypeId
                         )
                 );
        return itemAddedResult.get();
    }

    @Transactional
    public void removeItemFromInventoryOfType(InventoryTypeId inventoryTypeId, ItemInstanceId itemInstanceId) {
        log.info("Removing item {} from inventory of type {}...", inventoryTypeId, inventoryTypeId);
        inventory.findByInventoryTypeId(inventoryTypeId)
                 .ifPresentOrElse(
                         inventoryEntry -> {
                             var itemRemoved = inventoryEntry.removeItem(itemInstanceId);
                             if (itemRemoved) {
                                 inventory.save(inventoryEntry);
                             } else {
                                 log.info(
                                         "Item {} was not removed from inventory of type {}",
                                         itemInstanceId,
                                         inventoryTypeId
                                 );
                             }
                         },
                         () -> log.info(
                                 "Cannot remove item {} from inventory of type {} because no such inventory found",
                                 itemInstanceId, inventoryTypeId
                         )
                 );
    }

    @Transactional
    public boolean changeInventoryEntryCapacity(InventoryTypeId inventoryTypeId, Capacity capacity) {
        log.info("Changing inventory of type {} capacity to {}...", inventoryTypeId, capacity);
        var changingCapacityResult = new AtomicBoolean(false);
        inventory.findByInventoryTypeId(inventoryTypeId)
                 .ifPresentOrElse(
                         inventoryEntry -> {
                             boolean capacityChanged = inventoryEntry.changeCapacity(capacity);
                             changingCapacityResult.set(capacityChanged);
                             if (capacityChanged) {
                                 inventory.save(inventoryEntry);
                             } else {
                                 log.info(
                                         "Cannot change capacity for inventory of type {} because of state conflict",
                                         inventoryTypeId
                                 );
                             }
                         },
                         () -> log.info(
                                 "Cannot change capacity for inventory of type {} because no such inventory found",
                                 inventoryTypeId
                         )
                 );
        return changingCapacityResult.get();
    }
}
