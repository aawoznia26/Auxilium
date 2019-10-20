package com.rest.auxilium.client;


import com.rest.auxilium.config.TicketMasterConfig;
import com.rest.auxilium.dto.ticketMaster.TicketMasterDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
public class TicketMasterClient {


    private static final Logger LOGGER = LoggerFactory.getLogger(TicketMasterClient.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TicketMasterConfig ticketMasterConfig;

    private URI urlBuild(){
        LOGGER.info("Building url started");
        URI url = UriComponentsBuilder.fromHttpUrl(ticketMasterConfig.getEventEndpoint() + "?" + ticketMasterConfig.getCountryCode()
                + "&" + ticketMasterConfig.getApiKey()).build().encode().toUri();
        LOGGER.info("Building url started. URI build: " + url);
        return url;

    }

    public TicketMasterDto getTicketMasterEvents(){
        LOGGER.info("Getting TicketMaster events started");
        URI url = urlBuild();

        try{
            TicketMasterDto response = Optional.ofNullable(restTemplate.getForObject(url, TicketMasterDto.class)).orElse(new TicketMasterDto());
            LOGGER.info("Getting TicketMaster events finished successfully");
            return response;

        } catch (RuntimeException e){
            LOGGER.error(e.getMessage());
            return new TicketMasterDto();

        }

    }
}
