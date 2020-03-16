package springconfig;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;



/**
 * @author Djamel
 *
 */

@Configuration
@ComponentScan(basePackages = {"services","dao","servlet","test.mavencdb.dao","mapper"})
@PropertySource("classpath:Connexion.properties")
public class AppConfig extends AbstractContextLoaderInitializer {
	private String mysqlUrl = "jdbcUrl";


	private String driver = "driverClassName";
	

	private String username = "username";


	private String password = "password";
	
	@Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(mysqlUrl);
        dataSource.setUrl(driver);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        
        return dataSource;
    }
	
	protected WebApplicationContext createRootApplicationContext() {
		 AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		 rootContext.register(AppConfig.class);
		 return rootContext;
	}
}