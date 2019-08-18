package com.rest.auxilium.service;

import com.rest.auxilium.domain.PointStatus;
import com.rest.auxilium.domain.Points;
import com.rest.auxilium.domain.User;
import com.rest.auxilium.repository.PointsRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PointsServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PointsRepository pointsRepository;

    @Autowired
    private PointsService pointsService;

    @Test
    public void issuePointsToUserTest() {
        //Given
        User user = new User("Kamil", 872738292, "mammanjud@gmailo.com", "hhakiisj");
        userService.saveUser(user);
        Points points = new Points(70, user);

        //When
        int pointsRecordsNumber = pointsRepository.findAll().size();
        pointsService.issuePointsToUser(points);

        //Then
        Assert.assertEquals(++pointsRecordsNumber, pointsRepository.findAll().size());

    }

    @Test
    public void expirePointsTest() {
        //Given
        User user = new User("Adam", 836482973, "fdfsrgfregre@gmailo.com", "dfefrergfr");
        userService.saveUser(user);

        Points points = new Points(70, user);
        Points pointsToExpire = pointsService.issuePointsToUser(points);

        //When

        pointsService.expirePoints(pointsToExpire);

        //Then
        Assert.assertEquals(PointStatus.EXPIRED, pointsRepository.getOne(pointsToExpire.getId()).getPointStatus());

    }

    @Test
    public void usePointsTest() {
        //Given
        User user = new User("Laura", 353522524, "rfrgegreg@gmailo.com", "r5tge5te");
        userService.saveUser(user);

        Points points = new Points(70, user);
        Points pointsToUse = pointsService.issuePointsToUser(points);

        //When

        pointsService.usePoints(pointsToUse);

        //Then
        Assert.assertEquals(PointStatus.USED, pointsRepository.getOne(pointsToUse.getId()).getPointStatus());

    }

    @Test
    public void getAllUserPointsTest() {
        //Given
        User user = new User("Laura", 353522524, "rfrgegreg@gmailo.com", "Kun65(876fd");
        User savedUser = userService.saveUser(user);

        Points points1 = new Points(70, savedUser);
        Points points2 = new Points(600, savedUser);
        Points pointsToGet1 = pointsService.issuePointsToUser(points1);
        Points pointsToGet2 = pointsService.issuePointsToUser(points2);

        //When

        long result = pointsService.getAllUserPoints(savedUser.getUuid());

        //Then
        Assert.assertEquals(670L, result);

    }


}
