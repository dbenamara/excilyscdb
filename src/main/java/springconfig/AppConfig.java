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
@PropertySource("classpath:Connexion.properties")
public class AppConfig extends AbstractContextLoaderInitializer {
	@Autowired
    Environment environment;

	
	private String mysqlUrl = "jdbcUrl";


	private String driver = "driverClassName";
	

	private String username = "username";


	private String password = "password";
	
	@Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/computer-database-db");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("admincdb");
        dataSource.setPassword("qwerty1234");
        
        return dataSource;
    }
	
	protected WebApplicationContext createRootApplicationContext() {
		 AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		 rootContext.register(AppConfig.class);
		 return rootContext;
	}
}
