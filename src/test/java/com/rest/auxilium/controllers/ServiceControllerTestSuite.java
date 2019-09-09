package com.rest.auxilium.controllers;


import com.google.gson.Gson;
import com.rest.auxilium.domain.Services;
import com.rest.auxilium.domain.ServicesTransactionStatus;
import com.rest.auxilium.dto.ServicesDto;
import com.rest.auxilium.service.ServicesService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ServiceController.class)
public class ServiceControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicesService servicesService;

    @Test
    public void createServiceTest() throws Exception{

        //Given
        ServicesDto servicesDto1 = new ServicesDto("test name", "test description", "test city");
        Services services2 = new Services("test name", "test description","test city", 187, ServicesTransactionStatus.PUBLISHED);
        services2.setId(12L);

        when(servicesService.addService(org.mockito.Matchers.any(Services.class))).thenReturn(services2);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(servicesDto1);

        //When & Then
        mockMvc.perform(post("/v1/services").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name", Matchers.is("test name")))
                .andExpect(jsonPath("$.servicesTransactionStatus", Matchers.is("PUBLISHED")));
    }

    @Test
    public void getAllServicesTest() throws Exception{

        //Given
        Services services1 = new Services("test name1", "test description1","test city1"
                , 181, ServicesTransactionStatus.PUBLISHED);
        services1.setId(11L);
        Services services2 = new Services("test name2", "test description2","test city2"
                , 182, ServicesTransactionStatus.ACCEPTED);
        services2.setId(12L);
        List<Services> servicesList = new ArrayList<>();
        servicesList.add(services1);
        servicesList.add(services2);

        when(servicesService.getServicesList()).thenReturn(servicesList);

        //When & Then
        mockMvc.perform(get("/v1/services").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].name", Matchers.is("test name2")))
                .andExpect(jsonPath("$[0].servicesTransactionStatus", Matchers.is("PUBLISHED")));
    }


    @Test
    public void getServicesByCityTest() throws Exception{

        //Given
        Services services1 = new Services("test name1", "test description1","test city1"
                , 181, ServicesTransactionStatus.PUBLISHED);
        services1.setId(11L);
        List<Services> servicesList = new ArrayList<>();
        servicesList.add(services1);

        String cityName = "test city1";

        when(servicesService.getServicesByCity(cityName)).thenReturn(servicesList);

        //When & Then
        mockMvc.perform(get("/v1/services/city/" + cityName).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].city", Matchers.is("test city1")));
    }

    @Test
    public void getServicesByTypeTest() throws Exception{

        //Given
        Services services1 = new Services("test name1", "test description1","test city1"
                , 181, ServicesTransactionStatus.PUBLISHED);
        services1.setId(11L);
        List<Services> servicesList = new ArrayList<>();
        servicesList.add(services1);

        String name = "test name1";

        when(servicesService.getServicesByName(name)).thenReturn(servicesList);

        //When & Then
        mockMvc.perform(get("/v1/services/type/" + name).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("test name1")));
    }

    @Test
    public void getServicesByTypeAndCityTest() throws Exception{

        //Given
        Services services1 = new Services("test name1", "test description1","test city1"
                , 181, ServicesTransactionStatus.PUBLISHED);
        services1.setId(11L);
        List<Services> servicesList = new ArrayList<>();
        servicesList.add(services1);

        String name = "test name1";
        String city = "test city1";

        when(servicesService.getServicesByCityAndName(city, name)).thenReturn(servicesList);

        //When & Then
        mockMvc.perform(get("/v1/services/filter/" + city + "/"+ name).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("test name1")));
    }

    @Test
    public void deleteServiceTest() throws Exception{

        //Given
        Long serviceId = 53L;

        //When & Then
        mockMvc.perform(delete("/v1/services/" + serviceId).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().is(200));
    }
}
