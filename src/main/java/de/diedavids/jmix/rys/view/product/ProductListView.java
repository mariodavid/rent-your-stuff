package de.diedavids.jmix.rys.view.product;

import de.diedavids.jmix.rys.product.Product;

import de.diedavids.jmix.rys.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "products", layout = MainView.class)
@ViewController("rys_Product.list")
@ViewDescriptor("product-list-view.xml")
@LookupComponent("productsDataGrid")
@DialogMode(width = "64em")
public class ProductListView extends StandardListView<Product> {
}
