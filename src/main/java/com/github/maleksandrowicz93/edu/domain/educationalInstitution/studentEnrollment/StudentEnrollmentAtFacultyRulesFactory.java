package com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment;

import com.github.maleksandrowicz93.edu.common.rule.Rule;
import com.github.maleksandrowicz93.edu.common.rule.RuleFactory;
import com.github.maleksandrowicz93.edu.common.rule.Rules;
import com.github.maleksandrowicz93.edu.common.rule.RulesFactory;

record StudentEnrollmentAtFacultyRulesFactory(
        StudentEnrollmentAtFacultyConfig config
) implements RulesFactory<StudentEnrollmentAtFacultyContext> {

    static StudentEnrollmentAtFacultyRulesFactory from(StudentEnrollmentAtFacultyConfig config) {
        return new StudentEnrollmentAtFacultyRulesFactory(config);
    }

    @Override
    public Rules<StudentEnrollmentAtFacultyContext> createRules() {
        return Rules.<StudentEnrollmentAtFacultyContext>from()
                    .theRule(new TakenExams())
                    .theRule(new MainExamResult())
                    .theRule(new SecondaryExamsResults())
                    .compose();
    }

    private abstract static class StudentEnrollmentAtFacultyRuleFactory
            implements RuleFactory<StudentEnrollmentAtFacultyContext> {

        @Override
        public Rule<StudentEnrollmentAtFacultyContext> get() {
            return new Rule<>(
                    this::condition,
                    context -> "Student cannot be enrolled at faculty with id %s because %s"
                            .formatted(context.application().facultyId(), reason())
            );
        }

        abstract boolean condition(StudentEnrollmentAtFacultyContext context);

        abstract String reason();
    }

    private static class TakenExams extends StudentEnrollmentAtFacultyRuleFactory {

        @Override
        boolean condition(StudentEnrollmentAtFacultyContext context) {
            var examinedFieldsOfStudies = context.application().examinedFieldsOfStudies();
            var facultyFieldsOfStudies = context.faculty().allFieldsOfStudies();
            return examinedFieldsOfStudies.matchesAllOf(facultyFieldsOfStudies);
        }

        @Override
        String reason() {
            return "not all required exams are taken";
        }
    }

    private class MainExamResult extends StudentEnrollmentAtFacultyRuleFactory {

        @Override
        boolean condition(StudentEnrollmentAtFacultyContext context) {
            var examScore = context.application().mainExamResult().percentageScore();
            return examScore.equalToOrGreaterThan(config.minMainExamScore());
        }

        @Override
        String reason() {
            return "main field of study exam not passed";
        }
    }

    private class SecondaryExamsResults extends StudentEnrollmentAtFacultyRuleFactory {

        @Override
        boolean condition(StudentEnrollmentAtFacultyContext context) {
            var lowestSecondaryExamScore = context.application().secondaryExamResults().lowestScore();
            return lowestSecondaryExamScore.equalToOrGreaterThan(config.minSecondaryExamScore());
        }

        @Override
        String reason() {
            return "not all secondary fields of studies exams are passed";
        }
    }
}
