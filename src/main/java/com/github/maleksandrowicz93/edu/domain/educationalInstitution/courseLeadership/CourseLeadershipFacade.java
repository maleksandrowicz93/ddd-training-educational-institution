package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership;

import com.github.maleksandrowicz93.edu.common.rule.Rules;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog.CourseCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorCatalog.ProfessorCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CourseLeadershipFacade {

    Rules<CourseTakingContext> courseTakingRules;
    ProfessorCatalog professorCatalog;
    CourseCatalog courseCatalog;
    CourseLeadershipService courseLeadershipService;

    public boolean createCourseLeadership(CourseLeadershipCreationApplication application) {
        log.info("Creating course for application: {}...", application);
        var professor = professorCatalog.getBtId(application.professorId());
        var context = new CourseTakingContext(professor, application.course());
        var result = courseTakingRules.examine(context);
        if (result.isFailed()) {
            log.info(result.reason());
            return false;
        }
        return courseLeadershipService.createCourseLeadership(application);
    }

    public boolean overtakeCourse(CourseOvertakingApplication application) {
        log.info("Overtaking course for application: {}...", application);
        var professor = professorCatalog.getBtId(application.professorId());
        var course = courseCatalog.getById(application.courseId());
        var context = new CourseTakingContext(professor, course);
        var result = courseTakingRules.examine(context);
        if (result.isFailed()) {
            log.info(result.reason());
            return false;
        }
        return courseLeadershipService.overtakeCourse(application);
    }

    public void resignFromCourseLeadership(CourseId courseId, ProfessorId professorId) {
        log.info("Resigning from course {} leadership by professor {}...", courseId, professorId);
        courseLeadershipService.resignFromCourseLeadership(courseId, professorId);
    }

    public void resignFromAllCoursesLeadership(ProfessorId professorId) {
        log.info("Resigning from all courses leaderships by {}...", professorId);
        courseLeadershipService.resignFromAllCoursesLeadership(professorId);
    }

    public void closeCourse(CourseId courseId) {
        log.info("Closing course {} leadership possibility...", courseId);
        courseLeadershipService.closeCourse(courseId);
    }
}
