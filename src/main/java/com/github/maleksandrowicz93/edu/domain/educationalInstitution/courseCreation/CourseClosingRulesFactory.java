package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation;

import com.github.maleksandrowicz93.edu.common.rule.Rule;
import com.github.maleksandrowicz93.edu.common.rule.RuleFactory;
import com.github.maleksandrowicz93.edu.common.rule.Rules;
import com.github.maleksandrowicz93.edu.common.rule.RulesFactory;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.PercentageScore;

record CourseClosingRulesFactory(
        CourseClosingConfig config
) implements RulesFactory<CourseClosingContext> {

    static CourseClosingRulesFactory from(CourseClosingConfig config) {
        return new CourseClosingRulesFactory(config);
    }

    @Override
    public Rules<CourseClosingContext> createRules() {
        return Rules.<CourseClosingContext>from()
                    .theRule(new EnrollmentsUnderLimit())
                    .compose();
    }

    private class EnrollmentsUnderLimit implements RuleFactory<CourseClosingContext> {

        @Override
        public Rule<CourseClosingContext> get() {
            return new Rule<>(
                    context -> {
                        var enrollmentsSummary = context.enrollmentsSummary();
                        var enrolledStudentsNumber = enrollmentsSummary.enrollmentsNumber();
                        var maxVacancies = enrollmentsSummary.maxVacancies();
                        var percentageScore = new PercentageScore(enrolledStudentsNumber, maxVacancies.count());
                        return percentageScore.lessThan(config.minEnrolledStudentsList());
                    },
                    context -> "Cannot close course %s with id %s because too many students already enrolled"
                            .formatted(context.course().name(), context.course().id())
            );
        }
    }
}
