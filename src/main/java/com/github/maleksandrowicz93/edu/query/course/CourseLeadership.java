package com.github.maleksandrowicz93.edu.query.course;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;

public record CourseLeadership(
        Professor takenBy
) {

    static final CourseLeadership FREE = new CourseLeadership(null);

    public static CourseLeadership takenBy(Professor professor) {
        return new CourseLeadership(professor);
    }

    public boolean isAvailable() {
        return takenBy == null;
    }

    public boolean isTaken() {
        return !isAvailable();
    }

    public boolean isTakenBy(ProfessorId professorId) {
        return isTaken() && takenBy.professorId().equals(professorId);
    }
}
