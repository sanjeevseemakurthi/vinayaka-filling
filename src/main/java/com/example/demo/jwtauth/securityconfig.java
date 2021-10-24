package com.example.demo.jwtauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration 
@EnableWebSecurity
public class securityconfig extends WebSecurityConfigurerAdapter {
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	@Autowired
	private MyUserDetailsService UserDetailsService ;
	
	@Autowired
	public jwtfilter jwtfilter;
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(UserDetailsService);
	}
	@Bean
    public PasswordEncoder passwordEncoder(){
		 return new BCryptPasswordEncoder();
    }
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable()
	            .authorizeRequests()
	            .antMatchers("/authenticate").permitAll()
	            .antMatchers("/newuser").permitAll()
	            .anyRequest().authenticated()
	            .and().sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	    http.addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);
	}
	
}
