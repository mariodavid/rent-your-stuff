package de.diedavids.jmix.rys.customer;

import de.diedavids.jmix.rys.entity.Address;
import de.diedavids.jmix.rys.test_support.ValidationVerification;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AddressValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    ValidationVerification validationVerification;

    private Address address;

    @BeforeEach
    void setUp() {
        address = dataManager.create(Address.class);
    }

    @Test
    void given_addressWithInvalidStreet_when_validateAddress_then_oneViolationOccurs() {

        // given
        address.setStreet(null);

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(address);

        // then
        assertThat(violations)
                .hasSize(1);
    }

    @Test
    void given_addressWithInvalidStreet_when_validateAddress_then_addressIsInvalidBecauseOfStreet() {

        // given
        address.setStreet(null);

        // when
        ValidationVerification.ValidationResult streetViolation = validationVerification.validateFirst(address);

        // and
        assertThat(streetViolation.getAttribute())
                .isEqualTo("street");

        assertThat(streetViolation.getErrorType())
                .isEqualTo(validationVerification.validationMessage("NotBlank"));
    }

}