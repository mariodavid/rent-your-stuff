package de.diedavids.jmix.rys.order;

import de.diedavids.jmix.rys.test_support.Validations;
import de.diedavids.jmix.rys.test_support.test_data.Orders;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class OrderValidationTest {

    @Autowired
    Validations validations;

    @Autowired
    Orders orders;

    private final LocalDate TODAY = LocalDate.now();
    private final LocalDate YESTERDAY = TODAY.minusDays(1);

    @Test
    void given_validOrder_when_validate_then_noViolationOccurs() {

        // given
        Order order = orders.createDefault();

        // expect
        validations.assertNoViolations(order);
    }

    @Test
    void given_orderWithoutOrderDate_when_validate_then_oneViolationOccurs() {

        // given
        Order order = orders.create(
                orders.defaultData()
                        .orderDate(null)
                        .build()
        );

        // expect
        validations.assertExactlyOneViolationWith(order, "orderDate", "NotNull");
    }

    @Test
    void given_orderWithOrderDateInThePast_when_validate_then_oneViolationOccurs() {

        // given
        Order order = orders.create(
                orders.defaultData()
                        .orderDate(YESTERDAY)
                        .build()
        );

        // expect
        validations.assertExactlyOneViolationWith(order, "orderDate", "FutureOrPresent");
    }


    @Test
    void given_orderWithoutCustomer_when_validate_then_oneViolationOccurs() {

        // given
        Order order = orders.create(
                orders.defaultData()
                        .customer(null)
                        .build()
        );

        // expect
        validations.assertExactlyOneViolationWith(order, "customer", "NotNull");
    }
}