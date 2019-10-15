package com.jwtest.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // needed?
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    //@Autowired
    //private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private DataSource dataSource;

    /*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("Jan Kowalski").password(passwordEncoder().encode("test1"))
    }*/

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()
                .withUser("JanKowalski").password(securityPasswordEncoder().encode("kowalski1"))
                .authorities("ROLE_USER").and()
                .withUser("admin").password(securityPasswordEncoder().encode("admin1"))
                .authorities("ROLE_ADMIN");
    }

    /*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource);
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/client/registration").permitAll()
                .antMatchers("/greeting").permitAll()
                .antMatchers("/h2-console/**").permitAll()// For H2 .anyRequest().authenticated()
                .and()
                .httpBasic();
        // A custom AuthenticationEntryPoint can be used to set necessary response headers,
        // content-type, and so on before sending the response back to the client.
        // .authenticationEntryPoint(authenticationEntryPoint);

        //For H2 and Angular
        http.csrf().disable();
        http.headers().frameOptions().disable();

        //Sometimes it's necessary to implement new functionality with create a new filter to use in the chain.
        //http.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder securityPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Needed for password oauth2 flow?
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean()
            throws Exception {
        return super.authenticationManagerBean();
    }
}
