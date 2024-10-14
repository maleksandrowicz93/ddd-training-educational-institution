package com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared;

import java.util.UUID;

public record CourseId(
        UUID value
) implements EducationalInstitutionId {
}
