package com.rest.auxilium.scheduler;

import com.rest.auxilium.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;

public class PointsTermination {

    @Autowired
    private PointsService pointsService;

    @Scheduled(cron = "0 10 0 * * *")
    public void expirePoints(){
        LocalDate today = LocalDate.now();
        pointsService.expirePoints(today);

    }


}
