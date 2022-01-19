package de.diedavids.jmix.rys.customer.screen;

import io.jmix.ui.screen.*;
import de.diedavids.jmix.rys.customer.Customer;

@UiController("rys_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowse extends StandardLookup<Customer> {

}