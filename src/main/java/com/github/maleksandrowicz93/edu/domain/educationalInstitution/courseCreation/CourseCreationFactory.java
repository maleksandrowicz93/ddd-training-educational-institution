package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation;

import com.github.maleksandrowicz93.edu.common.infra.NotificationPublisher;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog.CourseCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership.CourseLeadershipFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.StudentEnrollmentReadModel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CourseCreationFactory {

    CourseCreationConfig courseCreationConfig;
    CourseClosingConfig courseClosingConfig;
    CourseCatalog courseCatalog;
    InventoryReadModel inventoryReadModel;
    InventoryFacade inventoryFacade;
    CourseLeadershipFacade courseLeadershipFacade;
    StudentEnrollmentReadModel studentEnrollmentReadModel;
    NotificationPublisher notificationPublisher;

    public CourseCreationReadModel courseCreationReadModel() {
        return new CourseCreationReadModel(inventoryReadModel);
    }

    public CourseCreationFacade courseCreationFacade() {
        return new CourseCreationFacade(
                RestrictingCourseVacationsRulesFactory.from(courseCreationConfig).createRules(),
                CourseClosingRulesFactory.from(courseClosingConfig).createRules(),
                courseCatalog,
                studentEnrollmentReadModel,
                courseCreation(),
                courseEnrollments(),
                notificationPublisher
        );
    }

    CourseCreation courseCreation() {
        return new CourseCreation(
                inventoryFacade,
                courseLeadershipFacade
        );
    }

    CourseEnrollments courseEnrollments() {
        return new CourseEnrollments(
                courseCreationConfig,
                inventoryFacade
        );
    }
}
