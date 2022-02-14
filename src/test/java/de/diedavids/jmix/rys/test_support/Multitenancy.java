package de.diedavids.jmix.rys.test_support;

import de.diedavids.jmix.rys.entity.User;
import de.diedavids.jmix.rys.security.FullAccessRole;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.multitenancy.entity.Tenant;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Scope("prototype")
@Component("rys_Multitenancy")
public class Multitenancy {


    private final DataManager dataManager;
    private final SystemAuthenticator systemAuthenticator;


    private final User user;
    private final Tenant tenant;


    public Multitenancy(DataManager dataManager, SystemAuthenticator systemAuthenticator) {
        this.dataManager = dataManager;
        this.systemAuthenticator = systemAuthenticator;

        tenant = initTenant();
        user = createUserForTenant(tenant, FullAccessRole.CODE);
    }


    public User getUser() {
        return user;
    }

    public Tenant getTenant() {
        return tenant;
    }


    @NotNull
    private Tenant initTenant() {
        return systemAuthenticator.withSystem(() -> dataManager.save(createTenant()));
    }

    @NotNull
    private Tenant createTenant() {
        Tenant entity = dataManager.create(Tenant.class);
        String tenantId = uniqueString();
        entity.setTenantId("tenant-" + tenantId);
        entity.setName("Tenant " + tenantId);
        return entity;
    }


    @NotNull
    private User createUserForTenant(Tenant tenant, String roleCode) {
        return systemAuthenticator.withSystem(() -> {
            User user = createUser(tenant);
            dataManager.save(user, createRoleAssignment(roleCode, user));

            return dataManager.load(Id.of(user)).one();
        });
    }

    @NotNull
    private User createUser(Tenant tenant) {
        User user = dataManager.create(User.class);
        user.setTenant(tenant.getTenantId());
        user.setUsername(tenant.getTenantId() + "-user");
        return user;
    }

    @NotNull
    private RoleAssignmentEntity createRoleAssignment(String roleCode, User user) {
        RoleAssignmentEntity userRoleAssignment = dataManager.create(RoleAssignmentEntity.class);
        userRoleAssignment.setUsername(user.getUsername());
        userRoleAssignment.setRoleCode(roleCode);
        userRoleAssignment.setRoleType(RoleAssignmentRoleType.RESOURCE);
        return userRoleAssignment;
    }

    private String uniqueString() {
        return UUID.randomUUID().toString();
    }


    public void setup() {
        systemAuthenticator.begin(user.getUsername());
    }

    public void end() {
        systemAuthenticator.end();
    }
}