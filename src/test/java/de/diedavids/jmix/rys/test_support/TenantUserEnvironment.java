package de.diedavids.jmix.rys.test_support;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class TenantUserEnvironment implements BeforeEachCallback, AfterEachCallback {

    private Multitenancy multitenancy;


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);

        multitenancy = applicationContext.getBean(Multitenancy.class);

        multitenancy.setup();
    }


    @Override
    public void afterEach(ExtensionContext context) {
        multitenancy.end();
    }
}
