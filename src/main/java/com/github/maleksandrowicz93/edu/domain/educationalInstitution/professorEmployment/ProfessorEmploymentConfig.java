package com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment;

public record ProfessorEmploymentConfig(
        int minYearsOfExperience,
        int minMatchedFieldsOfStudy,
        int maxCourseLeaderships
) {
}
