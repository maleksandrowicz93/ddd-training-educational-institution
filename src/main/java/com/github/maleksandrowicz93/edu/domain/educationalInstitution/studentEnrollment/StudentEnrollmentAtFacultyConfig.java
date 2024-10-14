package com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.PercentageScore;

public record StudentEnrollmentAtFacultyConfig(
        PercentageScore minMainExamScore,
        PercentageScore minSecondaryExamScore
) {
}
