package com.gmail.yauhen2012.springbootmodule.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import static com.gmail.yauhen2012.springbootmodule.constant.TestAdminConstant.*;

@Configuration
public class TestAPIConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                .withUser(TEST_ADMIN)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .roles("ADMINISTRATOR");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/city-info/**")
                .authorizeRequests()
                .anyRequest().hasRole("ADMINISTRATOR")
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }

}
