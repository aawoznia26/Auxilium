package com.rest.auxilium.vipDecorator;

public class VipBasedOnPointsDecorator extends AbstractVipPrizeDecorator {

    public VipBasedOnPointsDecorator(VipPrize vipPrize) {
        super(vipPrize);
    }

    @Override
    public Long generateCode() {
        return super.generateCode();
    }

    @Override
    public int calculateValue() {
        return super.calculateValue() + 35;
    }

    @Override
    public String getName() {
        return super.getName() + " " + calculateValue() + " zł";
    }

    @Override
    public String getDescription() {
        return "Zrobiłeś już tak dużo dla innych. Pora na coś dla Ciebie! Wraz z 2000. punktem trzymujesz " + super.getDescription()
                + " o wartości " + calculateValue() + " zł";
    }
}
