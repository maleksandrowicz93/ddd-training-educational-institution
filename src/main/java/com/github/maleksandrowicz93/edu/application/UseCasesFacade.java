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

public class UseCasesFacade implements UseCases {

    public UseCasesFacade(FacadeInjector injector) {
    }

    @Override
    public Optional<FacultyId> createFaculty(FacultyCreationApplication application) {
        return Optional.empty();
    }

    @Override
    public Optional<ProfessorId> employProfessor(ProfessorEmploymentApplication application) {
        return Optional.empty();
    }

    @Override
    public void receiveEmploymentResignation(ProfessorId professorId, FacultyId facultyId) {
    }

    @Override
    public Optional<StudentId> enrollAtFaculty(StudentEnrollmentAtFacultyApplication application) {
        return Optional.empty();
    }

    @Override
    public void resignFromFacultyEnrollment(StudentId studentId, FacultyId facultyId) {
    }

    @Override
    public boolean enrollForCourse(StudentEnrollmentForCourseApplication application) {
        return false;
    }

    @Override
    public void resignFromCourseEnrollment(StudentId studentId, CourseId courseId) {
    }

    @Override
    public Optional<CourseId> createCourse(CourseCreationApplication application) {
        return Optional.empty();
    }

    @Override
    public boolean restrictMaxVacanciesNumberFor(CourseId courseId, Vacancies vacancies) {
        return false;
    }

    @Override
    public boolean closeCourse(CourseId courseId, FacultyId facultyId) {
        return false;
    }

    @Override
    public void resignFromCourseLeadership(CourseId courseId, ProfessorId professorId) {
    }

    @Override
    public boolean overtakeCourse(CourseOvertakingApplication application) {
        return false;
    }
}
