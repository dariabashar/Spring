package com.example.practice1.audit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest(properties = "app.audit.enabled=false")
class AuditDisabledTest {

    @Autowired
    IdAuditor auditor;

    @Test
    void noopAuditorIsUsed() {
        assertInstanceOf(NoopIdAuditor.class, auditor);
    }
}