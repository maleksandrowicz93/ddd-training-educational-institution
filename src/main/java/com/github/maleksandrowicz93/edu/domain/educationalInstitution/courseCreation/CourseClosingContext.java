package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog.CourseCatalogEntry;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.CourseEnrollmentSummary;

record CourseClosingContext(
        CourseCatalogEntry course,
        CourseEnrollmentSummary enrollmentsSummary
) {
}
