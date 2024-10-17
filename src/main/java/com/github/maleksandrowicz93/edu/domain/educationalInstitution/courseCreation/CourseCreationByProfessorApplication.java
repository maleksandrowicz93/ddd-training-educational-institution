package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FieldsOfStudies;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.Vacancies;

public record CourseCreationByProfessorApplication(
        FacultyId facultyId,
        ProfessorId professorId,
        String courseName,
        FieldsOfStudies fieldsOfStudies,
        Vacancies vacancies
) {

    public CourseCreationByProfessorApplication(
            FacultyId facultyId,
            ProfessorId professorId,
            String courseName,
            FieldsOfStudies fieldsOfStudies
    ) {
        this(facultyId, professorId, courseName, fieldsOfStudies, new Vacancies(0));
    }
}
