package com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
import com.github.maleksandrowicz93.edu.common.rule.Rules;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership.CourseLeadershipFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog.FacultyCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorCatalog.ProfessorCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorCatalog.ProfessorCatalogEntry;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ProfessorEmploymentFacade {

    ProfessorEmploymentConfig config;
    Rules<ProfessorEmploymentContext> employmentRules;
    FacultyCatalog facultyCatalog;
    ProfessorCatalog professorCatalog;
    ProfessorEmployment professorEmployment;
    CourseLeadershipFacade courseLeadershipFacade;

    public Optional<ProfessorId> employProfessor(ProfessorEmploymentApplication application) {
        log.info("Employing professor with application {}", application);
        var facultyId = application.facultyId();
        var faculty = facultyCatalog.getById(facultyId);
        var context = new ProfessorEmploymentContext(application, faculty);
        var result = employmentRules.examine(context);
        if (result.isFailed()) {
            log.info(result.reason());
            return Optional.empty();
        }
        var professorCapacity = Capacity.of(config.maxCourseLeaderships());
        var maybeEmployed = professorEmployment.employNewAt(facultyId, professorCapacity);
        maybeEmployed.ifPresentOrElse(
                professorId -> professorCatalog.save(
                        new ProfessorCatalogEntry(
                                professorId, application.professorName(),
                                application.fieldsOfStudies()
                        )
                ),
                () -> log.info("Professor was not employed at faculty {}", facultyId)
        );
        return maybeEmployed;
    }

    public void resignFromEmployment(ProfessorId professorId, FacultyId facultyId) {
        log.info("Resigning from employment at faculty {} by professor {}...", facultyId, professorId);
        professorCatalog.deleteById(professorId);
        courseLeadershipFacade.resignFromAllCoursesLeaderships(professorId);
        professorEmployment.resign(professorId, facultyId);
    }
}
