package de.diedavids.jmix.rys.product.screen;

import de.diedavids.jmix.rys.product.ProductCategory;
import de.diedavids.jmix.rys.product.ProductCategoryData;
import de.diedavids.jmix.rys.product.screen.productcategory.ProductCategoryEdit;
import de.diedavids.jmix.rys.test_support.test_data.ProductCategories;
import de.diedavids.jmix.rys.test_support.ui.FormInteractions;
import de.diedavids.jmix.rys.test_support.ui.ScreenInteractions;
import de.diedavids.jmix.rys.test_support.ui.WebIntegrationTest;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.ui.Screens;
import io.jmix.ui.util.OperationResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ProductCategoryEditTest extends WebIntegrationTest {

    @Autowired
    DataManager dataManager;
    FormInteractions formInteractions;
    @Autowired
    private ProductCategories productCategories;

    @Test
    void given_validProductCategory_when_saveProductCategoryThroughTheForm_then_productCategoryIsSaved(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        ProductCategoryEdit productCategoryEdit = screenInteractions.openEditorForCreation(ProductCategoryEdit.class, ProductCategory.class);
        formInteractions = FormInteractions.of(productCategoryEdit);

        // and:
        ProductCategoryData categoryData = productCategories.defaultData().build();
        formInteractions.setTextFieldValue("nameField", categoryData.getName());

        // when:
        OperationResult operationResult = formInteractions.saveForm();

        assertThat(operationResult)
                .isEqualTo(OperationResult.success());

        // then:
        Optional<ProductCategory> savedProductCategory = findProductCategoryByAttribute("name", categoryData.getName());

        assertThat(savedProductCategory)
                .isPresent();
    }

    @Test
    void given_productCategoryWithoutName_when_saveProductCategoryThroughTheForm_then_productCategoryIsNotSaved(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        ProductCategoryEdit productCategoryEdit = screenInteractions.openEditorForCreation(ProductCategoryEdit.class, ProductCategory.class);
        formInteractions = FormInteractions.of(productCategoryEdit);

        // and:
        formInteractions.setTextFieldValue("nameField", null);

        // when:
        OperationResult operationResult = formInteractions.saveForm();


        // then:
        assertThat(operationResult)
                .isEqualTo(OperationResult.fail());

        // and:
        assertThat(dataManager.load(ProductCategory.class).all().list())
                .isEmpty();

    }

    private Optional<ProductCategory> findProductCategoryByAttribute(String attribute, String value) {
        return dataManager.load(ProductCategory.class)
                .condition(PropertyCondition.equal(attribute, value))
                .optional();
    }
}
