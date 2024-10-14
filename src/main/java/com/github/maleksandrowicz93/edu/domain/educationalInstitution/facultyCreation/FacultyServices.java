package com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCreation;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryFacade;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
class FacultyServices {

    EducationalInstitutionInventoryFacade inventoryFacade;

    void open(FacultyServicesSpecification specification) {
        InventoryTypeCreationsFactory.initFor(specification.facultyId())
                                     .addCourseInventory(Capacity.of(specification.coursesCapacity().value()))
                                     .addProfessorInventory(Capacity.of(specification.professorVacancies().count()))
                                     .addStudentInventory(Capacity.of(specification.studentVacancies().count()))
                                     .create()
                                     .forEach(inventoryFacade::createInventoryOfType);
    }
}
