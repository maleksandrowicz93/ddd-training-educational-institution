package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership;

import com.github.maleksandrowicz93.edu.common.infra.Notification;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;

public record ProfessorResignedFromCourseLeadership(
        ProfessorId professorId,
        CourseId courseId
) implements Notification {
}
