package com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog.FacultyCatalogEntry;

record ProfessorEmploymentContext(
        ProfessorEmploymentApplication application,
        FacultyCatalogEntry faculty
) {
}
