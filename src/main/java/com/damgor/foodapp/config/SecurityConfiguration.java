package com.damgor.foodapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.http.HttpMethod.GET;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and().cors()
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/resources/**", "/css/**", "/js/**").permitAll()
                .antMatchers(GET, "/", "/recipes/**", "/products/**", "/profiles", "/profiles/*",
                        "/ui", "/ui/", "/ui/recipes/**", "/ui/products/**", "/ui/profiles", "/ui/profiles/*").permitAll()
                .antMatchers("/", "/recipes/**", "/products/**", "/profiles/**",
                        "/ui/", "/ui/recipes/**", "/ui/products/**", "/ui/profiles/**", "/ui/login/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/**").hasRole("ADMIN")
                .and().formLogin()
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/icon/**, /*.ico");
    }

}
