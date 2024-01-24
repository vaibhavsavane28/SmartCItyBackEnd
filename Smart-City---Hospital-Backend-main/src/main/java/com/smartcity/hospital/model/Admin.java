package com.smartcity.hospital.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "Admin")
public class Admin {
    
    @Id
    private String email;

    private String firstName;

    private String lastName;

    private String contactNumber;

    private String password;

    public Admin() {

    }

    public Admin(String email, String firstName, String lastName, String contactNumber, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Citizen [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", contactNumber="
                + contactNumber + ", password=" + password + "]";
    }
}
