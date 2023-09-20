package com.foxtrotpotato.chickentest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT username, password, active FROM users WHERE username = ?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?");
        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                        authorizeRequests
                        .requestMatchers("/").hasRole("EMPLOYEE")

                        .requestMatchers(HttpMethod.GET, "/balances/**").hasAuthority("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/balances/**").hasAuthority("EMPLOYEE")

                        .requestMatchers("/chickens/**").hasAuthority("EMPLOYEE")
                        .requestMatchers("/api/chickens/**").hasAuthority("EMPLOYEE")

                        .requestMatchers("/eggs/**").hasAuthority("EMPLOYEE")

                        .requestMatchers(HttpMethod.GET, "/farms/**").hasAuthority("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/farms/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/farms/**").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/parameters/**").hasAuthority("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/parameters/**").hasAuthority("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/parameters/**").hasAuthority("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/parameters/**").hasAuthority("MANAGER")

                        .requestMatchers(HttpMethod.GET, "/products/**").hasAuthority("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/products/**").hasAuthority("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/products/**").hasAuthority("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/products/**").hasAuthority("MANAGER")

                        .requestMatchers(HttpMethod.GET, "/transactions/**").hasAuthority("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/transactions/**").hasAuthority("EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT, "/transactions/**").hasAuthority("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/transactions/**").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/users/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/users/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ADMIN")

                        .requestMatchers("/", "/index.html", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()

                        //.anyRequest().permitAll()
        ).formLogin(withDefaults());


        //http.httpBasic(withDefaults());
        return http.build();
    }


}