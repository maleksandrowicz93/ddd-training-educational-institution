package com.github.maleksandrowicz93.edu.query.faculty;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;

import java.util.function.Function;

enum FacultySummaryInitializer implements Function<FacultyId, FacultySummary> {

    INSTANCE;

    @Override
    public FacultySummary apply(FacultyId facultyId) {
        return FacultySummary.builder()
                             .facultyId(facultyId)
                             .build();
    }
}
