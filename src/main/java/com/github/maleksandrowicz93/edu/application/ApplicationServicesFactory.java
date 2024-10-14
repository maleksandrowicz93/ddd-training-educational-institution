package com.github.maleksandrowicz93.edu.application;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog.CourseCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog.FacultyCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorCatalog.ProfessorCatalog;

public record ApplicationServicesFactory(
        FacultyCatalog facultyCatalog,
        ProfessorCatalog professorCatalog,
        CourseCatalog courseCatalog
) implements Injector {
}
