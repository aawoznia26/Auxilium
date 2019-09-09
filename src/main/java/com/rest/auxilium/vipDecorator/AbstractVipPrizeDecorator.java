package com.rest.auxilium.vipDecorator;

public abstract class AbstractVipPrizeDecorator implements VipPrize {

    private final VipPrize vipPrize;

    public AbstractVipPrizeDecorator(VipPrize vipPrize) {
        this.vipPrize = vipPrize;
    }

    @Override
    public Long generateCode() {
        return vipPrize.generateCode();
    }

    @Override
    public int calculateValue() {
        return vipPrize.calculateValue();
    }

    @Override
    public String getName() {
        return vipPrize.getName();
    }

    @Override
    public String getDescription() {
        return vipPrize.getDescription();
    }
}
