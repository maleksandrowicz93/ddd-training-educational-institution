package com.github.maleksandrowicz93.edu.application;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation.CourseClosingConfig;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation.CourseCreationConfig;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCreation.FacultyCreationConfig;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment.ProfessorEmploymentConfig;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.StudentEnrollmentAtFacultyConfig;

public record ApplicationConfig(
        CourseCreationConfig courseCreationConfig,
        CourseClosingConfig courseClosingConfig,
        FacultyCreationConfig facultyCreationConfig,
        ProfessorEmploymentConfig professorEmploymentConfig,
        StudentEnrollmentAtFacultyConfig studentEnrollmentAtFacultyConfig
) {
}
