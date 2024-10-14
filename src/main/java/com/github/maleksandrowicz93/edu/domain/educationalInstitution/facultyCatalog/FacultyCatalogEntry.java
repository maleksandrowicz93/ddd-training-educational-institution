package com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog;

import com.github.maleksandrowicz93.edu.common.infra.Entity;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FieldOfStudy;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FieldsOfStudies;

public record FacultyCatalogEntry(
        FacultyId id,
        String name,
        FieldOfStudy mainFieldOfStudy,
        FieldsOfStudies secondaryFieldOfStudies
) implements Entity<FacultyId> {

    public int fieldsOfStudiesNumber() {
        return secondaryFieldOfStudies.count() + 1;
    }

    public FieldsOfStudies allFieldsOfStudies() {
        return secondaryFieldOfStudies.add(mainFieldOfStudy);
    }
}
