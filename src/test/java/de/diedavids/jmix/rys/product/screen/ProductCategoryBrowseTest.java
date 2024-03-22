package de.diedavids.jmix.rys.product.screen;

import de.diedavids.jmix.rys.product.ProductCategory;
import de.diedavids.jmix.rys.product.screen.productcategory.ProductCategoryBrowse;
import de.diedavids.jmix.rys.product.screen.productcategory.ProductCategoryEdit;
import de.diedavids.jmix.rys.test_support.test_data.ProductCategories;
import de.diedavids.jmix.rys.test_support.ui.ScreenInteractions;
import de.diedavids.jmix.rys.test_support.ui.TableInteractions;
import de.diedavids.jmix.rys.test_support.ui.WebIntegrationTest;
import io.jmix.core.DataManager;
import io.jmix.ui.Screens;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class ProductCategoryBrowseTest extends WebIntegrationTest {

    @Autowired
    DataManager dataManager;

    private ProductCategory productCategory;
    @Autowired
    private ProductCategories productCategories;


    @BeforeEach
    void setUp() {
        productCategory = productCategories.saveDefault();
    }

    @Test
    void given_oneProductCategoryExists_when_openProductCategoryBrowse_then_tableContainsTheProductCategory(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        ProductCategoryBrowse productCategoryBrowse = screenInteractions.open(ProductCategoryBrowse.class);
        TableInteractions<ProductCategory> productCategoryTable = productCategoryTable(productCategoryBrowse);

        // expect:
        assertThat(productCategoryTable.firstItem())
                .isEqualTo(productCategory);
    }


    @Test
    void given_oneProductCategoryExists_when_editProductCategory_then_editProductCategoryEditorIsShown(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        ProductCategoryBrowse productCategoryBrowse = screenInteractions.open(ProductCategoryBrowse.class);
        TableInteractions<ProductCategory> productCategoryTable = productCategoryTable(productCategoryBrowse);

        // and:
        ProductCategory firstProductCategory = productCategoryTable.firstItem();

        // and:
        productCategoryTable.edit(firstProductCategory);

        // then:
        ProductCategoryEdit productCategoryEdit = screenInteractions.findOpenScreen(ProductCategoryEdit.class);

        assertThat(productCategoryEdit.getEditedEntity())
                .isEqualTo(firstProductCategory);
    }

    private TableInteractions<ProductCategory> productCategoryTable(ProductCategoryBrowse productCategoryBrowse) {
        return TableInteractions.of(productCategoryBrowse, ProductCategory.class, "productCategoriesTable");
    }
}
