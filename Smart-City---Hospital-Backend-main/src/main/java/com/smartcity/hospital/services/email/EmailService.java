package com.smartcity.hospital.services.email;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.smartcity.hospital.helper.ResponseMessage;

@Component
public class EmailService {
    

    @Autowired
    private ResponseMessage responseMessage;

    public ResponseEntity<?> getOtp(String email) {
        try {
            String from = "smartcity7781@gmail.com";
            String to = email;
            String subject= "OTP-Email Verifiaction";
           


            String host = "smtp.gmail.com";

            Properties properties = System.getProperties();
        
            properties.put("mail.smtp.host", host);  //set up host as gmail.com
            properties.put("mail.smtp.port", "465"); // gmail port number
            properties.put("mail.smtp.ssl.enable", "true"); // security purposes
            properties.put("mail.smtp.auth", "true"); //for authentication

            // Step 1: Create session class object and override PasswordAuthentication method to set from

            Session session = Session.getInstance(properties, new Authenticator() {
            
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(from, "cmky owlr cqbo grnc");
                }

            });

            session.setDebug(true);

            //Step 2 Use Mimemessage class to send mail

            MimeMessage m = new MimeMessage(session);

            m.setFrom(from);

            m.addRecipient(RecipientType.TO, new InternetAddress(to));

            m.setSubject(subject);

            Random random = new Random();
            int number = random.nextInt(999999);

            String otp = String.format("%06d", number);



            String message = "Welcome to Smart City...\n Here is your OTP:"+otp;

            m.setText(message);

            //Step 3: Transport mail
            Transport.send(m);  //message send karne ke liye

            responseMessage.setMessage(otp);

            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage); 
        }
    }
}
