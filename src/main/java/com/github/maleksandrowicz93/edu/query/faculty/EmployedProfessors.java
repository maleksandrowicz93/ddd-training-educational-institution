package com.github.maleksandrowicz93.edu.query.faculty;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.Vacancies;

import java.util.Collection;

public record EmployedProfessors(
        Vacancies maxVacancies,
        Collection<ProfessorId> all
) {

    public int count() {
        return all.size();
    }

    public boolean hasVacancy() {
        return maxVacancies.count() > all.size();
    }

    public boolean contain(ProfessorId professorId) {
        return all.contains(professorId);
    }
}
