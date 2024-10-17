package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.Vacancies;

public record RestrictingCourseVacationsContext(
        CourseId courseId,
        Vacancies vacancies
) {
}
