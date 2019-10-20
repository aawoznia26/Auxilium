package com.rest.auxilium.dto;

import com.rest.auxilium.domain.Email;
import com.rest.auxilium.domain.EmailGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    private String receiverEmail;
    private String subject;
    private String message;
    private String userName;
    private EmailGroup emailGroup;

    public static Email mapToEmail(EmailDto emailDto){
        return new Email(emailDto.getReceiverEmail(), emailDto.getSubject(), emailDto.getMessage(), emailDto.getUserName()
                , emailDto.getEmailGroup());
    }

}
