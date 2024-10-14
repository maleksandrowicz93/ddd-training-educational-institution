package com.github.maleksandrowicz93.edu.query.course;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog.CourseCatalogEntry;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.CourseEnrollmentSummary;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.stream.Collector;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
@FieldDefaults(level = PRIVATE)
class CourseSummaryAssembler {

    CourseSummary courseSummary;

    static Collector<Object, CourseSummaryAssembler, CourseSummary> toCourseSummary(CourseId courseId) {
        return Collector.of(
                () -> CourseSummaryAssembler.init(courseId),
                CourseSummaryAssembler::add,
                (_, b) -> b,
                CourseSummaryAssembler::create
        );
    }

    private static CourseSummaryAssembler init(CourseId courseId) {
        return new CourseSummaryAssembler(
                CourseSummaryInitializer.INSTANCE.apply(courseId)
        );
    }

    private void add(Object o) {
        courseSummary = switch (o) {
            case CourseCatalogEntry course -> AddCourseCatalogEntry.COMPOSER.apply(courseSummary, course);
            case CourseLeadership course -> AddCourseLeadership.COMPOSER.apply(courseSummary, course);
            case CourseEnrollmentSummary course -> AddCourseEnrollment.COMPOSER.apply(courseSummary, course);
            default -> courseSummary;
        };
    }

    private CourseSummary create() {
        return courseSummary;
    }
}
