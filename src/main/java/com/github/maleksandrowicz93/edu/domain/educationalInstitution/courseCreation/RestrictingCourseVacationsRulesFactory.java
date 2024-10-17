package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation;

import com.github.maleksandrowicz93.edu.common.rule.Rule;
import com.github.maleksandrowicz93.edu.common.rule.RuleFactory;
import com.github.maleksandrowicz93.edu.common.rule.Rules;
import com.github.maleksandrowicz93.edu.common.rule.RulesFactory;

public record RestrictingCourseVacationsRulesFactory(
        CourseCreationConfig config
) implements RulesFactory<RestrictingCourseVacationsContext> {

    static RestrictingCourseVacationsRulesFactory from(CourseCreationConfig config) {
        return new RestrictingCourseVacationsRulesFactory(config);
    }

    @Override
    public Rules<RestrictingCourseVacationsContext> createRules() {
        return Rules.<RestrictingCourseVacationsContext>from()
                    .theRule(new MinCourseEnrollmentsLimit())
                    .compose();
    }

    private class MinCourseEnrollmentsLimit implements RuleFactory<RestrictingCourseVacationsContext> {

        @Override
        public Rule<RestrictingCourseVacationsContext> get() {
            return new Rule<>(
                    context -> context.vacancies().count() >= config.minCourseEnrollmentsLimit(),
                    context -> ("Cannot restrict max vacancies limit for course with id %s " +
                            "because that value cannot be lower than allowed limit %d students")
                            .formatted(context.courseId().value(), config.minCourseEnrollmentsLimit())
            );
        }
    }
}
