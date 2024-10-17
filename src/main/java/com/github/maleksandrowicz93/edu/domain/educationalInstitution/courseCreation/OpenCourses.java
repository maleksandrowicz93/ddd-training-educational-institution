package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryType;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.Unit;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;

import java.util.function.Function;

import static com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.ItemType.COURSE;
import static com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.UnitType.FACULTY;

enum OpenCourses implements Function<FacultyId, InventoryType> {

    FACTORY {
        @Override
        public InventoryType apply(FacultyId facultyId) {
            return new InventoryType(
                    new Unit(facultyId, FACULTY),
                    COURSE
            );
        }
    }
}
