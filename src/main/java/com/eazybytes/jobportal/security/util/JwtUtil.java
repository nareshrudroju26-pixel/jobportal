package com.eazybytes.jobportal.security.util;


import com.eazybytes.jobportal.constants.ApplicationConstants;
import com.eazybytes.jobportal.entity.JobPortalUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@PropertySource(value = "classpath:jwt.properties")
public class JwtUtil {

    private final Environment environment;

    @Value("${jwt.issuer:Job Portal}")
    private String jwtIssuer;

    @Value("${jwt.subject:JWT Token}")
    private String jwtSubject;

    @Value("${jwt.expiration.hours:1}")
    private int jwtExpirationHours;

    public String generateJwtToken(Authentication authentication){
       String jwtToken;
       var fetchedUser = (JobPortalUser)authentication.getPrincipal();
        String secret = environment.getProperty(ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        jwtToken = Jwts.builder()
                .issuer(jwtIssuer)
                .issuedAt(new java.util.Date())
                .setSubject(jwtSubject)
                .claim("email", fetchedUser.getEmail())
                .claim("mobileNumber", fetchedUser.getMobileNumber())
                .claim("name", fetchedUser.getName())
                .claim("roles", authentication.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(",")))
                .expiration(new java.util.Date(new java.util.Date().getTime() + jwtExpirationHours))
                .signWith(secretKey)
                .compact();

        return jwtToken;
    }
}
