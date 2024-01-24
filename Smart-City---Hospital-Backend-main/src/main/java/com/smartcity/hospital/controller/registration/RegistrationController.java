package com.smartcity.hospital.controller.registration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartcity.hospital.helper.ResponseMessage;
import com.smartcity.hospital.model.Admin;
import com.smartcity.hospital.model.Citizen;
import com.smartcity.hospital.services.registration.RegistrationService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@CrossOrigin(origins="*")
public class RegistrationController {
    

    @Autowired
    private ResponseMessage responseMessage;

    @Autowired
    private RegistrationService registrationService;

    private Log log = LogFactory.getLog(RegistrationController.class);

    @PostMapping(value = "/citizen", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCitizen(@RequestBody Citizen citizen) {
        try {
            log.info("Received Citizen: " + citizen);
            return registrationService.createCitizen(citizen);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @GetMapping(value = "/citizen/{email}/{password}")
    public ResponseEntity<?> loginCitizen(@PathVariable("email") String email, @PathVariable("password") String password) {
        try {
            return registrationService.loginCitizen(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping(value = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCitizen(@RequestBody Admin admin) {
        try {
            return registrationService.createAdmin(admin);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @GetMapping(value = "/admin/{email}/{password}")
    public ResponseEntity<?> loginAdmin(@PathVariable("email") String email, @PathVariable("password") String password) {
        try {
            return registrationService.loginAdmin(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
    
    @PutMapping("/user/contact")
    public ResponseEntity<?> updateContact(@RequestHeader("Authorization") String authorization, @RequestParam("contactNumber") String contactNumber) {
        try {
            return registrationService.updateContact(authorization, contactNumber);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PutMapping("/user/name")
    public ResponseEntity<?> updateContact(@RequestHeader("Authorization") String authorization, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        try {
            return registrationService.updateName(authorization, firstName, lastName);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PutMapping("/user/password")
    public ResponseEntity<?> updatePassword(@RequestHeader("Authorization") String authorization, @RequestParam("password") String password) {
        try {
            return registrationService.updatePassword(authorization, password);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> updatePassword(@RequestHeader("Authorization") String authorization) {
        try {
            return registrationService.deletePassword(authorization);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }



}
