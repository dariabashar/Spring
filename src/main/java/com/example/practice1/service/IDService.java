package com.example.practice1.service;

import org.springframework.stereotype.Service;

@Service
public class IDService {

    public String getId(String id){
        return "My uni id is: " + id;
    }

}
