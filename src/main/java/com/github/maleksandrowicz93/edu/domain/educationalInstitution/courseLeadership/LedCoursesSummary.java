package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryEntrySummary;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;

import java.util.Collection;

public record LedCoursesSummary(
        ProfessorId professorId,
        Capacity capacity,
        Collection<CourseId> leaderships
) {

    static LedCoursesSummary from(
            EducationalInstitutionInventoryEntrySummary<CourseId> inventoryEntrySummary
    ) {
        return new LedCoursesSummary(
                new ProfessorId(inventoryEntrySummary.inventoryType().unit().id().value()),
                inventoryEntrySummary.capacity(),
                inventoryEntrySummary.items()
        );
    }
}
