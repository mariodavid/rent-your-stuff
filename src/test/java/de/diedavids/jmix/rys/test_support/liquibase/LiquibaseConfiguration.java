package de.diedavids.jmix.rys.test_support.liquibase;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@TestConfiguration
public class LiquibaseConfiguration {

	@Bean(name = "jmix_Liquibase")
	@ConditionalOnClass({SpringLiquibase.class})
	@ConditionalOnMissingBean(name = "jmix_Liquibase")
	public SpringLiquibase liquibase(DataSource dataSource,
									 @Qualifier("jmix_LiquibaseProperties") LiquibaseProperties liquibaseProperties) {
		DatabaseMigrations liquibase = new DatabaseMigrations();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog(liquibaseProperties.getChangeLog());
		liquibase.setClearCheckSums(liquibaseProperties.isClearChecksums());
		liquibase.setContexts(liquibaseProperties.getContexts());
		liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
		liquibase.setLiquibaseSchema(liquibaseProperties.getLiquibaseSchema());
		liquibase.setLiquibaseTablespace(liquibaseProperties.getLiquibaseTablespace());
		liquibase.setDatabaseChangeLogTable(liquibaseProperties.getDatabaseChangeLogTable());
		liquibase.setDatabaseChangeLogLockTable(liquibaseProperties.getDatabaseChangeLogLockTable());
		liquibase.setDropFirst(liquibaseProperties.isDropFirst());
		liquibase.setShouldRun(liquibaseProperties.isEnabled());
		liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
		liquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
		liquibase.setTestRollbackOnUpdate(liquibaseProperties.isTestRollbackOnUpdate());
		liquibase.setTag(liquibaseProperties.getTag());
		return liquibase;
	}

}
