package com.rest.auxilium.service;

import com.rest.auxilium.domain.User;
import com.rest.auxilium.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveUser() {
        //Given
        User user = new User("Kamil", 872738292, "mammanjud@gmailo.com", "Fjsid876%");

        //When
        int usersNumber = userRepository.findAll().size();
        userService.saveUser(user);

        //Then
        Assert.assertEquals(++usersNumber, userRepository.findAll().size());

    }

    @Test
    public void shouldNotSaveUserPassword() {
        //Given
        User user = new User("Kamil", 872738292, "mammanjud@gmailo.com", "abcdef");

        //When
        int usersNumber = userRepository.findAll().size();
        userService.saveUser(user);

        //Then
        Assert.assertEquals(usersNumber, userRepository.findAll().size());

    }

    @Test
    public void shouldNotSaveUserEmail() {
        //Given
        User user = new User("Kamil", 872738292, "mammanjudgmailo.com", "Fjsid876%");

        //When
        int usersNumber = userRepository.findAll().size();
        userService.saveUser(user);

        //Then
        Assert.assertEquals(usersNumber, userRepository.findAll().size());

    }

    @Test
    public void shouldNotLoginUserEmail() {
        //Given

        //When
        boolean result = userService.loginUser("nnahaha@k2.pl", "Kdbdbdbuu0^");

        //Then
        Assert.assertEquals(result, false);

    }
    @Test
    public void shouldNotLoginUserPassword() {
        //Given
        User user = new User("Ewa", 72635362, "gafsss@jk.com", ")(736Kn");
        userService.saveUser(user);
        //When
        boolean result = userService.loginUser("gafsss.com", ")(736Kn");

        //Then
        Assert.assertEquals(result, false);

    }

    @Test
    public void shouldLoginUser() {
        //Given
        User user = new User("Malwina", 72527893, "thiananm@o2.pl", "Lk76m+09l");
        userService.saveUser(user);
        //When
        boolean result = userService.loginUser("thiananm@o2.pl", "Lk76m+09l");

        //Then
        Assert.assertEquals(result, true);

    }

    @Test
    public void changeEmailTest() {
        //Given
        User user = new User("Zenek", 826351733, "hagsgdv@o2.pl", "Lk76m+09l");
        String uuid = userService.saveUser(user).getUuid();

        User user1 = new User("Zenek", 826351733, "anakaisdu@o2.pl", "Lk76m+09l");
        user1.setUuid(user.getUuid());
        //When
        userService.changeData(user1);

        //Then
        Assert.assertEquals("anakaisdu@o2.pl", userRepository.findFirstByUuid(uuid).getEmail());

    }

    @Test
    public void changeNameTest() {
        //Given
        User user = new User("Zenek", 826351733, "hagsgdv@o2.pl", "Lk76m+09l");
        String uuid = userService.saveUser(user).getUuid();

        User user1 = new User("Agata", 826351733, "anakaisdu@o2.pl", "Lk76m+09l");
        user1.setUuid(user.getUuid());
        //When
        userService.changeData(user1);

        //Then
        Assert.assertEquals("Agata", userRepository.findFirstByUuid(uuid).getName());

    }

    @Test
    public void changePhoneTest() {
        //Given
        User user = new User("Zenek", 826351733, "hagsgdv@o2.pl", "Lk76m+09l");
        String uuid = userService.saveUser(user).getUuid();

        User user1 = new User("Agata", 111111111, "hagsgdv@o2.pl", "Lk76m+09l");
        user1.setUuid(user.getUuid());
        //When
        userService.changeData(user1);

        //Then
        Assert.assertEquals(111111111, userRepository.findFirstByUuid(uuid).getPhone());

    }

    @Test
    public void changePasswordTest() {
        //Given
        User user = new User("Zenek", 826351733, "hagsgdv@o2.pl", "Lk76m+09l");
        String uuid = userService.saveUser(user).getUuid();

        User user1 = new User("Agata", 826351733, "hagsgdv@o2.pl", "AbAd567*");
        user1.setUuid(user.getUuid());
        //When
        userService.changeData(user1);

        //Then
        Assert.assertEquals("AbAd567*", userRepository.findFirstByUuid(uuid).getPassword());

    }

    @Test
    public void foundUserByLoginDataTest() {
        //Given
        User user = new User("Eryka", 82749824, "dfdvfvdgv@o2.pl", "LmhbRwsj+342");
        userService.saveUser(user);
        //When
        User foundUser = userService.findUserByLoginData("dfdvfvdgv@o2.pl", "LmhbRwsj+342");

        //Then
        Assert.assertNotEquals(foundUser, null);
        Assert.assertEquals(foundUser.getEmail(), "dfdvfvdgv@o2.pl");
        Assert.assertEquals(foundUser.getPhone(), 82749824);

    }

    @Test
    public void foundUserByUUIDTest() {
        //Given
        User user = new User("Halina", 82543824, "jhhjgfhh@o2.pl", "Kbgcyf978654)");
        String uuid = userService.saveUser(user).getUuid();
        //When
        User foundUser = userService.findUserByUUID(uuid);

        //Then
        Assert.assertNotEquals(foundUser, null);
        Assert.assertEquals(foundUser.getEmail(), "jhhjgfhh@o2.pl");
        Assert.assertEquals(foundUser.getPhone(), 82543824);

    }

    @Test
    public void notifyAboutPointsChangeTest() {
        //Given
        User user = new User("Ala", 726383333, "dcvfvrf@o2.pl", "Lk75#$9278nsd");
        User savedUser = userService.saveUser(user);
        savedUser.setNotifyAboutPoints(true);

        //When
        userService.changeData(savedUser);

        //Then
        Assert.assertEquals(true, userRepository.findFirstByUuid(savedUser.getUuid()).isNotifyAboutPoints());

    }

    @Test
    public void shouldMarkAsRewardedForPointsTest() {
        //Given
        User user = new User("Ala", 726383333, "dcvfvrf@o2.pl", "Lk75#$9278nsd");
        User savedUser = userService.saveUser(user);
        savedUser.setNotifyAboutPoints(true);

        //When
        userService.markAsRewardedForPoints(savedUser.getUuid());

        //Then
        Assert.assertEquals(true, userRepository.findFirstByUuid(savedUser.getUuid()).isRewardedForPoints());

    }

    @Test
    public void shouldMarkAsRewardedForTransactionsTest() {
        //Given
        User user = new User("Ala", 726383333, "dcvfvrf@o2.pl", "Lk75#$9278nsd");
        User savedUser = userService.saveUser(user);
        savedUser.setNotifyAboutPoints(true);

        //When
        userService.markAsRewardedForTransactions(savedUser.getUuid());

        //Then
        Assert.assertEquals(true, userRepository.findFirstByUuid(savedUser.getUuid()).isRewardedForTransactions());

    }





}
