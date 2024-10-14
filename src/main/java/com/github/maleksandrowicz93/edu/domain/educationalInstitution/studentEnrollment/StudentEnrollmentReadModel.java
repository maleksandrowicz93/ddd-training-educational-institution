package com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.StudentId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class StudentEnrollmentReadModel {

    EducationalInstitutionInventoryReadModel inventoryReadModel;

    public Optional<FacultyEnrollmentSummary> findEnrollmentSummaryAt(FacultyId facultyId) {
        return inventoryReadModel.findSummaryByInventoryType(
                                         StudentsEnrolledForFaculty.FACTORY.apply(facultyId),
                                         StudentId::new
                                 )
                                 .map(FacultyEnrollmentSummary::from);
    }

    public FacultyEnrollmentSummary getEnrollmentSummaryAt(FacultyId facultyId) {
        return findEnrollmentSummaryAt(facultyId).orElseThrow();
    }

    public Optional<CourseEnrollmentSummary> findEnrollmentSummaryFor(CourseId courseId) {
        return inventoryReadModel.findSummaryByInventoryType(
                                         StudentsEnrolledForCourse.FACTORY.apply(courseId),
                                         StudentId::new
                                 )
                                 .map(CourseEnrollmentSummary::from);
    }

    public CourseEnrollmentSummary getEnrollmentSummaryFor(CourseId courseId) {
        return findEnrollmentSummaryFor(courseId).orElseThrow();
    }
}
