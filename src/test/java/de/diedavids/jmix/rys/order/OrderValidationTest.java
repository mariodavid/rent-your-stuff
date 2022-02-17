package de.diedavids.jmix.rys.order;

import de.diedavids.jmix.rys.customer.Customer;
import de.diedavids.jmix.rys.order.Order;
import de.diedavids.jmix.rys.test_support.ValidationVerification;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    ValidationVerification validationVerification;
    
    private Order order;
    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate YESTERDAY = TODAY.minusDays(1);

    @BeforeEach
    void setUp() {
        order = dataManager.create(Order.class);
    }

    @Test
    void given_validOrder_when_validate_then_noViolationOccurs() {

        // given
        order.setOrderDate(TODAY);
        order.setCustomer(dataManager.create(Customer.class));

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(order);

        // then
        assertThat(violations)
                .isEmpty();
    }

    @Test
    void given_orderWithoutOrderDate_when_validate_then_oneViolationOccurs() {

        // given
        order.setOrderDate(null);
        order.setCustomer(dataManager.create(Customer.class));

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(order);

        // then
        assertThat(violations)
                .hasSize(1);

        ValidationVerification.ValidationResult violation = violations.get(0);

        assertThat(violation.getAttribute())
                .isEqualTo("orderDate");

        assertThat(violation.getErrorType())
                .isEqualTo(validationVerification.validationMessage("NotNull"));
    }

    @Test
    void given_orderWithOrderDateInThePast_when_validate_then_oneViolationOccurs() {

        // given
        order.setOrderDate(YESTERDAY);
        order.setCustomer(dataManager.create(Customer.class));

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(order);

        // then
        assertThat(violations)
                .hasSize(1);

        ValidationVerification.ValidationResult violation = violations.get(0);

        assertThat(violation.getAttribute())
                .isEqualTo("orderDate");

        assertThat(violation.getErrorType())
                .isEqualTo(validationVerification.validationMessage("FutureOrPresent"));
    }



    @Test
    void given_orderWithoutCustomer_when_validate_then_oneViolationOccurs() {

        // given
        order.setOrderDate(TODAY);
        order.setCustomer(null);

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(order);

        // then
        assertThat(violations)
                .hasSize(1);

        ValidationVerification.ValidationResult violation = violations.get(0);

        assertThat(violation.getAttribute())
                .isEqualTo("customer");

        assertThat(violation.getErrorType())
                .isEqualTo(validationVerification.validationMessage("NotNull"));
    }
}