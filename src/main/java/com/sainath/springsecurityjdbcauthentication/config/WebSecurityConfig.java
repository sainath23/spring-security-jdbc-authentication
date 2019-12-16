package com.sainath.springsecurityjdbcauthentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                // Let say your tables are different then you can use overwrite queries.
                // By default spring uses USERS and AUTHORITIES table
                .usersByUsernameQuery("select username, password, enabled from my_users where username = ?")
                .authoritiesByUsernameQuery("select username, authority from my_authorities where username = ?");

                /*
                .withDefaultSchema()
                .withUser(User.withUsername("admin").password("admin").roles("ADMIN"))
                .withUser(User.withUsername("sainath").password("sainath").roles("USER"));
                 */
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("ADMIN", "USER")
                //.antMatchers("/h2/**").permitAll()
                .antMatchers("/").permitAll()
                .and()
                .formLogin()
                .and()
                .csrf().disable() // For h2
                .headers().frameOptions().disable(); // for h2
    }
}
