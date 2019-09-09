package com.rest.auxilium.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Email {

    private String receiverEmail;
    private String subject;
    private String message;
    private String userName;

}
