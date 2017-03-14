package ru.javafx.dolphin.security.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.javafx.dolphin.security.core.service.UserService;

@Configuration
@EnableGlobalAuthentication
@EnableGlobalMethodSecurity(
    jsr250Enabled = true,    
    order = 0, 
    mode = AdviceMode.PROXY,
    proxyTargetClass = false
)
public class GlobalSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
    
    @Autowired
    private UserService userService;
    
    @Bean(name="passwordEncoder")
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void init(AuthenticationManagerBuilder builder) throws Exception {
        builder
            .userDetailsService(userService)
            .passwordEncoder(passwordEncoder())
        .and()
            .eraseCredentials(true);
    }

}