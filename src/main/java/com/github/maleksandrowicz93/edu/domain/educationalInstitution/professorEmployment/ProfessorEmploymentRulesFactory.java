package com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment;

import com.github.maleksandrowicz93.edu.common.rule.Rule;
import com.github.maleksandrowicz93.edu.common.rule.RuleFactory;
import com.github.maleksandrowicz93.edu.common.rule.Rules;
import com.github.maleksandrowicz93.edu.common.rule.RulesFactory;

record ProfessorEmploymentRulesFactory(
        ProfessorEmploymentConfig config
) implements RulesFactory<ProfessorEmploymentContext> {

    static ProfessorEmploymentRulesFactory from(ProfessorEmploymentConfig config) {
        return new ProfessorEmploymentRulesFactory(config);
    }

    @Override
    public Rules<ProfessorEmploymentContext> createRules() {
        return Rules.<ProfessorEmploymentContext>from()
                    .theRule(new YearsOfExperience())
                    .theRule(new FieldsOfStudy())
                    .compose();
    }

    private abstract static class ProfessorEmploymentRuleFactory implements RuleFactory<ProfessorEmploymentContext> {

        @Override
        public Rule<ProfessorEmploymentContext> get() {
            return new Rule<>(
                    this::condition,
                    context -> {
                        var application = context.application();
                        return "Professor %s cannot be employed at faculty with id %s because %s"
                                .formatted(application.professorName(), application.facultyId(), reason());
                    }
            );
        }

        abstract boolean condition(ProfessorEmploymentContext context);

        abstract String reason();
    }

    private class YearsOfExperience extends ProfessorEmploymentRuleFactory {

        @Override
        boolean condition(ProfessorEmploymentContext context) {
            return context.application().yearsOfExperience() >= config.minYearsOfExperience();
        }

        @Override
        String reason() {
            return "has too small experience";
        }
    }

    private class FieldsOfStudy extends ProfessorEmploymentRuleFactory {

        @Override
        boolean condition(ProfessorEmploymentContext context) {
            var minMatchedFieldsOfStudy = config.minMatchedFieldsOfStudy();
            var professorFieldsOfStudy = context.application().fieldsOfStudies();
            var facultyFieldsOfStudy = context.faculty().allFieldsOfStudies();
            return context.faculty().fieldsOfStudiesNumber() < minMatchedFieldsOfStudy
                    && professorFieldsOfStudy.matchesAllOf(facultyFieldsOfStudy)
                    || professorFieldsOfStudy.numberOfMatched(facultyFieldsOfStudy) >= minMatchedFieldsOfStudy;
        }

        @Override
        String reason() {
            return "does not match faculty fields of studies";
        }
    }
}
