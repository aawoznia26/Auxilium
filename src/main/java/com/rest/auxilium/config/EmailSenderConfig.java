package com.rest.auxilium.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EmailSenderConfig {

    @Value("http://localhost:8081/v1/email")
    private String emailEndpoint;

}
