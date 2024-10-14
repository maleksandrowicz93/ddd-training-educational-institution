package com.github.maleksandrowicz93.edu.application;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;
import com.github.maleksandrowicz93.edu.query.course.CourseLeadership;
import com.github.maleksandrowicz93.edu.query.course.CourseSummary;
import com.github.maleksandrowicz93.edu.query.course.StudentsEnrolledFor;
import com.github.maleksandrowicz93.edu.query.faculty.EmployedProfessors;
import com.github.maleksandrowicz93.edu.query.faculty.FacultySummary;
import com.github.maleksandrowicz93.edu.query.faculty.OpenCourses;
import com.github.maleksandrowicz93.edu.query.faculty.StudentsEnrolledAt;
import com.github.maleksandrowicz93.edu.query.professor.LedCourses;
import com.github.maleksandrowicz93.edu.query.professor.ProfessorSummary;

import java.util.Optional;

public class QueriesFacade implements Queries {

    public QueriesFacade(QueryInjector injector) {
    }

    @Override
    public FacultySummary getFacultySummary(FacultyId facultyId) {
        return null;
    }

    @Override
    public EmployedProfessors professorsEmployedAt(FacultyId facultyId) {
        return getFacultySummary(facultyId).employedProfessors();
    }

    @Override
    public StudentsEnrolledAt studentsEnrolledAt(FacultyId facultyId) {
        return getFacultySummary(facultyId).studentsEnrolledAt();
    }

    @Override
    public OpenCourses openCoursesAt(FacultyId facultyId) {
        return getFacultySummary(facultyId).openCourses();
    }

    @Override
    public ProfessorSummary getProfessorSummary(ProfessorId professorId) {
        return null;
    }

    @Override
    public LedCourses ledCoursesBy(ProfessorId professorId) {
        return getProfessorSummary(professorId).ledCourses();
    }

    @Override
    public Optional<LedCourses> findLedCoursesBy(ProfessorId professorId) {
        return null;
    }

    @Override
    public CourseSummary getCourseSummary(CourseId courseId) {
        return null;
    }

    @Override
    public CourseLeadership leadershipFor(CourseId courseId) {
        return getCourseSummary(courseId).courseLeadership();
    }

    @Override
    public Optional<CourseLeadership> findLeadershipFor(CourseId courseId) {
        return Optional.empty();
    }

    @Override
    public StudentsEnrolledFor studentsEnrolledFor(CourseId courseId) {
        return getCourseSummary(courseId).studentsEnrolledFor();
    }

    @Override
    public Optional<StudentsEnrolledFor> findStudentsEnrolledFor(CourseId courseId) {
        return Optional.empty();
    }
}
