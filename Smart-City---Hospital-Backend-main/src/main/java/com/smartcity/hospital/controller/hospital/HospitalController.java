package com.smartcity.hospital.controller.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smartcity.hospital.helper.ResponseMessage;
import com.smartcity.hospital.model.Hospital;
import com.smartcity.hospital.services.hospital.HospitalService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@CrossOrigin(origins="*")
public class HospitalController {
    

    @Autowired
    private ResponseMessage responseMessage;


    @Autowired
    private HospitalService hospitalService;

    @PostMapping("/hospital")
    public ResponseEntity<?> addHospital(@RequestHeader("Authorization") String authorization, @RequestBody Hospital hospital) {
        try {
            return hospitalService.addHospital(authorization, hospital);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PostMapping("/hospital/image")
    public ResponseEntity<?> addHospital(@RequestHeader("Authorization") String authorization,@RequestParam("id") int id, @RequestParam("image") MultipartFile image) {
        try {
            return hospitalService.addHospitalImage(authorization, id, image);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
    
    @GetMapping("/hospital")
    public ResponseEntity<?> getHospitals() {
        try {
            return hospitalService.getHospitals();
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @GetMapping("/hospital/{id}")
    public ResponseEntity<?> getHospitalById(@PathVariable("id") int id) {
        try {
            return hospitalService.getHospital(id);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @DeleteMapping("/hospital")
    public ResponseEntity<?> deleteHospital(@RequestHeader("Authorization") String authorization, @RequestParam("id") int id) {
        try {
            return hospitalService.deleteHospital(authorization, id);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
