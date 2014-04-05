package com.koleksinaia.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.koleksinaia.dao")
//@PropertySource({ "classpath:com/koleksinaia/config/persistence-mysql.properties" })
@EnableTransactionManagement
public class JPAConfiguration {
	
	//TODO move database related properties to a properties file
//	private static final String PROPERTY_NAME_DATABASE_DRIVER = "com.mysql.jdbc.Driver";  
//    private static final String PROPERTY_NAME_DATABASE_URL = "jdbc:mysql://us-cdbr-east-05.cleardb.net:3306/ad_1d9b20ecf9df84b";
//    private static final String PROPERTY_NAME_DATABASE_USERNAME = "b3624aec9b08b4"; 
//    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "6c46b453";  
    
    private static final String PROPERTY_NAME_DATABASE_DRIVER = "com.mysql.jdbc.Driver";  
    private static final String PROPERTY_NAME_DATABASE_URL = "jdbc:mysql://localhost:3306/koleksinaia-db";
//    private static final String PROPERTY_NAME_TEST_DATABASE_URL = "jdbc:mysql://localhost:3306/koleksinaiatest-db";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "root"; 
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "password";
    
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";  
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "org.hibernate.dialect.MySQL5InnoDBDialect";  
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "true";  
    
    //TESTING PURPOSE
    
	
	@Resource  
    private Environment env;

	@Bean
	public DataSource dataSource() throws SQLException {

//		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//	    return builder.setType(EmbeddedDatabaseType.H2).build();
	    
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
//		dataSource.setUrl(env.getProperty("jdbc.url"));
//		dataSource.setUsername(env.getProperty("jdbc.username"));
//		dataSource.setPassword(env.getProperty("jdbc.password"));
		
		dataSource.setDriverClassName(PROPERTY_NAME_DATABASE_DRIVER);
		dataSource.setUrl(PROPERTY_NAME_DATABASE_URL);
		dataSource.setUsername(PROPERTY_NAME_DATABASE_USERNAME);
		dataSource.setPassword(PROPERTY_NAME_DATABASE_PASSWORD);

        return dataSource;

	}
	
	@Bean
	public EntityManagerFactory entityManagerFactory() throws SQLException {

//	    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//	    vendorAdapter.setGenerateDdl(true);

	    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//	    factory.setJpaVendorAdapter(vendorAdapter);
	    factory.setPackagesToScan("com.koleksinaia.core.entity");
	    factory.setDataSource(dataSource());
	    factory.setPersistenceProviderClass(HibernatePersistence.class);
	    factory.setJpaProperties(hibProperties());
	    factory.afterPropertiesSet();

	    return factory.getObject();
	  }
	
	@Bean
	public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
	    return entityManagerFactory.createEntityManager();
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws SQLException {

	    JpaTransactionManager txManager = new JpaTransactionManager();
	    txManager.setEntityManagerFactory(entityManagerFactory());
	    return txManager;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
	    return new HibernateExceptionTranslator();
	}
	
	private Properties hibProperties() {  
        Properties properties = new Properties();  
//        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));  
//        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));  
        properties.put(HIBERNATE_DIALECT, PROPERTY_NAME_HIBERNATE_DIALECT);  
        properties.put(HIBERNATE_SHOW_SQL, PROPERTY_NAME_HIBERNATE_SHOW_SQL);
        return properties; 
	}
}
