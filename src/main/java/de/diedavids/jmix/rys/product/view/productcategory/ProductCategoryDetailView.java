package de.diedavids.jmix.rys.product.view.productcategory;

import de.diedavids.jmix.rys.product.ProductCategory;

import de.diedavids.jmix.rys.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "productCategories/:id", layout = MainView.class)
@ViewController("rys_ProductCategory.detail")
@ViewDescriptor("product-category-detail-view.xml")
@EditedEntityContainer("productCategoryDc")
public class ProductCategoryDetailView extends StandardDetailView<ProductCategory> {
}
