package de.diedavids.jmix.rys.order;

import de.diedavids.jmix.rys.customer.Customer;
import de.diedavids.jmix.rys.test_support.TenantUserEnvironment;
import de.diedavids.jmix.rys.test_support.Validations;
import io.jmix.core.DataManager;
import io.jmix.core.MetadataTools;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.BeforeEach;
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
    DataManager dataManager;
    @Autowired
    MetadataTools metadataTools;
    
    private Order order;

    @BeforeEach
    void setUp() {
        order = dataManager.create(Order.class);
    }

    @Test
    void given_orderContainsCustomerAndOrderDate_expect_instanceNameContainsFormattedValues() {

        // given
        Customer customer = dataManager.create(Customer.class);
        customer.setFirstName("Mr.");
        customer.setLastName("Miyagi");
        order.setCustomer(customer);

        // and
        order.setOrderDate(LocalDate.parse("2022-01-01"));

        // when
        String orderInstanceName = metadataTools.getInstanceName(order);

        // then
        assertThat(orderInstanceName)
                .isEqualTo("Mr. Miyagi - 01/01/2022");
    }
}