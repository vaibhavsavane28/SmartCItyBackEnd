package com.smartcity.hospital.controller;

import org.springframework.web.bind.annotation.RestController;

import com.smartcity.hospital.helper.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin(origins="*")
public class WelcomeController {
    
    @Autowired
    private ResponseMessage responseMessage;

    @GetMapping("/")
    public ResponseEntity<ResponseMessage> getWelcomeMessage() {
        try {
            responseMessage.setMessage("Welcome");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);        
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
