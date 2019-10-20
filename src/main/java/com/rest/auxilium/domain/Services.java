package com.rest.auxilium.domain;

import com.rest.auxilium.dto.ServicesDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    private String description;

    @Column(length = 50)
    private String city;

    @Enumerated(EnumType.STRING)
    private ServicesTransactionStatus servicesTransactionStatus;

    private int points;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TRANSACTION_ID")
    private Transaction transaction;

    public Services(String name, String description, String city) {
        this.name = name;
        this.description = description;
        this.city = city;
    }

    public Services(String name, String description, String city, int points, ServicesTransactionStatus servicesTransactionStatus) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.points = points;
        this.servicesTransactionStatus = servicesTransactionStatus;
    }



    public static ServicesDto mapToServicesDto(final Services services){
        ServicesDto servicesDto = new ServicesDto(
                services.getId(),
                services.getName(),
                services.getDescription(),
                services.getPoints(),
                services.getCity(),
                services.getServicesTransactionStatus());
        return servicesDto;
    }

    public static List<ServicesDto> mapToServicesDtoList(final List<Services> services){
       return services.stream()
                .map(s -> new ServicesDto(
                        s.getId(),
                        s.getName(),
                        s.getDescription(),
                        s.getPoints(),
                        s.getCity(),
                        s.getServicesTransactionStatus()))
               .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Services{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", points=" + points +
                '}';
    }
}
