package com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.EducationalInstitutionId;
import com.github.maleksandrowicz93.edu.domain.inventory.InventoryEntryCreation;
import com.github.maleksandrowicz93.edu.domain.inventory.InventoryFacade;
import com.github.maleksandrowicz93.edu.domain.inventory.ItemInstanceId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class EducationalInstitutionInventoryFacade {

    InventoryFacade inventoryFacade;
    InventoryTypeCatalog inventoryTypeCatalog;

    public void createInventoryOfType(InventoryTypeCreation inventoryTypeCreation) {
        var inventoryTypeEntity = InventoryTypeEntityFactory.INSTANCE.apply(inventoryTypeCreation.inventoryType());
        inventoryTypeCatalog.save(inventoryTypeEntity);
        var inventoryEntryCreation = new InventoryEntryCreation(
                inventoryTypeEntity.id(),
                inventoryTypeCreation.capacity()
        );
        inventoryFacade.createInventoryEntry(inventoryEntryCreation);
    }

    public void removeInventoryOfType(InventoryType inventoryType) {
        inventoryTypeCatalog.findIdByInventoryType(inventoryType)
                            .ifPresent(inventoryTypeId -> {
                                inventoryFacade.deleteInventoryEntryByInventoryTypeId(inventoryTypeId);
                                inventoryTypeCatalog.deleteById(inventoryTypeId);
                            });
    }

    public <T extends EducationalInstitutionId> Optional<T> addItemToInventoryOfType(
            InventoryType unitType,
            Function<UUID, T> itemIdFactory
    ) {
        var itemInstanceId = ItemInstanceId.newOne();
        return addItemToInventoryOfType(unitType, itemInstanceId)
                ? Optional.of(itemIdFactory.apply(itemInstanceId.value()))
                : Optional.empty();
    }

    public boolean addItemToInventoryOfType(
            InventoryType unitType,
            EducationalInstitutionId itemId
    ) {
        return addItemToInventoryOfType(unitType, new ItemInstanceId(itemId.value()));
    }

    private boolean addItemToInventoryOfType(
            InventoryType inventoryType,
            ItemInstanceId itemInstanceId
    ) {
        var itemAddingResult = new AtomicBoolean(false);
        inventoryTypeCatalog.findIdByInventoryType(inventoryType)
                            .ifPresent(inventoryTypeId -> itemAddingResult.set(
                                    inventoryFacade.addItemToInventoryOfType(inventoryTypeId, itemInstanceId)
                            ));
        return itemAddingResult.get();
    }

    public void removeItem(EducationalInstitutionId itemId, InventoryType inventoryType) {
        inventoryTypeCatalog.findIdByInventoryType(inventoryType)
                            .ifPresent(inventoryTypeId -> inventoryFacade.removeItemFromInventoryOfType(
                                    inventoryTypeId,
                                    new ItemInstanceId(itemId.value())
                            ));
    }

    public boolean changeCapacityOfInventoryOfType(InventoryType inventoryType, Capacity capacity) {
        var capacityChangeResult = new AtomicBoolean(false);
        inventoryTypeCatalog.findIdByInventoryType(inventoryType)
                            .ifPresent(inventoryTypeId -> capacityChangeResult.set(
                                    inventoryFacade.changeInventoryEntryCapacity(inventoryTypeId, capacity)
                            ));
        return capacityChangeResult.get();
    }
}
