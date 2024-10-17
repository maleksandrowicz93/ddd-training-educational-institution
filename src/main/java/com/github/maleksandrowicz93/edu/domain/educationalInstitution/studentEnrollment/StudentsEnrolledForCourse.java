package com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryType;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryTypeFactory;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.Unit;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;

import static com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.ItemType.STUDENT;
import static com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.UnitType.COURSE;

enum StudentsEnrolledForCourse implements InventoryTypeFactory<CourseId> {

    FACTORY {
        @Override
        public InventoryType apply(CourseId courseId) {
            return new InventoryType(
                    new Unit(courseId, COURSE),
                    STUDENT
            );
        }
    }
}
