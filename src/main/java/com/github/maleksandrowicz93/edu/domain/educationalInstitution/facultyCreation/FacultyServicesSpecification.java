package com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCreation;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.Vacancies;

record FacultyServicesSpecification(
        FacultyId facultyId,
        Vacancies professorVacancies,
        Vacancies studentVacancies,
        Capacity coursesCapacity
) {
}
