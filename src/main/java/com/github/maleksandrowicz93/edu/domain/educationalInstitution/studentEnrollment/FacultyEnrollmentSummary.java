package com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryEntrySummary;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.StudentId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.Vacancies;

import java.util.Collection;

public record FacultyEnrollmentSummary(
        FacultyId facultyId,
        Vacancies maxVacancies,
        Collection<StudentId> enrolled
) {

    static FacultyEnrollmentSummary from(
            EducationalInstitutionInventoryEntrySummary<StudentId> inventoryEntrySummary
    ) {
        return new FacultyEnrollmentSummary(
                inventoryEntrySummary.unitId(FacultyId::new),
                new Vacancies(inventoryEntrySummary.capacity().value()),
                inventoryEntrySummary.items()
        );
    }
}
