package de.diedavids.jmix.rys.customer.screen;

import de.diedavids.jmix.rys.RentYourStuffApplication;
import de.diedavids.jmix.rys.customer.Customer;
import de.diedavids.jmix.rys.test_support.DatabaseCleanup;
import de.diedavids.jmix.rys.test_support.ui.FormInteractions;
import de.diedavids.jmix.rys.test_support.ui.ScreenInteractions;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.ui.Screens;
import io.jmix.ui.testassist.UiTestAssistConfiguration;
import io.jmix.ui.testassist.junit.UiTest;
import io.jmix.ui.util.OperationResult;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@UiTest(authenticatedUser = "admin", mainScreenId = "rys_MainScreen", screenBasePackages = "de.diedavids.jmix.rys")
@ContextConfiguration(classes = {RentYourStuffApplication.class, UiTestAssistConfiguration.class})
@AutoConfigureTestDatabase
class CustomerEditTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    DatabaseCleanup databaseCleanup;

    FormInteractions formInteractions;


    @BeforeEach
    void setUp() {
        databaseCleanup.removeAllEntities(Customer.class);
    }

    @Test
    void given_validCustomer_when_saveCustomerThroughTheForm_then_customerIsSaved(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        CustomerEdit customerEdit = screenInteractions.openEditorForCreation(CustomerEdit.class, Customer.class);
        formInteractions = FormInteractions.of(customerEdit);

        // and:
        String firstName = "Foo" + UUID.randomUUID();

        formInteractions.setTextFieldValue("firstNameField", firstName);
        String expectedLastName = "Bar";
        formInteractions.setTextFieldValue("lastNameField", expectedLastName);
        formInteractions.setTextFieldValue("addressStreetField", "Foo Street 123");

        // when:
        OperationResult operationResult = formInteractions.saveForm();

        assertThat(operationResult)
                .isEqualTo(OperationResult.success());

        // then:
        Optional<Customer> savedCustomer = findCustomerByAttribute("firstName", firstName);

        assertThat(savedCustomer)
                .isPresent()
                .get()
                .extracting("lastName")
                .isEqualTo(expectedLastName);

    }

    @Test
    void given_customerWithoutStreet_when_saveCustomerThroughTheForm_then_customerIsNotSaved(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        CustomerEdit customerEdit = screenInteractions.openEditorForCreation(CustomerEdit.class, Customer.class);
        formInteractions = FormInteractions.of(customerEdit);

        // and:
        String firstName = "Foo" + UUID.randomUUID();

        formInteractions.setTextFieldValue("firstNameField", firstName);
        String invalidStreetAddress = "";
        formInteractions.setTextFieldValue("addressStreetField", invalidStreetAddress);

        // when:
        OperationResult operationResult = formInteractions.saveForm();

        assertThat(operationResult)
                .isEqualTo(OperationResult.fail());

        // then:
        Optional<Customer> savedCustomer = findCustomerByAttribute("firstName", firstName);

        assertThat(savedCustomer)
                .isNotPresent();

    }

    @NotNull
    private Optional<Customer> findCustomerByAttribute(String attribute, String value) {
        return dataManager.load(Customer.class)
                .condition(PropertyCondition.equal(attribute, value))
                .optional();
    }

}