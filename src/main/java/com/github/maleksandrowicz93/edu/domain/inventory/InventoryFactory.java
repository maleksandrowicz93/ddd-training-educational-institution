package com.github.maleksandrowicz93.edu.domain.inventory;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class InventoryFactory {

    Inventory inventory;

    public static InventoryFactory forTests() {
        return new InventoryFactory(
                new InMemoryInventory()
        );
    }

    public InventoryReadModel inventoryReadModel() {
        return new InventoryReadModel(inventory);
    }

    public InventoryFacade inventoryFacade() {
        return new InventoryFacade(inventory);
    }
}
