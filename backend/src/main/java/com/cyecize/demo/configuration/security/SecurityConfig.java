package com.cyecize.demo.configuration.security;

import com.cyecize.demo.api.user.UserService;
import com.cyecize.demo.configuration.security.authloader.LocalAuthenticationLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    private final LocalAuthenticationLoader localAuthenticationLoader;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //@formatter:off
                .cors().disable()
                .csrf().disable()
                .userDetailsService(this.userService)
                .httpBasic()
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                //@formatter:on

        final TokenAuthenticationFilter tokenFilter = new TokenAuthenticationFilter(
                this.localAuthenticationLoader
        );

        // http.addFilter(successfulAuthenticationFilter);
        http.addFilterBefore(tokenFilter, BasicAuthenticationFilter.class);
        http.addFilterBefore(new CorsFilter(), TokenAuthenticationFilter.class);
    }
}
