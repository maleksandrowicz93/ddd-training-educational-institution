package com.github.maleksandrowicz93.edu.application;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation.CourseCreationFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership.CourseLeadershipFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCreation.FacultyCreationFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment.ProfessorEmploymentFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.StudentEnrollmentFacade;

public interface FacadeInjector {

    FacultyCreationFacade facultyCreationFacade();

    ProfessorEmploymentFacade professorEmploymentFacade();

    StudentEnrollmentFacade studentEnrollmentFacade();

    CourseCreationFacade courseCreationFacade();

    CourseLeadershipFacade courseLeadershipFacade();
}
