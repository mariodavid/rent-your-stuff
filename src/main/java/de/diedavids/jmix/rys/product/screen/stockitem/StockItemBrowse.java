package de.diedavids.jmix.rys.product.screen.stockitem;

import io.jmix.ui.screen.*;
import de.diedavids.jmix.rys.product.StockItem;

@UiController("rys_StockItem.browse")
@UiDescriptor("stock-item-browse.xml")
@LookupComponent("stockItemsTable")
public class StockItemBrowse extends StandardLookup<StockItem> {
}