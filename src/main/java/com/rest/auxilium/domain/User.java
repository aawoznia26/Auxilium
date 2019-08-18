package com.rest.auxilium.domain;

import com.rest.auxilium.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private Long id;

    @GeneratedValue
    private String uuid;

    @Column(length = 50)
    private String name;

    private long phone;

    @Column(length = 50)
    private String email;

    @Column(length = 25)
    private String password;


    @OneToMany(targetEntity = Points.class,
            cascade = CascadeType.MERGE,
            mappedBy = "user")
    private List<Points> points;

    @OneToMany(targetEntity = Transaction.class,
            mappedBy = "owner")
    private Set<Transaction> ownerTransaction;

    @OneToMany(targetEntity = Transaction.class,
            mappedBy = "serviceProvider")
    private Set<Transaction> providerTransaction;

    public User(String name, long phone, String email, String password) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }


    public static UserDto mapToUserDto(final User user){
        UserDto userDto = new UserDto(
                user.getId(),
                user.getUuid(),
                user.getName(),
                user.getPhone(),
                user.getEmail(),
                user.getPassword());
        return userDto;
    }
}
