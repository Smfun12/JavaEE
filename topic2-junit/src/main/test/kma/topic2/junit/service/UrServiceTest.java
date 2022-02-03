package kma.topic2.junit.service;

import kma.topic2.junit.exceptions.UserNotFoundException;
import kma.topic2.junit.model.NewUser;
import kma.topic2.junit.repository.UserRepository;
import kma.topic2.junit.validation.UserValidator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UrServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @SpyBean
    UserValidator validator;

    @Test
    void createNewUser() {
        NewUser build = NewUser.builder().login("test123").password("test").build();
        userService.createNewUser(build);
        Mockito.verify(validator).validateNewUser(ArgumentMatchers.any());
        assertThat(userRepository.isLoginExists("test123")).isTrue();

    }

    @Test
    void getUserByLogin() {
        String userLogin = "test";
        assertThatThrownBy(() -> userService.getUserByLogin(userLogin))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("Can't find user by login: " + userLogin);
    }

}