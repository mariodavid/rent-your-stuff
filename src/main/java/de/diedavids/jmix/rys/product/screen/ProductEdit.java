package de.diedavids.jmix.rys.product.screen;

import io.jmix.ui.screen.*;
import de.diedavids.jmix.rys.product.Product;

@UiController("rys_Product.edit")
@UiDescriptor("product-edit.xml")
@EditedEntityContainer("productDc")
public class ProductEdit extends StandardEditor<Product> {
}