package com.rest.auxilium.vipDecorator;

import com.rest.auxilium.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VipBasedOnPointsDecorator extends AbstractVipPrizeDecorator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    public VipBasedOnPointsDecorator(VipPrize vipPrize) {
        super(vipPrize);
    }

    @Override
    public Long generateCode() {
        LOGGER.info("Generating vip based on points code");
        return super.generateCode();
    }

    @Override
    public int calculateValue() {
        LOGGER.info("Calculating value vip based on points");
        return super.calculateValue() + 35;
    }

    @Override
    public String getName() {
        LOGGER.info("Getting name vip based on points");
        return super.getName() + " " + calculateValue() + " zł";
    }

    @Override
    public String getDescription() {
        LOGGER.info("Getting description vip based on points");
        return "Zrobiłeś już tak dużo dla innych. Pora na coś dla Ciebie! Wraz z 2000. punktem trzymujesz " + super.getDescription()
                + " o wartości " + calculateValue() + " zł";
    }
}
