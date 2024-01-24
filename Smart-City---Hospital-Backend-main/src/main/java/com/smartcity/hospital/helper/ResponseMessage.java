package com.smartcity.hospital.helper;

import org.springframework.stereotype.Component;

@Component
public class ResponseMessage {
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseMessage() {
    }

    public ResponseMessage(String message) {
        this.message = message;
    }

    
}
