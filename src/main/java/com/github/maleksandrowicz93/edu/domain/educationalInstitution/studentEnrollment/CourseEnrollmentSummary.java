package com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryEntrySummary;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.StudentId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.Vacancies;

import java.util.Collection;

public record CourseEnrollmentSummary(
        CourseId courseId,
        Vacancies maxVacancies,
        Collection<StudentId> enrolled
) {

    public int enrollmentsNumber() {
        return enrolled.size();
    }

    static CourseEnrollmentSummary from(
            EducationalInstitutionInventoryEntrySummary<StudentId> inventoryEntrySummary
    ) {
        return new CourseEnrollmentSummary(
                new CourseId(inventoryEntrySummary.inventoryType().unit().id().value()),
                new Vacancies(inventoryEntrySummary.capacity().value()),
                inventoryEntrySummary.items()
        );
    }
}
