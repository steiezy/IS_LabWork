package com.lab.airbnb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.util.matcher.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import java.util.Arrays;
import java.util.List;


@Configuration
public class WebSecurityConfig {

    JWTRequestFilter jwtRequestFilter;

    public WebSecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> cors.configurationSource(request -> {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("http://localhost:8080")); //
            config.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE")); // Allow these HTTP methods
            config.setAllowCredentials(true);
            config.setAllowedHeaders(List.of("*"));
            source.registerCorsConfiguration("/**", config);
            return config;
        }));


        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests(authorize -> authorize
                // Specific exclusions or rules.
                .requestMatchers("/api/v1/auth/**",
                "/api/v1/lease/**",
                "/open/**").permitAll()
                .anyRequest().authenticated());
        return http.build();
    }

}
