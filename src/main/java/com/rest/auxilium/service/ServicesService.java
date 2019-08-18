package com.rest.auxilium.service;

import com.rest.auxilium.domain.Services;
import com.rest.auxilium.repository.ServicesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ServicesService {

    @Autowired
    private ServicesRepository servicesRepository;

    public Services addService(Services services){
        return servicesRepository.save(services);
    }

    public List<Services> getServicesList(){
        return servicesRepository.findAll();
    }

    public List<Services> getServicesByCity(String city){
        return servicesRepository.findAllByCity(city);
    }

    public List<Services> getServicesByName(String name){
        return servicesRepository.findAllByName(name);
    }

    public List<Services> getServicesByCityAndName(String city, String name){
        return servicesRepository.findAllByCityAndName(city, name);
    }
}
