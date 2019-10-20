package com.rest.auxilium.domain;

import com.rest.auxilium.dto.EmailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Email {

    private String receiverEmail;
    private String subject;
    private String message;
    private String userName;
    private EmailGroup emailGroup;

    public static EmailDto mapToEmailDto(Email email){
        return new EmailDto( email.getReceiverEmail(), email.getSubject(), email.getMessage(), email.getUserName()
                , email.getEmailGroup());
    }

}
