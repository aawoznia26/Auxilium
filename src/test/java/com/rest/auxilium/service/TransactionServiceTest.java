package com.rest.auxilium.service;


import com.rest.auxilium.domain.Services;
import com.rest.auxilium.domain.Transaction;
import com.rest.auxilium.domain.TransactionStatus;
import com.rest.auxilium.domain.User;
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

    @Test
    public void createTransactionTest() {
        //Given
        User user = new User("Kamil", 872738292, "mammanjud@gmailo.com", "Hkanj86$2");
        userService.saveUser(user);

        Services service = new Services("go for shopping", "Go for shopping content", 90, "Warszawa");
        servicesService.addService(service);

        Transaction transaction = new Transaction(user, service);
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

        Services service = new Services("go for shopping", "Go for shopping content", 90, "Rzeszów");
        servicesService.addService(service);

        Transaction transaction = new Transaction(owner, service);
        Long id = transactionService.createTransaction(transaction).getId();

        User serviceProvider = new User("Patryk", 273628933, "skqjqhhehe@gmailo.com", "GhsN827%2d");
        userService.saveUser(serviceProvider);

        //When
        transactionService.assignTransaction(id, serviceProvider);

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

        Services service = new Services("go for shopping", "Go for shopping content", 90, "Piastów");
        servicesService.addService(service);

        Transaction transaction = new Transaction(owner, service);
        Transaction savedTransaction = transactionService.createTransaction(transaction);

        //When
        transactionService.acceptTransaction(savedTransaction);

        //Then
        Assert.assertEquals(TransactionStatus.ACCEPTED, transactionRepository.getOne(savedTransaction.getId()).getTransactionStatus());
    }

    @Test
    public void deleteTransactionTest() {
        //Given
        User user = new User("Kamil", 872738292, "mammanjud@gmailo.com", "Hkanj86$2");
        userService.saveUser(user);

        Services service = new Services("go for shopping", "Go for shopping content", 90, "Gdańsk");
        servicesService.addService(service);

        Transaction transaction = new Transaction(user, service);
        Transaction savedTransaction = transactionService.createTransaction(transaction);
        long transactionCountAfterSave = transactionRepository.count();

        //When
        transactionService.deleteTransaction(savedTransaction.getId());
        long transactionCountAfterDelete = transactionRepository.count();

        //Then
        Assert.assertEquals(++transactionCountAfterDelete, transactionCountAfterSave);

    }

    @Test
    public void getAllPublishedTransactionsTest() {
        //Given
        User user = new User("Kamil", 872738292, "mammanjud@gmailo.com", "Hkanj86$2");
        userService.saveUser(user);

        Services service = new Services("go for shopping", "Go for shopping content", 90, "Koszalin");
        servicesService.addService(service);

        Transaction transaction = new Transaction(user, service);
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

        Services service = new Services("go for shopping", "Go for shopping content", 90, "Koszalin");
        servicesService.addService(service);

        Transaction transaction = new Transaction(user, service);
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

        Services service = new Services("go for shopping", "Go for shopping content", 90, "Warszawa");
        servicesService.addService(service);

        Transaction transaction = new Transaction(owner, service);
        Long id = transactionService.createTransaction(transaction).getId();

        User serviceProvider = new User("Patryk", 273628933, "skqjqhhehe@gmailo.com", "GhsN827%2d");
        userService.saveUser(serviceProvider);
        transactionService.assignTransaction(id, serviceProvider);

        //When
        List<Transaction> transactionList = transactionService.getAllTransactionsServicedByUser(serviceProvider.getUuid());

        //Then
        Assert.assertEquals(1, transactionList.size());

    }
}
