package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.User;

import java.util.List;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oUserRepositoryTest {

    private static Sql2o sql2o;

    private static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class
                .getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void clearUsers() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("TRUNCATE TABLE users;");
            query.executeUpdate();
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var user = sql2oUserRepository.save(new User(
                0, "name", "email", "password")).get();
        var savedUser = sql2oUserRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).get();
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        var user1 = sql2oUserRepository.save(new User(
                0, "name1", "email1", "password1")).get();
        var user2 = sql2oUserRepository.save(new User(
                0, "name2", "email2", "password2")).get();
        var result = sql2oUserRepository.findAll();
        assertThat(result).isEqualTo(List.of(user1, user2));
    }

    @Test
    public void whenSaveDuplicateEmailThenEmpty() {
        var user = sql2oUserRepository.save(new User(
                0, "name", "email", "password")).get();
        assertThat(sql2oUserRepository.save(new User(
                0, user.getName(), "email", "password"
        ))).isEqualTo(empty());
    }

    @Test
    public void whenDontSaveThenNothingFound() {
        assertThat(sql2oUserRepository.findByEmailAndPassword("email", "password")).isEqualTo(empty());
        assertThat(sql2oUserRepository.findAll()).isEqualTo(emptyList());
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        var user = sql2oUserRepository.save(new User(
                0, "name", "email", "password")).get();
        var isDeleted = sql2oUserRepository.deleteById(user.getId());
        var savedUser = sql2oUserRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        assertThat(isDeleted).isTrue();
        assertThat(savedUser).isEqualTo(empty());
    }

    @Test
    public void whenDeleteByInvalidIdThenGetFalse() {
        assertThat(sql2oUserRepository.deleteById(0)).isFalse();
    }
}