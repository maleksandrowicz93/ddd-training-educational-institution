package com.github.maleksandrowicz93.edu.query.faculty;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.FacultyEnrollmentSummary;

enum AddFacultyEnrollment implements FacultySummaryComposer<FacultyEnrollmentSummary> {

    COMPOSER;

    @Override
    public FacultySummary apply(FacultySummary facultySummary, FacultyEnrollmentSummary enrollmentSummary) {
        return facultySummary.toBuilder()
                             .studentsEnrolledAt(
                                     new StudentsEnrolledAt(
                                             enrollmentSummary.maxVacancies(),
                                             enrollmentSummary.enrolled()
                                     )
                             )
                             .build();
    }
}
