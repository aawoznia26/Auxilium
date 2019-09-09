package com.rest.auxilium.dto;


import com.rest.auxilium.domain.Services;
import com.rest.auxilium.domain.ServicesTransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServicesDto {

    private Long id;

    private String name;

    private String description;

    private int points;

    private String city;

    private ServicesTransactionStatus servicesTransactionStatus;

    public ServicesDto(String name, String description, String city) {
        this.name = name;
        this.description = description;
        this.city = city;
    }

    public ServicesDto(String name, String description, int points, String city, ServicesTransactionStatus servicesTransactionStatus) {
        this.name = name;
        this.description = description;
        this.points = points;
        this.city = city;
        this.servicesTransactionStatus = servicesTransactionStatus;
    }

    public static Services mapToServices(final ServicesDto servicesDto){
        Services services = new Services(
                servicesDto.getName(),
                servicesDto.getDescription(),
                servicesDto.getCity());

        if(servicesDto.getId() != null){
            services.setId(servicesDto.getId());
        }
        if(servicesDto.getServicesTransactionStatus() != null){
            services.setServicesTransactionStatus(servicesDto.getServicesTransactionStatus());
        }
        if(servicesDto.getPoints() == 0){
            services.setPoints(servicesDto.getPoints());
        }
        return services;
    }

}
