package com.rest.auxilium.service;

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

    public String buildEmailWithPointsChange(String message, String userName){

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("user_name", userName);
        return templateEngine.process("email/Points_update_email", context);
    }

    public String buildEmailWithVipPrize(String message, String userName, String subject){

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("user_name", userName);
        context.setVariable("subject", subject);
        return templateEngine.process("email/Vip_prize_email", context);
    }

}
