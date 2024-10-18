package com.github.maleksandrowicz93.edu.query.course;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog.CourseCatalogEntry;

enum AddCourseCatalogEntry implements CourseSummaryComposer<CourseCatalogEntry> {

    COMPOSER {
        @Override
        public CourseSummary apply(CourseSummary courseSummary, CourseCatalogEntry catalogEntry) {
            return courseSummary.toBuilder()
                                .courseName(catalogEntry.name())
                                .fieldsOfStudies(catalogEntry.fieldsOfStudies())
                                .build();
        }
    }
}
