package de.diedavids.jmix.rys.product;

import de.diedavids.jmix.rys.entity.Currency;
import de.diedavids.jmix.rys.entity.Money;
import de.diedavids.jmix.rys.test_support.ValidationVerification;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductCategoryValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    ValidationVerification validationVerification;
    
    private ProductCategory category;

    @BeforeEach
    void setUp() {
        category = dataManager.create(ProductCategory.class);
    }

    @Test
    void given_validProductCategory_when_validate_then_noViolationOccurs() {

        // given
        category.setName("Foo Category");

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(category);

        // then
        assertThat(violations)
                .isEmpty();
    }

    @NullSource
    @EmptySource
    @ParameterizedTest
    void given_productCategoryWithoutUnit_when_validate_then_oneViolationOccurs(String name) {

        // given
        category.setName(name);

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(category);

        // then
        assertThat(violations)
                .hasSize(1);

        ValidationVerification.ValidationResult unitViolation = violations.get(0);

        assertThat(unitViolation.getAttribute())
                .isEqualTo("name");

        assertThat(unitViolation.getErrorType())
                .isEqualTo(validationVerification.validationMessage("NotBlank"));
    }
}