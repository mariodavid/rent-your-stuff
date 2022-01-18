package de.diedavids.jmix.rys.customer;

import de.diedavids.jmix.rys.entity.Address;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerIntegrationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    SystemAuthenticator systemAuthenticator;

    @Autowired
    Validator validator;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = dataManager.create(Customer.class);
    }

    @Test
    void given_validCustomer_when_saveCustomer_then_customerIsSaved() {

        // given
        customer.setFirstName("Foo");
        customer.setLastName("Bar");
        customer.setEmail("foo@bar.com");

        Address address = dataManager.create(Address.class);

        address.setStreet("Foo Street 1");
        address.setPostCode("38919");
        address.setCity("Bar City");

        customer.setAddress(address);

        // when
        Customer savedCustomer = systemAuthenticator.withSystem(() -> dataManager.save(customer));

        // then
        assertThat(savedCustomer.getId())
                .isNotNull();
    }

    @Test
    void given_customerWithInvalidEmail_when_validateCustomer_then_customerIsInvalid() {

        // given
        customer.setEmail("invalidEmailAddress");

        // when
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer, Default.class);

        // then
        assertThat(violations)
                .hasSize(1);

        // and
        assertThat(firstViolation(violations).getPropertyPath().toString())
                .isEqualTo("email");

        assertThat(firstViolation(violations).getMessageTemplate())
                .isEqualTo("{javax.validation.constraints.Email.message}");
    }

    private ConstraintViolation<Customer> firstViolation(Set<ConstraintViolation<Customer>> violations) {
        return violations.stream().findFirst().orElseThrow();
    }
}