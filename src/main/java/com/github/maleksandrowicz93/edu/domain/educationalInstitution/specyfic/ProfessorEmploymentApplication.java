package com.github.maleksandrowicz93.edu.domain.educationalInstitution.specyfic;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FieldsOfStudies;

public record ProfessorEmploymentApplication(
        FacultyId facultyId,
        String professorName,
        int yearsOfExperience,
        FieldsOfStudies fieldsOfStudies
) {
}
