package com.chisom.transactionservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

/**
 * @author Chisom.Iwowo
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthProvider provider;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                 "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        OrRequestMatcher securedUrls = new OrRequestMatcher(new AntPathRequestMatcher("/transaction/**"));
        JwtAuthFilter filter = new JwtAuthFilter(securedUrls);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new SuccessHandler());

        http.csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(provider)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

}
