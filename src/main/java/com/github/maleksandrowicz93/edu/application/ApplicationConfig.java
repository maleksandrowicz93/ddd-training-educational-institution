package com.github.maleksandrowicz93.edu.application;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.specyfic.CourseClosingConfig;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.specyfic.CourseCreationConfig;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.specyfic.FacultyCreationConfig;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.specyfic.ProfessorEmploymentConfig;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.specyfic.StudentEnrollmentAtFacultyConfig;

public record ApplicationConfig(
        CourseCreationConfig courseCreationConfig,
        CourseClosingConfig courseClosingConfig,
        FacultyCreationConfig facultyCreationConfig,
        ProfessorEmploymentConfig professorEmploymentConfig,
        StudentEnrollmentAtFacultyConfig studentEnrollmentAtFacultyConfig
) {
}
