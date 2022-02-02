package de.diedavids.jmix.rys.product.screen.stockitem;

import io.jmix.ui.screen.*;
import de.diedavids.jmix.rys.product.StockItem;

@UiController("rys_StockItem.edit")
@UiDescriptor("stock-item-edit.xml")
@EditedEntityContainer("stockItemDc")
public class StockItemEdit extends StandardEditor<StockItem> {
}