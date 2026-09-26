package com.example.practice1.audit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "app.audit", name = "enabled", havingValue = "false", matchIfMissing = true)
public class NoopIdAuditor implements IdAuditor {

    @Override
    public void audit(String id) {
        // auditing is turned off
    }
}