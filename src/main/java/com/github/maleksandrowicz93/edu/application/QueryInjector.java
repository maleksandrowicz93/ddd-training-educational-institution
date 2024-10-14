package com.github.maleksandrowicz93.edu.application;

import com.github.maleksandrowicz93.edu.query.course.CourseQueryFacade;
import com.github.maleksandrowicz93.edu.query.faculty.FacultyQueryFacade;
import com.github.maleksandrowicz93.edu.query.professor.ProfessorQueryFacade;

public interface QueryInjector {

    FacultyQueryFacade facultyQueryFacade();

    ProfessorQueryFacade professorQueryFacade();

    CourseQueryFacade courseQueryFacade();
}
