package com.rest.auxilium.scheduler;

import com.rest.auxilium.service.VipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class VipPrizeAssignment {

    @Autowired
    private VipService vipService;

    @Scheduled(cron = "0 30 0 * * *")
    public void assignVipPrizeTransactions(){
        vipService.assignTransactionsCoupons();

    }


    @Scheduled(cron = "0 45 0 * * *")
    public void assignVipPrizeCoupons(){
        vipService.assignPointsCoupons();

    }
}
