package net.charan.shoppingBackEndConfig;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ComponentScan(basePackages={"net.charan.shoppingBackEndDTO"})
@EnableTransactionManagement
public class HibernateConfig {
	
	//Variables representing configuration of Database
	private final static String DATABASE_URL = "jdbc:h2:tcp://localhost/~/onlineshopping";
	private final static String DATABASE_DRIVER = "org.h2.Driver";
	private final static String DATABASE_DIALECT = "org.hibernate.dialect.H2Dialect";
	private final static String DATABASE_USERNAME = "sa";
	private final static String DATABASE_PASSWORD = "";
	
	
	@Bean
	private DataSource getDataSource(){
		BasicDataSource datasource = new BasicDataSource();
		
		//Setting the parameters
		
		datasource.setDriverClassName(DATABASE_DRIVER);
		datasource.setUrl(DATABASE_URL);
		datasource.setUsername(DATABASE_USERNAME);
		datasource.setPassword(DATABASE_PASSWORD);
		
		
		return datasource;
	}
	
	
	@Bean
	public SessionFactory getSessionFactory(DataSource datasource)
	{
		LocalSessionFactoryBuilder builder =  new LocalSessionFactoryBuilder(datasource);
		
		builder.addProperties(getHibernateProperties());
		
		//Scan the following package and all my entities are inside
		builder.scanPackages("net.charan.shoppingBackEndDTO");
		
		return builder.buildSessionFactory();
	}

	//All the hibernate properties are returned from here
	private Properties getHibernateProperties() {
		// TODO Auto-generated method stub
		
		Properties properties = new Properties();
		
		properties.put("hibernate.dialect", DATABASE_DIALECT);
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");
		
		return properties;
	}
	
	@Bean
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory)
	{
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		
		return transactionManager;
	}
	
}
