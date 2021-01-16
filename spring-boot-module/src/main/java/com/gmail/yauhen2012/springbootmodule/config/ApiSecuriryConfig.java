package com.gmail.yauhen2012.springbootmodule.config;

import java.time.LocalDateTime;
import javax.servlet.http.HttpServletResponse;

import com.gmail.yauhen2012.service.model.UserRoleEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@Order(1)
@Profile("!test")
public class ApiSecuriryConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public ApiSecuriryConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationSuccessHandler apiAuthenticationSuccessHandler() {
        return new APISimpleUrlAuthenticationSuccessHandler();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/api/")
                .permitAll()
                .antMatchers("/api/city-info/**")
                .hasAnyRole(UserRoleEnum.ADMINISTRATOR.name())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, e) ->
                {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("{\n" +
                            "    \"message\"   : \"Access denied\",\n" +
                            "    \"timestamp\" : " + LocalDateTime.now() + "\n" +
                            "}");
                })
                .and()
                .httpBasic()
                .and()
                .csrf().disable();

    }

}
