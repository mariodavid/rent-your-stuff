package de.diedavids.jmix.rys.product;

import de.diedavids.jmix.rys.entity.Currency;
import de.diedavids.jmix.rys.entity.Money;
import de.diedavids.jmix.rys.test_support.DatabaseCleanup;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.security.SystemAuthenticator;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static de.diedavids.jmix.rys.product.PriceUnit.*;
import static java.math.BigDecimal.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductStorageTest {

    @Autowired
    DatabaseCleanup databaseCleanup;
    @Autowired
    DataManager dataManager;

    @Autowired
    SystemAuthenticator systemAuthenticator;

    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities(Product.class);
        databaseCleanup.removeAllEntities(ProductPrice.class);
    }

    @Test
    void given_validProduct_when_save_then_productIsSaved() {
        // given
        Product product = dataManager.create(Product.class);
        product.setName("Foo Product");
        product.setDescription("Foo Description");

        // when
        Product savedProduct = systemAuthenticator.withSystem(() ->
            dataManager.save(product)
        );

        // then
        assertThat(savedProduct.getId())
                .isNotNull();
    }

    @Test
    void given_validProductWithPrices_when_save_then_productAndPricesAreSaved() {
        // given
        Product product = dataManager.create(Product.class);
        product.setName("Foo Product");
        product.setDescription("Foo Description");

        // and
        ProductPrice pricePerDay = createProductPrice(ONE, DAY, product);
        ProductPrice pricePerWeek = createProductPrice(TEN, WEEK, product);
        product.setPrices(List.of(pricePerDay, pricePerWeek));

        // when
        Optional<Product> savedProduct = systemAuthenticator.withSystem(() -> {
            dataManager.save(product, pricePerDay, pricePerWeek);
            return dataManager.load(Id.of(product)).optional();
        });

        // then
        assertThat(savedProduct)
                .isPresent();
    }

    @NotNull
    private ProductPrice createProductPrice(BigDecimal amount, PriceUnit priceUnit, Product product) {
        ProductPrice pricePerWeek = dataManager.create(ProductPrice.class);
        Money money = dataManager.create(Money.class);
        money.setCurrency(Currency.EUR);
        money.setAmount(amount);
        pricePerWeek.setPrice(money);
        pricePerWeek.setProduct(product);
        pricePerWeek.setUnit(priceUnit);
        return pricePerWeek;
    }
}