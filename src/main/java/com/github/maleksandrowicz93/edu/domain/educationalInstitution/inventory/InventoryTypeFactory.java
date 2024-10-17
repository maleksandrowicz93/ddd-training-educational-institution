package com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.EducationalInstitutionId;

import java.util.function.Function;

public interface InventoryTypeFactory<ID extends EducationalInstitutionId> extends Function<ID, InventoryType> {
}
