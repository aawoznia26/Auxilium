package com.rest.auxilium.controllers;


import com.rest.auxilium.service.PointsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PointsController.class)
public class PointsControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PointsService pointsService;

    @Test
    public void getUserAvailablePointsTest() throws Exception{

        //Given
        String uuid = "8278kbiewedwe";
        when(pointsService.getAllUserPoints(uuid)).thenReturn(70L);

        //When & Then
        mockMvc.perform(get("/v1/points/" + uuid).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(70));
    }
}
