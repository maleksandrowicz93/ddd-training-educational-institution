package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog.CourseCatalogEntry;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;

public record TakenCourseCreationApplication(
        ProfessorId professorId,
        CourseCatalogEntry course
) {
}
