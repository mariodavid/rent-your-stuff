package de.diedavids.jmix.rys.product;

import de.diedavids.jmix.rys.customer.Customer;
import de.diedavids.jmix.rys.entity.Address;
import de.diedavids.jmix.rys.test_support.DatabaseCleanup;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    }

    @Test
    void given_validProduct_when_save_then_productIsSaved() {
        Product product = dataManager.create(Product.class);
        // given
        product.setName("Foo Product");
        product.setDescription("Foo Description");

        // when
        Product savedProduct = systemAuthenticator.withSystem(() -> dataManager.save(product));

        // then
        assertThat(savedProduct.getId())
                .isNotNull();
    }
}