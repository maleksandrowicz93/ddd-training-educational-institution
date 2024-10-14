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

public interface Queries {

    FacultySummary getFacultySummary(FacultyId facultyId);

    EmployedProfessors professorsEmployedAt(FacultyId facultyId);

    StudentsEnrolledAt studentsEnrolledAt(FacultyId facultyId);

    OpenCourses openCoursesAt(FacultyId facultyId);

    ProfessorSummary getProfessorSummary(ProfessorId professorId);

    LedCourses ledCoursesBy(ProfessorId professorId);

    Optional<LedCourses> findLedCoursesBy(ProfessorId professorId);

    CourseSummary getCourseSummary(CourseId courseId);

    CourseLeadership leadershipFor(CourseId courseId);

    Optional<CourseLeadership> findLeadershipFor(CourseId courseId);

    StudentsEnrolledFor studentsEnrolledFor(CourseId courseId);

    Optional<StudentsEnrolledFor> findStudentsEnrolledFor(CourseId courseId);
}
