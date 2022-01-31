package de.diedavids.jmix.rys.product;

import de.diedavids.jmix.rys.entity.Currency;
import de.diedavids.jmix.rys.entity.Money;
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
        productPrice.setPrice(eur(ONE));
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
        productPrice.setPrice(eur(ONE));
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
    void given_productPriceWithoutPrice_when_validate_then_oneViolationOccurs() {

        // given
        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setProduct(dataManager.create(Product.class));

        // and
        productPrice.setPrice(null);

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(productPrice);

        // then
        assertThat(violations)
                .hasSize(1);

        ValidationVerification.ValidationResult amountViolation = violations.get(0);

        assertThat(amountViolation.getAttribute())
                .isEqualTo("price");

        assertThat(amountViolation.getErrorType())
                .isEqualTo(validationVerification.validationMessage("NotNull"));
    }

    @Test
    void given_productPriceWithoutPriceAmount_when_validate_then_oneViolationOccurs() {

        // given
        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setProduct(dataManager.create(Product.class));

        // and
        productPrice.setPrice(eur(null));

        // when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(productPrice);

        // then
        assertThat(violations)
                .hasSize(1);

        ValidationVerification.ValidationResult amountViolation = violations.get(0);

        assertThat(amountViolation.getAttribute())
                .isEqualTo("price.amount");

        assertThat(amountViolation.getErrorType())
                .isEqualTo(validationVerification.validationMessage("NotNull"));
    }

    @Test
    void given_productPriceWithoutProduct_when_validate_then_oneViolationOccurs() {

        // given
        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setPrice(eur(ONE));

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

    private Money eur(BigDecimal amount) {

        Money money = dataManager.create(Money.class);
        money.setCurrency(Currency.EUR);
        money.setAmount(amount);

        return money;
    }
}