package com.smartcity.hospital.controller.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.smartcity.hospital.helper.ResponseMessage;
import com.smartcity.hospital.model.Appointment;
import com.smartcity.hospital.services.appointment.AppointmentService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@CrossOrigin(origins="*")
public class AppointmentController {
    
    @Autowired
    private ResponseMessage responseMessage;

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/appointment")
    public ResponseEntity<?> bookAppointment(@RequestHeader("Authorization") String authorization,@RequestBody Appointment appointment) {
        try {
            return appointmentService.bookAppointment(authorization, appointment);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
    
    @GetMapping("/appointment")
    public ResponseEntity<?> getAppointmentsByUser(@RequestHeader("Authorization") String authorization) {
        try {
            return appointmentService.getAppointmentsByUser(authorization);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @PutMapping("appointment/{id}")
    public ResponseEntity<?> updateAppointmentSlot(@RequestHeader("Authorization") String authorization,@PathVariable int id, @RequestParam("date") String date, @RequestParam("slot") String slot) {
        try {
            return appointmentService.updateAppointmentSlot(authorization, id, date, slot);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
    
}
