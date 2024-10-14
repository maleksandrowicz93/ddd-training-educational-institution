package com.github.maleksandrowicz93.edu.query.course;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FieldsOfStudies;
import lombok.Builder;

@Builder(toBuilder = true)
public record CourseSummary(
        CourseId courseId,
        String courseName,
        FieldsOfStudies fieldsOfStudies,
        CourseLeadership courseLeadership,
        StudentsEnrolledFor studentsEnrolledFor
) {
}
