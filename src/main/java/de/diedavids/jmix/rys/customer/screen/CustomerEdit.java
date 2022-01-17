package de.diedavids.jmix.rys.customer.screen;

import io.jmix.ui.screen.*;
import de.diedavids.jmix.rys.customer.Customer;

@UiController("rys_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {
}