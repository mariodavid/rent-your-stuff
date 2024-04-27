package de.diedavids.jmix.rys.product.view.productcategory;

import de.diedavids.jmix.rys.product.ProductCategory;

import de.diedavids.jmix.rys.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "productCategories", layout = MainView.class)
@ViewController("rys_ProductCategory.list")
@ViewDescriptor("product-category-list-view.xml")
@LookupComponent("productCategoriesDataGrid")
@DialogMode(width = "64em")
public class ProductCategoryListView extends StandardListView<ProductCategory> {
}
