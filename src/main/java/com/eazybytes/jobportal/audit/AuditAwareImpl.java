package com.eazybytes.jobportal.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public java.util.Optional<String> getCurrentAuditor() {
        return java.util.Optional.of("Anonymous User");
    }
}
