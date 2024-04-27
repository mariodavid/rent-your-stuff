package de.diedavids.jmix.rys.view.user;

import com.vaadin.flow.router.Route;
import de.diedavids.jmix.rys.entity.User;
import de.diedavids.jmix.rys.view.main.MainView;
import io.jmix.flowui.view.*;

@Route(value = "users", layout = MainView.class)
@ViewController("rys_User.list")
@ViewDescriptor("user-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class UserListView extends StandardListView<User> {
}
