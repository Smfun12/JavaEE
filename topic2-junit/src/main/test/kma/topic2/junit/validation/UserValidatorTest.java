package kma.topic2.junit.validation;

import kma.topic2.junit.model.NewUser;
import kma.topic2.junit.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @Mock
    UserRepository repository;
    @InjectMocks
    UserValidator userValidator;

    @Test
    void validateNewUser() {
        userValidator.validateNewUser(
                NewUser.builder()
                        .login("test")
                        .password("test")
                        .build());
    }

    @Test
    void validateUserWithExistedLogin() {
        when(repository.isLoginExists(ArgumentMatchers.anyString())).thenReturn(true);
        Throwable thrown = assertThrows(RuntimeException.class, () -> userValidator.validateNewUser(
                NewUser.builder()
                        .login("test")
                        .password("tes")
                        .build()));
        assertNotNull(thrown.getMessage());

    }

    @ParameterizedTest(name = "login [{0}] password [{1}]")
    @MethodSource("dataProvider")
    void validateUserWithBadPassword(String login, String password) {
        NewUser user = NewUser.builder()
                .login(login)
                .password(password)
                .build();
        assertThatThrownBy(() -> userValidator.validateNewUser(user))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("You have errors in you object");
    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> dataProvider() {
        return Stream.of(
                Arguments.of("test","te"),
                Arguments.of("test","passworlds"));
    }

}