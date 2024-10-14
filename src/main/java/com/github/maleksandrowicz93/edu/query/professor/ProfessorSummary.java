package com.github.maleksandrowicz93.edu.query.professor;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FieldsOfStudies;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;
import lombok.Builder;

@Builder
public record ProfessorSummary(
        ProfessorId professorId,
        String professorName,
        FieldsOfStudies fieldsOfStudies,
        LedCourses ledCourses
) {
}
