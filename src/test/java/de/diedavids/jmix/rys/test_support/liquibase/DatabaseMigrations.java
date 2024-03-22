package de.diedavids.jmix.rys.test_support.liquibase;

import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.SneakyThrows;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.validation.constraints.NotNull;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class DatabaseMigrations extends SpringLiquibase {


    @Override
    public void afterPropertiesSet() throws LiquibaseException {
        // noop, since no automatic migration should be performed
    }

    @SneakyThrows
    public void migrateTo(String tag) {
        setDropFirst(false);
        setTag(tag);
        Connection connection = getDataSource().getConnection();
        Liquibase liquibase = createLiquibase(connection);
        performUpdate(liquibase);
    }

    @SneakyThrows
    public void dropDbAndMigrateTo(String tag) {
        setDropFirst(true);
        migrateTo(tag);
    }

    @SneakyThrows
    public void dropDb() {
        setDropFirst(true);
        Connection connection = getDataSource().getConnection();

        // createLiquibase will drop the tables if dropFirst is set
        createLiquibase(connection);
    }


    public Boolean dbIsEmpty() {
        List<Map<String, Object>> result = queryForList(
                "SELECT EXISTS (" +
                        "SELECT FROM information_schema.tables     " +
                        "WHERE  table_schema = 'public'    " +
                        "AND    table_name   = 'databasechangelog'    " +
                        ");"
        );
        Boolean databaseChangelogTableExists = (Boolean) result.get(0).get("exists");
        return !databaseChangelogTableExists;
    }

    @NotNull
    private JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    public void executeSql(String sql) {
        jdbcTemplate().execute(sql);
    }

    public List<Map<String, Object>> queryForList(String sql) {
        return jdbcTemplate().queryForList(sql);
    }
}
