package com.github.maleksandrowicz93.edu.domain.educationalInstitution.specyfic;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;

public record CourseOvertakingApplication(
        CourseId courseId,
        ProfessorId professorId
) {
}
