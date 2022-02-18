package de.diedavids.jmix.rys.product;

import de.diedavids.jmix.rys.test_support.Validations;
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
    Validations validations;
    
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
        validations.assertNoViolations(product);
    }

    @Test
    void given_productWithoutName_when_validate_then_oneViolationOccurs() {

        // given
        product.setName(null);

        // when
        validations.assertExactlyOneViolationWith(product, "name", "NotNull");
    }
}