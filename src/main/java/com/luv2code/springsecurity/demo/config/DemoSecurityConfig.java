package com.luv2code.springsecurity.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.luv2code.springsecurity.demo.service.UserService;
@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	//add a reference to our security data source
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//use jdbc authentication ... oh year!!!
		auth.authenticationProvider(authenticationProvider());
	}
	//configure security web paths in application, login, logout etc
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/css/**")
		.permitAll()
		.antMatchers("/").hasRole("EMPLOYEE")//add roles 
		.antMatchers("/leaders/**").hasRole("MANAGER")
		.antMatchers("/systems/**").hasRole("ADMIN")
		.and()
		.formLogin()
		.loginPage("/showMyLoginPage")
		.loginProcessingUrl("/authenticateTheUser")
		.successHandler(customAuthenticationSuccessHandler)
		.permitAll()
		.and()
		.logout().permitAll()//add logout into spring
		.and()
		.exceptionHandling().accessDeniedPage("/access-denied");
	}
	
	//beans
	//bcrypt bean definition
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	//authenticationProvider bean definition
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth= new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);//set the custom user detail service
		auth.setPasswordEncoder(passwordEncoder());//set password encoder by -crypt
		return auth;
	}
	
	
}	
