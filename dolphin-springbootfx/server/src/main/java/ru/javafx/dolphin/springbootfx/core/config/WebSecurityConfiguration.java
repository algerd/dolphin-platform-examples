
package ru.javafx.dolphin.springbootfx.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
       
    //To enable authentication annotation support
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    //@Value("${spring.data.rest.basePath}")
    //private String basePath;
    
    @Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {      
        httpSecurity.csrf().disable()
            .authorizeRequests()       
            //.antMatchers(basePath + "/users/**").hasAuthority("ADMIN")
            //.antMatchers(basePath + "/authorities/**").hasAuthority("ADMIN")    
            .anyRequest().authenticated()
            //.anyRequest().permitAll()    
        .and()
            .httpBasic();

	}

}
