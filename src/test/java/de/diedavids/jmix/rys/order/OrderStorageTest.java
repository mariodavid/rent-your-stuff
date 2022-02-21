package de.diedavids.jmix.rys.order;

import de.diedavids.jmix.rys.customer.Customer;
import de.diedavids.jmix.rys.entity.Address;
import de.diedavids.jmix.rys.product.Product;
import de.diedavids.jmix.rys.test_support.test_data.*;
import de.diedavids.jmix.rys.product.StockItem;
import de.diedavids.jmix.rys.test_support.TenantUserEnvironment;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static de.diedavids.jmix.rys.order.Assertions.assertThat;


@SpringBootTest
@ExtendWith(TenantUserEnvironment.class)
class OrderStorageTest {

    @Autowired
    DataManager dataManager;
    @Autowired
    Orders orders;
    @Autowired
    OrderLines orderLines;
    @Autowired
    Products products;
    @Autowired
    StockItems stockItems;
    @Autowired
    Customers customers;

    private final LocalDateTime IN_TWO_DAYS = LocalDateTime.now().plusDays(2);
    private final LocalDateTime IN_THREE_DAYS = LocalDateTime.now().plusDays(3);

    @Test
    void given_validOrder_when_validate_then_noViolationOccurs() {

        // given
        Customer customer = customers.saveDefault();

        // and
        Product product = products.save(
                products.defaultData().name("Giant Stance E+ 1").build()
        );

        StockItem stockItem1 = stockItems.save(
                stockItems.defaultData()
                        .product(product)
                        .identifier("GS76928394")
                        .build()
        );

        StockItem stockItem2 = stockItems.save(
                stockItems.defaultData()
                        .product(product)
                        .identifier("GS23928390")
                        .build()
        );

        // and

        Order order = orders.save(
                orders.defaultData()
                        .customer(customer)
                        .build()
        );
        orderLines.save(
                orderLines.defaultData()
                        .order(order)
                        .stockItem(stockItem1)
                        .endsAt(IN_TWO_DAYS)
                        .build()
        );
        orderLines.save(
                orderLines.defaultData()
                        .order(order)
                        .stockItem(stockItem2)
                        .endsAt(IN_THREE_DAYS)
                        .build()
        );

        // when
        Order savedOrder = dataManager.load(Id.of(order)).one();

        // then
        assertThat(savedOrder)
                .hasCustomer(customer);

        assertThat(orderLineWithEndsAt(savedOrder, IN_TWO_DAYS))
                .hasStockItem(stockItem1);

        assertThat(orderLineWithEndsAt(savedOrder, IN_THREE_DAYS))
                .hasStockItem(stockItem2);

    }

    @NotNull
    private OrderLine orderLineWithEndsAt(Order savedOrder, LocalDateTime endsAt) {
        return savedOrder.getOrderLines().stream().filter(orderLine -> orderLine.getEndsAt().equals(endsAt)).findFirst().orElseThrow();
    }
}