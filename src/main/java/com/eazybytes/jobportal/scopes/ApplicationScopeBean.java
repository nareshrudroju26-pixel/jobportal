package com.eazybytes.jobportal.scopes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.SessionScope;

@Component
@Getter
@Setter
@ApplicationScope
public class ApplicationScopeBean {
    private Integer userCount;
    public ApplicationScopeBean(){
        System.out.println("ApplicationScopeBean created");
    }

    public int incrementUser(){
        return userCount++;
    }
}
