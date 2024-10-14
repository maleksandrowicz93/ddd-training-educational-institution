package com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorCatalog;

import com.github.maleksandrowicz93.edu.common.infra.Entity;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FieldsOfStudies;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;

public record ProfessorCatalogEntry(
        ProfessorId id,
        String name,
        FieldsOfStudies fieldsOfStudies
) implements Entity<ProfessorId> {
}
