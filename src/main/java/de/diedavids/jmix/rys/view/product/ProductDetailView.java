package de.diedavids.jmix.rys.view.product;

import de.diedavids.jmix.rys.product.Product;

import de.diedavids.jmix.rys.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "products/:id", layout = MainView.class)
@ViewController("rys_Product.detail")
@ViewDescriptor("product-detail-view.xml")
@EditedEntityContainer("productDc")
public class ProductDetailView extends StandardDetailView<Product> {
}
