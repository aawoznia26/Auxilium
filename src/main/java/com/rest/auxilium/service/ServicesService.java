package com.rest.auxilium.service;

import com.rest.auxilium.domain.Services;
import com.rest.auxilium.domain.ServicesTransactionStatus;
import com.rest.auxilium.repository.ServicesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicesService {

    @Autowired
    private ServicesRepository servicesRepository;

    private Map<String, Integer> pointsMap= new HashMap<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesService.class);

    public ServicesService() {
        pointsMap.put("Zrobienie zakupów", 70);
        pointsMap.put("Pomoc w zakupach", 80);
        pointsMap.put("Asysta w wizycie w kościele", 80);
        pointsMap.put("Wyjście na spacer", 80);
        pointsMap.put("Sprzątanie domu", 460);
        pointsMap.put("Sprzątanie mieszkania", 280);
        pointsMap.put("Asysta podczas wizyty u lekarza", 70);
        pointsMap.put("Przygotowanie posiłku", 150);
        pointsMap.put("Asysta w urzędzie", 100);
    }

    public Services addService(Services services){
        String name = services.getName();
        Services serviceSaved = new Services();
        if(pointsMap.get(name) != null) {
            int points = pointsMap.get(name);
            Services servicesToSave = new Services(name, services.getDescription(),
                    services.getCity(), points, ServicesTransactionStatus.PUBLISHED);
            serviceSaved = servicesRepository.save(servicesToSave);
            LOGGER.info("Services saved successfully");
        } else {
            LOGGER.error("Wrong services name " + name);
        }
        return serviceSaved;

    }

    public List<Services> getServicesList(){
        return servicesRepository.findAllByServicesTransactionStatus(ServicesTransactionStatus.PUBLISHED);
    }

    public List<Services> getServicesByCity(String city){
        return servicesRepository.findAllByCityAndServicesTransactionStatus(city, ServicesTransactionStatus.PUBLISHED);
    }

    public List<Services> getServicesByName(String name){
        return servicesRepository.findAllByNameAndServicesTransactionStatus(name, ServicesTransactionStatus.PUBLISHED);
    }

    public List<Services> getServicesByCityAndName(String city, String name){
        return servicesRepository.findAllByCityAndNameAndServicesTransactionStatus(city, name, ServicesTransactionStatus.PUBLISHED);
    }

    public void deleteService(Long serviceId){
        if(servicesRepository.findOne(serviceId) != null){
            servicesRepository.delete(serviceId);
            LOGGER.info("Service deleted id: " + serviceId);
        } else {
            LOGGER.warn("Such service does not exist id: " + serviceId);
        }
    }
}
