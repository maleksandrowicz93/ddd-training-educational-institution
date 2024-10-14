package com.github.maleksandrowicz93.edu.domain.educationalInstitution.specyfic;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FieldsOfStudies;

public record StudentEnrollmentAtFacultyApplication(
        FacultyId facultyId,
        ExamResult mainExamResult,
        ExamResults secondaryExamResults
) {

    public FieldsOfStudies examinedFieldsOfStudies() {
        return secondaryExamResults.fieldsOfStudies()
                                   .add(mainExamResult.fieldOfStudyId());
    }
}
