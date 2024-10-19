package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryEntrySummary;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;

import java.util.Collection;

public record LedCoursesSummary(
        ProfessorId professorId,
        Capacity capacity,
        Collection<CourseId> leaderships
) {

    static LedCoursesSummary from(
            InventoryEntrySummary<CourseId> inventoryEntrySummary
    ) {
        return new LedCoursesSummary(
                inventoryEntrySummary.unitId(ProfessorId::new),
                inventoryEntrySummary.capacity(),
                inventoryEntrySummary.items()
        );
    }
}
