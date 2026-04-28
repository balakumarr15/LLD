package com.example.ecom.services;

import com.example.ecom.libraries.Sendgrid;
import org.springframework.stereotype.Component;

@Component
public class EmailAdapter {
    private final Sendgrid sendgrid = new Sendgrid();

    public void sendEmail(String email, String subject, String body) {
        sendgrid.sendEmailAsync(email, subject, body);
    }
}
