package com.smartcity.hospital.services.appointment;

import java.util.*;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import com.smartcity.hospital.dao.AdminDao;
import com.smartcity.hospital.dao.AppointmentDao;
import com.smartcity.hospital.dao.CitizenDao;
import com.smartcity.hospital.dao.HospitalDao;
import com.smartcity.hospital.helper.JwtUtil;
import com.smartcity.hospital.helper.ResponseMessage;
import com.smartcity.hospital.model.Appointment;
import com.smartcity.hospital.model.Citizen;
import com.smartcity.hospital.model.Hospital;

@Component
public class AppointmentService {
    
    @Autowired
    private ResponseMessage responseMessage;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CitizenDao citizenDao;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private HospitalDao hospitalDao;
    
    private Logger logger = LoggerFactory.getLogger(AppointmentService.class);


    public ResponseEntity<?> bookAppointment(String authorizaiton, Appointment appointment) {
        try {
            String token = authorizaiton.substring(7);
            String email = jwtUtil.extractUsername(token);
            if (adminDao.getAdminByemail(email)!=null) {
                responseMessage.setMessage("Booking is allowed only for citizen.....");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMessage);
            }
            appointment.setEmail(email);
            appointmentDao.save(appointment);
            String subject = "Booking confirmed - "+appointment.getName(); //this will be the subject of the email
            generatePdf(appointment, subject);
            responseMessage.setMessage("Appointment booked successfully....");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    
    public void generatePdf(Appointment appointment, String subject) {
        try {
            // Create a ByteArrayOutputStream to store the PDF content
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
    
            // important step setting margins. based on the header and footer conent change the margin size
            Document document = new Document(PageSize.A4, 36, 36, 65, 36);

            PdfWriter writer = PdfWriter.getInstance(document, baos);
            writer.setPageEvent(new HeaderAndFooterPageEventHelper());
    
            document.open();
        
            Citizen citizen = citizenDao.getCitizenByemail(appointment.getEmail());
            // Sample content for demonstration purposes
            Paragraph p1 = new Paragraph(new Chunk(
                    "Booking Details",
                    FontFactory.getFont(FontFactory.HELVETICA, 18)));
            document.add(p1);
            Paragraph p2 = new Paragraph(new Phrase(
                    "Patient:"+citizen.getFirstName()+" "+citizen.getLastName()+"\nHospital:"+appointment.getName()+"\nDate Of Appointment:"+appointment.getDate()+"\nBooking slot:"+appointment.getSlot()+"\nServices booked for:"+appointment.getServices(), FontFactory.getFont(
                            FontFactory.HELVETICA, 12)));
            document.add(p2);
    
            document.close();
            writer.close();
    
           
            sendEmail(baos.toByteArray(), appointment, citizen.getFirstName()+" "+citizen.getLastName(), subject);
    
        
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
        }
    }    
    

    public void sendEmail(byte []arr,Appointment appointment, String name, String subject){
        String smtpHost = "smtp.gmail.com"; //replace this with a valid host
        int smtpPort = 465; //replace this with a valid port

        String sender = "smartcity7781@gmail.com"; //replace this with a valid sender email address
        String recipient = appointment.getEmail(); //replace this with a valid recipient email address
        String content = "Dear Customer,\nHere are your booking details."; //this will be the text of the email

        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);     
        properties.put("mail.smtp.ssl.enable", "true"); // security purposes
        properties.put("mail.smtp.auth", "true"); //for authentication
        
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(sender, "cmky owlr cqbo grnc");
            }
        });

        session.setDebug(true);
        // session.setTLS(true);
        //construct the pdf body part
        DataHandler dataSource = new DataHandler(arr, "application/pdf");

        MimeBodyPart pdfBodyPart = new MimeBodyPart();
        try {
            pdfBodyPart.setDataHandler(dataSource);
            String str = "Booking-"+name+".pdf";
            pdfBodyPart.setFileName(str);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        //construct the mime multi part
        MimeMultipart mimeMultipart = new MimeMultipart();

        
        try {
            MimeBodyPart texBodyPart = new MimeBodyPart();
            texBodyPart.setText(content);
            mimeMultipart.addBodyPart(texBodyPart);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        try {
            mimeMultipart.addBodyPart(pdfBodyPart);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        //create the sender/recipient addresses
        
        InternetAddress iaRecipient=null;
        InternetAddress iaSender=null;
        try {
            iaSender = new InternetAddress(sender);
            iaRecipient = new InternetAddress(recipient);
        } catch (AddressException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //construct the mime message
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setSender(iaSender);
            mimeMessage.setSubject(subject);
            mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
            mimeMessage.setContent(mimeMultipart);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        //send off the email
        try {
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        logger.info("Mail Sent......");
    }

    


    public ResponseEntity<?> getAppointmentsByUser(String authorization) {
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            List<Appointment> list = (List<Appointment>) appointmentDao.findAll();

            // Assuming List<Appointment> list is already populated from appointmentDao.findAll()
            List<Appointment> filteredList = list.stream()
            .filter(appointment -> appointment.getEmail().equals(email))
            .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK).body(filteredList);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }


    public ResponseEntity<?> updateAppointmentSlot(String authorization, int id, String date, String slot) {
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            if (adminDao.getAdminByemail(email)!=null) {
                responseMessage.setMessage("Booking is allowed only for citizen.....");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMessage);
            }
            Appointment appointment = appointmentDao.getAppointmentById(id);
            if (appointment==null) {
                responseMessage.setMessage("Appointment does not exist.....");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            if (!appointment.getEmail().equals(email)) {
                responseMessage.setMessage("You are not authorized to update any one else's appointment.....");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMessage);
            }
            appointment.setDate(date);
            appointment.setSlot(slot);

            appointmentDao.save(appointment);
            String subject = "Booking Slot updated - "+appointment.getName(); //this will be the subject of the email
            generatePdf(appointment, subject);
            responseMessage.setMessage("Appointment slot updated successfully....");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
