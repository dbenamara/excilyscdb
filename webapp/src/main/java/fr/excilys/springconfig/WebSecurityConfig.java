package fr.excilys.springconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.excilys.services.UserInfoService;
 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    
    //@Autowired
    //MyDBAuthenticationService myDBAauthenticationService;
	@Autowired
	private UserInfoService UserInfoService;
	
//	 @Bean
//	 public UserDetailsService userDetailsService() {
//	        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//	        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
//	        return manager;
//	 }
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
 
//    	auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("password")).roles("USER");
//		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
//		
		auth.userDetailsService(UserInfoService).passwordEncoder(passwordEncoder());
       
 
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	 
        http.csrf().disable();
 
        http.authorizeRequests().antMatchers("/", "/Login", "ListComputers", "/Logout").permitAll();
 
        //http.authorizeRequests().antMatchers("/ListComputers").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
 
        http.authorizeRequests().antMatchers("/EditComputer", "/AddComputer", "/deleteComputer").access("hasRole('ROLE_ADMIN')");
 
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
 
        http.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/j_spring_security_check")
                .loginPage("/Login")
                .defaultSuccessUrl("/ListComputers")
                .failureUrl("/Login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout().logoutUrl("/Logout").logoutSuccessUrl("/LogoutSuccessful");
 
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}