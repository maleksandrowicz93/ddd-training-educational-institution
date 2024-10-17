package com.github.maleksandrowicz93.edu.scenarios

import com.github.maleksandrowicz93.edu.application.ApplicationConfig
import com.github.maleksandrowicz93.edu.application.QueriesFacade
import com.github.maleksandrowicz93.edu.application.UseCasesFacade
import com.github.maleksandrowicz93.edu.common.infra.NotificationPublisher
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation.CourseClosed
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation.CourseClosingConfig
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation.CourseCreationByProfessorApplication
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation.CourseCreationConfig
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership.CourseOvertakingApplication
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership.ProfessorResignedFromCourseLeadership
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCreation.FacultyCreationApplication
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCreation.FacultyCreationConfig
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment.ProfessorEmploymentApplication
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment.ProfessorEmploymentConfig
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment.ProfessorResignedFromEmployment
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FieldOfStudy
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FieldsOfStudies
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.PercentageScore
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.Vacancies
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.ExamResult
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.ExamResults
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.StudentEnrollmentAtFacultyApplication
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.StudentEnrollmentAtFacultyConfig
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.StudentEnrollmentForCourseApplication
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.StudentResignedFromEnrollmentAtFaculty
import spock.lang.Specification

class ApplicationSpec extends Specification {

    static def COMPUTER_SCIENCE = new FieldOfStudy("Computer Science")
    static def PROGRAMMING = new FieldOfStudy("Programming")
    static def CYBER_SECURITY = new FieldOfStudy("Cyber Security")
    static def ALL_COURSES = new FieldsOfStudies(Set.of(COMPUTER_SCIENCE, PROGRAMMING, CYBER_SECURITY))
    static def PASSED_COMPUTER_SCIENCE = passedExam(COMPUTER_SCIENCE)
    static def PASSED_PROGRAMMING = passedExam(PROGRAMMING)
    static def PASSED_CYBER_SECURITY = passedExam(CYBER_SECURITY)

    //config
    def courseCreationConfig = new CourseCreationConfig(1)
    def courseClosingConfig = new CourseClosingConfig(new PercentageScore(30))
    def facultyCreationConfig = new FacultyCreationConfig(2)
    def professorEmploymentConfig = new ProfessorEmploymentConfig(2, 2, 2)
    def studentEnrollmentAtFacultyConfig = new StudentEnrollmentAtFacultyConfig(
            new PercentageScore(50),
            new PercentageScore(50)
    )
    def applicationConfig = new ApplicationConfig(
            courseCreationConfig,
            courseClosingConfig,
            facultyCreationConfig,
            professorEmploymentConfig,
            studentEnrollmentAtFacultyConfig
    )

    //application services factory
    def notificationPublisher = Mock(NotificationPublisher)
    def factory = new InjectorFactoryForTests(applicationConfig, notificationPublisher)
    def injector = factory.createInjector()
    def useCases = new UseCasesFacade(injector)
    def queries = new QueriesFacade(injector)

    def facultyCatalog = injector.facultyCatalog()
    def professorCatalog = injector.professorCatalog()
    def courseCatalog = injector.courseCatalog()

    //fixture
    FacultyId itFaculty

    def setup() {
        when: "IT faculty is created"
            itFaculty = useCases.createFaculty(
                    new FacultyCreationApplication(
                            "IT_FACULTY",
                            COMPUTER_SCIENCE,
                            new FieldsOfStudies(Set.of(PROGRAMMING, CYBER_SECURITY)),
                            new Vacancies(2),
                            new Vacancies(4)
                    )
            ).orElseThrow()

        then: "is created successfully"
            with(facultyCatalog.getById(itFaculty)) {
                name() == "IT_FACULTY"
                mainFieldOfStudy() == COMPUTER_SCIENCE
                secondaryFieldOfStudies() == new FieldsOfStudies(Set.of(PROGRAMMING, CYBER_SECURITY))
            }
    }

    def "A long time ago in an IT faculty far, far away...."() {
        given: "musk employment application"
            def muskEmploymentApplication = new ProfessorEmploymentApplication(
                    itFaculty,
                    "musk",
                    3,
                    ALL_COURSES
            )
        and: "zuck employment application"
            def zuckEmploymentApplication = new ProfessorEmploymentApplication(
                    itFaculty,
                    "zuck",
                    3,
                    ALL_COURSES
            )
        and: "gates employment application"
            def gatesEmploymentApplication = new ProfessorEmploymentApplication(
                    itFaculty,
                    "gates",
                    3,
                    ALL_COURSES
            )
        and: "mati enrollment at faculty application"
            def matiEnrollmentAtFacultyApplication = new StudentEnrollmentAtFacultyApplication(
                    itFaculty,
                    PASSED_COMPUTER_SCIENCE,
                    new ExamResults(Set.of(
                            PASSED_PROGRAMMING,
                            PASSED_CYBER_SECURITY
                    ))
            )

        when: "musk applies for employment"
            def musk = useCases.employProfessor(muskEmploymentApplication)
                               .orElseThrow()
        then: "musk is employed"
            with(professorCatalog.getBtId(musk)) {
                id() == musk
                name() == muskEmploymentApplication.professorName()
                fieldsOfStudies() == muskEmploymentApplication.fieldsOfStudies()
            }

        when: "zuck applies for employment"
            def zuck = useCases.employProfessor(zuckEmploymentApplication)
                               .orElseThrow()
        then: "zuck is employed"
            with(professorCatalog.getBtId(zuck)) {
                id() == zuck
                name() == zuckEmploymentApplication.professorName()
                fieldsOfStudies() == zuckEmploymentApplication.fieldsOfStudies()
            }

        when: "gates applies for employment"
            def maybeEmployed = useCases.employProfessor(gatesEmploymentApplication)
        then: "gates is not employed because no vacancy at the faculty"
            maybeEmployed.isEmpty()
            professorCatalog.countAll() == 2
            with(queries.professorsEmployedAt(itFaculty)) {
                count() == 2
                !hasVacancy()
                contain(musk)
                contain(zuck)
            }

        when: "musk applies for java course creation"
            def javaCourseCreationApplication = new CourseCreationByProfessorApplication(
                    itFaculty,
                    musk,
                    "java",
                    new FieldsOfStudies(Set.of(PROGRAMMING)),
                    new Vacancies(3)
            )
            def javaCourse = useCases.createCourseByProfessor(javaCourseCreationApplication)
                                     .orElseThrow()
        then: "java course is created by musk"
            with(courseCatalog.getById(javaCourse)) {
                name() == javaCourseCreationApplication.courseName()
                fieldsOfStudies() == javaCourseCreationApplication.fieldsOfStudies()
            }
            with(queries.openCoursesAt(itFaculty)) {
                count() == 1
                hasCapacity()
                contain(javaCourse)
            }
            with(queries.ledCoursesBy(musk)) {
                count() == 1
                hasCapacity()
                contain(javaCourse)
            }
            with(queries.leadershipFor(javaCourse)) {
                isTakenBy(musk)
            }

        when: "mati applies for enrollment at faculty"
            def mati = useCases.enrollAtFaculty(matiEnrollmentAtFacultyApplication)
                               .orElseThrow()
        then: "mati is enrolled at faculty"
            with(queries.studentsEnrolledAt(itFaculty)) {
                count() == 1
                hasVacancy()
                contain(mati)
            }

        when: "mati applies for enrollment for java course"
            def matiEnrollmentForJavaApplication = new StudentEnrollmentForCourseApplication(mati, javaCourse)
            def matiEnrolledForJava = useCases.enrollForCourse(matiEnrollmentForJavaApplication)
        then: "mati is enrolled for java course"
            matiEnrolledForJava
            with(queries.studentsEnrolledFor(javaCourse)) {
                count() == 1
                hasVacancy()
                contain(mati)
            }

        when: "mati resigns from java course enrollment"
            useCases.resignFromEnrollmentForCourse(mati, javaCourse)
        then: "resignation is accepted"
            with(queries.studentsEnrolledFor(javaCourse)) {
                count() == 0
            }

        when: "mati applies for enrollment for java course again"
            useCases.enrollForCourse(matiEnrollmentForJavaApplication)
        then: "mati is enrolled for java course"
            matiEnrolledForJava
            with(queries.studentsEnrolledFor(javaCourse)) {
                count() == 1
                hasVacancy()
                contain(mati)
            }

        when: "musk resigns from leading java course"
            useCases.resignFromCourseLeadership(javaCourse, musk)
        then: "resignation is accepted"
            with(queries.ledCoursesBy(musk)) {
                count() == 0
            }
            with(queries.leadershipFor(javaCourse)) {
                isAvailable()
            }
        and: "notification is published"
            1 * notificationPublisher.publish(new ProfessorResignedFromCourseLeadership(musk, javaCourse))

        when: "zuck applies for java course overtaking"
            def javaCourseOvertakingApplication = new CourseOvertakingApplication(
                    javaCourse,
                    zuck
            )
            def javaCourseOvertaken = useCases.overtakeCourse(javaCourseOvertakingApplication)
        then: "java course is overtaken by zuck"
            javaCourseOvertaken
            with(queries.ledCoursesBy(zuck)) {
                count() == 1
                hasCapacity()
                contain(javaCourse)
            }
            with(queries.leadershipFor(javaCourse)) {
                isTakenBy(zuck)
            }

        when: "zuck resign from employment"
            useCases.resignFromEmployment(zuck, itFaculty)
        then: "resignation is accepted"
            professorCatalog.findById(zuck)
                            .isEmpty()
            with(queries.professorsEmployedAt(itFaculty)) {
                count() == 1
                !contain(zuck)
            }
            queries.findLedCoursesBy(zuck)
                   .isEmpty()
            with(queries.leadershipFor(javaCourse)) {
                isAvailable()
            }
        and: "notification is published"
            1 * notificationPublisher.publish(new ProfessorResignedFromEmployment(zuck, itFaculty))

        when: "gates applies for employment again"
            def gates = useCases.employProfessor(gatesEmploymentApplication)
                                .orElseThrow()
        then: "gates is employed"
            with(professorCatalog.getBtId(gates)) {
                id() == gates
                name() == gatesEmploymentApplication.professorName()
                fieldsOfStudies() == gatesEmploymentApplication.fieldsOfStudies()
            }
            with(queries.professorsEmployedAt(itFaculty)) {
                count() == 2
                !hasVacancy()
                contain(musk)
                contain(gates)
            }

        when: "gates applies for java course overtaking"
            def javaOvertakingByGatesApplication = new CourseOvertakingApplication(
                    javaCourse,
                    gates
            )
            def overtakenByGates = useCases.overtakeCourse(javaOvertakingByGatesApplication)
        then: "java course is overtaken by gates"
            overtakenByGates
            with(queries.ledCoursesBy(gates)) {
                count() == 1
                hasCapacity()
                contain(javaCourse)
            }
            with(queries.leadershipFor(javaCourse)) {
                isTakenBy(gates)
            }

        when: "java course is closed"
            def javaCourseClosed = useCases.closeCourse(javaCourse, itFaculty)
        then: "course is no longer available"
            javaCourseClosed
            courseCatalog.findById(javaCourse)
                         .isEmpty()
            queries.findStudentsEnrolledFor(javaCourse)
                   .isEmpty()
            with(queries.openCoursesAt(itFaculty)) {
                !contain(javaCourse)
            }
            with(queries.ledCoursesBy(gates)) {
                !contain(javaCourse)
            }
            queries.findLeadershipFor(javaCourse)
                   .isEmpty()
        and: "notification is published"
            1 * notificationPublisher.publish(new CourseClosed(javaCourse, itFaculty))

        when: "gates applies for spring course creation"
            def springCourseCreationApplication = new CourseCreationByProfessorApplication(
                    itFaculty,
                    gates,
                    "spring",
                    new FieldsOfStudies(Set.of(PROGRAMMING))
            )
            def springCourse = useCases.createCourseByProfessor(springCourseCreationApplication)
                                       .orElseThrow()
        then: "spring course is created by gates"
            with(courseCatalog.getById(springCourse)) {
                name() == springCourseCreationApplication.courseName()
                fieldsOfStudies() == springCourseCreationApplication.fieldsOfStudies()
            }
            with(queries.openCoursesAt(itFaculty)) {
                count() == 1
                hasCapacity()
                contain(springCourse)
            }
            with(queries.ledCoursesBy(gates)) {
                count() == 1
                hasCapacity()
                contain(springCourse)
            }
            with(queries.leadershipFor(springCourse)) {
                isTakenBy(gates)
            }

        when: "mati applies for spring course enrollment"
            def matiEnrollmentForSpringCourseApplication = new StudentEnrollmentForCourseApplication(mati, springCourse)
            def matiEnrolledForSpring = useCases.enrollForCourse(matiEnrollmentForSpringCourseApplication)
        then: "mati is enrolled for spring course"
            matiEnrolledForSpring
            with(queries.studentsEnrolledFor(springCourse)) {
                count() == 1
                !hasVacancy()
                contain(mati)
            }

        when: "spring course max vacancies are restricted to 1"
            def restricted = useCases.restrictMaxVacanciesNumberFor(springCourse, new Vacancies(1))
        then: "restriction is accepted"
            restricted
            with(queries.studentsEnrolledFor(springCourse)) {
                maxVacancies().count() == 1
            }

        when: "Uncle Bob applies for enrollment at faculty"
            var uncleBobEnrollmentAtFacultyApplication = new StudentEnrollmentAtFacultyApplication(
                    itFaculty,
                    PASSED_COMPUTER_SCIENCE,
                    new ExamResults(Set.of(
                            PASSED_PROGRAMMING,
                            PASSED_CYBER_SECURITY
                    ))
            )
            var uncleBob = useCases.enrollAtFaculty(uncleBobEnrollmentAtFacultyApplication)
                                   .orElseThrow()
        then: "Uncle Bob is enrolled at faculty"
            with(queries.studentsEnrolledAt(itFaculty)) {
                count() == 2
                hasVacancy()
                contain(uncleBob)
            }

        when: "Uncle Bob applies for enrollment for spring course"
            var uncleBobEnrollmentForSpringCourseApplication = new StudentEnrollmentForCourseApplication(
                    uncleBob,
                    springCourse
            )
            var uncleBobEnrolledForSpring = useCases.enrollForCourse(uncleBobEnrollmentForSpringCourseApplication)
        then: "Uncle Bob is not enrolled for spring course"
            !uncleBobEnrolledForSpring
            with(queries.studentsEnrolledFor(springCourse)) {
                count() == 1
                !hasVacancy()
                !contain(uncleBob)
            }

        when: "mati resigns from enrollment at faculty"
            useCases.resignFromEnrollmentAtFaculty(mati, itFaculty)
        then: "resignation is accepted"
            with(queries.studentsEnrolledAt(itFaculty)) {
                hasVacancy()
                !contain(mati)
            }
            with(queries.studentsEnrolledFor(springCourse)) {
                !contain(mati)
            }
        and: "notification is published"
            1 * notificationPublisher.publish(new StudentResignedFromEnrollmentAtFaculty(mati, itFaculty))

        when: "Uncle Bob applies for enrollment for spring course again"
            uncleBobEnrolledForSpring = useCases.enrollForCourse(uncleBobEnrollmentForSpringCourseApplication)
        then: "Uncle Bob is finally enrolled for spring course"
            uncleBobEnrolledForSpring
            with(queries.studentsEnrolledFor(springCourse)) {
                !hasVacancy()
                contain(uncleBob)
            }
    }

    static def passedExam(FieldOfStudy fieldOfStudy) {
        new ExamResult(fieldOfStudy, new PercentageScore(100))
    }
}
