package com.rest.auxilium.service;


import com.rest.auxilium.domain.Points;
import com.rest.auxilium.domain.Services;
import com.rest.auxilium.domain.Transaction;
import com.rest.auxilium.domain.User;
import com.rest.auxilium.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VipServiceTestSuite {

    @Autowired
    private VipService vipService;

    @Autowired
    private UserService userService;

    @Autowired
    private ServicesService servicesService;

    @Autowired
    private PointsService pointsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionService transactionService;


    @Test
    public void assignPointsCouponsTest(){

        //Given
        User user = new User("Janusz", 872738292, "ania.woznia@gmail.com", "Fjsid876%");
        userService.saveUser(user);
        Points points = pointsService.issuePointsToUser(new Points(2000, user));
        List<Points> pointsList = new ArrayList<>();
        pointsList.add(points);
        user.setPoints(pointsList);

        //When
        vipService.assignPointsCoupons();

        // Then
        assertEquals(true, userRepository.findOne(user.getId()).isRewardedForPoints());

    }

    @Test
    public void assignTransactionCouponsTest(){

        //Given
        User user = new User("Janusz", 872738292, "ania.woznia@gmail.com", "Fjsid876%");
        User savedUser = userService.saveUser(user);
        Set<Transaction> transactionList = new HashSet<>();

        for(int i=0; i < 15; i++){
            Services service = new Services("Zrobienie zakupów", "Go to school content", "Zabrze");
            Services savedService = servicesService.addService(service);
            Long servicesId = savedService.getId();
            transactionService.createTransaction(new Transaction(savedUser, savedService));
            transactionService.assignTransaction(servicesId, savedUser);
            Transaction acceptedTransaction = transactionService.acceptTransaction(servicesId);
            transactionList.add(acceptedTransaction);

        }
        savedUser.setProviderTransaction(transactionList);
        userRepository.save(savedUser);

        //When
        vipService.assignTransactionsCoupons();

        // Then
        assertEquals(true, userRepository.findOne(user.getId()).isRewardedForTransactions());

    }
}
