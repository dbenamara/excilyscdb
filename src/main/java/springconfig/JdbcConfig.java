package springconfig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author djamel
 *
 */
@Configuration
@ComponentScan("dao")
@PropertySource("classpath:Connexion.properties")
public class JdbcConfig {
	

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
}
