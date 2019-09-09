package com.rest.auxilium.vipDecorator;

public class VipBasedOnTransactionsNumberDecorator extends AbstractVipPrizeDecorator {

    public VipBasedOnTransactionsNumberDecorator(VipPrize vipPrize) {
        super(vipPrize);
    }

    @Override
    public Long generateCode() {
        return super.generateCode();
    }

    @Override
    public int calculateValue() {
        return super.calculateValue() + 10;
    }

    @Override
    public String getName() {
        return super.getName() + " " + calculateValue() + " zł";
    }

    @Override
    public String getDescription() {
        return "Nagradzamy najbardziej aktywnych pomagających! Dlatego otrzymujesz " + super.getDescription()
                + " o wartości " + calculateValue() + " zł";
    }
}
