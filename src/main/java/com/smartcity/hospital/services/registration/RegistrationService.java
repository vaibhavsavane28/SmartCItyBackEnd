package com.smartcity.hospital.services.registration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.smartcity.hospital.config.MySecurityConfig;
import com.smartcity.hospital.dao.AdminDao;
import com.smartcity.hospital.dao.CitizenDao;
import com.smartcity.hospital.helper.JwtUtil;
import com.smartcity.hospital.helper.ResponseMessage;
import com.smartcity.hospital.model.Admin;
import com.smartcity.hospital.model.Citizen;
import com.smartcity.hospital.services.CustomUserDetailsService;

@Component
public class RegistrationService {
    
    @Autowired
    private ResponseMessage responseMessage;

    @Autowired
    private CitizenDao citizenDao;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public CustomUserDetailsService customUserDetailsService;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public MySecurityConfig mySecurityConfig;

    private Log log = LogFactory.getLog(RegistrationService.class);

    public ResponseEntity<?> createCitizen(Citizen citizen) {
        try {
            if (citizen.getPassword() == null) {
                // Handle the case where the password is null
                throw new IllegalArgumentException("Password cannot be null");
            }
            //Step 1: Citizen should not exist either in citizen or admin DB.
            String email = citizen.getEmail();
            if (citizenDao.getCitizenByemail(email)!=null || adminDao.getAdminByemail(email)!=null) {
                responseMessage.setMessage("Email already exists.....");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }

            log.info("Citizen:"+citizen.toString());
            
            String password = citizen.getPassword();
            citizen.setPassword(mySecurityConfig.passwordEncoder().encode(citizen.getPassword()));

            //Step 2: Save the citizen in db.
            citizenDao.save(citizen);

            //Step 3: Token generate krna for the citizen
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(citizen.getEmail()); //username == email
            
            String token = jwtUtil.generateToken(userDetails);

            //Step 4: Return the token in message.
            responseMessage.setMessage(token);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> loginCitizen(String email, String password) {
        try {
            Citizen citizen = citizenDao.getCitizenByemail(email);
            if (citizen==null) {
                responseMessage.setMessage("Email id does not exist....");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if (bCryptPasswordEncoder.matches(password, citizen.getPassword())) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(citizen.getEmail());
                String token = jwtUtil.generateToken(userDetails);
                responseMessage.setMessage(token);
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            }

            responseMessage.setMessage("Bad credentials.");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> createAdmin(Admin admin) {
        try {
            if (admin.getPassword() == null) {
                // Handle the case where the password is null
                throw new IllegalArgumentException("Password cannot be null");
            }
            //Step 1: Admin should not exist either in citizen or admin DB.
            String email = admin.getEmail();
            if (citizenDao.getCitizenByemail(email)!=null || adminDao.getAdminByemail(email)!=null) {
                responseMessage.setMessage("Email already exists.....");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }

            log.info("Admin:"+admin.toString());
            
            String password = admin.getPassword();
            admin.setPassword(mySecurityConfig.passwordEncoder().encode(admin.getPassword()));

            //Step 2: Save the citizen in db.
            adminDao.save(admin);

            //Step 3: Token generate krna for the citizen
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(admin.getEmail()); //username == email
            
            String token = jwtUtil.generateToken(userDetails);

            //Step 4: Return the token in message.
            responseMessage.setMessage(token);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> loginAdmin(String email, String password) {
        try {
            Admin admin = adminDao.getAdminByemail(email);
            if (admin==null) {
                responseMessage.setMessage("Email id does not exist....");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if (bCryptPasswordEncoder.matches(password, admin.getPassword())) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(admin.getEmail());
                String token = jwtUtil.generateToken(userDetails);
                responseMessage.setMessage(token);
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            }

            responseMessage.setMessage("Bad credentials.");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> updateContact(String authorization, String contact){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            Citizen citizen = citizenDao.getCitizenByemail(email);
            if (citizen!=null){
                citizen.setContactNumber(contact);
                citizenDao.save(citizen);
                responseMessage.setMessage("Contact updated successfully....");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseMessage);
            }
            Admin admin = adminDao.getAdminByemail(email);
            admin.setContactNumber(contact);
            adminDao.save(admin);
            responseMessage.setMessage("Contact updated successfully....");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> updateName(String authorization, String firstName, String lastName) {
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            Citizen citizen = citizenDao.getCitizenByemail(email);
            if (citizen!=null){
                citizen.setFirstName(firstName);
                citizen.setLastName(lastName);
                citizenDao.save(citizen);
                responseMessage.setMessage("Name updated successfully....");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseMessage);
            }
            Admin admin = adminDao.getAdminByemail(email);
            admin.setFirstName(firstName);
            admin.setLastName(lastName);
            adminDao.save(admin);
            responseMessage.setMessage("Name updated successfully....");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> updatePassword(String authorization, String password) {
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            Citizen citizen = citizenDao.getCitizenByemail(email);
            if (citizen!=null){
                citizen.setPassword(mySecurityConfig.passwordEncoder().encode(password));
                citizenDao.save(citizen);
                responseMessage.setMessage("Password updated successfully....");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseMessage);
            }
            Admin admin = adminDao.getAdminByemail(email);
            admin.setPassword(mySecurityConfig.passwordEncoder().encode(password));
            adminDao.save(admin);
            responseMessage.setMessage("Password updated successfully....");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
        
    }

    public ResponseEntity<?> deletePassword(String authorization) {
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            Citizen citizen = citizenDao.getCitizenByemail(email);
            if (citizen!=null){
                citizenDao.delete(citizen);
                responseMessage.setMessage("User deleted successfully....");
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            }
            Admin admin = adminDao.getAdminByemail(email);
            adminDao.delete(admin);
            responseMessage.setMessage("Admin updated successfully....");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
