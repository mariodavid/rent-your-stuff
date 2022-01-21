package de.diedavids.jmix.rys.product;

import de.diedavids.jmix.rys.test_support.ValidationVerification;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    ValidationVerification validationVerification;
    
    private Product product;

    @BeforeEach
    void setUp() {
        product = dataManager.create(Product.class);
    }

    @Test
    void given_validProduct_when_validate_then_noViolationOccurs() {

        // given
        product.setName("validName");

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(product);

        // then
        assertThat(violations)
                .isEmpty();
    }

    @Test
    void given_productWithoutName_when_validate_then_oneViolationOccurs() {

        // given
        product.setName(null);

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(product);

        // then
        assertThat(violations)
                .hasSize(1);
    }
}