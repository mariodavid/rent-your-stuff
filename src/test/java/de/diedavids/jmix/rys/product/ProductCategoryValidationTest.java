package de.diedavids.jmix.rys.product;

import de.diedavids.jmix.rys.test_support.Validations;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductCategoryValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    Validations validations;
    
    private ProductCategory category;

    @BeforeEach
    void setUp() {
        category = dataManager.create(ProductCategory.class);
    }

    @Test
    void given_validProductCategory_when_validate_then_noViolationOccurs() {

        // given
        category.setName("Foo Category");

        // expect
        validations.assertNoViolations(category);
    }

    @NullSource
    @EmptySource
    @ParameterizedTest
    void given_productCategoryWithoutUnit_when_validate_then_oneViolationOccurs(String name) {

        // given
        category.setName(name);

        // expect
        validations.assertExactlyOneViolationWith(category, "name", "NotBlank");
    }
}