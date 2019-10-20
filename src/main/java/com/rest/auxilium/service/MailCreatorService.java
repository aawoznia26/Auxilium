package com.rest.auxilium.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    public String buildEmailWithPointsChange(String message, String userName){
        LOGGER.info("Email building with points change started.");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("user_name", userName);
        LOGGER.info("Email building with points change finished.");
        return templateEngine.process("email/Points_update_email", context);

    }

    public String buildEmailWithVipPrize(String message, String userName, String subject){
        LOGGER.info("Email building with vip prize started.");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("user_name", userName);
        context.setVariable("subject", subject);
        LOGGER.info("Email building with vip prize finished.");
        return templateEngine.process("email/Vip_prize_email", context);
    }

}
