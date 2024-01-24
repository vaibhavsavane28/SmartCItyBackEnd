package com.smartcity.hospital.controller.email;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.smartcity.hospital.helper.ResponseMessage;
import com.smartcity.hospital.services.email.EmailService;

import io.swagger.models.Response;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin(origins="*")
public class EmailController {
    
    Log log = LogFactory.getLog(EmailController.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private ResponseMessage responseMessage;

    @GetMapping("/email/{id}")
    public ResponseEntity<?> getOtp(@PathVariable("id") String email) {
        try {
            return emailService.getOtp(email);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
