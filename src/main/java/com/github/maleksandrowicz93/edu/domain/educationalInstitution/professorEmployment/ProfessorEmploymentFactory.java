package com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment;

import com.github.maleksandrowicz93.edu.common.infra.NotificationPublisher;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership.CourseLeadershipFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog.FacultyCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryReadModel;
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
    InventoryReadModel inventoryReadModel;
    InventoryFacade inventoryFacade;
    CourseLeadershipFacade courseLeadershipFacade;
    NotificationPublisher notificationPublisher;

    public ProfessorEmploymentReadModel professorEmploymentReadModel() {
        return new ProfessorEmploymentReadModel(inventoryReadModel);
    }

    public ProfessorEmploymentFacade professorEmploymentFacade() {
        return new ProfessorEmploymentFacade(
                professorEmploymentConfig,
                ProfessorEmploymentRulesFactory.from(professorEmploymentConfig).createRules(),
                facultyCatalog,
                professorCatalog,
                professorEmployment(),
                courseLeadershipFacade,
                notificationPublisher
        );
    }

    ProfessorEmployment professorEmployment() {
        return new ProfessorEmployment(inventoryFacade);
    }
}
