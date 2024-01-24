package com.smartcity.hospital.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "Appointment")
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id")
    private int id;

    @Column(name = "AppointmentDate")
    private String date;

    @Column(name = "Email")
    private String email;

    @Column(name = "Hospital")
    private String name;

    @Column(name = "Time_Slot")
    private String slot;

    @Column(name = "Services")
    private String services;

    public Appointment() {

    }

    public Appointment(String date, String email, String name, String slot, String services) {
        this.date = date;
        this.email = email;
        this.name = name;
        this.slot = slot;
        this.services = services;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "Appointment [id=" + id + ", date=" + date + ", email=" + email + ", name=" + name + ", slot=" + slot
                + ", services=" + services + "]";
    }
}
