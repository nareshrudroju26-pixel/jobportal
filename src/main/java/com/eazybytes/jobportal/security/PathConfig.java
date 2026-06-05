package com.eazybytes.jobportal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PathConfig {

    @Bean(name = "publicPaths")
    public List<String> publicPaths(){
        return List.of(
                "/api/auth/login/public",
                "/api/contacts/public",
                "/api/contacts",
                "/api/v3/api-docs/**",
                "/api/v3/api-docs/**",
                "/swagger-ui.html",
                "/api/swagger-ui.html",
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/webjars/**"
        );
    }

    @Bean(name = "protectedPaths")
    public List<String> protectedPaths(){
        return List.of(
                "/api/companies/public",
                "/api/**"
        );
    }
}
