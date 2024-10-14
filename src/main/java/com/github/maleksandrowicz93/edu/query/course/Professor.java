package com.github.maleksandrowicz93.edu.query.course;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorCatalog.ProfessorCatalogEntry;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;

public record Professor(
        ProfessorId professorId,
        String professorName
) {

    static Professor from(ProfessorCatalogEntry catalogEntry) {
        return new Professor(catalogEntry.id(), catalogEntry.name());
    }
}
