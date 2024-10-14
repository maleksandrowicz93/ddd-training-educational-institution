package com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership.CourseLeadershipFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog.FacultyCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorCatalog.ProfessorCatalog;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ProfessorEmploymentFactory {

    ProfessorEmploymentConfig professorEmploymentConfig;
    FacultyCatalog facultyCatalog;
    ProfessorCatalog professorCatalog;
    EducationalInstitutionInventoryReadModel inventoryReadModel;
    EducationalInstitutionInventoryFacade inventoryFacade;
    CourseLeadershipFacade courseLeadershipFacade;

    public ProfessorEmploymentReadModel professorEmploymentReadModel() {
        return new ProfessorEmploymentReadModel(inventoryReadModel);
    }

    public ProfessorEmploymentFacade professorEmploymentFacade() {
        return new ProfessorEmploymentFacade(
                professorEmploymentConfig,
                ProfessorEmploymentRulesFactory.from(professorEmploymentConfig).createRules(),
                facultyCatalog,
                professorCatalog,
                professorInventory(),
                courseLeadershipFacade
        );
    }

    ProfessorInventory professorInventory() {
        return new ProfessorInventory(inventoryFacade);
    }
}
