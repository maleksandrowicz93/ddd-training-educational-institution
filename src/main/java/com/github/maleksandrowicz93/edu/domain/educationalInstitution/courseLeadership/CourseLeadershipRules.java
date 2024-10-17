package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership;

import com.github.maleksandrowicz93.edu.common.rule.Rule;
import com.github.maleksandrowicz93.edu.common.rule.RuleFactory;
import com.github.maleksandrowicz93.edu.common.rule.Rules;
import com.github.maleksandrowicz93.edu.common.rule.RulesFactory;

enum CourseLeadershipRules implements RulesFactory<CourseTakingContext> {

    FACTORY {
        @Override
        public Rules<CourseTakingContext> createRules() {
            return Rules.<CourseTakingContext>from()
                        .theRule(new FieldsOfStudiesMatched())
                        .compose();
        }
    };

    private static class FieldsOfStudiesMatched implements RuleFactory<CourseTakingContext> {

        @Override
        public Rule<CourseTakingContext> get() {
            return new Rule<>(
                    context -> {
                        var professorFieldsOfStudies = context.professor().fieldsOfStudies();
                        var courseFieldsOfStudies = context.course().fieldsOfStudies();
                        return professorFieldsOfStudies.matchesAllOf(courseFieldsOfStudies);
                    },
                    context -> {
                        var professor = context.professor();
                        var course = context.course();
                        return ("Professor %s with id %s cannot take course %s with id %s " +
                                "because fields of studies are not matched")
                                .formatted(professor.name(), professor.id(), course.name(), course.id());
                    }
            );
        }
    }
}
