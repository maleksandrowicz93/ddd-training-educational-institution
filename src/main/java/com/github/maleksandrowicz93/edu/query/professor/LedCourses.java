package com.github.maleksandrowicz93.edu.query.professor;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;

import java.util.Collection;

public record LedCourses(
        Capacity maxCapacity,
        Collection<CourseId> all
) {

    public int count() {
        return all.size();
    }

    public boolean hasCapacity() {
        return maxCapacity.value() > all.size();
    }

    public boolean contain(CourseId courseId) {
        return all.contains(courseId);
    }
}
