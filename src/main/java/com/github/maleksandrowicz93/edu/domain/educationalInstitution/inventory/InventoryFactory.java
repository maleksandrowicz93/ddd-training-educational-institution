package com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory;

import com.github.maleksandrowicz93.edu.domain.availability.AvailabilityFacade;
import com.github.maleksandrowicz93.edu.domain.availability.AvailabilityReadModel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class InventoryFactory {

    AvailabilityReadModel availabilityReadModel;
    AvailabilityFacade availabilityFacade;
    InventoryTypeCatalog inventoryTypeCatalog;

    public static InventoryFactory forTests(
            AvailabilityReadModel availabilityReadModel,
            AvailabilityFacade availabilityFacade
    ) {
        return new InventoryFactory(
                availabilityReadModel,
                availabilityFacade,
                new InMemoryInventoryTypeCatalog()
        );
    }

    public InventoryReadModel inventoryReadModel() {
        return new InventoryReadModel(
                availabilityReadModel,
                inventoryTypeCatalog
        );
    }

    public InventoryFacade inventoryFacade() {
        return new InventoryFacade(
                inventoryTypeCatalog,
                availabilityReadModel,
                availabilityFacade
        );
    }
}
