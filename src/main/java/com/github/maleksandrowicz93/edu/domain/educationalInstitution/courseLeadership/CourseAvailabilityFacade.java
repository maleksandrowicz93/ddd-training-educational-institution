package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership;

import com.github.maleksandrowicz93.edu.domain.availability.AvailabilityFacade;
import com.github.maleksandrowicz93.edu.domain.availability.OwnerId;
import com.github.maleksandrowicz93.edu.domain.availability.ResourceId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
class CourseAvailabilityFacade {

    AvailabilityFacade availabilityFacade;

    void createTakenCourse(CourseId courseId, ProfessorId blockedBy) {
        var resourceId = new ResourceId(courseId.value());
        var ownerId = new OwnerId(blockedBy.value());
        availabilityFacade.createBlockedAvailabilityUnitFor(resourceId, ownerId);
    }

    void deleteCourseTakingAvailability(CourseId courseId) {
        var resourceId = new ResourceId(courseId.value());
        availabilityFacade.deleteAvailabilityUnitByResourceId(resourceId);
    }

    boolean takeCourse(CourseId courseId, ProfessorId professorId) {
        var resourceId = new ResourceId(courseId.value());
        var ownerId = new OwnerId(professorId.value());
        return availabilityFacade.block(resourceId, ownerId);
    }

    boolean releaseCourse(CourseId courseId, ProfessorId professorId) {
        var resourceId = new ResourceId(courseId.value());
        var ownerId = new OwnerId(professorId.value());
        return availabilityFacade.release(resourceId, ownerId);
    }
}
