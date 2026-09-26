package com.example.practice1.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "app.audit", name = "enabled", havingValue = "true")
public class LoggingIdAuditor implements IdAuditor {

    private static final Logger log = LoggerFactory.getLogger(LoggingIdAuditor.class);

    @Override
    public void audit(String id) {
        log.info("ID accepted, length={}", id.length());
    }
}