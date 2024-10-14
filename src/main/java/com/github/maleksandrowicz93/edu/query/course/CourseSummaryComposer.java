package com.github.maleksandrowicz93.edu.query.course;

import java.util.function.BiFunction;

interface CourseSummaryComposer<T> extends BiFunction<CourseSummary, T, CourseSummary> {
}
