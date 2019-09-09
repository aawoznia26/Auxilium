package com.rest.auxilium.controllers;


import com.google.gson.Gson;
import com.rest.auxilium.domain.Services;
import com.rest.auxilium.domain.ServicesTransactionStatus;
import com.rest.auxilium.domain.Transaction;
import com.rest.auxilium.domain.User;
import com.rest.auxilium.dto.ServicesDto;
import com.rest.auxilium.dto.TransactionDto;
import com.rest.auxilium.dto.UserDto;
import com.rest.auxilium.service.TransactionService;
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
@WebMvcTest(TransactionController.class)
public class TransactionControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void createTransactionTest() throws Exception{

        //Given
        ServicesDto servicesDto = new ServicesDto(11L, "test name1", "test description1", 166, "test city1"
                , ServicesTransactionStatus.PUBLISHED);
        UserDto userDto = new UserDto(13L, "ggsdghs", "Kamil", 82738292, "mammanjud@gmailo.com", "Fjsid876%", false);
        TransactionDto transactionDto = new TransactionDto(userDto, servicesDto);

        User user = new User(  "Kamil", 82738292, "mammanjud@gmailo.com", "Fjsid876%");
        user.setUuid("ggsdghs");
        user.setId(13L);
        user.setNotifyAboutPoints(false);
        Services services = new Services("test name1", "test description1", "test city1", 166, ServicesTransactionStatus.PUBLISHED);
        Transaction transaction = new Transaction(user, services, ServicesTransactionStatus.PUBLISHED);
        transaction.setId(14L);

        when(transactionService.createTransaction(org.mockito.Matchers.any(Transaction.class))).thenReturn(transaction);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(transactionDto);

        //When & Then
        mockMvc.perform(post("/v1/transaction").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.servicesTransactionStatus", Matchers.is("PUBLISHED")));
    }

    @Test
    public void assignTransactionTest() throws Exception{

        //Given
        UserDto userDto = new UserDto(13L, "ggsdghs", "Kamil", 82738292, "mammanjud@gmailo.com", "Fjsid876%", false);
        User user = new User(  "Kamil", 82738292, "mammanjud@gmailo.com", "Fjsid876%");
        user.setUuid("ggsdghs");
        user.setId(13L);
        user.setNotifyAboutPoints(false);

        Services services = new Services("test name1", "test description1", "test city1", 166, ServicesTransactionStatus.PUBLISHED);
        services.setId(15L);
        Transaction transaction = new Transaction(user, services, ServicesTransactionStatus.PUBLISHED);
        transaction.setId(14L);
        transaction.setServiceProvider(user);

        when(transactionService.assignTransaction(org.mockito.Matchers.any(Long.class), org.mockito.Matchers.any(User.class))).thenReturn(transaction);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        mockMvc.perform(put("/v1/transaction/assign/" + 15).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.serviceProviderDto.name", Matchers.is("Kamil")));
    }

    @Test
    public void acceptTransactionTest() throws Exception{

        //Given
        UserDto userDto = new UserDto(13L, "ggsdghs", "Kamil", 82738292, "mammanjud@gmailo.com", "Fjsid876%", false);
        User user = new User(  "Kamil", 82738292, "mammanjud@gmailo.com", "Fjsid876%");
        user.setUuid("ggsdghs");
        user.setId(13L);
        user.setNotifyAboutPoints(false);

        Services services = new Services("test name1", "test description1", "test city1", 166, ServicesTransactionStatus.PUBLISHED);
        services.setId(15L);
        Transaction transaction = new Transaction(user, services, ServicesTransactionStatus.ACCEPTED);
        transaction.setId(14L);
        transaction.setServiceProvider(user);

        when(transactionService.acceptTransaction(org.mockito.Matchers.any(Long.class))).thenReturn(transaction);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        mockMvc.perform(put("/v1/transaction/accept/" + 15).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.servicesTransactionStatus", Matchers.is("ACCEPTED")));
    }

    @Test
    public void getAllPublishedTransactionsTest() throws Exception{

        //Given
        User user = new User(  "Kamil", 82738292, "mammanjud@gmailo.com", "Fjsid876%");
        user.setUuid("ggsdghs");
        user.setId(13L);
        user.setNotifyAboutPoints(false);

        Services services = new Services("test name1", "test description1", "test city1", 166, ServicesTransactionStatus.PUBLISHED);
        services.setId(15L);
        Transaction transaction = new Transaction(user, services, ServicesTransactionStatus.ACCEPTED);
        transaction.setId(14L);
        transaction.setServiceProvider(user);

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        when(transactionService.getAllPublishedTransactions()).thenReturn(transactionList);

        //When & Then
        mockMvc.perform(get("/v1/transaction").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].ownerDto.name", Matchers.is("Kamil")));
    }


    @Test
    public void getAllTransactionsOwnedByUserTest() throws Exception{

        //Given
        User user = new User(  "Kamil", 82738292, "mammanjud@gmailo.com", "Fjsid876%");
        user.setUuid("ggsdghs");
        user.setId(13L);
        user.setNotifyAboutPoints(false);

        Services services = new Services("test name1", "test description1", "test city1", 166, ServicesTransactionStatus.PUBLISHED);
        services.setId(15L);
        Transaction transaction = new Transaction(user, services, ServicesTransactionStatus.ACCEPTED);
        transaction.setId(14L);
        transaction.setServiceProvider(user);

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        String uuid = "ggsdghs";
        when(transactionService.getAllTransactionsOwnedByUser(uuid)).thenReturn(transactionList);


        //When & Then
        mockMvc.perform(get("/v1/transaction/owned/" + uuid).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].ownerDto.name", Matchers.is("Kamil")));
    }


    @Test
    public void getAllTransactionsServicedByUserTest() throws Exception{

        //Given
        User user = new User(  "Kamil", 82738292, "mammanjud@gmailo.com", "Fjsid876%");
        user.setUuid("ggsdghs");
        user.setId(13L);
        user.setNotifyAboutPoints(false);

        User serviceProvider = new User(  "Bartek", 736364, "hbshhsb@gmailo.com", "Fbdbbd876%");
        user.setUuid("wadwafafaef");
        user.setId(18L);
        user.setNotifyAboutPoints(false);

        Services services = new Services("test name1", "test description1", "test city1", 166, ServicesTransactionStatus.PUBLISHED);
        services.setId(15L);

        Transaction transaction = new Transaction(user, services, ServicesTransactionStatus.ACCEPTED);
        transaction.setId(14L);
        transaction.setServiceProvider(serviceProvider);

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        String uuid = "wadwafafaef";
        when(transactionService.getAllTransactionsServicedByUser(uuid)).thenReturn(transactionList);


        //When & Then
        mockMvc.perform(get("/v1/transaction/serviced/" + uuid).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].serviceProviderDto.name", Matchers.is("Bartek")));
    }

}
