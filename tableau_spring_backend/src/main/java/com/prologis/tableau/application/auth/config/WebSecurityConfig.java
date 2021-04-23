package com.prologis.tableau.application.auth.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.prologis.tableau.security.JwtAuthenticationProvider;
import com.prologis.tableau.security.JwtAuthenticationTokenFilter;
import com.prologis.tableau.security.JwtFailureHandler;
import com.prologis.tableau.security.JwtSuccesshandler;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile({"!test"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${spring.security.oauth2.client.logout.uri}")
	private String logoutUri;

	@Autowired
	private JwtAuthenticationProvider authenticationProvider;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(Collections.singletonList(authenticationProvider));
	}

	public JwtAuthenticationTokenFilter authenticationTokenFilter() {
		JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter();
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(new JwtSuccesshandler());
        filter.setAuthenticationFailureHandler(new JwtFailureHandler());
		return filter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/tableau-poc/**").permitAll();
		http.csrf().disable().cors().and().authorizeRequests().antMatchers("/authorize/**").fullyAuthenticated().and()
				.oauth2Login().loginPage("/oauth2/authorization/azure").
				and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER).
				and().addFilterBefore(authenticationTokenFilter(),
						UsernamePasswordAuthenticationFilter.class);

		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl(logoutUri)
		.deleteCookies("JSESSIONID").invalidateHttpSession(true);

		http.headers().cacheControl();
	}

	
	@Override 
	 public void configure(WebSecurity webSecurity) {

		 webSecurity.ignoring().antMatchers("/token/**").
		 antMatchers("/swagger-ui.html").antMatchers("/swagger-resources/**").
		 antMatchers("/v2/api-docs/**").antMatchers("/webjars/**")
		 .antMatchers("/cookie/**").antMatchers("/console/**");
	  
	  }
	
}