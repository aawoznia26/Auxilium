package com.rest.auxilium.vipDecorator;

import java.util.Random;

public class BasicVipPrize implements VipPrize {


    @Override
    public Long generateCode() {
        Long code = new Random().nextLong();
        return Math.abs(code);

    }

    @Override
    public int calculateValue() {
        return 40;
    }

    @Override
    public String getName() {
        return "Kupon rabatowy";
    }

    @Override
    public String getDescription() {
        return "kupon rabatowy do wykorzystania w sieci sklepów Rossmann";
    }
}
