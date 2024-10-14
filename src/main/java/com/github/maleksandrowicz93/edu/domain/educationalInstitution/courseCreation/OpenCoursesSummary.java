package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryEntrySummary;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;

import java.util.Collection;

public record OpenCoursesSummary(
        FacultyId facultyId,
        Capacity capacity,
        Collection<CourseId> courses
) {

    static OpenCoursesSummary from(
            EducationalInstitutionInventoryEntrySummary<CourseId> inventoryEntrySummary
    ) {
        return new OpenCoursesSummary(
                new FacultyId(inventoryEntrySummary.inventoryType().unit().id().value()),
                inventoryEntrySummary.capacity(),
                inventoryEntrySummary.items()
        );
    }
}
