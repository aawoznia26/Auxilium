package com.rest.auxilium.scheduler;

import com.rest.auxilium.service.MailService;
import com.rest.auxilium.service.PointsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;

public class PointsTermination {

    @Autowired
    private PointsService pointsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    @Scheduled(cron = "0 10 0 * * *")
    public void expirePoints(){
        LOGGER.info("Scheduler terminating points started to work");
        LocalDate today = LocalDate.now();
        pointsService.expirePoints(today);
        LOGGER.info("Scheduler terminating points finished work");
    }


}
