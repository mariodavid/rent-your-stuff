package de.diedavids.jmix.rys.test_support;

import de.diedavids.jmix.rys.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ValidationVerification {

    @Autowired
    Validator validator;

    public <T> List<ValidationResult> validate(T address) {
        return validator.validate(address, Default.class)
                .stream()
                .map(ValidationResult::new)
                .collect(Collectors.toList());
    }

    public String validationMessage(String errorType) {
        return "{javax.validation.constraints." + errorType + ".message}";
    }

    public <T> ValidationResult validateFirst(T address) {
        return validate(address)
                .get(0);
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
    }
}
