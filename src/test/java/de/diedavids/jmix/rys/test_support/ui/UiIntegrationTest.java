package de.diedavids.jmix.rys.test_support.ui;


import de.diedavids.jmix.rys.RentYourStuffApplication;
import de.diedavids.jmix.rys.test_support.TenantUserEnvironment;
import io.jmix.flowui.testassist.FlowuiTestAssistConfiguration;
import io.jmix.flowui.testassist.UiTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

@UiTest
@SpringBootTest(classes = {
        RentYourStuffApplication.class,
        FlowuiTestAssistConfiguration.class
})
@ExtendWith(TenantUserEnvironment.class)
public abstract class UiIntegrationTest {

}
