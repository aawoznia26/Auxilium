package com.rest.auxilium.scheduler;

import com.rest.auxilium.service.MailService;
import com.rest.auxilium.service.VipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class VipPrizeAssignment {

    @Autowired
    private VipService vipService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    @Scheduled(cron = "0 30 0 * * *")
    public void assignVipPrizeTransactions(){
        LOGGER.info("Scheduler assigning vip prize based on transactions started to work");
        vipService.assignTransactionsCoupons();
        LOGGER.info("Scheduler assigning vip prize based on transactions finished work");
    }


    @Scheduled(cron = "0 45 0 * * *")
    public void assignVipPrizePoints(){
        LOGGER.info("Scheduler assigning vip prize based on points started to work");
        vipService.assignPointsCoupons();
        LOGGER.info("Scheduler assigning vip prize based on points finished work");
    }
}
