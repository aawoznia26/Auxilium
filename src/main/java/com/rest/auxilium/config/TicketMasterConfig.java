package com.rest.auxilium.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TicketMasterConfig {

    @Value("https://app.ticketmaster.com/discovery/v2/events.json")
    private String eventEndpoint;

    @Value("apikey=t5EIJfzmAdFHDYXYtvGp3AArvbJqATXS")
    private String apiKey;

    @Value("countryCode=PL")
    private String countryCode;

}
