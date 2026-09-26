package com.example.practice1.service;

import com.example.practice1.audit.IdAuditor;
import com.example.practice1.config.AppIdProperties;
import org.springframework.stereotype.Service;

@Service
public class IDService {

    private final AppIdProperties properties;
    private final IdAuditor auditor;

    public IDService(AppIdProperties properties, IdAuditor auditor) {
        this.properties = properties;
        this.auditor = auditor;
    }

    public String getId(String id) {
        if (id == null || id.length() < properties.minIdLength()) {
            return "ID is too short, minimum length is " + properties.minIdLength();
        }
        if (id.length() > properties.maxIdLength()) {
            return "ID is too long, maximum length is " + properties.maxIdLength();
        }
        auditor.audit(id);
        return properties.greeting() + " " + id;
    }
}