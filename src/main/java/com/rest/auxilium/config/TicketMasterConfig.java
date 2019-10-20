package com.rest.auxilium.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TicketMasterConfig {

    @Value("${ticketmaster.event.endpoint}")
    private String eventEndpoint;

    @Value("${ticketmaster.api.key}")
    private String apiKey;

    @Value("${ticketmaster.country.code}")
    private String countryCode;

}
