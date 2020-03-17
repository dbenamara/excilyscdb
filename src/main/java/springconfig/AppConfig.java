package springconfig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
@PropertySource("classpath:datasource.properties")
public class AppConfig extends AbstractContextLoaderInitializer {
	@Autowired
    Environment environment;

	
	private String url = "datasource.jdbcUrl";


	private String driver = "datasource.driverClassName";
	

	private String username = "datasource.username";


	private String password = "datasource.password";
	
	@Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getRequiredProperty(driver));
        dataSource.setDriverClassName(environment.getRequiredProperty(url));
        dataSource.setUsername(environment.getRequiredProperty(username));
        dataSource.setPassword(environment.getRequiredProperty(password));
        
        return dataSource;
    }
	
	protected WebApplicationContext createRootApplicationContext() {
		 AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		 rootContext.register(AppConfig.class);
		 return rootContext;
	}
}
