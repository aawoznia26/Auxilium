package com.rest.auxilium.service;


import com.rest.auxilium.domain.Event;
import com.rest.auxilium.domain.Points;
import com.rest.auxilium.domain.User;
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
public class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private PointsService pointsService;

    @Test
    public void shouldFetchEvents() {

        //Given

        //When
        List<Event> eventList = eventService.fetchEvents();

        //Then
        Assert.assertNotEquals(0, eventList.size());

    }

    @Test
    public void collectProductTest() {

        //Given
        User user = new User("Mietek", 452736562, "eferfrfr@gmailo.com", "Gnag#nus#64723");
        User savedUser = userService.saveUser(user);

        Points points = new Points(276, savedUser);
        pointsService.issuePointsToUser(points);

        Event event = new Event("id", "test product", "test url", "test image", LocalDate.now()
                ,  "test segment", "test subsegment", 276);
        List<Event> eventList = new ArrayList<>();
        eventList.add(event);
        savedUser.setEvent(eventList);

        //When
        Event collectedEvent = eventService.collectEvent(event, savedUser.getUuid());

        //Then
        Assert.assertNotEquals(null, collectedEvent.getUser());
        Assert.assertNotEquals(0, savedUser.getEvent().size());


    }
}
