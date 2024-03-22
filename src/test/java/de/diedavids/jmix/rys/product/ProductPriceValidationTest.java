package de.diedavids.jmix.rys.product;

import de.diedavids.jmix.rys.entity.Currency;
import de.diedavids.jmix.rys.entity.Money;
import de.diedavids.jmix.rys.test_support.Validations;
import de.diedavids.jmix.rys.test_support.test_data.ProductPrices;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class ProductPriceValidationTest {

    @Autowired
    private DataManager dataManager;

    @Autowired
    private Validations validations;

    @Autowired
    private ProductPrices productPrices;

    @Test
    void given_validProductPrice_when_validate_then_noViolationOccurs() {

        // given
        ProductPrice productPrice = productPrices.createDefault();

        // expect
        validations.assertNoViolations(productPrice);
    }

    @Test
    void given_productPriceWithoutUnit_when_validate_then_oneViolationOccurs() {

        // given
        ProductPrice productPrice = productPrices.create(
                productPrices.defaultData()
                        .unit(null)
                        .build()
        );

        // expect
        validations.assertExactlyOneViolationWith(productPrice, "unit", "NotNull");
    }

    @Test
    void given_productPriceWithoutPrice_when_validate_then_oneViolationOccurs() {

        // given
        ProductPrice productPrice = productPrices.create(
                productPrices.defaultData()
                        .price(null)
                        .build()
        );

        // expect
        validations.assertExactlyOneViolationWith(productPrice, "price", "NotNull");
    }

    @Test
    void given_productPriceWithoutPriceAmount_when_validate_then_oneViolationOccurs() {

        // given
        ProductPrice productPrice = productPrices.create(
                productPrices.defaultData()
                        .price(eur(null))
                        .build()
        );

        // expect
        validations.assertExactlyOneViolationWith(productPrice, "price.amount", "NotNull");
    }

    @Test
    void given_productPriceWithoutProduct_when_validate_then_oneViolationOccurs() {

        // given
        ProductPrice productPrice = productPrices.create(
                productPrices.defaultData()
                        .product(null)
                        .build()
        );

        // expect
        validations.assertExactlyOneViolationWith(productPrice, "product", "NotNull");
    }

    private Money eur(BigDecimal amount) {

        Money money = dataManager.create(Money.class);
        money.setCurrency(Currency.EUR);
        money.setAmount(amount);

        return money;
    }
}
