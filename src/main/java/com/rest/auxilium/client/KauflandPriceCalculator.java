package com.rest.auxilium.client;

import com.rest.auxilium.dto.kaufland.KauflandOfferDto;

public interface KauflandPriceCalculator {

    int calculatePrice(KauflandOfferDto kauflandOfferDto);
}
