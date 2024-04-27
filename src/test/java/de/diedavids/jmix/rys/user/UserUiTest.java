package de.diedavids.jmix.rys.user;

import com.vaadin.flow.component.button.Button;
import de.diedavids.jmix.rys.entity.User;
import de.diedavids.jmix.rys.test_support.ui.UiIntegrationTest;
import de.diedavids.jmix.rys.test_support.ui.interactions.ComponentInteractions;
import de.diedavids.jmix.rys.test_support.ui.interactions.FormInteractions;
import de.diedavids.jmix.rys.test_support.ui.interactions.ViewInteractions;
import de.diedavids.jmix.rys.view.user.UserDetailView;
import de.diedavids.jmix.rys.view.user.UserListView;
import io.jmix.core.DataManager;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.data.grid.DataGridItems;
import io.jmix.flowui.util.OperationResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Sample UI integration test for the User entity.
 */
public class UserUiTest extends UiIntegrationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    ViewNavigators viewNavigators;
    @Autowired
    private DialogWindows dialogWindows;

    @Test
    void test_createUser() {

        // given
        ViewInteractions viewInteractions = ViewInteractions.of(viewNavigators, dialogWindows);
        ComponentInteractions listComponents = ComponentInteractions.of(viewInteractions.open(UserListView.class));
        Button createBtn = listComponents.button( "createBtn");
        createBtn.click();

        // when
        UserDetailView userDetailView = viewInteractions.findCurrentView(UserDetailView.class);

        FormInteractions formInteractions = FormInteractions.of(userDetailView);
        formInteractions.components().setTextFieldValue("usernameField", "test-user-" + System.currentTimeMillis());
        formInteractions.components().setPasswordFieldValue("passwordField", "test-passwd");
        formInteractions.components().setPasswordFieldValue("confirmPasswordField", "test-passwd");

        // and
        OperationResult operationResult = formInteractions.saveForm();

        // then
        assertThat(operationResult).isEqualTo(OperationResult.success());

        // and
        listComponents = ComponentInteractions.of(viewInteractions.findCurrentView(UserListView.class));
        DataGrid<User> usersDataGrid = listComponents.dataGrid("usersDataGrid");
        DataGridItems<User> dataGridItems = usersDataGrid.getItems();

        assertThat(dataGridItems).isNotNull();
        assertThat(dataGridItems.getItems())
                .anyMatch(u -> u.getUsername().startsWith("test-user-"));
    }

    @AfterEach
    void tearDown() {
        dataManager.load(User.class)
                .query("e.username like ?1", "test-user-%")
                .list()
                .forEach(u -> dataManager.remove(u));
    }
}
