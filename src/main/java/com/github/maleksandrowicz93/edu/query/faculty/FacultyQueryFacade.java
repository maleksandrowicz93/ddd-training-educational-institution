package com.github.maleksandrowicz93.edu.query.faculty;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation.CourseCreationReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog.FacultyCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog.FacultyCatalogEntry;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment.ProfessorEmploymentReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.StudentEnrollmentReadModel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;
import java.util.stream.Stream;

import static com.github.maleksandrowicz93.edu.query.faculty.FacultySummaryAssembler.toFacultySummary;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class FacultyQueryFacade {

    FacultyCatalog facultyCatalog;
    ProfessorEmploymentReadModel professorEmploymentReadModel;
    StudentEnrollmentReadModel studentEnrollmentReadModel;
    CourseCreationReadModel courseCreationReadModel;

    public Optional<FacultySummary> findFacultySummary(FacultyId facultyId) {
        return facultyCatalog.findById(facultyId)
                             .map(this::getFacultySummaryFor);
    }

    public FacultySummary getFacultySummary(FacultyId facultyId) {
        return findFacultySummary(facultyId).orElseThrow();
    }

    private FacultySummary getFacultySummaryFor(FacultyCatalogEntry facultyCatalogEntry) {
        var facultyId = facultyCatalogEntry.id();
        var employmentSummary = professorEmploymentReadModel.getEmploymentSummaryAt(facultyId);
        var enrollmentSummary = studentEnrollmentReadModel.getEnrollmentSummaryAt(facultyId);
        var coursesSummary = courseCreationReadModel.getCoursesSummaryAt(facultyId);
        return Stream.of(facultyCatalogEntry, employmentSummary, enrollmentSummary, coursesSummary)
                     .collect(toFacultySummary(facultyId));
    }
}
