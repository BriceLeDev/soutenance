package com.soutenence.publiciteApp.service;

import com.soutenence.publiciteApp.enums.EmailTemplateName;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.lang.String;

@Service
public class EmailService {

    private  final JavaMailSender  javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;

    public EmailService(JavaMailSender javaMailSender, SpringTemplateEngine springTemplateEngine) {
        this.javaMailSender = javaMailSender;
        this.springTemplateEngine = springTemplateEngine;
    }
    //method d'envoie du mail
    // c est une method asycrone et il faut ajouté EnableAscy dans la methode principale l application
    @Async
    public void sendMail(
            String to,
            String username,
            EmailTemplateName emailTemplateName,
            String confimationUrl,
            String activationCode,
            String subject
    ) throws MessagingException {
        String templateName ;
        if (emailTemplateName==null){
            templateName = "confirm-email";

        }else {
            templateName = emailTemplateName.name();
        }
        //creation du mail sender
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                mimeMailMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );

        Map<String,Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("activationUrl",confimationUrl);
        properties.put("confimation_code", activationCode);

        //creation de thymleaf context qui prend les propriété
        Context context = new Context();
        context.setVariables(properties);

        //les propriétés de l envoie de l email
        mimeMessageHelper.setFrom("efo.amegnito@gmail.com");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);

         String template = springTemplateEngine.process(templateName,context);
        mimeMessageHelper.setText(template,true);

        javaMailSender.send(mimeMailMessage);
    }
}
