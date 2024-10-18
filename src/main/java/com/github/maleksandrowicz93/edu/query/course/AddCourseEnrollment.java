package com.github.maleksandrowicz93.edu.query.course;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.CourseEnrollmentSummary;

enum AddCourseEnrollment implements CourseSummaryComposer<CourseEnrollmentSummary> {

    COMPOSER {
        @Override
        public CourseSummary apply(CourseSummary courseSummary, CourseEnrollmentSummary enrollmentSummary) {
            return CourseSummary.builder()
                                .studentsEnrolledFor(
                                        new StudentsEnrolledFor(
                                                enrollmentSummary.maxVacancies(),
                                                enrollmentSummary.enrolled()
                                        )
                                )
                                .build();
        }
    }
}
