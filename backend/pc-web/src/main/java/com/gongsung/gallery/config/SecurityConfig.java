package com.gongsung.gallery.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
//    http.authorizeRequests()
//        .mvcMatchers("/api/**", "/sign-in", "/sign-up", "check-email-token","/swagger-ui.html","/**").permitAll()
//        .anyRequest().authenticated();
    http.httpBasic().disable()
            .csrf().disable();

  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }
}
