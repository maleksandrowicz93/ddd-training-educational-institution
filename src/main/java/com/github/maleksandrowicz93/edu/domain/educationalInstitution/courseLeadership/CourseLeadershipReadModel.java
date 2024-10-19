package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CourseLeadershipReadModel {

    InventoryReadModel inventoryReadModel;

    public Optional<LedCoursesSummary> findSummaryOfCoursesLedBy(ProfessorId professorId) {
        return inventoryReadModel.findSummaryByInventoryType(
                                         LedCourses.FACTORY.apply(professorId),
                                         CourseId::new
                                 )
                                 .map(LedCoursesSummary::from);
    }

    public LedCoursesSummary getSummaryOfCoursesLedBy(ProfessorId professorId) {
        return findSummaryOfCoursesLedBy(professorId).orElseThrow();
    }
}
