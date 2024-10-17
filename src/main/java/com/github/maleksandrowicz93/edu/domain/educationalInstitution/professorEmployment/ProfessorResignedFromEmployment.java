package com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment;

import com.github.maleksandrowicz93.edu.common.infra.Notification;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;

public record ProfessorResignedFromEmployment(
        ProfessorId professorId,
        FacultyId facultyId
) implements Notification {
}
