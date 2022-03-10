package de.diedavids.jmix.rys.order.screen;

import de.diedavids.jmix.rys.customer.Customer;
import de.diedavids.jmix.rys.order.Order;
import de.diedavids.jmix.rys.test_support.test_data.Customers;
import de.diedavids.jmix.rys.test_support.ui.FormInteractions;
import de.diedavids.jmix.rys.test_support.ui.ScreenInteractions;
import de.diedavids.jmix.rys.test_support.ui.WebIntegrationTest;
import io.jmix.core.DataManager;
import io.jmix.ui.Screens;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static de.diedavids.jmix.rys.order.Assertions.assertThat;


class OrderEditCustomerSearchTest extends WebIntegrationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    private Customers customers;

    FormInteractions formInteractions;
    private Customer myMiyagi;
    private Customer lordVoldemort;
    private Customer lukeSkywalker;

    @BeforeEach
    void setUp() {

        myMiyagi = customers.save(
                customers.defaultData()
                        .firstName("Mr")
                        .lastName("Miyagi")
                        .build()
        );

        lordVoldemort = customers.save(
                customers.defaultData()
                        .firstName("Lord")
                        .lastName("Voldemort")
                        .build()
        );

        lukeSkywalker = customers.save(
                customers.defaultData()
                        .firstName("Luke")
                        .lastName("Skywalker")
                        .build()
        );
    }

    @Test
    void given_twoCustomersWithLetterLArePresent_when_searchingForLetterL_then_twoCustomersAreFound(Screens screens) {

        // given:
        OrderEdit orderEdit = openOrderEditor(screens);

        formInteractions = FormInteractions.of(orderEdit);

        // and:
        List<Customer> searchResults = formInteractions.getSuggestions("L", "customerField", Customer.class);

        assertThat(searchResults)
                .containsExactlyInAnyOrder(lordVoldemort, lukeSkywalker);

    }

    @Test
    void given_aMatchingCustomer_when_selectingTheCustomer_then_theCustomerIsAssignedToTheOrder(Screens screens) {

        // given:
        OrderEdit orderEdit = openOrderEditor(screens);

        formInteractions = FormInteractions.of(orderEdit);

        // and:
        List<Customer> searchResults = formInteractions.getSuggestions("Mr", "customerField", Customer.class);

        assertThat(searchResults)
                .containsExactlyInAnyOrder(myMiyagi);

        // when:
        formInteractions.setEntitySuggestionFieldValue("customerField", myMiyagi, Customer.class);

        // and:
        assertThat(orderEdit.getEditedEntity())
                .hasCustomer(myMiyagi);
    }

    private OrderEdit openOrderEditor(Screens screens) {
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        return screenInteractions.openEditorForCreation(OrderEdit.class, Order.class);
    }

}