package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.PercentageScore;

public record CourseClosingConfig(
        PercentageScore minEnrolledStudentsList
) {
}
