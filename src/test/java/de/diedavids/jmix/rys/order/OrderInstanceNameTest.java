package de.diedavids.jmix.rys.order;

import de.diedavids.jmix.rys.customer.Customer;
import de.diedavids.jmix.rys.test_support.TenantUserEnvironment;
import de.diedavids.jmix.rys.test_support.test_data.Customers;
import de.diedavids.jmix.rys.test_support.test_data.Orders;
import io.jmix.core.DataManager;
import io.jmix.core.MetadataTools;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(TenantUserEnvironment.class)
class OrderInstanceNameTest {

    @Autowired
    MetadataTools metadataTools;

    @Autowired
    private Orders orders;
    @Autowired
    private Customers customers;


    @Test
    void given_orderContainsCustomerAndOrderDate_expect_instanceNameContainsFormattedValues() {

        // given
        Customer customer = customers.save(
                customers.defaultData()
                        .firstName("Mr.")
                        .lastName("Miyagi")
                        .build()
        );

        // and
        Order order = orders.create(
                orders.defaultData()
                        .customer(customer)
                        .orderDate(LocalDate.parse("2022-01-01"))
                        .build()
        );

        // expect
        assertThat(metadataTools.getInstanceName(order))
                .isEqualTo("Mr. Miyagi - 01/01/2022");
    }
}