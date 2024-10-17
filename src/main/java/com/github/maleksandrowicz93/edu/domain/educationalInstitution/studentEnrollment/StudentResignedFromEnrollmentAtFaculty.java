package com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment;

import com.github.maleksandrowicz93.edu.common.infra.Notification;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.StudentId;

public record StudentResignedFromEnrollmentAtFaculty(
        StudentId studentId,
        FacultyId facultyId
) implements Notification {
}
