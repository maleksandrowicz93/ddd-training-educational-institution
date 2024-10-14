package com.github.maleksandrowicz93.edu.application;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation.CourseCreationApplication;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership.CourseOvertakingApplication;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCreation.FacultyCreationApplication;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment.ProfessorEmploymentApplication;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.StudentId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.Vacancies;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.StudentEnrollmentAtFacultyApplication;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.StudentEnrollmentForCourseApplication;

import java.util.Optional;

public interface UseCases {

    Optional<FacultyId> createFaculty(FacultyCreationApplication application);

    Optional<ProfessorId> employProfessor(ProfessorEmploymentApplication application);

    void receiveEmploymentResignation(ProfessorId professorId, FacultyId facultyId);

    Optional<StudentId> enrollAtFaculty(StudentEnrollmentAtFacultyApplication application);

    void resignFromFacultyEnrollment(StudentId studentId, FacultyId facultyId);

    boolean enrollForCourse(StudentEnrollmentForCourseApplication application);

    void resignFromCourseEnrollment(StudentId studentId, CourseId courseId);

    Optional<CourseId> createCourse(CourseCreationApplication application);

    boolean restrictMaxVacanciesNumberFor(CourseId courseId, Vacancies vacancies);

    boolean closeCourse(CourseId courseId, FacultyId facultyId);

    void resignFromCourseLeadership(CourseId courseId, ProfessorId professorId);

    boolean overtakeCourse(CourseOvertakingApplication application);
}
