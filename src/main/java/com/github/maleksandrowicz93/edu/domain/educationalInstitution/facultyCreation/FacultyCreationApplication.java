package com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCreation;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FieldOfStudy;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FieldsOfStudies;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.Vacancies;

public record FacultyCreationApplication(
        String name,
        FieldOfStudy mainFieldOfStudy,
        FieldsOfStudies secondaryFieldsOfStudies,
        Vacancies professorVacancies,
        Vacancies studentVacancies
) {
}
