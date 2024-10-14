package com.github.maleksandrowicz93.edu.query.course;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;

import java.util.function.Function;

enum CourseSummaryInitializer implements Function<CourseId, CourseSummary> {

    INSTANCE;

    @Override
    public CourseSummary apply(CourseId courseId) {
        return CourseSummary.builder()
                            .courseId(courseId)
                            .build();
    }
}
