package com.github.maleksandrowicz93.edu.query.professor;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership.CourseLeadershipReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorCatalog.ProfessorCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorCatalog.ProfessorCatalogEntry;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ProfessorQueryFacade {

    ProfessorCatalog professorCatalog;
    CourseLeadershipReadModel courseLeadershipReadModel;

    public Optional<ProfessorSummary> findProfessorSummary(ProfessorId professorId) {
        return professorCatalog.findById(professorId)
                               .map(this::getProfessorSummaryFor);
    }

    public ProfessorSummary getProfessorSummary(ProfessorId professorId) {
        return findProfessorSummary(professorId).orElseThrow();
    }

    private ProfessorSummary getProfessorSummaryFor(ProfessorCatalogEntry professorCatalogEntry) {
        var professorId = professorCatalogEntry.id();
        var leadershipSummary = courseLeadershipReadModel.getSummaryOfCoursesLedBy(professorId);
        return ProfessorSummary.builder()
                               .professorId(professorId)
                               .professorName(professorCatalogEntry.name())
                               .fieldsOfStudies(professorCatalogEntry.fieldsOfStudies())
                               .ledCourses(
                                       new LedCourses(
                                               leadershipSummary.capacity(),
                                               leadershipSummary.leaderships()
                                       )
                               )
                               .build();
    }
}
