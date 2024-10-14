package com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory;

import com.github.maleksandrowicz93.edu.domain.inventory.InventoryFacade;
import com.github.maleksandrowicz93.edu.domain.inventory.InventoryReadModel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class EducationalInstitutionInventoryFactory {

    InventoryReadModel inventoryReadModel;
    InventoryFacade inventoryFacade;
    InventoryTypeCatalog inventoryTypeCatalog;

    public static EducationalInstitutionInventoryFactory forTests(
            InventoryReadModel inventoryReadModel,
            InventoryFacade inventoryFacade
    ) {
        return new EducationalInstitutionInventoryFactory(
                inventoryReadModel,
                inventoryFacade,
                new InMemoryInventoryTypeCatalog()
        );
    }

    public EducationalInstitutionInventoryReadModel educationalInstitutionInventoryReadModel() {
        return new EducationalInstitutionInventoryReadModel(
                inventoryReadModel,
                inventoryTypeCatalog
        );
    }

    public EducationalInstitutionInventoryFacade educationalInstitutionInventoryFacade() {
        return new EducationalInstitutionInventoryFacade(
                inventoryFacade,
                inventoryTypeCatalog
        );
    }
}
