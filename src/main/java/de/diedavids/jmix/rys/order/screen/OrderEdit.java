package de.diedavids.jmix.rys.order.screen;

import io.jmix.ui.screen.*;
import de.diedavids.jmix.rys.order.Order;

@UiController("rys_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit extends StandardEditor<Order> {
}