package com.jwtest.demo.configuration.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableResourceServer
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Oauth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/clients")
                .antMatchers("/accounts")
                .antMatchers("/myAccount/*")
                .and()
                .requestMatcher(new Oauth2ResourceServerConfig.BasicRequestMatcher())
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }

    private static class BasicRequestMatcher implements RequestMatcher {
        @Override
        public boolean matches(HttpServletRequest request) {
            String auth = request.getHeader("Authorization");
            return (auth == null || !auth.startsWith("Basic"));
        }
    }
}
