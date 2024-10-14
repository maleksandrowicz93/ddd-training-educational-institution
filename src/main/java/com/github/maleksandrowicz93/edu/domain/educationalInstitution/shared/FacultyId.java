package com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared;

import java.util.UUID;

public record FacultyId(
        UUID value
) implements EducationalInstitutionId {

    public static FacultyId newOne() {
        return new FacultyId(UUID.randomUUID());
    }
}
