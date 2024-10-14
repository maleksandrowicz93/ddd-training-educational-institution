package com.github.maleksandrowicz93.edu.domain.educationalInstitution.specyfic;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FieldsOfStudies;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.PercentageScore;

import java.util.Collection;

import static com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FieldsOfStudies.toFieldsOfStudies;

public record ExamResults(
        Collection<ExamResult> all
) {

    public FieldsOfStudies fieldsOfStudies() {
        return all.stream()
                  .map(ExamResult::fieldOfStudyId)
                  .collect(toFieldsOfStudies());
    }

    public PercentageScore lowestScore() {
        return all.stream()
                  .map(ExamResult::percentageScore)
                  .min(PercentageScore.comparator())
                  .orElse(PercentageScore.ZERO);
    }
}
