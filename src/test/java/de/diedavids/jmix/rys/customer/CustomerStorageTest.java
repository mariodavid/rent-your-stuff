package de.diedavids.jmix.rys.customer;

import de.diedavids.jmix.rys.test_support.TenantUserEnvironment;
import de.diedavids.jmix.rys.test_support.test_data.Customers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static de.diedavids.jmix.rys.order.Assertions.assertThat;


@SpringBootTest
@ExtendWith(TenantUserEnvironment.class)
class CustomerStorageTest {

    @Autowired
    Customers customers;

    @Test
    void given_validCustomer_when_saveCustomer_then_customerIsSaved() {

        // given
        Customer savedCustomer = customers.save(
                customers.defaultData()
                        .firstName("Foo")
                        .lastName("Bar")
                        .address(customers.defaultAddress())
                        .build()
        );

        // then
        assertThat(savedCustomer)
                .hasFirstName("Foo")
                .hasLastName("Bar");
    }
}