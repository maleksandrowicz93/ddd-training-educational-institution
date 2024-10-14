package com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.EducationalInstitutionId;

public record Unit(
        EducationalInstitutionId id,
        UnitType type
) {
}
