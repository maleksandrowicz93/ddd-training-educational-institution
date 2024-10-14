package com.github.maleksandrowicz93.edu.query.course;

enum AddCourseLeadership implements CourseSummaryComposer<CourseLeadership> {

    COMPOSER;

    @Override
    public CourseSummary apply(CourseSummary courseSummary, CourseLeadership courseLeadership) {
        return courseSummary.toBuilder()
                            .courseLeadership(courseLeadership)
                            .build();
    }
}
