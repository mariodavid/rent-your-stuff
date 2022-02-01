package de.diedavids.jmix.rys.product.screen;

import de.diedavids.jmix.rys.product.PriceUnit;
import de.diedavids.jmix.rys.product.Product;
import de.diedavids.jmix.rys.product.ProductCategory;
import de.diedavids.jmix.rys.product.ProductPrice;
import de.diedavids.jmix.rys.product.screen.productprice.ProductPriceEdit;
import de.diedavids.jmix.rys.test_support.DatabaseCleanup;
import de.diedavids.jmix.rys.test_support.ui.FormInteractions;
import de.diedavids.jmix.rys.test_support.ui.ScreenInteractions;
import de.diedavids.jmix.rys.test_support.ui.TableInteractions;
import de.diedavids.jmix.rys.test_support.ui.WebIntegrationTest;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.ui.Screens;
import io.jmix.ui.util.OperationResult;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProductEditTest extends WebIntegrationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    DatabaseCleanup databaseCleanup;

    FormInteractions formInteractions;


    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities(Product.class);
        databaseCleanup.removeAllEntities(ProductCategory.class);
    }

    @Test
    void given_validProduct_when_saveProductThroughTheForm_then_productIsSaved(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        ProductEdit productEdit = screenInteractions.openEditorForCreation(ProductEdit.class, Product.class);
        formInteractions = FormInteractions.of(productEdit);

        // and:
        String name = "Foo Product" + UUID.randomUUID();
        formInteractions.setTextFieldValue("nameField", name);

        // when:
        OperationResult operationResult = formInteractions.saveForm();

        assertThat(operationResult)
                .isEqualTo(OperationResult.success());

        // then:
        Optional<Product> savedProduct = findProductByAttribute("name", name);

        assertThat(savedProduct)
                .isPresent();
    }

    @Test
    void given_twoProductCategoriesAreInDb_when_openingTheProductEditor_then_categoriesAreDisplayedInTheComboBox(Screens screens) {

        // given:
        ProductCategory productCategory1 = saveProductCategory("Product Category 1");
        ProductCategory productCategory2 = saveProductCategory("Product Category 2");


        // when:
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        ProductEdit productEdit = screenInteractions.openEditorForCreation(ProductEdit.class, Product.class);
        formInteractions = FormInteractions.of(productEdit);

        // expect:
        List<ProductCategory> availableProductCategories = formInteractions.getEntityComboBoxValues("categoryField", ProductCategory.class);

        assertThat(availableProductCategories)
                .contains(productCategory1, productCategory2);
    }

    @Test
    void given_validProductWithCategory_when_saveProductThroughTheForm_then_productAndCategoryAssociationAreSaved(Screens screens) {

        // given:
        ProductCategory productCategory1 = saveProductCategory("Product Category 1");

        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        ProductEdit productEdit = screenInteractions.openEditorForCreation(ProductEdit.class, Product.class);
        formInteractions = FormInteractions.of(productEdit);

        // and:
        String name = "Foo Product" + UUID.randomUUID();
        formInteractions.setTextFieldValue("nameField", name);

        // and:
        formInteractions.setEntityComboxBoxFieldValue("categoryField", productCategory1, ProductCategory.class);

        // when:
        OperationResult operationResult = formInteractions.saveForm();

        assertThat(operationResult)
                .isEqualTo(OperationResult.success());

        // then:
        Optional<Product> savedProduct = findProductByAttribute("name", name);

        assertThat(savedProduct)
                .isPresent()
                .get()
                .extracting("category")
                .isEqualTo(productCategory1);
    }

    @Test
    void given_validProductWithPrice_when_saveProductThroughTheForm_then_productAndPriceAreSaved(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        ProductEdit productEdit = screenInteractions.openEditorForCreation(ProductEdit.class, Product.class);
        formInteractions = FormInteractions.of(productEdit);

        // and:
        String name = "Foo Product" + UUID.randomUUID();
        formInteractions.setTextFieldValue("nameField", name);

        // and:
        TableInteractions<ProductPrice> pricesTable = TableInteractions.of(productEdit, ProductPrice.class, "pricesTable");

        pricesTable.create();

        ProductPriceEdit productPriceEdit = screenInteractions.findOpenScreen(ProductPriceEdit.class);
        FormInteractions priceForm = FormInteractions.of(productPriceEdit);

        BigDecimal expectedAmount = BigDecimal.TEN;
        PriceUnit expectedUnit = PriceUnit.DAY;

        priceForm.setCurrencyFieldValue("priceAmountField", expectedAmount);
        priceForm.setEnumFieldValue("unitField", expectedUnit);

        // when:
        OperationResult priceFormResult = priceForm.saveForm();

        // then:
        assertThat(priceFormResult)
                .isEqualTo(OperationResult.success());

        // when:
        OperationResult productFormResult = formInteractions.saveForm();

        assertThat(productFormResult)
                .isEqualTo(OperationResult.success());

        // then:
        Optional<Product> savedProduct = findProductByAttribute("name", name);

        assertThat(savedProduct)
                .isPresent();


        // and:
        List<ProductPrice> prices = savedProduct.get().getPrices();

        assertThat(prices)
                .hasSize(1);

        // and:
        ProductPrice price = prices.get(0);

        assertThat(price.getPrice().getAmount())
                .isEqualByComparingTo(expectedAmount);

        assertThat(price.getUnit())
                .isEqualTo(expectedUnit);
    }

    @Test
    void given_productWithoutStreet_when_saveProductThroughTheForm_then_productIsNotSaved(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        ProductEdit productEdit = screenInteractions.openEditorForCreation(ProductEdit.class, Product.class);
        formInteractions = FormInteractions.of(productEdit);

        // and:
        formInteractions.setTextFieldValue("nameField", null);

        // when:
        OperationResult operationResult = formInteractions.saveForm();


        // then:
        assertThat(operationResult)
                .isEqualTo(OperationResult.fail());

        // and:
        assertThat(dataManager.load(Product.class).all().list())
                .isEmpty();

    }

    @NotNull
    private Optional<Product> findProductByAttribute(String attribute, String value) {
        return dataManager.load(Product.class)
                .condition(PropertyCondition.equal(attribute, value))
                .optional();
    }

    @NotNull
    private ProductCategory saveProductCategory(String name) {
        ProductCategory productCategory = dataManager.create(ProductCategory.class);
        productCategory.setName(name);
        return dataManager.save(productCategory);
    }
}