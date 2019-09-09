package com.rest.auxilium.service;

import com.rest.auxilium.domain.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    public void send(final Email email){
        LOGGER.info("Starting email preparation");
        try{
            javaMailSender.send(createMimeMessage(email));
            LOGGER.info("Email has been sent");

        }catch(MailException e){
            LOGGER.error("Failed to process email sending", e.getMessage(), e);
        }

    }

    private MimeMessagePreparator createMimeMessage(final Email email){
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(email.getReceiverEmail());
            messageHelper.setSubject(email.getSubject());
            if(email.getSubject().equals("Zmiana licznika punktów na Auxilium")) {
                messageHelper.setText(mailCreatorService.buildEmailWithPointsChange(email.getMessage(), email.getUserName()), true);
            } else {
                messageHelper.setText(mailCreatorService.buildEmailWithVipPrize(email.getMessage(), email.getUserName(), email.getSubject()), true);
            }

        };
    }
}
