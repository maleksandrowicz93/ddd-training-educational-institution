package com.github.maleksandrowicz93.edu.query.faculty;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FieldOfStudy;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FieldsOfStudies;
import lombok.Builder;

@Builder(toBuilder = true)
public record FacultySummary(
        FacultyId facultyId,
        String facultyName,
        FieldOfStudy mainFieldOfStudy,
        FieldsOfStudies secondaryFieldsOfStudies,
        EmployedProfessors employedProfessors,
        StudentsEnrolledAt studentsEnrolledAt,
        OpenCourses openCourses
) {
}
