package com.smartcity.hospital.helper;

import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class HospitalResponse {
    
    private int id;
    private String name;
    private String address;
    private String contactNumber;
    private String servicesOffered;
    private String type;
    private byte[] img;
    private String hours;

    HospitalResponse() {

    }

    public HospitalResponse(int id, String name, String address, String contactNumber, String servicesOffered,
            String type, byte[] img, String hours) {
        this.id = id;
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

    public void setId(int id) {
        this.id = id;
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

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
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
        return "HospitalResponse [id=" + id + ", name=" + name + ", address=" + address + ", contactNumber="
                + contactNumber + ", servicesOffered=" + servicesOffered + ", type=" + type + ", img="
                + Arrays.toString(img) + ", hours=" + hours + "]";
    }

    
}
