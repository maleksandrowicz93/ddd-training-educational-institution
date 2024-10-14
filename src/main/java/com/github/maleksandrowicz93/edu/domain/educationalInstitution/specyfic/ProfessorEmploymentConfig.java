package com.github.maleksandrowicz93.edu.domain.educationalInstitution.specyfic;

public record ProfessorEmploymentConfig(
        int minYearsOfExperience,
        int minMatchedFieldsOfStudy,
        int maxCourseLeaderships
) {
}
