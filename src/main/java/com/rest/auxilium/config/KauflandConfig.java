package com.rest.auxilium.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KauflandConfig {
    @Value("${kaufland.offer.endpoint}")
    private String offerEndpoint;

    @Value("${kaufland.subscription.key}")
    private String subscriptionKey;

    @Value("${kaufland.store.id}")
    private String storeId;
}
