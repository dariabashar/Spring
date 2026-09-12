package com.example.practice1.controller;

import com.example.practice1.service.IDService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/id")
public class IDController {

    private final IDService idService;

    public IDController(IDService idService) {
        this.idService = idService;
    }

    @PostMapping
    public String get(@RequestBody String id){
        return idService.getId(id);
    }
}
