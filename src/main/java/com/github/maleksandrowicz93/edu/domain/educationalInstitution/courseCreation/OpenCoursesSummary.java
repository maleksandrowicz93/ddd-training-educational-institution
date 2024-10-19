package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryEntrySummary;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;

import java.util.Collection;

public record OpenCoursesSummary(
        FacultyId facultyId,
        Capacity capacity,
        Collection<CourseId> courses
) {

    static OpenCoursesSummary from(
            InventoryEntrySummary<CourseId> inventoryEntrySummary
    ) {
        return new OpenCoursesSummary(
                inventoryEntrySummary.unitId(FacultyId::new),
                inventoryEntrySummary.capacity(),
                inventoryEntrySummary.items()
        );
    }
}
