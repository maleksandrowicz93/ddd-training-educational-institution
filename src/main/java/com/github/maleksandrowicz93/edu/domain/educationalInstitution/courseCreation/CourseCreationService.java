package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog.CourseCatalogEntry;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership.CourseLeadershipCreationApplication;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership.CourseLeadershipFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
class CourseCreationService {

    EducationalInstitutionInventoryFacade inventoryFacade;
    CourseLeadershipFacade courseLeadershipFacade;

    Optional<CourseCatalogEntry> createCourse(CourseCreationApplication application) {
        var facultyId = application.facultyId();
        var coursesAtFaculty = OpenCourses.FACTORY.apply(facultyId);
        var createdItem = inventoryFacade.addItemToInventoryOfType(coursesAtFaculty, CourseId::new);
        if (createdItem.isEmpty()) {
            log.info("Course cannot be created within faculty {}", facultyId);
            return Optional.empty();
        }
        var courseId = createdItem.get();
        var course = new CourseCatalogEntry(courseId, application.courseName(), application.fieldsOfStudies());
        var courseLeadershipCreationApplication = new CourseLeadershipCreationApplication(
                application.professorId(),
                course
        );
        var leadershipTaken = courseLeadershipFacade.createCourseLeadership(courseLeadershipCreationApplication);
        if (leadershipTaken) {
            return Optional.of(course);
        }
        log.info("Course was not created within faculty {} because course leadership cannot be taken", facultyId);
        inventoryFacade.removeItem(courseId, coursesAtFaculty);
        return Optional.empty();
    }

    void closeCourse(CourseId courseId, FacultyId facultyId) {
        courseLeadershipFacade.closeCourse(courseId);
        var openCourses = OpenCourses.FACTORY.apply(facultyId);
        inventoryFacade.removeItem(courseId, openCourses);
    }
}
