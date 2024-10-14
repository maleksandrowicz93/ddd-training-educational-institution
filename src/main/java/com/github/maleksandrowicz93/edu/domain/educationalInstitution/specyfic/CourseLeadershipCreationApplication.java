package com.github.maleksandrowicz93.edu.domain.educationalInstitution.specyfic;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog.CourseCatalogEntry;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;

public record CourseLeadershipCreationApplication(
        ProfessorId professorId,
        CourseCatalogEntry course
) {
}
