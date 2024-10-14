package com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared;

import java.util.UUID;

public record StudentId(
        UUID value
) implements EducationalInstitutionId {
}
