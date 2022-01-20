package de.diedavids.jmix.rys.product.screen.productprice;

import io.jmix.ui.screen.*;
import de.diedavids.jmix.rys.product.ProductPrice;

@UiController("rys_ProductPrice.edit")
@UiDescriptor("product-price-edit.xml")
@EditedEntityContainer("productPriceDc")
public class ProductPriceEdit extends StandardEditor<ProductPrice> {
}