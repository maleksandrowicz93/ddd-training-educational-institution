package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog;

import com.github.maleksandrowicz93.edu.common.infra.Entity;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FieldsOfStudies;

public record CourseCatalogEntry(
        CourseId id,
        String name,
        FieldsOfStudies fieldsOfStudies
) implements Entity<CourseId> {
}
