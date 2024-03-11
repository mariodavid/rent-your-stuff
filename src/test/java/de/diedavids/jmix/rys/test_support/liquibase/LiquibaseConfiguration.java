package de.diedavids.jmix.rys.test_support.liquibase;

import io.jmix.core.Stores;
import io.jmix.data.impl.liquibase.LiquibaseChangeLogProcessor;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@TestConfiguration
public class LiquibaseConfiguration {

	@Bean(name = "jmix_Liquibase")
	public DatabaseMigrations liquibase(LiquibaseProperties liquibaseProperties, DataSource dataSource, LiquibaseChangeLogProcessor processor) {
		DatabaseMigrations liquibase = new DatabaseMigrations();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLogContent(processor.createMasterChangeLog(Stores.MAIN));
		liquibase.setContexts(liquibaseProperties.getContexts());
		liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
		liquibase.setDropFirst(liquibaseProperties.isDropFirst());
		liquibase.setShouldRun(liquibaseProperties.isEnabled());
		liquibase.setLabels(liquibaseProperties.getLabels());
		liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
		liquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
		return liquibase;
	}

}