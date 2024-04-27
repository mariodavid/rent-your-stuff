package de.diedavids.jmix.rys.product.view;

import de.diedavids.jmix.rys.product.Product;
import de.diedavids.jmix.rys.test_support.test_data.Products;
import de.diedavids.jmix.rys.test_support.ui.UiIntegrationTest;
import de.diedavids.jmix.rys.test_support.ui.interactions.DataGridInteractions;
import de.diedavids.jmix.rys.test_support.ui.interactions.ViewInteractions;
import de.diedavids.jmix.rys.view.product.ProductDetailView;
import de.diedavids.jmix.rys.view.product.ProductListView;
import io.jmix.core.DataManager;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.ViewNavigators;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class ProductListViewTest extends UiIntegrationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    private Products products;
    @Autowired
    private ViewNavigators viewNavigators;
    @Autowired
    private DialogWindows dialogWindows;
    private Product product;


    @BeforeEach
    void setUp() {
        product = products.saveDefault();
    }

    @Test
    void given_oneProductExists_when_openProductBrowse_then_tableContainsTheProduct() {

        // given:
        ViewInteractions viewInteractions = ViewInteractions.of(viewNavigators, dialogWindows);
        ProductListView listView = viewInteractions.open(ProductListView.class);
        DataGridInteractions<Product> dataGrid = dataGrid(listView);

        // expect:
        assertThat(dataGrid.items())
                .contains(product);
    }


    @Test
    void given_oneProductExists_when_editProduct_then_editProductEditorIsShown() {

        // given:
        ViewInteractions viewInteractions = ViewInteractions.of(viewNavigators, dialogWindows);
        ProductListView listView = viewInteractions.open(ProductListView.class);
        DataGridInteractions<Product> dataGrid = dataGrid(listView);

        // and:
        Product firstProduct = dataGrid.firstItem();

        // and:
        dataGrid.edit(firstProduct);

        // then:
        ProductDetailView detailView = viewInteractions.findCurrentView(ProductDetailView.class);

        assertThat(detailView.getEditedEntity())
                .isEqualTo(firstProduct);
    }

    private DataGridInteractions<Product> dataGrid(ProductListView listView) {
        return DataGridInteractions.of(listView,"productsDataGrid");
    }
}
