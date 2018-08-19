package com.imdrissi.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Slf4j
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(final HttpSecurity http) throws Exception {
    http.httpBasic().and().csrf().disable();

    http.authorizeRequests().antMatchers(HttpMethod.GET, "/actuator/**").permitAll();
    http.authorizeRequests()
        .antMatchers(
            "/swagger-ui.html",
            "/swagger-resources",
            "/swagger-resources/configuration/ui",
            "/swagger-resources/configuration/security",
            "/v2/api-docs",
            "/webjars/**")
        .permitAll();
    http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();

    http.authorizeRequests().anyRequest().authenticated();

    http.headers().frameOptions().disable();
  }
}
