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
                "/api/auth/register/public",
                "/api/companies/public",
                "/api/csrf-token/public",
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
                "/api/**"
        );
    }

    @Bean(name = "adminPaths")
    public List<String> adminPaths() {
        return List.of(
                "/api/contacts/admin",
                "/api/contacts/sort/admin",
                "/api/contacts/page/admin",
                "/api/contacts/${id}/status/admin"
        );
    }
}
