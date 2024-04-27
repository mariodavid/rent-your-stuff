package de.diedavids.jmix.rys.product.view;

import de.diedavids.jmix.rys.product.*;
import de.diedavids.jmix.rys.test_support.DatabaseCleanup;
import de.diedavids.jmix.rys.test_support.test_data.ProductCategories;
import de.diedavids.jmix.rys.test_support.test_data.ProductPrices;
import de.diedavids.jmix.rys.test_support.test_data.Products;
import de.diedavids.jmix.rys.test_support.ui.UiIntegrationTest;
import de.diedavids.jmix.rys.test_support.ui.interactions.DataGridInteractions;
import de.diedavids.jmix.rys.test_support.ui.interactions.FormInteractions;
import de.diedavids.jmix.rys.test_support.ui.interactions.ViewInteractions;
import de.diedavids.jmix.rys.view.product.ProductDetailView;
import de.diedavids.jmix.rys.view.productprice.ProductPriceDetailView;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.util.OperationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ProductDetailViewTest extends UiIntegrationTest {

    @Autowired
    DataManager dataManager;
    @Autowired
    DatabaseCleanup databaseCleanup;
    @Autowired
    private Products products;
    @Autowired
    private ProductPrices productPrices;
    @Autowired
    private ProductCategories productCategories;

    private FormInteractions formInteractions;
    @Autowired
    private ViewNavigators viewNavigators;
    @Autowired
    private DialogWindows dialogWindows;

    private ViewInteractions viewInteractions;
    private ProductDetailView detailView;


    private void initProductDetailView() {
        viewInteractions = ViewInteractions.of(viewNavigators, dialogWindows);
        detailView = viewInteractions.openDetailForNew(ProductDetailView.class, Product.class);
        formInteractions = FormInteractions.of(detailView);
    }

    private Optional<Product> findProductByAttribute(String attribute, String value) {
        return dataManager.load(Product.class)
                .condition(PropertyCondition.equal(attribute, value))
                .optional();
    }


    @Nested
    class WithOpenedProductEditForm {

        @BeforeEach
        void setUp() {
            initProductDetailView();
        }

        @Test
        void given_validProduct_when_saveProductThroughTheForm_then_productIsSaved() {

            // given:
            var productData = products.defaultData().build();
            formInteractions.components().textField("nameField").setValue(productData.getName());

            // when:
            OperationResult operationResult = formInteractions.saveForm();

            // then:
            assertThat(operationResult).isEqualTo(OperationResult.success());

            // and:
            assertThat(findProductByAttribute("name", productData.getName())).isPresent();
        }

        @Test
        void given_productWithoutName_when_saveProductThroughTheForm_then_productIsNotSaved() {

            // given:
            formInteractions.components().textField("nameField").setValue("");

            // when:
            OperationResult operationResult = formInteractions.saveForm();

            // then:
            assertThat(operationResult).isEqualTo(OperationResult.fail());

            // and:
            assertThat(dataManager.load(Product.class).all().list()).isEmpty();
        }

        @Test
        void given_validProductWithPrice_when_saveProductThroughTheForm_then_productAndPriceAreSaved() {
            // given:
            ProductData productData = products.defaultData().build();
            formInteractions.components().textField("nameField").setValue(productData.getName());

            formInteractions.components().selectTab("contentTabSheet", "pricesTab");


            // and:
            var pricesDataGrid = DataGridInteractions.of(detailView, "pricesDataGrid");
            pricesDataGrid.create("createPriceBtn");

            var productPriceDetailView = viewInteractions.findCurrentView(ProductPriceDetailView.class);
            FormInteractions priceForm = FormInteractions.of(productPriceDetailView);

            ProductPriceData productPriceData = productPrices.defaultData().build();
            priceForm.components().bigDecimalField("priceAmountField").setTypedValue(productPriceData.getPrice().getAmount());
            priceForm.components().selectField("unitField").setValue(productPriceData.getUnit());

            // when:
            OperationResult priceFormResult = priceForm.saveForm();

            // then:
            assertThat(priceFormResult).isEqualTo(OperationResult.success());

            // when:
            OperationResult productFormResult = formInteractions.saveForm();

            assertThat(productFormResult).isEqualTo(OperationResult.success());

            // then:
            Optional<Product> savedProduct = findProductByAttribute("name", productData.getName());

            assertThat(savedProduct).isPresent();

            // and:
            List<ProductPrice> prices = savedProduct.get().getPrices();

            assertThat(prices).hasSize(1);
            ProductPrice price = prices.get(0);

            assertThat(price.getPrice().getAmount()).isEqualByComparingTo(productPriceData.getPrice().getAmount());
            assertThat(price.getUnit()).isEqualTo(productPriceData.getUnit());
        }
    }


    @Nested
    class ProductCategoryTests {

        private ProductCategory productCategory1;
        private ProductCategory productCategory2;

        @BeforeEach
        void setUp() {
            productCategory1 = productCategories.saveDefault();
            productCategory2 = productCategories.saveDefault();
            initProductDetailView();
        }

        @Test
        void given_twoProductCategoriesAreInDb_when_openingTheProductEditor_then_categoriesAreDisplayedInTheComboBox() {

            // expect:
            Stream<ProductCategory> availableProductCategories = formInteractions.components().getEntityComboBoxValues("categoryField");

            assertThat(availableProductCategories)
                    .contains(productCategory1, productCategory2);
        }

        @Test
        void given_validProductWithCategory_when_saveProductThroughTheForm_then_productAndCategoryAssociationAreSaved() {

            // given:
            ProductData productData = products.defaultData().build();
            formInteractions.components().textField("nameField").setValue(productData.getName());

            // and:
            formInteractions.components().setEntityComboBoxFieldValue("categoryField", productCategory1);

            // when:
            OperationResult operationResult = formInteractions.saveForm();

            assertThat(operationResult)
                    .isEqualTo(OperationResult.success());

            // then:
            Optional<Product> savedProduct = findProductByAttribute("name", productData.getName());

            assertThat(savedProduct)
                    .isPresent()
                    .get()
                    .extracting(Product::getCategory)
                    .isEqualTo(productCategory1);
        }
    }
}
