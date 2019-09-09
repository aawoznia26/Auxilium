package com.rest.auxilium.dto;

import com.rest.auxilium.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String uuid;
    private String name;
    private long phone;
    private String email;
    private String password;
    private boolean notifyAboutPoints;

    public UserDto(String name, long phone, String email, String password) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public static User mapToUser(final UserDto userDto){
        User user = new User(
                userDto.getName(),
                userDto.getPhone(),
                userDto.getEmail(),
                userDto.getPassword());
        if(userDto.getUuid() != null){
            user.setUuid(userDto.getUuid());
        }
        if(userDto.getId() != null){
            user.setId(userDto.getId());
        }
        if(userDto.isNotifyAboutPoints() != false){
            user.setNotifyAboutPoints(userDto.isNotifyAboutPoints());
        }

        return user;
    }

}
