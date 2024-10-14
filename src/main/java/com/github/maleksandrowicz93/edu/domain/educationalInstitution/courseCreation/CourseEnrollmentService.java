package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryType;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryTypeCreation;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.Unit;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.Vacancies;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

import static com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.ItemType.STUDENT;
import static com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.UnitType.COURSE;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
class CourseEnrollmentService {

    CourseCreationConfig config;
    EducationalInstitutionInventoryFacade inventoryFacade;

    void openEnrollmentsFor(CourseId courseId, Vacancies vacancies) {
        inventoryFacade.createInventoryOfType(
                new InventoryTypeCreation(
                        CourseEnrollment.FACTORY.apply(courseId),
                        calculateCourseCapacity(vacancies)
                )
        );
    }

    boolean restrictMaxVacanciesNumberFor(CourseId courseId, Vacancies vacancies) {
        if (vacancies.count() < config.minCourseEnrollmentsLimit()) {
            log.info(
                    "Cannot change max vacancies for course {} because desired value {} is bellow allowed limit",
                    courseId, vacancies
            );
            return false;
        }
        var inventoryType = CourseEnrollment.FACTORY.apply(courseId);
        var capacity = Capacity.of(vacancies.count());
        return inventoryFacade.changeCapacityOfInventoryOfType(inventoryType, capacity);
    }

    void closeEnrollmentsFor(CourseId courseId) {
        inventoryFacade.removeInventoryOfType(CourseEnrollment.FACTORY.apply(courseId));
    }

    private Capacity calculateCourseCapacity(Vacancies vacancies) {
        var capacityValue = Math.max(vacancies.count(), config.minCourseEnrollmentsLimit());
        return Capacity.of(capacityValue);
    }

    private enum CourseEnrollment implements Function<CourseId, InventoryType> {

        FACTORY;

        @Override
        public InventoryType apply(CourseId courseId) {
            return new InventoryType(
                    new Unit(courseId, COURSE),
                    STUDENT
            );
        }
    }
}
