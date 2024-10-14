package com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.Item;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.StudentId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

import static com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.ItemType.STUDENT;
import static com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.UnitType.COURSE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
class StudentInventory {

    EducationalInstitutionInventoryReadModel inventoryReadModel;
    EducationalInstitutionInventoryFacade inventoryFacade;

    Optional<StudentId> addToFaculty(FacultyId facultyId) {
        var studentEnrollmentAtFaculty = StudentsEnrolledForFaculty.FACTORY.apply(facultyId);
        return inventoryFacade.addItemToInventoryOfType(studentEnrollmentAtFaculty, StudentId::new);
    }

    public void removeFromFaculty(StudentId studentId, FacultyId facultyId) {
        resignFromAllCoursesEnrollments(studentId);
        var studentEnrollmentAtFaculty = StudentsEnrolledForFaculty.FACTORY.apply(facultyId);
        inventoryFacade.removeItem(studentId, studentEnrollmentAtFaculty);
    }

    boolean addToCourse(StudentId studentId, CourseId courseId) {
        var studentEnrollmentForCourse = StudentsEnrolledForCourse.FACTORY.apply(courseId);
        return inventoryFacade.addItemToInventoryOfType(studentEnrollmentForCourse, studentId);
    }

    public void removeFromCourse(StudentId studentId, CourseId courseId) {
        var studentEnrollmentForCourse = StudentsEnrolledForCourse.FACTORY.apply(courseId);
        inventoryFacade.removeItem(studentId, studentEnrollmentForCourse);
    }

    private void resignFromAllCoursesEnrollments(StudentId studentId) {
        inventoryReadModel.findAllUnitsBy(
                                  COURSE,
                                  new Item(studentId, STUDENT),
                                  CourseId::new
                          )
                          .forEach(courseId -> removeFromCourse(studentId, courseId));
    }
}
