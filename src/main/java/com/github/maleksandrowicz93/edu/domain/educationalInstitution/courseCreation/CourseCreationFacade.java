package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation;

import com.github.maleksandrowicz93.edu.common.rule.Rules;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog.CourseCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog.CourseCatalogEntry;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.Vacancies;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.StudentEnrollmentReadModel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CourseCreationFacade {

    Rules<CourseClosingContext> courseClosingRules;
    CourseCatalog courseCatalog;
    CourseCreationService courseCreationService;
    CourseEnrollmentService courseEnrollmentService;
    StudentEnrollmentReadModel studentEnrollmentReadModel;

    public Optional<CourseId> createCourse(CourseCreationApplication application) {
        log.info("Creating course for application {}...", application);
        var maybeCreated = courseCreationService.createCourse(application);
        maybeCreated.ifPresentOrElse(
                course -> {
                    courseEnrollmentService.openEnrollmentsFor(course.id(), application.vacancies());
                    courseCatalog.save(course);
                },
                () -> log.info("Course cannot be created at faculty {}", application.facultyId())

        );
        return maybeCreated.map(CourseCatalogEntry::id);
    }

    public boolean closeCourse(CourseId courseId, FacultyId facultyId) {
        log.info("Closing course {}...", courseId);
        var course = courseCatalog.getById(courseId);
        var enrollmentsSummary = studentEnrollmentReadModel.getEnrollmentSummaryFor(courseId);
        var context = new CourseClosingContext(course, enrollmentsSummary);
        var result = courseClosingRules.examine(context);
        if (result.isFailed()) {
            log.info(result.reason());
            return false;
        }
        courseCatalog.deleteById(courseId);
        courseEnrollmentService.closeEnrollmentsFor(courseId);
        courseCreationService.closeCourse(courseId, facultyId);
        return true;
    }

    public boolean restrictMaxVacanciesNumberFor(CourseId courseId, Vacancies vacancies) {
        log.info("Restricting vacancies number to {} for {}...", vacancies, courseId);
        return courseEnrollmentService.restrictMaxVacanciesNumberFor(courseId, vacancies);
    }
}
