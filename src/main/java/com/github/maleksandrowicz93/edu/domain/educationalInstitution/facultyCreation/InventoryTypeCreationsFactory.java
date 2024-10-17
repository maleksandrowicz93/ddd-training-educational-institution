package com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCreation;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryType;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryTypeCreation;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.ItemType;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.Unit;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.ItemType.COURSE;
import static com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.ItemType.PROFESSOR;
import static com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.ItemType.STUDENT;
import static com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.UnitType.FACULTY;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
class InventoryTypeCreationsFactory {

    FacultyId facultyId;
    Collection<InventoryTypeCreation> inventoryTypeCreations = new HashSet<>();

    static InventoryTypeCreationsFactory initFor(FacultyId facultyId) {
        return new InventoryTypeCreationsFactory(facultyId);
    }

    InventoryTypeCreationsFactory addCourseInventory(Capacity capacity) {
        inventoryTypeCreations.add(toInventoryTypeCreation(COURSE, capacity));
        return this;
    }

    InventoryTypeCreationsFactory addProfessorInventory(Capacity capacity) {
        inventoryTypeCreations.add(toInventoryTypeCreation(PROFESSOR, capacity));
        return this;
    }

    InventoryTypeCreationsFactory addStudentInventory(Capacity capacity) {
        inventoryTypeCreations.add(toInventoryTypeCreation(STUDENT, capacity));
        return this;
    }

    Collection<InventoryTypeCreation> create() {
        return Set.copyOf(inventoryTypeCreations);
    }

    private InventoryTypeCreation toInventoryTypeCreation(
            ItemType itemType,
            Capacity capacity
    ) {
        return new InventoryTypeCreation(
                new InventoryType(
                        new Unit(facultyId, FACULTY),
                        itemType
                ),
                capacity
        );
    }
}
