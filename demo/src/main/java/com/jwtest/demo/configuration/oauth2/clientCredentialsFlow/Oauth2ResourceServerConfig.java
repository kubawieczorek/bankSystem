package com.jwtest.demo.configuration.oauth2.clientCredentialsFlow;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

//@Configuration
//@EnableResourceServer
public class Oauth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers()
                .antMatchers("/admin/*")
                .antMatchers("/myAccount/money")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }
}
