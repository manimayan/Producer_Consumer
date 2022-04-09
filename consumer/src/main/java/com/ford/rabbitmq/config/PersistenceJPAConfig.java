package com.ford.rabbitmq.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@RefreshScope
@Configuration
@EnableTransactionManagement
public class PersistenceJPAConfig {

	@Bean 
	public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() { 
		return new PropertySourcesPlaceholderConfigurer();
	}
			
	@Value("${jdbc.driver}") 
	private String jdbcDriver;

	@Value("${jdbc.url}") 
	private String jdbcUrl;

	@Value("${jdbc.user}") 
	private String jdbcUser;

	@Value("${jdbc.password}") 
	private String jdbcPassword;

	@Value("${hibernate.showsql}") 
	private boolean hibernateShowsql;
	
	@Value("${database.platform}") 
	private String databasePlatform;

	@Bean
	@RefreshScope
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(dataSource());
		entityManager.setPersistenceUnitName("mysqlServer");
		entityManager.setPackagesToScan("com.ford.rabbitmq.model");
		entityManager.setJpaVendorAdapter(vendorAdapter());
		entityManager.setJpaProperties(additionalProperties());
		return entityManager;
	}

	@Bean 
	public JpaVendorAdapter vendorAdapter() { 
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(hibernateShowsql);
		adapter.setDatabasePlatform(databasePlatform);
		adapter.setDatabase(Database.MYSQL); 
		return adapter;
	}

	public javax.sql.DataSource dataSource() {
		PoolProperties props = new PoolProperties(); 
		props.setUrl(jdbcUrl);
		props.setDriverClassName(jdbcDriver); 
		props.setUsername(jdbcUser);
		props.setPassword(jdbcPassword); 
		props.setJmxEnabled(true);
		props.setTestWhileIdle(true); 
		props.setTestOnBorrow(true);
		props.setValidationQuery("SELECT 1"); 
		props.setValidationQueryTimeout(45);
		props.setTestOnReturn(true); 
		props.setValidationInterval(60000);
		props.setTimeBetweenEvictionRunsMillis(30000); 
		props.setMaxActive(20);
		props.setMaxIdle(10); 
		props.setInitialSize(10); 
		props.setMaxWait(60000);
		props.setMinEvictableIdleTimeMillis(30000); 
		props.setMinIdle(5);
		props.setLogAbandoned(false); 
		props.setRemoveAbandoned(true);
		props.setRemoveAbandonedTimeout(420); 
		props.setJdbcInterceptors("ConnectionState;StatementFinalizer(trace=true);ResetAbandonedTimer;" + "SlowQueryReport(threshold=30000,logFailed=true)"); 
		DataSource dataSource =	new DataSource(); 
		dataSource.setPoolProperties(props);
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) { 
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf); 
		return transactionManager; 
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() { 
		return new PersistenceExceptionTranslationPostProcessor(); 
	}

	@Bean 
	public Properties additionalProperties() { 
		Properties properties = new	Properties(); 
		properties.setProperty("hibernate.id.new_generator_mappings", "false"); 
		properties.setProperty("hibernate.generate_statistics", "false");
		properties.setProperty("hibernate.dialect", databasePlatform); 
		return properties;
	}
}
