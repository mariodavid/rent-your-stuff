package de.diedavids.jmix.rys.orderLine;

import de.diedavids.jmix.rys.customer.Customer;
import de.diedavids.jmix.rys.order.Order;
import de.diedavids.jmix.rys.order.OrderLine;
import de.diedavids.jmix.rys.product.StockItem;
import de.diedavids.jmix.rys.test_support.ValidationVerification;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderLineLineValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    ValidationVerification validationVerification;
    
    private OrderLine orderLine;
    private final LocalDateTime NOW = LocalDateTime.now();
    private final LocalDateTime YESTERDAY = NOW.minusDays(1);
    private final LocalDateTime TOMORROW = NOW.plusDays(1);
    private final LocalDateTime IN_TWO_DAYS = NOW.plusDays(2);

    @BeforeEach
    void setUp() {
        orderLine = dataManager.create(OrderLine.class);
    }

    @Test
    void given_validOrderLine_when_validate_then_noViolationOccurs() {

        // given
        orderLine.setOrder(dataManager.create(Order.class));
        orderLine.setStockItem(dataManager.create(StockItem.class));
        orderLine.setStartsAt(TOMORROW);
        orderLine.setEndsAt(IN_TWO_DAYS);

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(orderLine);

        // then
        assertThat(violations)
                .isEmpty();
    }

    @Test
    void given_orderLineWithoutOrder_when_validate_then_oneViolationOccurs() {

        // given
        orderLine.setStockItem(dataManager.create(StockItem.class));
        orderLine.setStartsAt(TOMORROW);
        orderLine.setEndsAt(IN_TWO_DAYS);

        // and
        orderLine.setOrder(null);

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(orderLine);

        // then
        assertThat(violations)
                .hasSize(1);

        ValidationVerification.ValidationResult violation = violations.get(0);

        assertThat(violation.getAttribute())
                .isEqualTo("order");

        assertThat(violation.getErrorType())
                .isEqualTo(validationVerification.validationMessage("NotNull"));
    }

    @Test
    void given_orderLineWithoutStockItem_when_validate_then_oneViolationOccurs() {

        // given
        orderLine.setOrder(dataManager.create(Order.class));
        orderLine.setStartsAt(TOMORROW);
        orderLine.setEndsAt(IN_TWO_DAYS);

        // and
        orderLine.setStockItem(null);

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(orderLine);

        // then
        assertThat(violations)
                .hasSize(1);

        ValidationVerification.ValidationResult violation = violations.get(0);

        assertThat(violation.getAttribute())
                .isEqualTo("stockItem");

        assertThat(violation.getErrorType())
                .isEqualTo(validationVerification.validationMessage("NotNull"));
    }

    @Test
    void given_orderLineWithStartsAtNotPresent_when_validate_then_oneViolationOccurs() {

        // given
        orderLine.setOrder(dataManager.create(Order.class));
        orderLine.setStockItem(dataManager.create(StockItem.class));
        orderLine.setEndsAt(IN_TWO_DAYS);

        // and
        orderLine.setStartsAt(null);

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(orderLine);

        // then
        assertThat(violations)
                .hasSize(1);

        ValidationVerification.ValidationResult violation = violations.get(0);

        assertThat(violation.getAttribute())
                .isEqualTo("startsAt");

        assertThat(violation.getErrorType())
                .isEqualTo(validationVerification.validationMessage("NotNull"));
    }

    @Test
    void given_orderLineWithEndsAtNotPresent_when_validate_then_oneViolationOccurs() {

        // given
        orderLine.setOrder(dataManager.create(Order.class));
        orderLine.setStockItem(dataManager.create(StockItem.class));
        orderLine.setStartsAt(TOMORROW);

        // and
        orderLine.setEndsAt(null);

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(orderLine);

        // then
        assertThat(violations)
                .hasSize(1);

        ValidationVerification.ValidationResult violation = violations.get(0);

        assertThat(violation.getAttribute())
                .isEqualTo("endsAt");

        assertThat(violation.getErrorType())
                .isEqualTo(validationVerification.validationMessage("NotNull"));
    }

    @Test
    void given_orderLineWithStartsAtInThePast_when_validate_then_oneViolationOccurs() {

        // given
        orderLine.setOrder(dataManager.create(Order.class));
        orderLine.setStockItem(dataManager.create(StockItem.class));
        orderLine.setEndsAt(IN_TWO_DAYS);

        // and
        orderLine.setStartsAt(YESTERDAY);

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(orderLine);

        // then
        assertThat(violations)
                .hasSize(1);

        ValidationVerification.ValidationResult violation = violations.get(0);

        assertThat(violation.getAttribute())
                .isEqualTo("startsAt");

        assertThat(violation.getErrorType())
                .isEqualTo(validationVerification.validationMessage("FutureOrPresent"));
    }

    @Test
    void given_orderLineWithEndsAtInThePast_when_validate_then_oneViolationOccurs() {

        // given
        orderLine.setOrder(dataManager.create(Order.class));
        orderLine.setStockItem(dataManager.create(StockItem.class));
        orderLine.setStartsAt(TOMORROW);

        // and
        orderLine.setEndsAt(YESTERDAY);

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(orderLine);

        // then
        assertThat(violations)
                .hasSize(1);

        ValidationVerification.ValidationResult violation = violations.get(0);

        assertThat(violation.getAttribute())
                .isEqualTo("endsAt");

        assertThat(violation.getErrorType())
                .isEqualTo(validationVerification.validationMessage("FutureOrPresent"));
    }
}