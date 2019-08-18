package com.rest.auxilium.service;

import com.rest.auxilium.domain.User;
import com.rest.auxilium.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

;

@RequiredArgsConstructor
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*!()])(?=\\S+$).{7,25}$";

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private Pattern pattern;
    private Matcher matcher;

    @Transactional
    public User saveUser(User user) {
        User savedUser = null;
        if (isEmailValid(user.getEmail()) && isPasswordCorrect(user.getPassword())) {
            String uuid = UUID.randomUUID().toString();
            user.setUuid(uuid);
            savedUser = userRepository.save(user);
        }
        return savedUser;
    }

    private boolean isEmailValid(String email) {
        boolean isEmailValid = false;
        if (EmailValidator.getInstance().isValid(email)) {
            isEmailValid = true;
            LOGGER.info("Email is valid");
        } else {
            LOGGER.error("Invalid email address");
        }
        return isEmailValid;
    }

    private boolean isPasswordCorrect(String password) {
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        if(matcher.matches()){
            LOGGER.info("Password correct");
        } else {
            LOGGER.error("Weak password. Your password must contain 8-25 characters, at least 1 lower case letter, upper case letter, digit and special character");
        }
        return matcher.matches();

    }

    public boolean loginUser(String email, String password) {
        boolean ifLoginSuccessful = false;
        if (userRepository.findFirstByEmail(email) != null) {
            User foundUser = userRepository.findFirstByEmail(email);
            LOGGER.info("User found");
            if (foundUser.getPassword().equals(password)) {
                ifLoginSuccessful = true;
                LOGGER.info("Password correct");
            }
        } else {
            LOGGER.error("Login failed. User does not exist or password is incorrect");
        }
        return ifLoginSuccessful;
    }

    public User changeEmail(User user){
        User userToUpdate = userRepository.findFirstByUuid(user.getUuid());
        userToUpdate.setEmail(user.getEmail());
        return userRepository.save(userToUpdate);
    }

    public User changeName(User user){
        User userToUpdate = userRepository.findFirstByUuid(user.getUuid());
        userToUpdate.setName(user.getName());
        return userRepository.save(userToUpdate);
    }

    public User changePassword(User user){
        User userToUpdate = userRepository.findFirstByUuid(user.getUuid());
        if(isPasswordCorrect(user.getPassword())){
            userToUpdate.setPassword(user.getPassword());
        }
        return userRepository.save(userToUpdate);
    }

    public User changePhone(User user){
        User userToUpdate = userRepository.findFirstByUuid(user.getUuid());
        userToUpdate.setPhone(user.getPhone());
        return userRepository.save(userToUpdate);
    }

    public User findUserByLoginData(String email, String password){
        User foundUser = userRepository.findFirstByEmailAndPassword(email, password);
        return foundUser;
    }

}
