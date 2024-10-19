package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CourseCreationReadModel {

    InventoryReadModel inventoryReadModel;

    public Optional<OpenCoursesSummary> findCoursesSummaryAt(FacultyId facultyId) {
        return inventoryReadModel.findSummaryByInventoryType(
                                         OpenCourses.FACTORY.apply(facultyId),
                                         CourseId::new
                                 )
                                 .map(OpenCoursesSummary::from);
    }

    public OpenCoursesSummary getCoursesSummaryAt(FacultyId facultyId) {
        return findCoursesSummaryAt(facultyId).orElseThrow();
    }
}
