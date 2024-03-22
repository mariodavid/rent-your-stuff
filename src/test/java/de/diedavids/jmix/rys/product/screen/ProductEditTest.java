package de.diedavids.jmix.rys.product.screen;

import de.diedavids.jmix.rys.product.*;
import de.diedavids.jmix.rys.product.screen.productprice.ProductPriceEdit;
import de.diedavids.jmix.rys.product.screen.stockitem.StockItemEdit;
import de.diedavids.jmix.rys.test_support.test_data.ProductCategories;
import de.diedavids.jmix.rys.test_support.test_data.ProductPrices;
import de.diedavids.jmix.rys.test_support.test_data.Products;
import de.diedavids.jmix.rys.test_support.ui.FormInteractions;
import de.diedavids.jmix.rys.test_support.ui.ScreenInteractions;
import de.diedavids.jmix.rys.test_support.ui.TableInteractions;
import de.diedavids.jmix.rys.test_support.ui.WebIntegrationTest;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.ui.Screens;
import io.jmix.ui.util.OperationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static de.diedavids.jmix.rys.order.Assertions.assertThat;


class ProductEditTest extends WebIntegrationTest {

    FormInteractions formInteractions;
    ScreenInteractions screenInteractions;
    ProductEdit productEdit;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Products products;
    @Autowired
    private ProductPrices productPrices;
    @Autowired
    private ProductCategories productCategories;

    private void initProductEditForm(Screens screens) {
        screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        productEdit = screenInteractions.openEditorForCreation(ProductEdit.class, Product.class);
        formInteractions = FormInteractions.of(productEdit);
    }

    private Optional<Product> findProductByAttribute(String attribute, String value) {
        return dataManager.load(Product.class)
                .condition(PropertyCondition.equal(attribute, value))
                .optional();
    }

    @Nested
    class WithOpenedProductEditForm {

        @BeforeEach
        void setUp(Screens screens) {
            initProductEditForm(screens);
        }

        @Test
        void given_validProduct_when_saveProductThroughTheForm_then_productIsSaved() {

            // given:
            ProductData productData = products.defaultData().build();
            formInteractions.setTextFieldValue("nameField", productData.getName());

            // when:
            OperationResult operationResult = formInteractions.saveForm();

            assertThat(operationResult)
                    .isEqualTo(OperationResult.success());

            // then:
            Optional<Product> savedProduct = findProductByAttribute("name", productData.getName());

            assertThat(savedProduct)
                    .isPresent();
        }


        @Test
        void given_validProductWithPrice_when_saveProductThroughTheForm_then_productAndPriceAreSaved() {

            // given:
            ProductData productData = products.defaultData().build();
            formInteractions.setTextFieldValue("nameField", productData.getName());

            // and:
            TableInteractions<ProductPrice> pricesTable = TableInteractions.of(productEdit, ProductPrice.class, "pricesTable");

            pricesTable.create();

            ProductPriceEdit productPriceEdit = screenInteractions.findOpenScreen(ProductPriceEdit.class);
            FormInteractions priceForm = FormInteractions.of(productPriceEdit);

            ProductPriceData productPriceData = productPrices.defaultData().build();


            priceForm.setCurrencyFieldValue("priceAmountField", productPriceData.getPrice().getAmount());
            priceForm.setEnumFieldValue("unitField", productPriceData.getUnit());

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
            Optional<Product> savedProduct = findProductByAttribute("name", productData.getName());

            assertThat(savedProduct)
                    .isPresent();


            // and:
            List<ProductPrice> prices = savedProduct.get().getPrices();

            assertThat(prices)
                    .hasSize(1);

            // and:
            ProductPrice price = prices.get(0);

            assertThat(price.getPrice().getAmount())
                    .isEqualByComparingTo(productPriceData.getPrice().getAmount());

            assertThat(price)
                    .hasUnit(productPriceData.getUnit());

        }

        @Test
        void given_validProductWithStockItem_when_saveProductThroughTheForm_then_productAndStockItemAreSaved() {

            // given:
            String name = "Foo Product" + UUID.randomUUID();
            formInteractions.setTextFieldValue("nameField", name);

            // and:
            TableInteractions<StockItem> stockItemsTable = TableInteractions.of(productEdit, StockItem.class, "stockItemsTable");

            stockItemsTable.create();

            StockItemEdit stockItemEdit = screenInteractions.findOpenScreen(StockItemEdit.class);
            FormInteractions stockItemForm = FormInteractions.of(stockItemEdit);


            String expectedIdentifier = "Foo Identifier " + UUID.randomUUID();
            stockItemForm.setTextFieldValue("identifierField", expectedIdentifier);

            // when:
            OperationResult stockItemFormResult = stockItemForm.saveForm();

            // then:
            assertThat(stockItemFormResult)
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
            List<StockItem> stockItems = savedProduct.get().getStockItems();

            assertThat(stockItems)
                    .hasSize(1);

            // and:
            StockItem stockItem = stockItems.get(0);

            assertThat(stockItem.getIdentifier())
                    .isEqualTo(expectedIdentifier);
        }

        @Test
        void given_productWithoutName_when_saveProductThroughTheForm_then_productIsNotSaved(Screens screens) {

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

    }

    @Nested
    class ProductCategoryTests {

        private ProductCategory productCategory1;
        private ProductCategory productCategory2;

        @BeforeEach
        void setUp(Screens screens) {
            productCategory1 = productCategories.saveDefault();
            productCategory2 = productCategories.saveDefault();

            initProductEditForm(screens);
        }

        @Test
        void given_twoProductCategoriesAreInDb_when_openingTheProductEditor_then_categoriesAreDisplayedInTheComboBox(Screens screens) {

            // expect:
            List<ProductCategory> availableProductCategories = formInteractions.getEntityComboBoxValues("categoryField", ProductCategory.class);

            assertThat(availableProductCategories)
                    .contains(productCategory1, productCategory2);
        }

        @Test
        void given_validProductWithCategory_when_saveProductThroughTheForm_then_productAndCategoryAssociationAreSaved(Screens screens) {

            // given:
            ProductData productData = products.defaultData().build();
            formInteractions.setTextFieldValue("nameField", productData.getName());

            // and:
            formInteractions.setEntityComboBoxFieldValue("categoryField", productCategory1, ProductCategory.class);

            // when:
            OperationResult operationResult = formInteractions.saveForm();

            assertThat(operationResult)
                    .isEqualTo(OperationResult.success());

            // then:
            Optional<Product> savedProduct = findProductByAttribute("name", productData.getName());

            assertThat(savedProduct)
                    .isPresent()
                    .get()
                    .extracting("category")
                    .isEqualTo(productCategory1);
        }

    }
}
