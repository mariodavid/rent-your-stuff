package de.diedavids.jmix.rys.customer.screen;

import de.diedavids.jmix.rys.customer.Customer;
import de.diedavids.jmix.rys.test_support.test_data.Customers;
import de.diedavids.jmix.rys.test_support.ui.ScreenInteractions;
import de.diedavids.jmix.rys.test_support.ui.TableInteractions;
import de.diedavids.jmix.rys.test_support.ui.WebIntegrationTest;
import io.jmix.core.DataManager;
import io.jmix.ui.Screens;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerBrowseTest extends WebIntegrationTest {

    @Autowired
    DataManager dataManager;

    private Customer customer;
    @Autowired
    private Customers customers;


    @BeforeEach
    void setUp() {
        customer = customers.saveDefault();
    }

    @Test
    void given_oneCustomerExists_when_openCustomerBrowse_then_tableContainsTheCustomer(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        CustomerBrowse customerBrowse = screenInteractions.open(CustomerBrowse.class);
        TableInteractions<Customer> customerTable = customerTable(customerBrowse);

        // expect:
        assertThat(customerTable.firstItem())
                .isEqualTo(customer);
    }


    @Test
    void given_oneCustomerExists_when_editCustomer_then_editCustomerEditorIsShown(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        CustomerBrowse customerBrowse = screenInteractions.open(CustomerBrowse.class);
        TableInteractions<Customer> customerTable = customerTable(customerBrowse);

        // and:
        Customer firstCustomer = customerTable.firstItem();

        // and:
        customerTable.edit(firstCustomer);

        // then:
        CustomerEdit customerEdit = screenInteractions.findOpenScreen(CustomerEdit.class);

        assertThat(customerEdit.getEditedEntity())
                .isEqualTo(firstCustomer);
    }

    private TableInteractions<Customer> customerTable(CustomerBrowse customerBrowse) {
        return TableInteractions.of(customerBrowse, Customer.class, "customersTable");
    }
}
