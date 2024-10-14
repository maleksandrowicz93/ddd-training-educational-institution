package com.github.maleksandrowicz93.edu.application;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation.CourseCreationApplication;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation.CourseCreationFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership.CourseLeadershipFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership.CourseOvertakingApplication;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCreation.FacultyCreationApplication;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCreation.FacultyCreationFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment.ProfessorEmploymentApplication;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment.ProfessorEmploymentFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.StudentId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.Vacancies;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.StudentEnrollmentAtFacultyApplication;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.StudentEnrollmentFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.StudentEnrollmentForCourseApplication;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UseCasesFacade implements UseCases {

    FacultyCreationFacade facultyCreationFacade;
    ProfessorEmploymentFacade professorEmploymentFacade;
    StudentEnrollmentFacade studentEnrollmentFacade;
    CourseCreationFacade courseCreationFacade;
    CourseLeadershipFacade courseLeadershipFacade;

    public UseCasesFacade(FacadeInjector injector) {
        facultyCreationFacade = injector.facultyCreationFacade();
        professorEmploymentFacade = injector.professorEmploymentFacade();
        studentEnrollmentFacade = injector.studentEnrollmentFacade();
        courseCreationFacade = injector.courseCreationFacade();
        courseLeadershipFacade = injector.courseLeadershipFacade();
    }

    @Override
    public Optional<FacultyId> createFaculty(FacultyCreationApplication application) {
        return facultyCreationFacade.createFaculty(application);
    }

    @Override
    public Optional<ProfessorId> employProfessor(ProfessorEmploymentApplication application) {
        return professorEmploymentFacade.employProfessor(application);
    }

    @Override
    public void receiveEmploymentResignation(ProfessorId professorId, FacultyId facultyId) {
        professorEmploymentFacade.receiveEmploymentResignation(professorId, facultyId);
    }

    @Override
    public Optional<StudentId> enrollAtFaculty(StudentEnrollmentAtFacultyApplication application) {
        return studentEnrollmentFacade.enrollAtFaculty(application);
    }

    @Override
    public void resignFromFacultyEnrollment(StudentId studentId, FacultyId facultyId) {
        studentEnrollmentFacade.resignFromFacultyEnrollment(studentId, facultyId);
    }

    @Override
    public boolean enrollForCourse(StudentEnrollmentForCourseApplication application) {
        return studentEnrollmentFacade.enrollForCourse(application);
    }

    @Override
    public void resignFromCourseEnrollment(StudentId studentId, CourseId courseId) {
        studentEnrollmentFacade.resignFromCourseEnrollment(studentId, courseId);
    }

    @Override
    public Optional<CourseId> createCourse(CourseCreationApplication application) {
        return courseCreationFacade.createCourse(application);
    }

    @Override
    public boolean restrictMaxVacanciesNumberFor(CourseId courseId, Vacancies vacancies) {
        return courseCreationFacade.restrictMaxVacanciesNumberFor(courseId, vacancies);
    }

    @Override
    public boolean closeCourse(CourseId courseId, FacultyId facultyId) {
        return courseCreationFacade.closeCourse(courseId, facultyId);
    }

    @Override
    public void resignFromCourseLeadership(CourseId courseId, ProfessorId professorId) {
        courseLeadershipFacade.resignFromCourseLeadership(courseId, professorId);
    }

    @Override
    public boolean overtakeCourse(CourseOvertakingApplication application) {
        return courseLeadershipFacade.overtakeCourse(application);
    }
}
