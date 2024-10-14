package com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment;

import com.github.maleksandrowicz93.edu.common.rule.Rules;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog.FacultyCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.StudentId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class StudentEnrollmentFacade {

    Rules<StudentEnrollmentAtFacultyContext> enrollmentAtFacultyRules;
    FacultyCatalog facultyCatalog;
    StudentInventory studentInventory;

    public Optional<StudentId> enrollAtFaculty(StudentEnrollmentAtFacultyApplication application) {
        log.info("Enrolling student at faculty with application {}...", application);
        var facultyId = application.facultyId();
        var faculty = facultyCatalog.getById(facultyId);
        var context = new StudentEnrollmentAtFacultyContext(application, faculty);
        var result = enrollmentAtFacultyRules.examine(context);
        if (result.isFailed()) {
            log.info(result.reason());
            return Optional.empty();
        }
        return studentInventory.addToFaculty(facultyId);
    }

    public void resignFromFacultyEnrollment(StudentId studentId, FacultyId facultyId) {
        log.info("Resigning from enrollment at faculty {} by student {}", facultyId, studentId);
        studentInventory.removeFromFaculty(studentId, facultyId);
    }

    public boolean enrollForCourse(StudentEnrollmentForCourseApplication application) {
        log.info("Enrolling student fpr course with application {}...", application);
        return studentInventory.addToCourse(application.studentId(), application.courseId());
    }

    public void resignFromCourseEnrollment(StudentId studentId, CourseId courseId) {
        log.info("Resigning from enrollment fpr course {} by student {}", courseId, studentId);
        studentInventory.removeFromCourse(studentId, courseId);
    }
}
