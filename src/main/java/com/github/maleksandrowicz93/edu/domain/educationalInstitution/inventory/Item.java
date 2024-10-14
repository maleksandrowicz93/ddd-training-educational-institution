package com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.EducationalInstitutionId;

public record Item(
        EducationalInstitutionId id,
        ItemType type
) {
}
