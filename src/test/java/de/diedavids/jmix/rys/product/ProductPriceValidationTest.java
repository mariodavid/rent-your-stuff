package de.diedavids.jmix.rys.product;

import de.diedavids.jmix.rys.test_support.ValidationVerification;
import io.jmix.core.DataManager;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductPriceValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    ValidationVerification validationVerification;
    
    private ProductPrice productPrice;

    @BeforeEach
    void setUp() {
        productPrice = dataManager.create(ProductPrice.class);
    }

    @Test
    void given_validProductPrice_when_validate_then_noViolationOccurs() {

        // given
        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setAmount(ONE);
        productPrice.setProduct(dataManager.create(Product.class));

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(productPrice);

        // then
        assertThat(violations)
                .isEmpty();
    }

    @Test
    void given_productPriceWithoutUnit_when_validate_then_oneViolationOccurs() {

        // given
        productPrice.setAmount(ONE);
        productPrice.setProduct(dataManager.create(Product.class));

        // and
        productPrice.setUnit(null);

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(productPrice);

        // then
        assertThat(violations)
                .hasSize(1);

        ValidationVerification.ValidationResult unitViolation = violations.get(0);

        assertThat(unitViolation.getAttribute())
                .isEqualTo("unit");

        assertThat(unitViolation.getErrorType())
                .isEqualTo(validationVerification.validationMessage("NotNull"));
    }

    @Test
    void given_productPriceWithoutAmount_when_validate_then_oneViolationOccurs() {

        // given
        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setProduct(dataManager.create(Product.class));

        // and
        productPrice.setAmount(null);

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(productPrice);

        // then
        assertThat(violations)
                .hasSize(1);

        ValidationVerification.ValidationResult amountViolation = violations.get(0);

        assertThat(amountViolation.getAttribute())
                .isEqualTo("amount");

        assertThat(amountViolation.getErrorType())
                .isEqualTo(validationVerification.validationMessage("NotNull"));
    }

    @Test
    void given_productPriceWithNegativeAmount_when_validate_then_oneViolationOccurs() {

        // given
        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setProduct(dataManager.create(Product.class));

        // and
        productPrice.setAmount(negativeAmount());

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(productPrice);

        // then
        assertThat(violations)
                .hasSize(1);

        ValidationVerification.ValidationResult amountViolation = violations.get(0);

        assertThat(amountViolation.getAttribute())
                .isEqualTo("amount");

        assertThat(amountViolation.getErrorType())
                .isEqualTo(validationVerification.validationMessage("PositiveOrZero"));
    }

    @Test
    void given_productPriceWithoutProduct_when_validate_then_oneViolationOccurs() {

        // given
        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setAmount(ONE);

        // and
        productPrice.setProduct(null);

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(productPrice);

        // then
        assertThat(violations)
                .hasSize(1);

        ValidationVerification.ValidationResult amountViolation = violations.get(0);

        assertThat(amountViolation.getAttribute())
                .isEqualTo("product");

        assertThat(amountViolation.getErrorType())
                .isEqualTo(validationVerification.validationMessage("NotNull"));
    }

    @NotNull
    private BigDecimal negativeAmount() {
        return valueOf(-10d);
    }
}