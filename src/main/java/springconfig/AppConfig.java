package springconfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;



/**
 * @author Djamel
 *
 */

@Configuration
@ComponentScan(basePackages = {"services","dao","servlet","test.mavencdb.dao","mapper","dto", "model"})
@EnableTransactionManagement
@PropertySource("classpath:dataSource.properties")
public class AppConfig implements WebApplicationInitializer {
	@Autowired
    Environment environment;

	private String driver = "dataSource.driverClassName";
	
	private String url = "dataSource.jdbcUrl";
	

	private String username = "dataSource.username";


	private String password = "dataSource.password";
	
	@Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty(driver));
        dataSource.setUrl(environment.getRequiredProperty(url));
        dataSource.setUsername(environment.getRequiredProperty(username));
        dataSource.setPassword(environment.getRequiredProperty(password));
        
        return dataSource;
    }
	
	protected WebApplicationContext createRootApplicationContext() {
		 AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		 rootContext.register(AppConfig.class);
		 return rootContext;
	}
	
	
	@Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        //factoryBean.setConfigLocation(context.getResource(dataSource));
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("model");
        return factoryBean;
    }
	
	@Bean
    public PlatformTransactionManager getTransactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
	
	@Bean
	@Qualifier(value = "entityManager")
	public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
	    return entityManagerFactory.createEntityManager();
	}
	
	@Override
	public void onStartup(ServletContext servletCxt) {
		AnnotationConfigWebApplicationContext acwac = new AnnotationConfigWebApplicationContext();
		acwac.register(WebConfig.class,AppConfig.class);
		acwac.setServletContext(servletCxt);
		acwac.refresh();
		
		DispatcherServlet servlet = new DispatcherServlet(acwac);
		ServletRegistration.Dynamic registration = servletCxt.addServlet("ListComputers", servlet);
		registration.setLoadOnStartup(1);
		registration.addMapping("/");
	}
}

