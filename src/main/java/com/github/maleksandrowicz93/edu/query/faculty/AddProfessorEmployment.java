package com.github.maleksandrowicz93.edu.query.faculty;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment.ProfessorEmploymentSummary;

enum AddProfessorEmployment implements FacultySummaryComposer<ProfessorEmploymentSummary> {

    COMPOSER {
        @Override
        public FacultySummary apply(FacultySummary facultySummary, ProfessorEmploymentSummary employmentSummary) {
            return facultySummary.toBuilder()
                                 .employedProfessors(
                                         new EmployedProfessors(
                                                 employmentSummary.maxVacancies(),
                                                 employmentSummary.employed()
                                         )
                                 )
                                 .build();
        }
    }
}
