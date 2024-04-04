package de.diedavids.jmix.rys.view.stockitem;

import de.diedavids.jmix.rys.product.StockItem;

import de.diedavids.jmix.rys.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "stockItems/:id", layout = MainView.class)
@ViewController("rys_StockItem.detail")
@ViewDescriptor("stock-item-detail-view.xml")
@EditedEntityContainer("stockItemDc")
public class StockItemDetailView extends StandardDetailView<StockItem> {
}
