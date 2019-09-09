package com.rest.auxilium.service;


import com.rest.auxilium.domain.Services;
import com.rest.auxilium.domain.Transaction;
import com.rest.auxilium.domain.ServicesTransactionStatus;
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
public class TransactionServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ServicesService servicesService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ServicesRepository servicesRepository;


    @Test
    public void createTransactionTest() {
        //Given
        User user = new User("Kamil", 872738292, "mammanjud@gmailo.com", "Hkanj86$2");
        userService.saveUser(user);

        Services savedService = servicesService.addService(new Services("Przygotowanie posiłku", "Go for shopping content","Warszawa"));

        Transaction transaction = new Transaction(user, savedService);
        long transactionCountBeforeSave = transactionRepository.count();

        //When
        transactionService.createTransaction(transaction);

        //Then
        Assert.assertEquals(++transactionCountBeforeSave, transactionRepository.count());

    }

    @Test
    public void assignTransactionTest() {
        //Given
        User owner = new User("Kamil", 872738292, "mammanjud@gmailo.com", "Hkanj86$2");
        userService.saveUser(owner);

        Services savedService =servicesService.addService(new Services("Asysta w wizycie w kościele", "Go for shopping content"
                ,"Rzeszów"));

        Transaction transaction = new Transaction(owner, savedService);
        Transaction savedTransaction= transactionService.createTransaction(transaction);
        savedService.setTransaction(savedTransaction);
        servicesRepository.save(savedService);
        Long id = savedTransaction.getId();

        User serviceProvider = new User("Patryk", 273628933, "skqjqhhehe@gmailo.com", "GhsN827%2d");
        userService.saveUser(serviceProvider);

        //When
        transactionService.assignTransaction(savedService.getId(), serviceProvider);

        //Then
        Assert.assertEquals("Patryk", transactionRepository.getOne(id).getServiceProvider().getName());
        Assert.assertEquals("skqjqhhehe@gmailo.com", transactionRepository.getOne(id).getServiceProvider().getEmail());
        Assert.assertEquals(273628933, transactionRepository.getOne(id).getServiceProvider().getPhone());

    }

    @Test
    public void acceptTransactionTest() {
        //Given
        User owner = new User("Kamil", 872738292, "mammanjud@gmailo.com", "Hkanj86$2");
        userService.saveUser(owner);
        User provider = new User("Bartosz", 872123292, "nshbdjhe@gmailo.com", "KhhdOe86$2");
        userService.saveUser(provider);


        Services savedService = servicesService.addService(new Services("Sprzątanie mieszkania", "Go for shopping content"
                ,"Piastów"));
        Long serviceId = savedService.getId();

        Transaction transaction = new Transaction(owner, savedService);
        Transaction savedTransaction = transactionService.createTransaction(transaction);
        savedService.setTransaction(savedTransaction);
        servicesRepository.save(savedService);
        transactionService.assignTransaction(serviceId, provider);

        //When
        transactionService.acceptTransaction(serviceId);

        //Then
        Assert.assertEquals(ServicesTransactionStatus.ACCEPTED, transactionRepository.getOne(savedTransaction.getId()).getServicesTransactionStatus());
    }


    @Test
    public void getAllPublishedTransactionsTest() {
        //Given
        User user = new User("Kamil", 872738292, "mammanjud@gmailo.com", "Hkanj86$2");
        userService.saveUser(user);

        Services savedService = servicesService.addService(new Services("Przygotowanie posiłku"
                , "Go for shopping content","Koszalin"));

        Transaction transaction = new Transaction(user, savedService);
        Transaction savedTransaction = transactionService.createTransaction(transaction);

        //When
        List<Transaction> transactionList = transactionService.getAllPublishedTransactions();

        //Then
        Assert.assertNotEquals(null, transactionList);

    }

    @Test
    public void getAllTransactionsOwnedByUserTest() {
        //Given
        User user = new User("Kamil", 872738292, "mammanjud@gmailo.com", "Hkanj86$2");
        userService.saveUser(user);

        Services savedService = servicesService.addService(new Services("Asysta podczas wizyty u lekarza"
                , "Go for shopping content","Koszalin"));

        Transaction transaction = new Transaction(user, savedService);
        Transaction savedTransaction = transactionService.createTransaction(transaction);

        //When
        List<Transaction> transactionList = transactionService.getAllTransactionsOwnedByUser(user.getUuid());

        //Then
        Assert.assertEquals(1, transactionList.size());

    }

    @Test
    public void getTransactionsServedByUserTest() {
        //Given
        User owner = new User("Kamil", 872738292, "mammanjud@gmailo.com", "Hkanj86$2");
        userService.saveUser(owner);

        Services savedService = servicesService.addService(new Services("Przygotowanie posiłku"
                , "Go for shopping content", "Warszawa"));

        Transaction transaction = new Transaction(owner, savedService);
        Transaction savedTransaction= transactionService.createTransaction(transaction);
        savedService.setTransaction(savedTransaction);
        servicesRepository.save(savedService);
        Long id = savedTransaction.getId();

        User serviceProvider = new User("Patryk", 273628933, "skqjqhhehe@gmailo.com", "GhsN827%2d");
        userService.saveUser(serviceProvider);
        transactionService.assignTransaction(savedService.getId(), serviceProvider);

        //When
        List<Transaction> transactionList = transactionService.getAllTransactionsServicedByUser(serviceProvider.getUuid());

        //Then
        Assert.assertEquals(1, transactionList.size());

    }

}
