package com.github.maleksandrowicz93.edu.query.faculty;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog.FacultyCatalogEntry;

enum AddFacultyCatalogEntry implements FacultySummaryComposer<FacultyCatalogEntry> {

    COMPOSER {
        @Override
        public FacultySummary apply(FacultySummary facultySummary, FacultyCatalogEntry catalogEntry) {
            return facultySummary.toBuilder()
                                 .facultyName(catalogEntry.name())
                                 .mainFieldOfStudy(catalogEntry.mainFieldOfStudy())
                                 .secondaryFieldsOfStudies(catalogEntry.secondaryFieldOfStudies())
                                 .build();
        }
    }
}
