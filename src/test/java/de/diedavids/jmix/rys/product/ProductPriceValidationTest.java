package de.diedavids.jmix.rys.product;

import de.diedavids.jmix.rys.entity.Currency;
import de.diedavids.jmix.rys.entity.Money;
import de.diedavids.jmix.rys.test_support.Validations;
import io.jmix.core.DataManager;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
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
    Validations validations;
    
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
        productPrice.setProduct(someProduct());

        // expect
        validations.assertNoViolations(productPrice);
    }

    @Test
    void given_productPriceWithoutUnit_when_validate_then_oneViolationOccurs() {

        // given
        productPrice.setPrice(eur(ONE));
        productPrice.setProduct(someProduct());

        // and
        productPrice.setUnit(null);

        // expect
        validations.assertExactlyOneViolationWith(productPrice, "unit", "NotNull");
    }

    @Test
    void given_productPriceWithoutPrice_when_validate_then_oneViolationOccurs() {

        // given
        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setProduct(someProduct());

        // and
        productPrice.setPrice(null);

        // expect
        validations.assertExactlyOneViolationWith(productPrice, "price", "NotNull");
    }

    @Test
    void given_productPriceWithoutPriceAmount_when_validate_then_oneViolationOccurs() {

        // given
        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setProduct(someProduct());

        // and
        productPrice.setPrice(eur(null));

        // expect
        validations.assertExactlyOneViolationWith(productPrice, "price.amount", "NotNull");
    }

    @Test
    void given_productPriceWithoutProduct_when_validate_then_oneViolationOccurs() {

        // given
        productPrice.setUnit(PriceUnit.DAY);
        productPrice.setPrice(eur(ONE));

        // and
        productPrice.setProduct(null);

        // expect
        validations.assertExactlyOneViolationWith(productPrice, "product", "NotNull");
    }

    private Money eur(BigDecimal amount) {

        Money money = dataManager.create(Money.class);
        money.setCurrency(Currency.EUR);
        money.setAmount(amount);

        return money;
    }

    @NotNull
    private Product someProduct() {
        return dataManager.create(Product.class);
    }

}