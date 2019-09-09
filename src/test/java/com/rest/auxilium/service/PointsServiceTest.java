package com.rest.auxilium.service;

import com.rest.auxilium.domain.PointStatus;
import com.rest.auxilium.domain.Points;
import com.rest.auxilium.domain.User;
import com.rest.auxilium.repository.PointsRepository;
import com.rest.auxilium.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PointsServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointsRepository pointsRepository;

    @Autowired
    private PointsService pointsService;

    @Test
    public void issuePointsToUserTest() {

        //Given
        User user = new User("Kamil", 872738292, "mammanjud@gmailo.com", "Kbggvhx542@3@");
        User savedUser = userService.saveUser(user);
        String uuid = savedUser.getUuid();
        Points points = new Points(70, savedUser);

        //When
        int pointsRecordsNumber = pointsRepository.findAll().size();
        Points issuedPoints = pointsService.issuePointsToUser(points);

        //Then
        Assert.assertEquals(++pointsRecordsNumber, pointsRepository.findAll().size());
        Assert.assertEquals(70, pointsRepository.findAllByUser(user).get(0).getValue());
    }

    @Test
    public void expirePointsTest() {

        //Given
        User user = new User("Adam", 836482973, "fdfsrgfregre@gmailo.com", "Kjbnw7265#$#");
        userService.saveUser(user);

        Points points = new Points(70, user);
        Points pointsToExpire = pointsService.issuePointsToUser(points);
        List<Points> pointsList = new ArrayList<>();
        pointsList.add(pointsToExpire);
        user.setPoints(pointsList);

        //When
        pointsService.expirePoints(LocalDate.now().plusDays(60));

        //Then
        Assert.assertEquals(PointStatus.EXPIRED, user.getPoints().get(0).getPointStatus());

    }

    @Test
    public void usePointsTest() {
        //Given
        User user = new User("Laura", 353522524, "rfrgegreg@gmailo.com", "OLhbx@3$@!c");
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
