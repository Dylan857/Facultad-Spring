package com.facultad.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(List<String> recipients, String subject, Object object, String template) throws MessagingException {
        Context context = new Context();
        context.setVariable("object", object);

        String emailBody = templateEngine.process(template, context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        
        for (String to: recipients) {
        	message.setTo(to);
            message.setFrom("TutoriasIngenieras");
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setText(emailBody, true);
            javaMailSender.send(mimeMessage);
        }
    }
}
