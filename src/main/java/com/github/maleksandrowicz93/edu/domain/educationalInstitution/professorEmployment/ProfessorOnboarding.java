package com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;

record ProfessorOnboarding(
        ProfessorId professorId,
        Capacity maxLedCoursesLimit
) {
}
