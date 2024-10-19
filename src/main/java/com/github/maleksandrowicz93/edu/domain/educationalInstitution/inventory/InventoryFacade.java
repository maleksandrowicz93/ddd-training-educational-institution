package com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
import com.github.maleksandrowicz93.edu.common.infra.Transactional;
import com.github.maleksandrowicz93.edu.domain.availability.AvailabilityFacade;
import com.github.maleksandrowicz93.edu.domain.availability.AvailabilityReadModel;
import com.github.maleksandrowicz93.edu.domain.availability.OwnerId;
import com.github.maleksandrowicz93.edu.domain.availability.ResourceId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.EducationalInstitutionId;
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
public class InventoryFacade {

    InventoryTypeCatalog inventoryTypeCatalog;
    AvailabilityReadModel availabilityReadModel;
    AvailabilityFacade availabilityFacade;

    @Transactional
    public void createInventoryOfType(InventoryTypeCreation inventoryTypeCreation) {
        var inventoryType = inventoryTypeCreation.inventoryType();
        var inventoryTypeEntity = new InventoryTypeEntity(
                inventoryType.unit().id(),
                inventoryType.unit().type(),
                inventoryType.itemType()
        );
        inventoryTypeCatalog.save(inventoryTypeEntity);
        availabilityFacade.createAvailabilityUnitsForParent(
                new ResourceId(inventoryTypeEntity.id().value()),
                inventoryTypeCreation.capacity().value()
        );
    }

    @Transactional
    public void removeInventoryOfType(InventoryType inventoryType) {
        inventoryTypeCatalog.findIdByInventoryType(inventoryType)
                            .ifPresent(inventoryTypeId -> {
                                var parentId = new ResourceId(inventoryTypeId.value());
                                availabilityFacade.deleteAllAvailabilityUnitsForParent(parentId);
                                inventoryTypeCatalog.deleteById(inventoryTypeId);
                            });
    }

    @Transactional
    public <T extends EducationalInstitutionId> Optional<T> addItemToInventoryOfType(
            InventoryType unitType,
            Function<UUID, T> itemIdFactory
    ) {
        var ownerId = OwnerId.newOne();
        return addItemToInventoryOfType(unitType, ownerId)
                ? Optional.of(itemIdFactory.apply(ownerId.value()))
                : Optional.empty();
    }

    @Transactional
    public boolean addItemToInventoryOfType(
            InventoryType unitType,
            EducationalInstitutionId itemId
    ) {
        return addItemToInventoryOfType(unitType, new OwnerId(itemId.value()));
    }

    private boolean addItemToInventoryOfType(
            InventoryType inventoryType,
            OwnerId ownerId
    ) {
        var itemAddingResult = new AtomicBoolean(false);
        inventoryTypeCatalog.findIdByInventoryType(inventoryType)
                            .ifPresent(inventoryTypeId -> {
                                var parentId = new ResourceId(inventoryTypeId.value());
                                var resources = availabilityReadModel.findAllFreeResourcesOfParent(parentId);
                                itemAddingResult.set(
                                        availabilityFacade.blockRandom(resources, ownerId)
                                );
                            });
        return itemAddingResult.get();
    }

    @Transactional
    public void removeItem(EducationalInstitutionId itemId, InventoryType inventoryType) {
        var ownerId = new OwnerId(itemId.value());
        inventoryTypeCatalog.findIdByInventoryType(inventoryType)
                            .map(InventoryTypeId::value)
                            .map(ResourceId::new)
                            .flatMap(parentId -> availabilityReadModel.findAnyBlockedResourceBy(ownerId, parentId))
                            .ifPresent(resourceId -> availabilityFacade.release(resourceId, ownerId));
    }

    @Transactional
    public boolean changeCapacityOfInventoryOfType(InventoryType inventoryType, Capacity capacity) {
        var capacityChangeResult = new AtomicBoolean(false);
        int desiredCapacityValue = capacity.value();
        inventoryTypeCatalog.findIdByInventoryType(inventoryType)
                            .map(InventoryTypeId::value)
                            .map(ResourceId::new)
                            .ifPresent(parentId -> {
                                var minAllowedCapacity = availabilityReadModel.countAllBlockedResourcesFor(parentId);
                                if (minAllowedCapacity > desiredCapacityValue) {
                                    return;
                                }
                                capacityChangeResult.set(true);
                                var currentCapacity = availabilityReadModel.countAllFor(parentId);
                                var difference = Math.abs(currentCapacity - desiredCapacityValue);
                                if (currentCapacity < desiredCapacityValue) {
                                    availabilityFacade.createAvailabilityUnitsForParent(parentId, difference);
                                }
                                if (currentCapacity > desiredCapacityValue) {
                                    availabilityFacade.deleteRandomAvailabilityUnitsForParent(parentId, difference);
                                }
                            });
        return capacityChangeResult.get();
    }
}
