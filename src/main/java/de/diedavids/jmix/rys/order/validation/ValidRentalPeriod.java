package de.diedavids.jmix.rys.order.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidRentalPeriodValidator.class)
public @interface ValidRentalPeriod {

    String message() default "{jakarta.validation.constraints.ValidRentalPeriod.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
