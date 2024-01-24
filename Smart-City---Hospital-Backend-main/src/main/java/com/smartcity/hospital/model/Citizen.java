package com.smartcity.hospital.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "Citizen")
public class Citizen {
    
    @Id
    @NonNull
    @Column(name = "email")
    private String email;

    @Column(name = "firstName")
    @NonNull
    private String firstName;

    @NonNull
    @Column(name = "lastName")
    private String lastName;

    @Column(name = "contactNumber")
    @NonNull
    private String contactNumber;

    @Column(name = "password")
    @NonNull
    private String password;

    public Citizen() {

    }

    public Citizen(String email, String firstName, String lastName, String contactNumber, String password) {
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
