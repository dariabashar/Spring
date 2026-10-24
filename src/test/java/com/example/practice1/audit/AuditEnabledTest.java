package com.example.practice1.audit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest(properties = "app.audit.enabled=true")
@ActiveProfiles("test")
class AuditEnabledTest {

    @Autowired
    IdAuditor auditor;

    @Test
    void loggingAuditorIsUsed() {
        assertInstanceOf(LoggingIdAuditor.class, auditor);
    }
}