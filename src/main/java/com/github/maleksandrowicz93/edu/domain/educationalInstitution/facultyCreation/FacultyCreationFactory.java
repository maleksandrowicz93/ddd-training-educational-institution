package com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCreation;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog.FacultyCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryFacade;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class FacultyCreationFactory {

    FacultyCreationConfig facultyCreationConfig;
    FacultyCatalog facultyCatalog;
    EducationalInstitutionInventoryFacade inventoryFacade;

    public FacultyCreationFacade facultyCreationFacade() {
        return new FacultyCreationFacade(
                facultyCreationConfig,
                facultyCatalog,
                facultyServices()
        );
    }

    FacultyServices facultyServices() {
        return new FacultyServices(
                inventoryFacade
        );
    }
}
