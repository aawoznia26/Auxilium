package com.rest.auxilium.domain;

import com.rest.auxilium.dto.ServicesDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Services {

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(length = 50)
    private String city;

    private int points;

    @OneToMany(targetEntity = Transaction.class,
            mappedBy = "service")
    private List<Transaction> transaction = new ArrayList<>();

    public Services(String name, String description, int points, String city) {
        this.name = name;
        this.description = description;
        this.points = points;
        this.city = city;

    }

    public static ServicesDto mapToServicesDto(final Services services){
        ServicesDto servicesDto = new ServicesDto(
                services.getId(),
                services.getName(),
                services.getDescription(),
                services.getPoints(),
                services.getCity());
        return servicesDto;
    }

    public static List<ServicesDto> mapToServicesDtoList(final List<Services> services){
       return services.stream()
                .map(s -> new ServicesDto(
                        s.getId(),
                        s.getName(),
                        s.getDescription(),
                        s.getPoints(),
                        s.getCity()))
               .collect(Collectors.toList());
    }

}
