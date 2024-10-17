package com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment;

import com.github.maleksandrowicz93.edu.common.infra.NotificationPublisher;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog.FacultyCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryReadModel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class StudentEnrollmentFactory {

    StudentEnrollmentAtFacultyConfig config;
    FacultyCatalog facultyCatalog;
    EducationalInstitutionInventoryReadModel inventoryReadModel;
    EducationalInstitutionInventoryFacade inventoryFacade;
    NotificationPublisher notificationPublisher;

    public StudentEnrollmentReadModel studentEnrollmentReadModel() {
        return new StudentEnrollmentReadModel(inventoryReadModel);
    }

    public StudentEnrollmentFacade studentEnrollmentFacade() {
        return new StudentEnrollmentFacade(
                StudentEnrollmentAtFacultyRulesFactory.from(config).createRules(),
                facultyCatalog,
                studentEnrollments(),
                notificationPublisher
        );
    }

    StudentEnrollments studentEnrollments() {
        return new StudentEnrollments(
                inventoryReadModel,
                inventoryFacade
        );
    }
}
