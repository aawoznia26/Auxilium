package com.rest.auxilium.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KauflandConfig {
    @Value("https://api.kaufland.net/offers/api/v5/")
    private String offerEndpoint;

    @Value("7234843e4be34f2980b40fe92d6e6554")
    private String subscriptionKey;

    @Value("PL7360")
    private String storeId;
}
