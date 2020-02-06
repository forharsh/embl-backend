package com.embl.backend.config;

import com.embl.backend.security.JwtAuthenticationEntryPoint;
import com.embl.backend.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.embl.backend.util.UrlKeys.H2_CONSOLE;
import static com.embl.backend.util.UrlKeys.TOKEN_GENERATE;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtAuthenticationFilter authenticationFilter;
    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/swagger*/**",
            "/configuration/**",
            "/webjars/**",
            TOKEN_GENERATE,
            H2_CONSOLE
    };

    @Autowired
    public SecurityConfig(final UserDetailsService userDetailsService, final JwtAuthenticationEntryPoint unauthorizedHandler, final JwtAuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.headers().frameOptions().disable();
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST)
                .permitAll()
                .anyRequest().authenticated().and().exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
