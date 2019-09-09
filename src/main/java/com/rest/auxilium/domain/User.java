package com.rest.auxilium.domain;

import com.rest.auxilium.dto.UserDto;
import com.rest.auxilium.observer.Observer;
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
public class User implements Observer {

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

    private boolean notifyAboutPoints;

    @OneToMany(targetEntity = Points.class,
            cascade = CascadeType.MERGE,
            mappedBy = "user", fetch = FetchType.EAGER)
    private List<Points> points;

    @OneToMany(targetEntity = Transaction.class,
            mappedBy = "owner")
    private Set<Transaction> ownerTransaction;

    @OneToMany(targetEntity = Transaction.class,
            mappedBy = "serviceProvider")
    private Set<Transaction> providerTransaction;

    @OneToMany(targetEntity = Product.class,
            mappedBy = "user")
    private List<Product> product;

    @OneToMany(targetEntity = Event.class,
            mappedBy = "user")
    private List<Event> event;

    private boolean rewardedForPoints;

    private boolean rewardedForTransactions;


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
                user.getPassword(),
                user.isNotifyAboutPoints());
        return userDto;
    }

    public Email update(Points points){
        return new Email(email, "Zmiana licznika punktów na Auxilium", "Własnie "
                + points.getValue() + " Twoich punktów zmieniło status na " + points.getPointStatus().label, name);
    };
}
