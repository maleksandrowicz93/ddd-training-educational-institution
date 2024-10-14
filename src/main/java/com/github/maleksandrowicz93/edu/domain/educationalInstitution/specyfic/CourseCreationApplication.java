package com.github.maleksandrowicz93.edu.domain.educationalInstitution.specyfic;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FieldsOfStudies;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.Vacancies;

public record CourseCreationApplication(
        FacultyId facultyId,
        ProfessorId professorId,
        String courseName,
        FieldsOfStudies fieldsOfStudies,
        Vacancies vacancies
) {

    public CourseCreationApplication(
            FacultyId facultyId,
            ProfessorId professorId,
            String courseName,
            FieldsOfStudies fieldsOfStudies
    ) {
        this(facultyId, professorId, courseName, fieldsOfStudies, new Vacancies(0));
    }
}
