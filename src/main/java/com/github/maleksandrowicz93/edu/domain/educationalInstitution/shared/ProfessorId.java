package com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared;

import java.util.UUID;

public record ProfessorId(
        UUID value
) implements EducationalInstitutionId {
}
