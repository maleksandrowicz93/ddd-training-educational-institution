package com.github.maleksandrowicz93.edu.query.faculty;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation.OpenCoursesSummary;

enum AddOpenCourses implements FacultySummaryComposer<OpenCoursesSummary> {

    COMPOSER {
        @Override
        public FacultySummary apply(FacultySummary facultySummary, OpenCoursesSummary openCoursesSummary) {
            return facultySummary.toBuilder()
                                 .openCourses(
                                         new OpenCourses(
                                                 openCoursesSummary.capacity(),
                                                 openCoursesSummary.courses()
                                         )
                                 )
                                 .build();
        }
    }
}
