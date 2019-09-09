package com.rest.auxilium.service;

import com.rest.auxilium.domain.Services;
import com.rest.auxilium.domain.ServicesTransactionStatus;
import com.rest.auxilium.domain.Transaction;
import com.rest.auxilium.domain.User;
import com.rest.auxilium.repository.ServicesRepository;
import com.rest.auxilium.repository.TransactionRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ServicesServiceTest {

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ServicesService servicesService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Test
    public void saveServiceTest() {
        //Given
        Services service = new Services("Zrobienie zakupów", "Go for shopping content", "Sopot");
        long servicesCountBeforeSave = servicesRepository.findAll().size();

        //When
        servicesService.addService(service);

        //Then
        Assert.assertEquals(++servicesCountBeforeSave, servicesRepository.count());

    }


    @Test
    public void getServicesListTest() {
        //Given
        Services service1 = new Services("Asysta w urzędzie", "Go for shopping content", "Kraków");
        Services service2 = new Services("Zrobienie zakupów", "Go to school content", "Zabrze");
        servicesService.addService(service1);
        servicesService.addService(service2);
        long servicesinDBCount = servicesRepository.findAllByServicesTransactionStatus(ServicesTransactionStatus.PUBLISHED).size();

        //When
        List<Services> services = servicesService.getServicesList();

        //Then
        Assert.assertEquals(servicesinDBCount, services.size());

    }

    @Test
    public void getServicesByCityTest() {
        //Given
        Services service1 = new Services("Asysta w urzędzie", "Go for shopping content", "Kraków");
        Services service2 = new Services("Zrobienie zakupów", "Go to school content", "Zabrze");
        servicesService.addService(service1);
        servicesService.addService(service2);
        long servicesInDBCount = servicesRepository.findAllByCityAndServicesTransactionStatus("Kraków"
                ,ServicesTransactionStatus.PUBLISHED).size();

        //When
        List<Services> services = servicesService.getServicesByCity("Kraków");

        //Then
        Assert.assertEquals(servicesInDBCount, services.size());

    }

    @Test
    public void getServicesByNameTest() {
        //Given
        Services service1 = new Services("Asysta w urzędzie", "Go for shopping content", "Kraków");
        Services service2 = new Services("Zrobienie zakupów", "Go to school content", "Zabrze");
        servicesService.addService(service1);
        servicesService.addService(service2);
        long servicesinDBCount = servicesRepository.findAllByNameAndServicesTransactionStatus("Zrobienie zakupów", ServicesTransactionStatus.PUBLISHED).size();

        //When
        List<Services> services = servicesService.getServicesByName("Zrobienie zakupów");

        //Then
        Assert.assertEquals(servicesinDBCount, services.size());

    }

    @Test
    public void getServicesByCityAndNameTest() {
        //Given
        Services service1 = new Services("Asysta w urzędzie", "Go for shopping content", "Kraków");
        Services service2 = new Services("Zrobienie zakupów", "Go to school content", "Zabrze");
        servicesService.addService(service1);
        servicesService.addService(service2);
        long servicesInDBCount = servicesRepository.findAllByCityAndNameAndServicesTransactionStatus(
                "Zabrze", "Zrobienie zakupów", ServicesTransactionStatus.PUBLISHED).size();

        //When
        List<Services> services = servicesService.getServicesByCityAndName("Zabrze", "Zrobienie zakupów");

        //Then
        Assert.assertEquals(servicesInDBCount, services.size());

    }

    @Test
    public void deleteServiceTest() {
        //Given
        User user = new User("Kamil", 872738292, "mammanjud@gmailo.com", "Hkanj86$2");
        userService.saveUser(user);

        Services savedService = servicesService.addService(new Services("Asysta podczas wizyty u lekarza"
                , "Go for shopping content", "Gdańsk"));

        long servicesCountAfterSave = servicesRepository.count();

        Transaction transaction = new Transaction(user, savedService);
        Transaction savedTransaction = transactionService.createTransaction(transaction);
        long transactionCountAfterSave = transactionRepository.count();
        savedService.setTransaction(savedTransaction);

        //When
        servicesService.deleteService(savedTransaction.getService().getId());
        long transactionCountAfterDelete = transactionRepository.count();
        long servicesCountAfterDelete = servicesRepository.count();

        //Then
        Assert.assertEquals(++transactionCountAfterDelete, transactionCountAfterSave);
        Assert.assertEquals(++servicesCountAfterDelete, servicesCountAfterSave);

    }
}
