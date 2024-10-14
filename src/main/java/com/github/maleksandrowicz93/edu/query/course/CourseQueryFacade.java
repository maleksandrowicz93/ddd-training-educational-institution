package com.github.maleksandrowicz93.edu.query.course;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog.CourseCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog.CourseCatalogEntry;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership.CourseAvailabilityReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorCatalog.ProfessorCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.StudentEnrollmentReadModel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;
import java.util.stream.Stream;

import static com.github.maleksandrowicz93.edu.query.course.CourseSummaryAssembler.toCourseSummary;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CourseQueryFacade {

    CourseCatalog courseCatalog;
    ProfessorCatalog professorCatalog;
    StudentEnrollmentReadModel studentEnrollmentReadModel;
    CourseAvailabilityReadModel courseAvailabilityReadModel;

    public Optional<CourseSummary> findCourseSummary(CourseId courseId) {
        return courseCatalog.findById(courseId)
                            .map(this::getCourseSummaryFor);
    }

    public CourseSummary getCourseSummary(CourseId courseId) {
        return findCourseSummary(courseId).orElseThrow();
    }

    private CourseSummary getCourseSummaryFor(CourseCatalogEntry courseCatalogEntry) {
        var courseId = courseCatalogEntry.id();
        var enrollmentSummary = studentEnrollmentReadModel.getEnrollmentSummaryFor(courseId);
        var maybeProfessor = courseAvailabilityReadModel.findProfessorLeadingCourse(courseId)
                                                        .map(professorCatalog::getBtId);
        var courseLeadership = CourseLeadershipFactory.INSTANCE.apply(maybeProfessor);
        return Stream.of(courseCatalogEntry, enrollmentSummary, courseLeadership)
                     .collect(toCourseSummary(courseId));
    }
}
