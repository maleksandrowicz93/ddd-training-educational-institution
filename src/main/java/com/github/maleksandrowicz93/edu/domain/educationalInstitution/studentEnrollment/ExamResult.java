package com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FieldOfStudy;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.PercentageScore;

public record ExamResult(
        FieldOfStudy fieldOfStudyId,
        PercentageScore percentageScore
) {
}
