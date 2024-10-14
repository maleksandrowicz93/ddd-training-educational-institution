package com.github.maleksandrowicz93.edu.query.faculty;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation.OpenCoursesSummary;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog.FacultyCatalogEntry;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment.ProfessorEmploymentSummary;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.FacultyEnrollmentSummary;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.stream.Collector;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
@FieldDefaults(level = PRIVATE)
class FacultySummaryAssembler {

    FacultySummary facultySummary;

    static Collector<Object, FacultySummaryAssembler, FacultySummary> toFacultySummary(FacultyId facultyId) {
        return Collector.of(
                () -> FacultySummaryAssembler.init(facultyId),
                FacultySummaryAssembler::add,
                (_, b) -> b,
                FacultySummaryAssembler::create
        );
    }

    private static FacultySummaryAssembler init(FacultyId facultyId) {
        return new FacultySummaryAssembler(
                FacultySummaryInitializer.INSTANCE.apply(facultyId)
        );
    }

    private void add(Object o) {
        facultySummary = switch (o) {
            case FacultyCatalogEntry faculty -> AddFacultyCatalogEntry.COMPOSER.apply(facultySummary, faculty);
            case ProfessorEmploymentSummary faculty -> AddProfessorEmployment.COMPOSER.apply(facultySummary, faculty);
            case FacultyEnrollmentSummary faculty -> AddFacultyEnrollment.COMPOSER.apply(facultySummary, faculty);
            case OpenCoursesSummary faculty -> AddOpenCourses.COMPOSER.apply(facultySummary, faculty);
            default -> facultySummary;
        };
    }

    private FacultySummary create() {
        return facultySummary;
    }
}
