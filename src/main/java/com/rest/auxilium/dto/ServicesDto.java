package com.rest.auxilium.dto;


import com.rest.auxilium.domain.Services;
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

    public ServicesDto(String name, String description, int points, String city) {
        this.name = name;
        this.description = description;
        this.points = points;
        this.city = city;
    }

    public static Services mapToServices(final ServicesDto servicesDto){
        Services services = new Services(
                servicesDto.getName(),
                servicesDto.getDescription(),
                servicesDto.getPoints(),
                servicesDto.getCity());
        if(servicesDto.getId() != null){
            services.setId(servicesDto.getId());
        }
        return services;
    }

}
