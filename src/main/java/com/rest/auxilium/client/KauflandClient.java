package com.rest.auxilium.client;

import com.rest.auxilium.config.KauflandConfig;
import com.rest.auxilium.dto.kaufland.KauflandDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class KauflandClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(KauflandClient.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KauflandConfig kauflandConfig;


    private URI urlBuild(){
        LOGGER.info("Building url started");
        URI url = UriComponentsBuilder.fromHttpUrl(kauflandConfig.getOfferEndpoint() + kauflandConfig.getStoreId()).build().encode().toUri();
        LOGGER.info("Building url started. URI build: " + url);
        return url;

    }

    public List<KauflandDto> getKauflandProducts(){
        LOGGER.info("Getting Kaufland products started");

        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Ocp-Apim-Subscription-Key", kauflandConfig.getSubscriptionKey());
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            URI url = urlBuild();

            ResponseEntity<KauflandDto[]> respEntity = restTemplate.exchange(url, HttpMethod.GET, entity, KauflandDto[].class);
            LOGGER.info("Getting Kaufland products finished successfully. List size: " + Arrays.stream(respEntity.getBody()).collect(Collectors.toList()).size());
            return Optional.ofNullable(Arrays.stream(respEntity.getBody()).collect(Collectors.toList())).orElse(new ArrayList<>());

        } catch (RuntimeException e){
            LOGGER.error(e.getMessage());
            return new ArrayList<>();

        }

    }

}
