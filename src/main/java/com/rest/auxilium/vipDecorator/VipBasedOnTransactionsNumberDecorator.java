package com.rest.auxilium.vipDecorator;

import com.rest.auxilium.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VipBasedOnTransactionsNumberDecorator extends AbstractVipPrizeDecorator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    public VipBasedOnTransactionsNumberDecorator(VipPrize vipPrize) {
        super(vipPrize);
    }

    @Override
    public Long generateCode() {
        LOGGER.info("Generating vip based on transactions code");
        return super.generateCode();
    }

    @Override
    public int calculateValue() {
        LOGGER.info("Calculating value vip based on transactions");
        return super.calculateValue() + 10;
    }

    @Override
    public String getName() {
        LOGGER.info("Getting name vip based on transactions");
        return super.getName() + " " + calculateValue() + " zł";
    }

    @Override
    public String getDescription() {
        LOGGER.info("Getting description vip based on transactions");
        return "Nagradzamy najbardziej aktywnych pomagających! Dlatego otrzymujesz " + super.getDescription()
                + " o wartości " + calculateValue() + " zł";
    }
}
