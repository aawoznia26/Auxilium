package com.rest.auxilium.controllers;


import com.rest.auxilium.domain.User;
import com.rest.auxilium.dto.UserDto;
import com.rest.auxilium.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public UserDto createUser(@RequestBody UserDto userDto) { return User.mapToUserDto(userService.saveUser(UserDto.mapToUser(userDto))); }

    @RequestMapping(method = RequestMethod.PUT, value = "/user")
    public UserDto changeData(@RequestBody UserDto userDto) { return User.mapToUserDto(userService.changeData(UserDto.mapToUser(userDto))); }

    @RequestMapping(method = RequestMethod.GET, value = "/user/login")
    public boolean login(@RequestParam String password, @RequestParam String email) { return (userService.loginUser(email, password)); }

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public UserDto getUserByEmailAndPassword(@RequestParam String password, @RequestParam String email) {
        return User.mapToUserDto(userService.findUserByLoginData(email, password)); }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{uuid}")
    public UserDto getUserByUUID(@PathVariable String uuid) {
        return User.mapToUserDto(userService.findUserByUUID(uuid)); }
}


