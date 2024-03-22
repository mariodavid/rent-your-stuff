package de.diedavids.jmix.rys.order;


import de.diedavids.jmix.rys.customer.Customer;
import de.diedavids.jmix.rys.product.Product;
import de.diedavids.jmix.rys.product.StockItem;
import de.diedavids.jmix.rys.test_support.TenantUserEnvironment;
import de.diedavids.jmix.rys.test_support.test_data.*;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(TenantUserEnvironment.class)
public class OrderStorageTest {

    @Autowired
    DataManager dataManager;
    @Autowired
    Customers customers;
    @Autowired
    Products products;
    @Autowired
    StockItems stockItems;
    @Autowired
    Orders orders;
    @Autowired
    OrderLines orderLines;


    private final LocalDate TODAY = LocalDate.now();

    private final LocalDateTime IN_TWO_DAYS = LocalDateTime.now().plusDays(2);
    private final LocalDateTime IN_THREE_DAYS = LocalDateTime.now().plusDays(3);


    @Test
    void given_validOrder_when_storingOrderWithOrderLines_then_allEntitiesAreStored() {

        // given
        Customer customer = customers.saveDefault();

        // and
        Product product = products.save(
                products.defaultData().name("Giant Stance E+ 1").build()
        );

        // and
        StockItem stockItem1 = stockItems.save(
                stockItems.defaultData()
                        .product(product)
                        .identifier("SI123")
                        .build()
        );
        StockItem stockItem2 = stockItems.save(
                stockItems.defaultData()
                        .product(product)
                        .identifier("SI234")
                        .build()
        );

        // when
        Order order = orders.save(
                orders.defaultData()
                        .customer(customer)
                        .orderDate(TODAY)
                        .build()
        );

        // and
        OrderLine orderLine1 = orderLines.save(
                orderLines.defaultData()
                        .order(order)
                        .stockItem(stockItem1)
                        .endsAt(IN_TWO_DAYS)
                        .build()
        );

        // and
        OrderLine orderLine2 = orderLines.save(
                orderLines.defaultData()
                        .order(order)
                        .stockItem(stockItem2)
                        .endsAt(IN_THREE_DAYS)
                        .build()
        );

        // then
        Order savedOrder = dataManager.load(Id.of(order)).one();

        // and
        assertThat(savedOrder.getCustomer())
                .isEqualTo(customer);

        // and
        assertThat(savedOrder.getOrderLines())
                .containsExactlyInAnyOrder(orderLine1, orderLine2);
    }
}
