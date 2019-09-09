package com.rest.auxilium.controllers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rest.auxilium.adapters.LocalDateAdapter;
import com.rest.auxilium.domain.Event;
import com.rest.auxilium.dto.EventDto;
import com.rest.auxilium.service.EventService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EventController.class)
public class EventControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    public void getAvailableEventsTest() throws Exception{

        //Given
        Event event1 = new Event("id1", "test product1", "test url1", "test image1", LocalDate.now()
                ,  "test segment", "test subsegment", 176);
        Event event2 = new Event("id2", "test product2", "test url2", "test image2", LocalDate.now()
                ,  "test segment", "test subsegment", 276);
        List<Event> eventList = new ArrayList<>();
        eventList.add(event1);
        eventList.add(event2);
        when(eventService.fetchEvents()).thenReturn(eventList);

        //When & Then
        mockMvc.perform(get("/v1/events").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].image", Matchers.is("test image1")));
    }

    @Test
    public void collectEventTest() throws Exception{

        //Given
        Event event1 = new Event("id1", "test product1", "test url1", "test image1", LocalDate.now()
                ,  "test segment", "test subsegment", 176);

        EventDto eventDto1 = new EventDto("id1", "test product1", "test url1", "test image1", LocalDate.now()
                ,  "test segment", "test subsegment", 176);
        String uuid = "jgekbckrjebckrhje";
        when(eventService.collectEvent(event1, uuid)).thenReturn(event1);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(eventDto1);

        //When & Then
        mockMvc.perform(post("/v1/events/" + uuid).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200));
    }
}
