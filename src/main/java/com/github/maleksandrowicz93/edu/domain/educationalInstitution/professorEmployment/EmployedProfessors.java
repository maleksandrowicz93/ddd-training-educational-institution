package com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryType;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.Unit;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;

import java.util.function.Function;

import static com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.ItemType.PROFESSOR;
import static com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.UnitType.FACULTY;

enum EmployedProfessors implements Function<FacultyId, InventoryType> {

    FACTORY {
        @Override
        public InventoryType apply(FacultyId facultyId) {
            return new InventoryType(
                    new Unit(facultyId, FACULTY),
                    PROFESSOR
            );
        }
    }
}
