package de.diedavids.jmix.rys.order;

import de.diedavids.jmix.rys.product.StockItem;
import de.diedavids.jmix.rys.test_support.Validations;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderLineValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    Validations validations;
    
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

        // expect
        validations.assertNoViolations(orderLine);
    }

    @Test
    void given_orderLineWithoutOrder_when_validate_then_oneViolationOccurs() {

        // given
        orderLine.setStockItem(dataManager.create(StockItem.class));
        orderLine.setStartsAt(TOMORROW);
        orderLine.setEndsAt(IN_TWO_DAYS);

        // and
        orderLine.setOrder(null);

        // expect
        validations.assertExactlyOneViolationWith(orderLine, "order", "NotNull");
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
        // expect
        validations.assertExactlyOneViolationWith(orderLine, "stockItem", "NotNull");
    }

    @Test
    void given_orderLineWithStartsAtNotPresent_when_validate_then_oneViolationOccurs() {

        // given
        orderLine.setOrder(dataManager.create(Order.class));
        orderLine.setStockItem(dataManager.create(StockItem.class));
        orderLine.setEndsAt(IN_TWO_DAYS);

        // and
        orderLine.setStartsAt(null);

        // expect
        validations.assertOneViolationWith(orderLine, "startsAt", "NotNull");
    }

    @Test
    void given_orderLineWithEndsAtNotPresent_when_validate_then_oneViolationOccurs() {

        // given
        orderLine.setOrder(dataManager.create(Order.class));
        orderLine.setStockItem(dataManager.create(StockItem.class));
        orderLine.setStartsAt(TOMORROW);

        // and
        orderLine.setEndsAt(null);

        // expect
        validations.assertOneViolationWith(orderLine, "endsAt", "NotNull");
    }

    @Test
    void given_orderLineWithStartsAtInThePast_when_validate_then_oneViolationOccurs() {

        // given
        orderLine.setOrder(dataManager.create(Order.class));
        orderLine.setStockItem(dataManager.create(StockItem.class));
        orderLine.setEndsAt(IN_TWO_DAYS);

        // and
        orderLine.setStartsAt(YESTERDAY);

        // expect
        validations.assertOneViolationWith(orderLine, "startsAt", "FutureOrPresent");
    }

    @Test
    void given_orderLineWithEndsAtInThePast_when_validate_then_oneViolationOccurs() {

        // given
        orderLine.setOrder(dataManager.create(Order.class));
        orderLine.setStockItem(dataManager.create(StockItem.class));
        orderLine.setStartsAt(TOMORROW);

        // and
        orderLine.setEndsAt(YESTERDAY);


        // expect
        validations.assertOneViolationWith(orderLine, "endsAt", "FutureOrPresent");
    }

    @Test
    void given_orderLineWithEndsBeforeTheStartDate_when_validate_then_oneViolationOccurs() {

        // given
        orderLine.setOrder(dataManager.create(Order.class));
        orderLine.setStockItem(dataManager.create(StockItem.class));

        // and
        orderLine.setStartsAt(IN_TWO_DAYS);
        orderLine.setEndsAt(TOMORROW);

        // expect
        validations.assertOneViolationWith(orderLine, "ValidRentalPeriod");
    }
}