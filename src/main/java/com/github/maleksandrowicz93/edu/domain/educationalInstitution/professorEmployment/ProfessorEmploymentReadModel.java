package com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ProfessorEmploymentReadModel {

    EducationalInstitutionInventoryReadModel inventoryReadModel;

    public Optional<ProfessorEmploymentSummary> findEmploymentSummaryAt(FacultyId facultyId) {
        return inventoryReadModel.findSummaryByInventoryType(
                                         EmployedProfessors.FACTORY.apply(facultyId),
                                         ProfessorId::new
                                 )
                                 .map(ProfessorEmploymentSummary::from);
    }

    public ProfessorEmploymentSummary getEmploymentSummaryAt(FacultyId facultyId) {
        return findEmploymentSummaryAt(facultyId).orElseThrow();
    }
}
