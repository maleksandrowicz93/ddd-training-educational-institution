package com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryEntrySummary;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.Vacancies;

import java.util.Collection;

public record ProfessorEmploymentSummary(
        FacultyId facultyId,
        Vacancies maxVacancies,
        Collection<ProfessorId> employed
) {

    static ProfessorEmploymentSummary from(
            EducationalInstitutionInventoryEntrySummary<ProfessorId> inventoryEntrySummary
    ) {
        return new ProfessorEmploymentSummary(
                inventoryEntrySummary.unitId(FacultyId::new),
                new Vacancies(inventoryEntrySummary.capacity().value()),
                inventoryEntrySummary.items()
        );
    }
}
