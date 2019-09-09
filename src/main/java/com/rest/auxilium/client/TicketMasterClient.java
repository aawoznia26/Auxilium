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
        URI url = UriComponentsBuilder.fromHttpUrl(ticketMasterConfig.getEventEndpoint() + "?" + ticketMasterConfig.getCountryCode()
                + "&" + ticketMasterConfig.getApiKey()).build().encode().toUri();
        return url;

    }

    public TicketMasterDto getTicketMasterEvents(){
        URI url = urlBuild();

        try{
            TicketMasterDto response = Optional.ofNullable(restTemplate.getForObject(url, TicketMasterDto.class)).orElse(new TicketMasterDto());
            return response;

        } catch (RuntimeException e){
            LOGGER.error(e.getMessage());
            return new TicketMasterDto();

        }

    }
}
