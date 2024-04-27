package de.diedavids.jmix.rys.product.view;

import de.diedavids.jmix.rys.product.ProductCategory;
import de.diedavids.jmix.rys.product.view.productcategory.ProductCategoryDetailView;
import de.diedavids.jmix.rys.product.view.productcategory.ProductCategoryListView;
import de.diedavids.jmix.rys.test_support.test_data.ProductCategories;
import de.diedavids.jmix.rys.test_support.ui.UiIntegrationTest;
import de.diedavids.jmix.rys.test_support.ui.interactions.DataGridInteractions;
import de.diedavids.jmix.rys.test_support.ui.interactions.ViewInteractions;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.ViewNavigators;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class ProductCategoryListViewTest extends UiIntegrationTest {
    @Autowired
    private ProductCategories productCategories;
    @Autowired
    private ViewNavigators viewNavigators;
    @Autowired
    private DialogWindows dialogWindows;
    private ProductCategory entity;


    @BeforeEach
    void setUp() {
        entity = productCategories.saveDefault();
    }

    @Test
    void given_oneProductCategoryExists_when_openProductCategoryBrowse_then_datagridContainsTheProductCategory() {

        // given:
        ViewInteractions viewInteractions = ViewInteractions.of(viewNavigators, dialogWindows);
        var listView = viewInteractions.open(ProductCategoryListView.class);
        var dataGrid = dataGrid(listView);

        // expect:
        assertThat(dataGrid.items())
                .contains(entity);
    }

    @Test
    void given_oneProductCategoryExists_when_editProductCategory_then_ProductCategoryDetailViewIsShown() {

        // given:
        ViewInteractions viewInteractions = ViewInteractions.of(viewNavigators, dialogWindows);
        var listView = viewInteractions.open(ProductCategoryListView.class);
        var dataGrid = dataGrid(listView);

        // and:
        var firstProductCategory = dataGrid.firstItem();

        // and:
        dataGrid.edit(firstProductCategory);

        // then:
        var detailView = viewInteractions.findCurrentView(ProductCategoryDetailView.class);

        assertThat(detailView.getEditedEntity())
                .isEqualTo(firstProductCategory);
    }

    private DataGridInteractions<ProductCategory> dataGrid(ProductCategoryListView listView) {
        return DataGridInteractions.of(listView,"productCategoriesDataGrid");
    }
}
