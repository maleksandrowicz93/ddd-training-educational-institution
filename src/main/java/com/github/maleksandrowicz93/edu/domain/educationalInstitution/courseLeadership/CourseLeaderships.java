package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership;

import com.github.maleksandrowicz93.edu.common.infra.Transactional;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
class CourseLeaderships {

    InventoryReadModel inventoryReadModel;
    InventoryFacade inventoryFacade;
    CourseAvailabilityReadModel courseAvailabilityReadModel;
    CourseAvailabilityFacade courseAvailabilityFacade;

    @Transactional
    boolean createTakenOne(TakenCourseCreationApplication application) {
        var professorId = application.professorId();
        var courseId = application.course().id();
        var courseLeadership = LedCourses.FACTORY.apply(professorId);
        var courseLed = inventoryFacade.addItemToInventoryOfType(courseLeadership, courseId);
        if (courseLed) {
            courseAvailabilityFacade.createTakenCourse(courseId, professorId);
            return true;
        }
        log.info("Professor {} cannot lead the course {}", professorId, courseId);
        return false;
    }

    @Transactional
    boolean overtake(CourseOvertakingApplication application) {
        var professorId = application.professorId();
        var courseId = application.courseId();
        var taken = courseAvailabilityFacade.takeCourse(courseId, professorId);
        if (!taken) {
            log.info("Course {} cannot be overtaken by professor {} because is not available", courseId, professorId);
            return false;
        }
        var courseLeadership = LedCourses.FACTORY.apply(professorId);
        var courseLed = inventoryFacade.addItemToInventoryOfType(courseLeadership, courseId);
        if (courseLed) {
            return true;
        }
        log.info("Professor {} cannot overtake the course {} because has no capacity", professorId, courseId);
        courseAvailabilityFacade.releaseCourse(courseId, professorId);
        return false;
    }

    @Transactional
    void resign(CourseId courseId, ProfessorId professorId) {
        var released = courseAvailabilityFacade.releaseCourse(courseId, professorId);
        if (released) {
            var courseLeadership = LedCourses.FACTORY.apply(professorId);
            inventoryFacade.removeItem(courseId, courseLeadership);
        } else {
            log.info("Professor {} has not led the course {}", professorId, courseId);
        }
    }

    @Transactional
    void resignFromAllBy(ProfessorId professorId) {
        var courseLeadership = LedCourses.FACTORY.apply(professorId);
        inventoryReadModel.findAllItemsByInventoryType(courseLeadership, CourseId::new)
                          .forEach(courseId -> resign(courseId, professorId));
    }

    @Transactional
    void close(CourseId courseId) {
        courseAvailabilityReadModel.findProfessorLeadingCourse(courseId)
                                   .map(LedCourses.FACTORY)
                                   .ifPresent(inventoryType -> inventoryFacade.removeItem(courseId, inventoryType));
        courseAvailabilityFacade.deleteCourseTakingAvailability(courseId);
    }
}
