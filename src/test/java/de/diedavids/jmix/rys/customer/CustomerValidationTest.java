package de.diedavids.jmix.rys.customer;

import de.diedavids.jmix.rys.test_support.Validations;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    Validations validations;
    
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = dataManager.create(Customer.class);
    }

    @Test
    void given_validCustomer_expect_noViolationOccurs() {

        // given
        customer.setEmail("valid@email.address");

        // expect
        validations.assertNoViolations(customer);
    }

    @Test
    void given_customerWithInvalidEmail_when_validateCustomer_then_oneViolationOccurs() {

        // given
        customer.setEmail("invalidEmailAddress");

        // expect
        validations.assertExactlyOneViolationWith(customer, "email", "Email");
    }

}