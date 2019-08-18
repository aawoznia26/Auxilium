package com.rest.auxilium.service;

import com.rest.auxilium.domain.Services;
import com.rest.auxilium.repository.ServicesRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServicesServiceTest {

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private ServicesService servicesService;

    @Test
    public void saveServiceTest() {
        //Given
        Services service = new Services("go for shopping", "Go for shopping content", 90, "Sopot");
        long servicesCountBeforeSave = servicesRepository.findAll().size();

        //When
        servicesService.addService(service);

        //Then
        Assert.assertEquals(++servicesCountBeforeSave, servicesRepository.count());

    }


    @Test
    public void getServicesListTest() {
        //Given
        Services service1 = new Services("go for shopping", "Go for shopping content", 90, "Kraków");
        Services service2 = new Services("go to school", "Go to school content", 18, "Zabrze");
        servicesService.addService(service1);
        servicesService.addService(service2);
        long servicesinDBCount = servicesRepository.count();

        //When
        List<Services> services = servicesService.getServicesList();

        //Then
        Assert.assertEquals(servicesinDBCount, services.size());

    }
}
