package com.eazybytes.jobportal.scopes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Component
@Getter
@Setter
@SessionScope
public class SessionScopeBean {
    private String userName;
    public SessionScopeBean(){
        System.out.println("SessionScopeBean created");
    }
}
