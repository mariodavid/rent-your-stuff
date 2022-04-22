
package de.diedavids.jmix.rys.migrations;

import de.diedavids.jmix.rys.customer.Customer;
import de.diedavids.jmix.rys.order.Order;
import de.diedavids.jmix.rys.product.Product;
import de.diedavids.jmix.rys.product.StockItem;
import de.diedavids.jmix.rys.test_support.liquibase.DatabaseMigrations;
import de.diedavids.jmix.rys.test_support.liquibase.LiquibaseConfiguration;
import de.diedavids.jmix.rys.test_support.test_data.Customers;
import de.diedavids.jmix.rys.test_support.test_data.Orders;
import de.diedavids.jmix.rys.test_support.test_data.Products;
import de.diedavids.jmix.rys.test_support.test_data.StockItems;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static de.diedavids.jmix.rys.order.Assertions.assertThat;

@SpringBootTest
@Import(LiquibaseConfiguration.class)
class RentalPeriodToOrderMigrationTest {

    private final LocalDateTime NOW = LocalDateTime.now().withNano(0);
    private final LocalDateTime TOMORROW = NOW.plusDays(1);
    private final LocalDateTime IN_TWO_DAYS = NOW.plusDays(2);

    @Autowired
    DataManager dataManager;
    @Autowired
    SystemAuthenticator systemAuthenticator;

    @Autowired
    DatabaseMigrations databaseMigrations;

    @Autowired
    Orders orders;
    @Autowired
    Products products;
    @Autowired
    StockItems stockItems;
    @Autowired
    Customers customers;

    @Test
    void migratingRentalPeriodsFromOrderToOrderLine_copiesTheRangeFromTheOrderLineToOrderDuringMigration() {

        // given:
        databaseMigrations.dropDb();

        // and:
        assertThat(databaseMigrations.dbIsEmpty())
                .isTrue();

        // and:
        databaseMigrations.migrateTo("beforeOrderTimeRange");

        // and:
        Product product = systemAuthenticator.withSystem(() -> products.saveDefault());
        Customer customer = systemAuthenticator.withSystem(() -> customers.saveDefault());

        // and:
        StockItem stockItem = systemAuthenticator.withSystem(() -> stockItems.save(
                stockItems.defaultData()
                        .product(product)
                        .identifier("SI123")
                        .build()
        ));

        // and:
        String orderId = insertOrderViaSQL(customer);

        // and:
        insertOrderLineViaSQL(
                orderId,
                stockItem.getId().toString(),
                NOW,
                TOMORROW
        );

        // when:
        databaseMigrations.migrateTo("afterOrderTimeRange");

        // then:
        Order orderWithMigratedTimeRange = systemAuthenticator.withSystem(() -> dataManager.load(Id.of(UUID.fromString(orderId), Order.class)).one());

        assertThat(orderWithMigratedTimeRange)
                .hasPickupDate(NOW)
                .hasReturnDate(TOMORROW);
    }

    private String insertOrderViaSQL(Customer customer) {
        String orderId = UUID.randomUUID().toString();
        String customerId = customer.getId().toString();
        databaseMigrations.executeSql("""
                INSERT INTO rys_order
                            (id,
                             tenant,
                             version,
                             created_by,
                             created_date,
                             last_modified_date,
                             order_date,
                             customer_id)
                VALUES      ('%s',
                             NULL,
                             1,
                             'admin',
                             '2022-04-13 07:43:30.791000',
                             '2022-04-13 07:43:30.791000',
                             '2022-04-13',
                             '%s');""".formatted(
                orderId,
                customerId
        ));
        return orderId;
    }

    private void insertOrderLineViaSQL(String orderId, String stockItemId, LocalDateTime startsAt, LocalDateTime endsAt) {

        DateTimeFormatter sqlDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        databaseMigrations.executeSql("""
                INSERT INTO PUBLIC.rys_order_line
                            (id,
                             tenant,
                             version,
                             created_by,
                             created_date,
                             last_modified_date,
                             stock_item_id,
                             order_id,
                             starts_at,
                             ends_at)
                VALUES      ('%s',
                             NULL,
                             1,
                             'admin',
                             '2022-04-13 07:43:30.791000',
                             '2022-04-13 07:43:30.791000',
                             '%s',
                             '%s',
                             '%s',
                             '%s');""".formatted(
                                     UUID.randomUUID().toString(),
                stockItemId,
                orderId,
                startsAt.format(sqlDateFormatter),
                endsAt.format(sqlDateFormatter)
        ));
    }
}
