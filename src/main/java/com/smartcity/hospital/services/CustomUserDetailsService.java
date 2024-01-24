package com.smartcity.hospital.services;

import java.util.ArrayList;

import com.smartcity.hospital.dao.AdminDao;
import com.smartcity.hospital.dao.CitizenDao;
import com.smartcity.hospital.helper.JwtUtil;
import com.smartcity.hospital.model.Admin;
import com.smartcity.hospital.model.Citizen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

//CustomUserDetailsService has to override loadByUsername function provided by spring security...

@Component
public class CustomUserDetailsService implements UserDetailsService{


    @Autowired
    public CitizenDao citizenDao;

    @Autowired
    public Citizen citizen;

    @Autowired
    public JwtUtil jwtUiUtil;

    @Autowired
    public AdminDao adminDao;

    @Autowired
    public Admin admin;

    public Citizen findByUsername(String email){
        return citizenDao.getCitizenByemail(email);
    }

    public Admin findByAdminname(String email){
        return adminDao.getAdminByemail(email);
    }
    @Override
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
       //username means email 
        citizen = findByUsername(useremail);
        if(citizen != null){

            return new User(citizen.getEmail(), citizen.getPassword(), new ArrayList<>()); 
        }

        admin = findByAdminname(useremail);
        if(admin != null){

            return new User(admin.getEmail(), admin.getPassword(), new ArrayList<>()); 
        }
        else{
            throw new UsernameNotFoundException("User not found");
        }
    }

    public ResponseEntity<?> getUserByToken(String token){
        try {
            String email = jwtUiUtil.extractUsername(token);
            UserDetails uDetails = loadUserByUsername(email);  //username == email id
            return ResponseEntity.ok(uDetails); 
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }


}