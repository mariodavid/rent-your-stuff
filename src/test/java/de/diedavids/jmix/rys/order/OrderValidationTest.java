package de.diedavids.jmix.rys.order;

import de.diedavids.jmix.rys.customer.Customer;
import de.diedavids.jmix.rys.test_support.Validations;
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
    Validations validations;
    
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

        // expect
        validations.assertNoViolations(order);
    }

    @Test
    void given_orderWithoutOrderDate_when_validate_then_oneViolationOccurs() {

        // given
        order.setOrderDate(null);
        order.setCustomer(dataManager.create(Customer.class));

        // expect
        validations.assertExactlyOneViolationWith(order, "orderDate", "NotNull");
    }

    @Test
    void given_orderWithOrderDateInThePast_when_validate_then_oneViolationOccurs() {

        // given
        order.setOrderDate(YESTERDAY);
        order.setCustomer(dataManager.create(Customer.class));

        // expect
        validations.assertExactlyOneViolationWith(order, "orderDate", "FutureOrPresent");
    }


    @Test
    void given_orderWithoutCustomer_when_validate_then_oneViolationOccurs() {

        // given
        order.setOrderDate(TODAY);
        order.setCustomer(null);

        // expect
        validations.assertExactlyOneViolationWith(order, "customer", "NotNull");
    }
}