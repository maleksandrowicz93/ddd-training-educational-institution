package com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog.FacultyCatalogEntry;

record StudentEnrollmentAtFacultyContext(
        StudentEnrollmentAtFacultyApplication application,
        FacultyCatalogEntry faculty
) {
}
