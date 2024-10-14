package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership;

import com.github.maleksandrowicz93.edu.domain.availability.AvailabilityFacade;
import com.github.maleksandrowicz93.edu.domain.availability.AvailabilityReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog.CourseCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorCatalog.ProfessorCatalog;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CourseLeadershipFactory {

    ProfessorCatalog professorCatalog;
    CourseCatalog courseCatalog;
    AvailabilityReadModel availabilityReadModel;
    AvailabilityFacade availabilityFacade;
    EducationalInstitutionInventoryReadModel inventoryReadModel;
    EducationalInstitutionInventoryFacade inventoryFacade;

    public CourseAvailabilityReadModel courseAvailabilityReadModel() {
        return new CourseAvailabilityReadModel(availabilityReadModel);
    }

    public CourseLeadershipReadModel courseLeadershipReadModel() {
        return new CourseLeadershipReadModel(inventoryReadModel);
    }

    public CourseLeadershipFacade courseLeadershipFacade() {
        return new CourseLeadershipFacade(
                CourseLeadershipRulesFactory.INSTANCE.createRules(),
                professorCatalog,
                courseCatalog,
                courseLeadershipService()
        );
    }

    CourseLeadershipService courseLeadershipService() {
        return new CourseLeadershipService(
                inventoryReadModel,
                inventoryFacade,
                courseAvailabilityReadModel(),
                courseAvailabilityFacade()
        );
    }

    CourseAvailabilityFacade courseAvailabilityFacade() {
        return new CourseAvailabilityFacade(availabilityFacade);
    }
}