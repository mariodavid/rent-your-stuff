package de.diedavids.jmix.rys.order.screen;

import io.jmix.ui.screen.*;
import de.diedavids.jmix.rys.order.Order;

@UiController("rys_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {
}