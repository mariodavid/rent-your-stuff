package de.diedavids.jmix.rys.test_support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class Validations {

    @Autowired
    Validator validator;

    public <T> List<ValidationResult> validate(T entity) {
        return validator.validate(entity, Default.class)
                .stream()
                .map(ValidationResult::new)
                .collect(Collectors.toList());
    }

    public String validationMessage(String errorType) {
        return "{javax.validation.constraints." + errorType + ".message}";
    }

    public <T> ValidationResult validateFirst(T entity) {
        return validate(entity)
                .get(0);
    }

    public <T> void assertExactlyOneViolationWith(T entity, String attribute, String errorType) {


        List<Validations.ValidationResult> violations = validate(entity);

        // then
        assertThat(violations)
                .hasSize(1);

        Validations.ValidationResult violation = violations.get(0);

        assertThat(violation.getAttribute())
                .isEqualTo(attribute);

        assertThat(violation.getErrorType())
                .isEqualTo(validationMessage(errorType));
    }

    public <T> void assertOneViolationWith(T entity, String attribute, String errorType) {


        List<Validations.ValidationResult> violations = validate(entity);

        // then
        assertThat(violations)
                .hasSizeGreaterThanOrEqualTo(1)
                .anyMatch(validationResult -> validationResult.matches(attribute, validationMessage(errorType)));
    }

    public <T> void assertOneViolationWith(T entity, String errorType) {


        List<Validations.ValidationResult> violations = validate(entity);

        // then
        assertThat(violations)
                .hasSizeGreaterThanOrEqualTo(1)
                .anyMatch(validationResult -> validationResult.matches(validationMessage(errorType)));
    }

    public <T> void assertNoViolations(T entity) {
        List<Validations.ValidationResult> violations = validate(entity);

        assertThat(violations)
                .isEmpty();
    }

    public static class ValidationResult<T> {

        private final ConstraintViolation<T> violation;

        public ValidationResult(ConstraintViolation<T> violation) {
            this.violation = violation;
        }

        public String getAttribute() {
            return violation.getPropertyPath().toString();
        }

        public String getErrorType() {
            return violation.getMessageTemplate();
        }

        public boolean matches(String attribute, String errorType) {
            return getAttribute().equals(attribute) &&
                    getErrorType().equals(errorType);
        }
        public boolean matches(String errorType) {
            return getErrorType().equals(errorType);
        }
    }
}
