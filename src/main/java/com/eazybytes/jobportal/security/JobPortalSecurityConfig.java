package com.eazybytes.jobportal.security;

import com.eazybytes.jobportal.security.filter.JwtTokenValidatorFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity //Optional
@RequiredArgsConstructor
public class JobPortalSecurityConfig {

    @Qualifier("publicPaths")
    private final List<String> publicPaths;

    @Qualifier("protectedPaths")
    private final List<String> protectedPaths;

    @Qualifier("adminPaths")
    private final List<String> adminPaths;

    @Bean
    public SecurityFilterChain securityConfig(HttpSecurity http){
        return http
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))
                .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests((requests) ->
                        {
                            publicPaths.forEach(path -> requests.requestMatchers(path).permitAll());
                            adminPaths.forEach(path -> requests.requestMatchers(path).hasRole("ADMIN"));
                            protectedPaths.forEach(path -> requests.requestMatchers(path).authenticated());
                            requests.anyRequest().denyAll();
                        }
                )
                .addFilterBefore(new JwtTokenValidatorFilter(publicPaths), BasicAuthenticationFilter.class)
                .formLogin(flc -> flc.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .exceptionHandling(exception -> exception
                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                    response.setContentType("application/json");
                                    response.getWriter().write("{\"error\": \"Access Denied\", \"message\": \"You don't have permission to access this resource\"}");
                                })
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"Authentication required\"}");
                        })
                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Replace with your allowed origins
        configuration.setAllowedHeaders(Collections.singletonList("*")); // Allow all headers
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); // Set max age for preflight requests

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply CORS settings to all endpoints
        return source; // Replace with actual CorsConfigurationSource implementation
    }

  /*  @Bean
    public UserDetailsService userDetailsService(){
       var  user1 =  User.builder()
                .username("naresh")
                .password("$2a$10$edGGOoAyZDuv8c9iJvbYne6rBhyWHUtPnpGHBPrUyVc4MPkh7NsPa")
                .roles("ADMIN")
                .build();

        var  user2 =  User.builder()
                .username("mayan")
                .password("$2a$10$1GphS/ZoOij.LWEs.AJtxOprUam00hE13AQzaNM0y5Y4P15610KYy")
                .roles("USER")
                .build();

       return new InMemoryUserDetailsManager(user1, user2);
    }*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationProvider authenticationProvider){
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

   /* @Bean
    public SecurityFilterChain securityConfig(HttpSecurity http){
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) ->
                        requests.requestMatchers("/api/request", "/api/companies").permitAll()
                                // .requestMatchers(RegexRequestMatcher.regexMatcher("/api/contacts/*")).authenticated()
                                .requestMatchers("/api/contacts").authenticated()
                                // Allow Swagger URLs
                                .requestMatchers("/api/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html").permitAll()
                )
                .formLogin(flc -> flc.disable())
                .httpBasic(Customizer.withDefaults())
                .build();
    }*/
}
