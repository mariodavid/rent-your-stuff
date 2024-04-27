package de.diedavids.jmix.rys.product.view;

import de.diedavids.jmix.rys.product.ProductCategory;
import de.diedavids.jmix.rys.product.view.productcategory.ProductCategoryDetailView;
import de.diedavids.jmix.rys.test_support.DatabaseCleanup;
import de.diedavids.jmix.rys.test_support.test_data.ProductCategories;
import de.diedavids.jmix.rys.test_support.ui.UiIntegrationTest;
import de.diedavids.jmix.rys.test_support.ui.interactions.FormInteractions;
import de.diedavids.jmix.rys.test_support.ui.interactions.ViewInteractions;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.util.OperationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ProductCategoryDetailViewTest extends UiIntegrationTest {

    @Autowired
    DataManager dataManager;
    @Autowired
    DatabaseCleanup databaseCleanup;
    @Autowired
    private ProductCategories productCategories;

    private FormInteractions formInteractions;
    @Autowired
    private ViewNavigators viewNavigators;
    @Autowired
    private DialogWindows dialogWindows;

    private ViewInteractions viewInteractions;
    private ProductCategoryDetailView detailView;


    private void initProductCategoryEditForm() {
        viewInteractions = ViewInteractions.of(viewNavigators, dialogWindows);
        detailView = viewInteractions.openDetailForNew(ProductCategoryDetailView.class, ProductCategory.class);
        formInteractions = FormInteractions.of(detailView);
    }

    private Optional<ProductCategory> findProductCategoryByAttribute(String attribute, String value) {
        return dataManager.load(ProductCategory.class)
                .condition(PropertyCondition.equal(attribute, value))
                .optional();
    }


    @Nested
    class WithOpenedProductCategoryEditForm {

        @BeforeEach
        void setUp() {
            initProductCategoryEditForm();
        }

        @Test
        void given_validProductCategory_when_saveProductCategoryThroughTheForm_then_productCategoryIsSaved() {

            // given:
            var productCategoryData = productCategories.defaultData().build();
            formInteractions.components().textField("nameField").setValue(productCategoryData.getName());

            // when:
            OperationResult operationResult = formInteractions.saveForm();

            // then:
            assertThat(operationResult).isEqualTo(OperationResult.success());

            // and:
            assertThat(findProductCategoryByAttribute("name", productCategoryData.getName())).isPresent();
        }

        @Test
        void given_productCategoryWithoutName_when_saveProductCategoryThroughTheForm_then_productCategoryIsNotSaved() {

            // given:
            formInteractions.components().textField("nameField").setValue("");

            // when:
            OperationResult operationResult = formInteractions.saveForm();

            // then:
            assertThat(operationResult).isEqualTo(OperationResult.fail());

            // and:
            assertThat(dataManager.load(ProductCategory.class).all().list()).isEmpty();
        }

    }
}
