package com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.StudentId;

public record StudentEnrollmentForCourseApplication(
        StudentId studentId,
        CourseId courseId
) {
}
