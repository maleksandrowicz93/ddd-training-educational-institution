package com.github.maleksandrowicz93.edu.query.course;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorCatalog.ProfessorCatalogEntry;

import java.util.Optional;
import java.util.function.Function;

enum CourseLeadershipFactory implements Function<Optional<ProfessorCatalogEntry>, CourseLeadership> {

    INSTANCE {
        @Override
        public CourseLeadership apply(Optional<ProfessorCatalogEntry> maybeProfessor) {
            return maybeProfessor
                    .map(Professor::from)
                    .map(CourseLeadership::takenBy)
                    .orElse(CourseLeadership.FREE);
        }
    }
}
