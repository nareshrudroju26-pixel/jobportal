package com.eazybytes.jobportal.scopes;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scopes")
@RequiredArgsConstructor
public class ScopesController {

    private final RequestScopeBean requestScopeBean;
    private final SessionScopeBean sessionScopeBean;

    @GetMapping("/request")
    public ResponseEntity<String> testRequestScope(){
        requestScopeBean.setUserName("John Doe");
        return ResponseEntity.ok("Request Scope Bean User Name: " + requestScopeBean.getUserName());
    }

    @GetMapping("/session")
    public ResponseEntity<String> testSessionScope(){
        sessionScopeBean.setUserName("John Doe");
        return ResponseEntity.ok("Session Scope Bean User Name: " + sessionScopeBean.getUserName());
    }

    @GetMapping("/test")
    public ResponseEntity<String> testScopes(){
        return ResponseEntity.ok(sessionScopeBean.getUserName());
    }



}
