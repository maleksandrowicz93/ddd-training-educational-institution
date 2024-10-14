package com.github.maleksandrowicz93.edu.query.faculty;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.StudentId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.Vacancies;

import java.util.Collection;

public record StudentsEnrolledAt(
        Vacancies maxVacancies,
        Collection<StudentId> all
) {

    public int count() {
        return all.size();
    }

    public boolean hasVacancy() {
        return maxVacancies.count() > all.size();
    }

    public boolean contain(StudentId studentId) {
        return all.contains(studentId);
    }
}
