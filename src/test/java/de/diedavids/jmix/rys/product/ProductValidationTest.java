package de.diedavids.jmix.rys.product;

import de.diedavids.jmix.rys.test_support.Validations;
import de.diedavids.jmix.rys.test_support.test_data.Products;
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
    private DataManager dataManager;

    @Autowired
    private Validations validations;
    @Autowired
    private Products products;


    @Test
    void given_validProduct_when_validate_then_noViolationOccurs() {

        // given
        Product product = products.createDefault();

        // when
        validations.assertNoViolations(product);
    }

    @Test
    void given_productWithoutName_when_validate_then_oneViolationOccurs() {

        // given
        Product product = products.create(
                products.defaultData()
                        .name(null)
                        .build()
        );

        // when
        validations.assertExactlyOneViolationWith(product, "name", "NotNull");
    }
}