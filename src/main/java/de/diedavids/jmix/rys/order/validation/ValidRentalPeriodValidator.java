package de.diedavids.jmix.rys.order.validation;

import de.diedavids.jmix.rys.order.OrderLine;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidRentalPeriodValidator implements ConstraintValidator<ValidRentalPeriod, OrderLine> {
    @Override
    public boolean isValid(OrderLine orderLine, ConstraintValidatorContext context) {
        if (orderLine == null) {
            return false;
        }

        if (orderLine.getStartsAt() == null || orderLine.getEndsAt() == null) {
            return false;
        }

        return orderLine.getStartsAt().isBefore(orderLine.getEndsAt());
    }
}
