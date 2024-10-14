package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryType;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.Unit;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;

import java.util.function.Function;

import static com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.ItemType.COURSE;
import static com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.UnitType.PROFESSOR;

enum LedCourses implements Function<ProfessorId, InventoryType> {

    FACTORY;

    @Override
    public InventoryType apply(ProfessorId professorId) {
        return new InventoryType(
                new Unit(professorId, PROFESSOR),
                COURSE
        );
    }
}
