package com.customerservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class CustomerController {
    @GetMapping
    public ResponseEntity<String> getCustomers(){
        return ResponseEntity.ok("Customer Data");
    }
}
