package com.rest.auxilium.vipDecorator;

public interface VipPrize {

    Long generateCode();
    int calculateValue();
    String getName();
    String getDescription();
}
