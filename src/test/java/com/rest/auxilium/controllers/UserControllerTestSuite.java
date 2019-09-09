package com.rest.auxilium.controllers;

import com.google.gson.Gson;
import com.rest.auxilium.domain.User;
import com.rest.auxilium.dto.UserDto;
import com.rest.auxilium.service.UserService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void createUserTest() throws Exception {

        //Given
        User user = new User("Kamil", 82738292, "mammanjud@gmailo.com", "Fjsid876%");
        user.setId(3L);
        user.setUuid("jhgjhghghv");
        user.setNotifyAboutPoints(false);

        UserDto userDto = new UserDto("Kamil", 82738292, "mammanjud@gmailo.com", "Fjsid876%");

        when(userService.saveUser(org.mockito.Matchers.any(User.class))).thenReturn(user);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        mockMvc.perform(post("/v1/user").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", Matchers.is(3)))
                .andExpect(jsonPath("$.uuid", Matchers.is("jhgjhghghv")));
    }

    @Test
    public void changeDataTest() throws Exception {

        //Given
        User user = new User("Kamil", 82738292, "mammanjud@gmailo.com", "Fjsid876%");
        user.setId(3L);
        user.setUuid("jhgjhghghv");
        user.setNotifyAboutPoints(false);

        UserDto userDto = new UserDto(3L, "jhgjhghghv", "Kamil", 82738292, "mammanjud@gmailo.com", "Fjsid876%", false);

        User userchangedData = new User("Mamddndnnd", 11111111, "aaaaaaa@gmailo.com", "Fjsid876%");
        user.setId(3L);
        user.setUuid("jhgjhghghv");
        user.setNotifyAboutPoints(false);

        when(userService.changeData(org.mockito.Matchers.any(User.class))).thenReturn(userchangedData);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        mockMvc.perform(put("/v1/user").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name", Matchers.is("Mamddndnnd")))
                .andExpect(jsonPath("$.email", Matchers.is("aaaaaaa@gmailo.com")));
    }

    @Test
    public void userLoginTest() throws Exception {

        //Given
        String email = "aaaaaaa@gmailo.com";
        String password = "Fjsid876%";

        when(userService.loginUser(email, password)).thenReturn(true);

        //When & Then
        mockMvc.perform(get("/v1//user/login").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("email", email)
                .param("password", password))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", Matchers.is(true)));
    }

    @Test
    public void getUserByEmailAndPasswordTest() throws Exception {

        //Given
        User user = new User("Kamil", 82738292, "mammanjud@gmailo.com", "Fjsid876%");
        user.setId(3L);
        user.setUuid("jhgjhghghv");
        user.setNotifyAboutPoints(false);

        String email = "aaaaaaa@gmailo.com";
        String password = "Fjsid876%";

        when(userService.findUserByLoginData(email, password)).thenReturn(user);

        //When & Then
        mockMvc.perform(get("/v1/user").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("email", email)
                .param("password", password))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name", Matchers.is("Kamil")));
    }

    @Test
    public void getUserByUUIDTest() throws Exception {

        //Given
        User user = new User("Kamil", 82738292, "mammanjud@gmailo.com", "Fjsid876%");
        user.setId(3L);
        user.setUuid("jhgjhghghv");
        user.setNotifyAboutPoints(false);

        String uuid = "hdhdhhjsbkbaLWODO";

        when(userService.findUserByUUID(uuid)).thenReturn(user);

        //When & Then
        mockMvc.perform(get("/v1/user/" + uuid).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.email", Matchers.is("mammanjud@gmailo.com")));
    }
}