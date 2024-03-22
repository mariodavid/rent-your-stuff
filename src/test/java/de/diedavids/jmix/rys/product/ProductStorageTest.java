package de.diedavids.jmix.rys.product;

import de.diedavids.jmix.rys.test_support.TenantUserEnvironment;
import de.diedavids.jmix.rys.test_support.test_data.ProductCategories;
import de.diedavids.jmix.rys.test_support.test_data.ProductPrices;
import de.diedavids.jmix.rys.test_support.test_data.Products;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.Id;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static de.diedavids.jmix.rys.entity.Currency.EUR;
import static de.diedavids.jmix.rys.product.PriceUnit.DAY;
import static de.diedavids.jmix.rys.product.PriceUnit.WEEK;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(TenantUserEnvironment.class)
class ProductStorageTest {

    @Autowired
    private DataManager dataManager;
    @Autowired
    private Products products;
    @Autowired
    private ProductPrices productPrices;
    @Autowired
    private ProductCategories productCategories;

    @Test
    void given_validProduct_when_save_then_productIsSaved() {

        // when
        Product product = products.save(
                products.defaultData()
                        .name("Foo Product")
                        .build()
        );

        // then
        assertThat(product.getId())
                .isNotNull();
    }

    @Test
    void given_validProductWithPrices_when_save_then_productAndPricesAreSaved() {
        // given
        Product product = products.saveDefault();

        // and
        ProductPrice pricePerDay = productPrices.save(
                productPrices.defaultData()
                        .product(product)
                        .price(productPrices.money(ONE, EUR))
                        .unit(DAY)
                        .build()
        );
        ProductPrice pricePerWeek = productPrices.save(
                productPrices.defaultData()
                        .product(product)
                        .price(productPrices.money(TEN, EUR))
                        .unit(WEEK)
                        .build()
        );

        // when
        Optional<Product> savedProduct =  dataManager.load(Id.of(product)).optional();

        // then
        assertThat(savedProduct)
                .isPresent();

        assertThat(savedProduct.get().getPrices())
                .containsExactlyInAnyOrder(pricePerDay, pricePerWeek);
    }

    @Test
    void given_validProductWithCategory_when_save_then_productAndCategoryAssociationAreSaved() {

        // given
        ProductCategory productCategory = productCategories.saveDefault();

        // when
        Product product = products.save(
                products.defaultData()
                        .category(productCategory)
                        .build()
        );

        // then
        assertThat(loadProductWithCategory(product))
                .isPresent()
                .get()
                .extracting("category")
                .isEqualTo(productCategory);
    }

    private Optional<Product> loadProductWithCategory(Product product) {
        return dataManager.load(Id.of(product))
                .fetchPlan(productFp -> {
                    productFp.addFetchPlan(FetchPlan.BASE);
                    productFp.add("category", categoryFp -> categoryFp.addFetchPlan(FetchPlan.BASE));
                })
                .optional();
    }
}
