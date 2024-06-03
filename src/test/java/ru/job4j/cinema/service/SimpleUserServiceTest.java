package ru.job4j.cinema.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleUserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void initServices() {
        userRepository = mock(UserRepository.class);
        userService = new SimpleUserService(userRepository);
    }

    @Test
    public void whenRegisterUserThenUserSavedAndGetOptionalUser() {
        var user = new User(1, "name", "user@email", "password");
        when(userRepository.save(user)).thenReturn(Optional.of(user));
        var optionalUser = userService.save(user);
        assertThat(optionalUser.get()).isEqualTo(user);
    }

    @Test
    public void whenRequestToRegisterUserThenGetEmptyOptionalUser() {
        var user = new User(1, "name", "user@email", "password");
        when(userRepository.save(user)).thenReturn(Optional.empty());
        var optionalUser = userService.save(user);
        assertThat(optionalUser).isEmpty();
    }

    @Test
    public void whenLoginUserThenGetOptionalUser() {
        var user = new User(1, "name", "test@mail.ru", "password");
        when(userRepository.findByEmailAndPassword(user.getEmail(),
                user.getPassword())).thenReturn(Optional.of(user));
        var optionalUser = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        assertThat(optionalUser.get()).isEqualTo(user);
    }

    @Test
    public void whenLoginUserThenGetEmptyOptionalUser() {
        var user = new User(1, "name", "test@mail.ru", "password");
        when(userRepository.findByEmailAndPassword(user.getEmail(),
                user.getPassword())).thenReturn(Optional.empty());
        var optionalUser = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        assertThat(optionalUser).isEmpty();
    }
}