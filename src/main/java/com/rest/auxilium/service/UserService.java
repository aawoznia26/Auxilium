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

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
        LOGGER.info("Preparation to create user " + user.toString());
        if (isEmailValid(user.getEmail()) && isPasswordCorrect(user.getPassword())) {
            String uuid = UUID.randomUUID().toString();
            user.setUuid(uuid);
            user.setNotifyAboutPoints(false);
            savedUser = userRepository.save(user);
            LOGGER.info("User created successfully");
        } else {
            LOGGER.info("System rejected to create user");
        }
        return savedUser;
    }

    private boolean isEmailValid(String email) {
        boolean isEmailValid = false;
        LOGGER.info("Validation of email " + email);
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
        LOGGER.info("Validation of password: " + password);
        if (matcher.matches()) {
            LOGGER.info("Password correct");
        } else {
            LOGGER.error("Weak password. Password must contain 8-25 characters, at least 1 lower case letter, upper case letter, digit and special character");
        }
        return matcher.matches();

    }

    public boolean loginUser(String email, String password) {
        LOGGER.info("Login email: " + email + " login password: " + password);
        boolean ifLoginSuccessful = false;

        if (findUserByLoginData(email, password).getUuid() != null) {
            User foundUser = findUserByLoginData(email, password);
            LOGGER.info("User found uuid:" + foundUser.getUuid());
            if (foundUser.getPassword().equals(password)) {
                ifLoginSuccessful = true;
                LOGGER.info("Password '" + password +"' correct");
            }
        } else {
            LOGGER.error("Login failed. User does not exist or password is incorrect");
        }
        return ifLoginSuccessful;
    }

    public User changeData(User user) {
        LOGGER.info("Preparation to change user data to " + user.toString());
        User userToUpdate = findUserByUUID(user.getUuid());
        LOGGER.info("Found user " + userToUpdate.toString());
        if(userToUpdate != null){
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setName(user.getName());
            if (isPasswordCorrect(user.getPassword())) {
                userToUpdate.setPassword(user.getPassword());
            }
            userToUpdate.setPhone(user.getPhone());
            userToUpdate.setNotifyAboutPoints(user.isNotifyAboutPoints());
            LOGGER.info("Data change successful");
        } else {
            LOGGER.error("User not found. Data change impossible");
        }
        return userRepository.save(userToUpdate);
    }

    public User findUserByLoginData(String email, String password) {
        return Optional.ofNullable(userRepository.findFirstByEmailAndPassword(email, password)).orElse(new User());
    }

    public User findUserByUUID(String uuid) {
        return Optional.ofNullable(userRepository.findFirstByUuid(uuid)).orElse(new User());
    }

    public void markAsRewardedForPoints(String uuid) {

        Optional.ofNullable(findUserByUUID(uuid)).map(user -> {
            LOGGER.info("Mark as rewarded for points, user " + user.toString());
            user.setRewardedForPoints(true);
            return userRepository.save(user);
        }).orElse(new User());

    }

    public void markAsRewardedForTransactions(String uuid) {
        Optional.ofNullable(findUserByUUID(uuid)).map(user -> {
            LOGGER.info("Mark as rewarded for transaction, user " + user.toString());
            user.setRewardedForTransactions(true);
            return userRepository.save(user);
        }).orElse(new User());
    }

}
