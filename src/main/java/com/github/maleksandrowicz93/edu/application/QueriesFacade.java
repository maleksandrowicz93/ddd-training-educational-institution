package com.github.maleksandrowicz93.edu.application;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;
import com.github.maleksandrowicz93.edu.query.course.CourseLeadership;
import com.github.maleksandrowicz93.edu.query.course.CourseQueryFacade;
import com.github.maleksandrowicz93.edu.query.course.CourseSummary;
import com.github.maleksandrowicz93.edu.query.course.StudentsEnrolledFor;
import com.github.maleksandrowicz93.edu.query.faculty.EmployedProfessors;
import com.github.maleksandrowicz93.edu.query.faculty.FacultyQueryFacade;
import com.github.maleksandrowicz93.edu.query.faculty.FacultySummary;
import com.github.maleksandrowicz93.edu.query.faculty.OpenCourses;
import com.github.maleksandrowicz93.edu.query.faculty.StudentsEnrolledAt;
import com.github.maleksandrowicz93.edu.query.professor.LedCourses;
import com.github.maleksandrowicz93.edu.query.professor.ProfessorQueryFacade;
import com.github.maleksandrowicz93.edu.query.professor.ProfessorSummary;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class QueriesFacade implements Queries {

    FacultyQueryFacade facultyQueryFacade;
    ProfessorQueryFacade professorQueryFacade;
    CourseQueryFacade courseQueryFacade;

    public QueriesFacade(QueryInjector injector) {
        facultyQueryFacade = injector.facultyQueryFacade();
        professorQueryFacade = injector.professorQueryFacade();
        courseQueryFacade = injector.courseQueryFacade();
    }

    @Override
    public FacultySummary getFacultySummary(FacultyId facultyId) {
        return facultyQueryFacade.getFacultySummary(facultyId);
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
        return professorQueryFacade.getProfessorSummary(professorId);
    }

    @Override
    public LedCourses ledCoursesBy(ProfessorId professorId) {
        return getProfessorSummary(professorId).ledCourses();
    }

    @Override
    public Optional<LedCourses> findLedCoursesBy(ProfessorId professorId) {
        return professorQueryFacade.findProfessorSummary(professorId)
                                   .map(ProfessorSummary::ledCourses);
    }

    @Override
    public CourseSummary getCourseSummary(CourseId courseId) {
        return courseQueryFacade.getCourseSummary(courseId);
    }

    @Override
    public CourseLeadership leadershipFor(CourseId courseId) {
        return getCourseSummary(courseId).courseLeadership();
    }

    @Override
    public Optional<CourseLeadership> findLeadershipFor(CourseId courseId) {
        return courseQueryFacade.findCourseSummary(courseId)
                                .map(CourseSummary::courseLeadership);
    }

    @Override
    public StudentsEnrolledFor studentsEnrolledFor(CourseId courseId) {
        return getCourseSummary(courseId).studentsEnrolledFor();
    }

    @Override
    public Optional<StudentsEnrolledFor> findStudentsEnrolledFor(CourseId courseId) {
        return courseQueryFacade.findCourseSummary(courseId)
                                .map(CourseSummary::studentsEnrolledFor);
    }
}
