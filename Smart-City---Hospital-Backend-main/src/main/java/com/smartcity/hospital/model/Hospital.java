package com.smartcity.hospital.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Component
@Entity
@Table(name = "Hospital")
public class Hospital {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id")
    private int id;

    @Column(name = "Name")
    private String name;
    
    @Column(name = "Address")
    private String address;

    @Column(name = "Contact_Number")
    private String contactNumber;

    @Column(name = "Services_Offered")
    private String servicesOffered;

    @Column(name = "Type")
    private String type;

    @Lob
    @Column(name = "Image")
    @JsonBackReference
    private Blob img;

    @Column(name = "Hours")
    private String hours;

    public Hospital() {
    }

    public Hospital(String name, String address, String contactNumber, String servicesOffered, String type, Blob img,
            String hours) {
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.servicesOffered = servicesOffered;
        this.type = type;
        this.img = img;
        this.hours = hours;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getServicesOffered() {
        return servicesOffered;
    }

    public void setServicesOffered(String servicesOffered) {
        this.servicesOffered = servicesOffered;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Blob getImg() {
        return img;
    }

    public void setImg(Blob img) {
        this.img = img;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "Hospital [id=" + id + ", name=" + name + ", address=" + address + ", contactNumber=" + contactNumber
                + ", servicesOffered=" + servicesOffered + ", type=" + type + ", img=" + img + ", hours=" + hours + "]";
    }

   
    
}
